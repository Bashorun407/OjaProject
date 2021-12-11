package com.akinovapp.repository;

import com.akinovapp.domain.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShopReppo extends JpaRepository<Shop, Long> {

    Optional<Shop> findShopByCompanyName(String companyName);
}
