package io.cockroachdb.demo.service;

import io.cockroachdb.demo.model.PurchaseOrder;

public interface OrderService {
    PurchaseOrder placeOrder(PurchaseOrder order);
}
