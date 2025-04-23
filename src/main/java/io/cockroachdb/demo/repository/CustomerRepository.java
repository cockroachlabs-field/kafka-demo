package io.cockroachdb.demo.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import io.cockroachdb.demo.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, UUID>,
        PagingAndSortingRepository<Customer, UUID> {
}
