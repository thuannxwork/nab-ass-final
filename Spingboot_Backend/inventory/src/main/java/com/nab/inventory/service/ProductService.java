package com.nab.inventory.service;

import com.nab.common.response.ServerResponse;
import com.nab.inventory.model.Product;
import com.nab.inventory.response.ProductResponse;
import org.springframework.web.multipart.MultipartFile;

public interface ProductService {

    ProductResponse addProduct(MultipartFile prodImage, String description, String price, String productName,
                               String quantity, String categoryId, Long brandId, String color);

    ServerResponse updateProducts(MultipartFile prodImage, String description, String price, String productName,
                                  String quantity, String productId);

    ProductResponse delProduct(String productId);

    ProductResponse getAllProducts();

    ProductResponse findProducts(Long categoryId, Long brandId, String color, Long fromPrice, Long toPrice);

    Product findById(int productId);
}




