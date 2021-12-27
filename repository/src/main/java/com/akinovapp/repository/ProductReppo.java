package com.akinovapp.repository;

import com.akinovapp.domain.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductReppo extends JpaRepository<Product, Long> {

    Optional<Product> findByProductName(String productName);
    Optional<Product> findProductByProductNumber(Long productNumber);
    Optional<Product> findProductByProductNameAndCompanyName(String productName, String companyName);
    List<Product> findProductByCompanyName(String companyName);
}
