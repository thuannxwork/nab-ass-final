package com.nab.inventory.repository;

import com.nab.inventory.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface ProductRepository extends JpaRepository<Product, Long> {

    Product findById(int id);

    void deleteById(int id);

    List<Product> findAll();

    @Query(value = "SELECT a FROM Product a WHERE 1 = 1 "
            + " AND (:categoryId IS NULL OR a.categoryId = :categoryId) "
            + " AND (:brandId IS NULL OR a.brandId = :brandId) "
            + " AND (:color IS NULL OR a.color = :color) "
            + " AND (:fromPrice IS NULL OR a.price >= :fromPrice) "
            + " AND (:toPrice IS NULL OR a.price <= :fromPrice) "
    )
    List<Product> getList(
            @Param("categoryId") Long categoryId,
            @Param("brandId") Long brandId,
            @Param("color") String color,
            @Param("fromPrice") Long fromPrice,
            @Param("toPrice") Long toPrice);
}
