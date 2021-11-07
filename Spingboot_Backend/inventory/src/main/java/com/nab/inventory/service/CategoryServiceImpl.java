package com.nab.inventory.service;

import com.nab.common.constants.ResponseCode;
import com.nab.common.exception.ProductCustomException;
import com.nab.inventory.model.Category;
import com.nab.inventory.repository.CategoryRepository;
import com.nab.inventory.response.CategoryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category findFirstById(Long id) {
        return categoryRepository.findFirstById(id);
    }

    @Override
    public CategoryResponse getAllCategory() {
        CategoryResponse resp = new CategoryResponse();
        try {
            resp.setStatus(ResponseCode.SUCCESS_CODE);
            resp.setMessage(ResponseCode.LIST_SUCCESS_MESSAGE);
            resp.setOblist(categoryRepository.findAll());
        } catch (Exception e) {
            throw new ProductCustomException("Unable to retrieve products, please try again");
        }
        return resp;
    }
}
