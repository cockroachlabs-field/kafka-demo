package io.cockroachdb.demo;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import io.cockroachdb.demo.model.Customer;
import io.cockroachdb.demo.model.Product;
import io.cockroachdb.demo.model.PurchaseOrder;
import io.cockroachdb.demo.repository.CustomerRepository;
import io.cockroachdb.demo.repository.EventRepository;
import io.cockroachdb.demo.repository.ProductRepository;

@ActiveProfiles({"verbose"})
@SpringBootTest(classes = {DemoApplication.class})
public class InboxPatternTest extends AbstractIntegrationTest {
    @Autowired
    @Qualifier("inboxJdbcRepository")
    private EventRepository inboxRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Order(1)
    @Test
    public void whenWritingPurchaseOrderToInboxTable_thenExpectChangeFeedEvent() {
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

        inboxRepository.writeEvent(purchaseOrder, "purchase_order");
    }
}
