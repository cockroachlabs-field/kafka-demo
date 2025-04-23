package io.cockroachdb.demo.config;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;
import org.springframework.retry.annotation.EnableRetry;

import io.cockroachdb.demo.aspect.AdvisorOrder;

@Configuration
@EnableRetry(proxyTargetClass = true, order = AdvisorOrder.TRANSACTION_RETRY_ADVISOR)
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
public class TransactionRetryConfig {
    @Bean
    public TransientExceptionClassifier exceptionClassifier() {
        return new TransientExceptionClassifier();
    }
}

