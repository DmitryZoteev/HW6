package com.netcracker.repository;

import com.netcracker.model.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
    List<Purchase> findPurchasesByTotalPriceGreaterThan(double price);
}
