package org.example.smartstocksystem.repository;

import org.example.smartstocksystem.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByStockLessThan(int threshold);
}
