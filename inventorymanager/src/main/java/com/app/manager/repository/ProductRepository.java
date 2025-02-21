package com.app.manager.repository;

import com.app.manager.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// Interface uses JpaRepository to maintain data persistence and handling from database
public interface ProductRepository extends JpaRepository<Product, Integer> {

    // Optional wrapper is used to handle potential null values
    Optional<Product> findByName(String productName);

}
