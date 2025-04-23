package io.cockroachdb.demo.config;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import io.cockroachdb.demo.aspect.AdvisorOrder;

@Configuration
@EnableJpaRepositories(basePackages = "io.cockroachdb.demo",
        enableDefaultTransactions = true)
@EnableTransactionManagement(proxyTargetClass = true, order = AdvisorOrder.TRANSACTION_MANAGER_ADVISOR)
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
public class TransactionConfig {
}
