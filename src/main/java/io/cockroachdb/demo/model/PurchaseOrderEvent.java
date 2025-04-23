package io.cockroachdb.demo.model;

import io.cockroachdb.demo.aspect.OutboxEvent;

public class PurchaseOrderEvent extends OutboxEvent<PurchaseOrder> {
}
