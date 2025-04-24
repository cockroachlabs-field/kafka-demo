package io.cockroachdb.demo.aspect;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.Assert;

import io.cockroachdb.demo.repository.EventRepository;

@Aspect
@Order(OutboxAspect.PRECEDENCE)
public class OutboxAspect {
    public static final int PRECEDENCE = AdvisorOrder.CHANGE_FEED_ADVISOR;

    private final EventRepository eventRepository;

    public OutboxAspect(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    /**
     * Pointcut expression matching all outbox event operations.
     */
    @Pointcut("execution(public * *(..)) && @annotation(outboxPayload)")
    public void anyOutboxEventOperation(OutboxOperation outboxPayload) {
    }

    @AfterReturning(pointcut = "anyOutboxEventOperation(outboxOperation)",
            returning = "returnValue", argNames = "returnValue,outboxOperation")
    public void doAfterOutboxOperation(Object returnValue, OutboxOperation outboxOperation) {
        Assert.isTrue(TransactionSynchronizationManager.isActualTransactionActive(),
                "Expected existing transaction - check advisor @Order");

        eventRepository.writeEvent(returnValue, outboxOperation.aggregateType());
    }
}

