package io.cockroachdb.demo.repository;

public interface EventRepository {
    void writeEvent(Object event, String aggregateType);
}
