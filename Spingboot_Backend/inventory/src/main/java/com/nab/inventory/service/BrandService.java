package com.nab.inventory.service;

import com.nab.inventory.model.Brand;
import com.nab.inventory.response.BrandResponse;

import java.util.List;

public interface BrandService {

    List<Brand> findAll();

    BrandResponse findAllByCategoryId(Long categoryId);

    Brand findFirstById(Long id);

}




