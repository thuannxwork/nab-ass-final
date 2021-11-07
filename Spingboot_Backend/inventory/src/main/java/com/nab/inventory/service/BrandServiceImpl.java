package com.nab.inventory.service;

import com.nab.common.constants.ResponseCode;
import com.nab.common.exception.ProductCustomException;
import com.nab.inventory.model.Brand;
import com.nab.inventory.repository.BrandRepository;
import com.nab.inventory.response.BrandResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BrandServiceImpl implements BrandService {

    @Autowired
    BrandRepository brandRepository;

    @Override
    public List<Brand> findAll() {
        return brandRepository.findAll();
    }

    @Override
    public BrandResponse findAllByCategoryId(Long categoryId) {
        BrandResponse resp = new BrandResponse();
        try {
            resp.setStatus(ResponseCode.SUCCESS_CODE);
            resp.setMessage(ResponseCode.LIST_SUCCESS_MESSAGE);
            if (categoryId != null && !categoryId.equals(0L)) {
                resp.setOblist(brandRepository.findAllByCategoryId(categoryId));
            } else {
                resp.setOblist(brandRepository.findAll());
            }
        } catch (Exception e) {
            throw new ProductCustomException("Unable to retrieve products, please try again");
        }
        return resp;
    }

    @Override
    public Brand findFirstById(Long id) {
        return brandRepository.findFirstById(id);
    }
}
