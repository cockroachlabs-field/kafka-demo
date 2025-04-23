package io.cockroachdb.demo;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import io.cockroachdb.demo.model.Address;
import io.cockroachdb.demo.model.Customer;
import io.cockroachdb.demo.model.Product;
import io.cockroachdb.demo.util.RandomData;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
})
@Tag("it-test")
public abstract class AbstractIntegrationTest {
    public static Product newProduct() {
        return Product.builder()
                .withName("CockroachDB Unleashed - First Edition")
                .withPrice(new BigDecimal("150.00").setScale(2, RoundingMode.UNNECESSARY))
                .withSku(RandomData.randomWord(12)) // pseudo-random
                .withInventory(10)
                .build();
    }

    public static Customer newCustomer() {
        String fn = RandomData.randomWord(12);
        String ln = RandomData.randomWord(12);
        String email = RandomData.randomEmail(fn, ln);

        return Customer.builder()
                .withFirstName(fn)
                .withLastName(ln)
                .withEmail(email)
                .withAddress(newAddress())
                .build();
    }

    public static Address newAddress() {
        return Address.builder()
                .withAddress1(RandomData.randomWord(15))
                .withAddress2(RandomData.randomWord(15))
                .withCity(RandomData.randomWord(10))
                .withPostcode(RandomData.randomZipCode())
                .withCountry(RandomData.randomCountry())
                .build();
    }
}

