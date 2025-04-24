package io.cockroachdb.demo;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import io.cockroachdb.demo.model.Customer;
import io.cockroachdb.demo.model.Product;
import io.cockroachdb.demo.model.PurchaseOrder;
import io.cockroachdb.demo.repository.CustomerRepository;
import io.cockroachdb.demo.repository.ProductRepository;
import io.cockroachdb.demo.service.OrderService;

@ActiveProfiles({"verbose"})
@SpringBootTest(classes = {KafkaDemo.class})
public class OutboxPatternTest extends AbstractIntegrationTest {
    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Order(1)
    @Test
    public void whenPlacingSingleOrder_thenExpectChangeFeedOutboxEvent() {
        Product product = newProduct();
        Customer customer = newCustomer();

        productRepository.saveAndFlush(product);
        customerRepository.saveAndFlush(customer);

        PurchaseOrder purchaseOrder = PurchaseOrder.builder()
                .withGeneratedId()
                .withCustomer(customer)
                .andOrderItem()
                .withProductId(product.getId())
                .withProductSku(product.getSku())
                .withUnitPrice(product.getPrice())
                .withQuantity(1)
                .then()
                .build();

        orderService.placeOrder(purchaseOrder);
    }
}
