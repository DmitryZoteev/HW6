package com.netcracker.repository;

import com.netcracker.model.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShopRepository extends JpaRepository<Shop, Long> {
    List<Shop> findCustomerByDistrict(String district);
}
