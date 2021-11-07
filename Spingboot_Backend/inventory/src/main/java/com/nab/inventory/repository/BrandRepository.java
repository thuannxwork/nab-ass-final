package com.nab.inventory.repository;

import com.nab.inventory.model.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface BrandRepository extends JpaRepository<Brand, Long> {

	List<Brand> findAll();

	List<Brand> findAllByCategoryId(Long categoryId);

	Brand findFirstById(Long id);

}
