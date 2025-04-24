package io.cockroachdb.demo.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Role;

import io.cockroachdb.demo.aspect.OutboxAspect;
import io.cockroachdb.demo.repository.EventRepository;

@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
public class AspectConfig {
    @Bean
    public OutboxAspect outboxAspect(@Qualifier("outboxJdbcRepository") EventRepository eventRepository) {
        return new OutboxAspect(eventRepository);
    }
}
