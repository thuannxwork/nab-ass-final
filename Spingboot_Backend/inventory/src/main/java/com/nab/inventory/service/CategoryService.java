package com.nab.inventory.service;

import com.nab.inventory.model.Category;
import com.nab.inventory.response.CategoryResponse;

import java.util.List;

public interface CategoryService {

    List<Category> findAll();

    Category findFirstById(Long id);

    CategoryResponse getAllCategory();

}




