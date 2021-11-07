package com.nab.inventory.service;

import com.nab.common.constants.ResponseCode;
import com.nab.common.exception.ProductCustomException;
import com.nab.common.response.ServerResponse;
import com.nab.common.util.Validator;
import com.nab.inventory.model.Product;
import com.nab.inventory.repository.ProductRepository;
import com.nab.inventory.response.ProductResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository prodRepo;

    @Override
    public ProductResponse addProduct(MultipartFile prodImage, String description, String price,
                                      String productName, String quantity, String categoryId, Long brandId, String color) {

        if (Validator.isStringEmpty(productName) || Validator.isStringEmpty(description)
                || Validator.isStringEmpty(price) || Validator.isStringEmpty(quantity)) {
            throw new ProductCustomException("Lack required field");
        }

        ProductResponse resp = new ProductResponse();
        try {
            Product prod = new Product();
            prod.setDescription(description);
            prod.setPrice(Double.parseDouble(price));
            prod.setName(productName);
            prod.setQuantity(Integer.parseInt(quantity));
            if (prodImage != null) {
                prod.setImage(prodImage.getBytes());
            }
            prod.setCategoryId(Long.parseLong(categoryId));
            prod.setBrandId(brandId);
            prod.setColor(color);
            prodRepo.save(prod);

            resp.setStatus(ResponseCode.SUCCESS_CODE);
            resp.setMessage(ResponseCode.ADD_SUCCESS_MESSAGE);
            resp.setOblist(prodRepo.findAll());
        } catch (Exception e) {
            throw new ProductCustomException("Unable to save product details, please try again");
        }

        return resp;
    }

    @Override
    public ServerResponse updateProducts(MultipartFile prodImage, String description, String price, String productName,
                                         String quantity, String productId) {

        if (Validator.isStringEmpty(productName) || Validator.isStringEmpty(description)
                || Validator.isStringEmpty(price) || Validator.isStringEmpty(quantity)) {
            throw new ProductCustomException("Lack required field");
        }

        ServerResponse resp = new ServerResponse();
        try {
            if (prodImage != null) {
                Product prod = new Product(Integer.parseInt(productId), description, productName,
                        Double.parseDouble(price), Integer.parseInt(quantity), prodImage.getBytes());
                prodRepo.save(prod);
            } else {
                Product prodOrg = prodRepo.findById(Integer.parseInt(productId));
                Product prod = new Product(Integer.parseInt(productId), description, productName,
                        Double.parseDouble(price), Integer.parseInt(quantity), prodOrg.getImage());
                prodRepo.save(prod);
            }
            resp.setStatus(ResponseCode.SUCCESS_CODE);
            resp.setMessage(ResponseCode.UPD_SUCCESS_MESSAGE);
        } catch (Exception e) {
            throw new ProductCustomException("Unable to update product details, please try again");
        }
        return resp;
    }

    @Override
    public ProductResponse delProduct(String productId) {
        if (Validator.isStringEmpty(productId)) {
            throw new ProductCustomException("productId required");
        }
        ProductResponse resp = new ProductResponse();

        try {
            prodRepo.deleteById(Integer.parseInt(productId));
            resp.setStatus(ResponseCode.SUCCESS_CODE);
            resp.setMessage(ResponseCode.DEL_SUCCESS_MESSAGE);
        } catch (Exception e) {
            throw new ProductCustomException("Unable to delete product details, please try again");
        }
        return resp;
    }

    @Override
    public ProductResponse getAllProducts() {
        ProductResponse resp = new ProductResponse();
        try {
            resp.setStatus(ResponseCode.SUCCESS_CODE);
            resp.setMessage(ResponseCode.LIST_SUCCESS_MESSAGE);
            resp.setOblist(prodRepo.findAll());
        } catch (Exception e) {
            throw new ProductCustomException("Unable to retrieve products, please try again");
        }
        return resp;
    }

    @Override
    public ProductResponse findProducts(Long categoryId, Long brandId, String color, Long fromPrice, Long toPrice) {
        ProductResponse resp = new ProductResponse();
        try {
            resp.setStatus(ResponseCode.SUCCESS_CODE);
            resp.setMessage(ResponseCode.LIST_SUCCESS_MESSAGE);
            resp.setOblist(prodRepo.getList(categoryId, brandId, color, fromPrice, toPrice));
        } catch (Exception e) {
            throw new ProductCustomException("Unable to retrieve products, please try again");
        }
        return resp;
    }

    @Override
    public Product findById(int productId) {
        return prodRepo.findById(productId);
    }
}
