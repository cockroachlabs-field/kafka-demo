package io.cockroachdb.demo.service;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.cockroachdb.demo.aspect.OutboxOperation;
import io.cockroachdb.demo.model.Product;
import io.cockroachdb.demo.model.PurchaseOrder;
import io.cockroachdb.demo.model.ShipmentStatus;
import io.cockroachdb.demo.repository.OrderRepository;
import io.cockroachdb.demo.repository.ProductRepository;
import io.cockroachdb.demo.util.AssertUtils;

@Service
public class OutboxOrderService implements OrderService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Override
    @Transactional
    @Retryable(exceptionExpression = "@exceptionClassifier.shouldRetry(#root)",
            maxAttempts = 5,
            backoff = @Backoff(maxDelay = 15_000, multiplier = 1.5))
    @OutboxOperation(aggregateType = "purchase_order")
    public PurchaseOrder placeOrder(PurchaseOrder order) throws BusinessException {
        AssertUtils.assertReadWriteTransaction();

        try {
            // Update product inventories for each line item
            order.getOrderItems().forEach(orderItem -> {
                Product product = productRepository.getReferenceById(
                        Objects.requireNonNull(orderItem.getProduct().getId()));
                product.addInventoryQuantity(-orderItem.getQuantity());
            });

            order.setStatus(ShipmentStatus.placed);
            order.setTotalPrice(order.subTotal());

            orderRepository.saveAndFlush(order); // flush to surface any constraint violations

            return order;
        } catch (DataIntegrityViolationException e) {
            throw new BusinessException("Constraint violation", e);
        }
    }
}
