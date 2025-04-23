package io.cockroachdb.demo.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.cockroachdb.demo.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
}
