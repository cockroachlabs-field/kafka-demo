package io.cockroachdb.demo.repository;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import io.cockroachdb.demo.model.PurchaseOrder;
import io.cockroachdb.demo.model.ShipmentStatus;

@Repository
public interface OrderRepository extends JpaRepository<PurchaseOrder, UUID> {
    @Query(value = "select sum(po.totalPrice) from PurchaseOrder po where po.status=:status")
    BigDecimal sumOrderTotal(@Param("status") ShipmentStatus status);

    @Query(value = "select sum(po.total_price) from purchase_order po " +
                   "as of system time follower_read_timestamp() " +
                   "where po.status=?1", nativeQuery = true)
    BigDecimal sumOrderTotalNativeQuery(String status);
}
