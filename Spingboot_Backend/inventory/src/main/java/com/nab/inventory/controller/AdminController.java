package com.nab.inventory.controller;

import com.nab.common.constants.ResponseCode;
import com.nab.common.constants.WebConstants;
import com.nab.common.response.ServerResponse;
import com.nab.inventory.response.ProductResponse;
import com.nab.inventory.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(origins = WebConstants.ALLOWED_URL)
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private ProductService productService;


    @PostMapping("/addProduct")
    public ResponseEntity<ProductResponse> addProduct(
            @RequestParam(name = WebConstants.PROD_FILE, required = false) MultipartFile prodImage,
            @RequestParam(name = WebConstants.PROD_DESC) String description,
            @RequestParam(name = WebConstants.PROD_PRICE) String price,
            @RequestParam(name = WebConstants.PROD_NAME) String productName,
            @RequestParam(name = WebConstants.PROD_QUANITY) String quantity,
            @RequestParam(name = WebConstants.PROD_CATEGORY) String categoryId,
            @RequestParam(name = WebConstants.PROD_BRAND) Long brandId,
            @RequestParam(name = WebConstants.PROD_COLOR) String color) {

        ProductResponse resp = new ProductResponse();
        try {
            resp = productService.addProduct(prodImage, description, price, productName, quantity, categoryId, brandId, color);
            return new ResponseEntity<>(resp, HttpStatus.OK);
        } catch (Exception e) {
            resp.setStatus(ResponseCode.BAD_REQUEST_CODE);
            resp.setMessage(ResponseCode.BAD_REQUEST_MESSAGE);
            return new ResponseEntity<>(resp, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PutMapping("/updateProducts")
    public ResponseEntity<ServerResponse> updateProducts(
            @RequestParam(name = WebConstants.PROD_FILE, required = false) MultipartFile prodImage,
            @RequestParam(name = WebConstants.PROD_DESC) String description,
            @RequestParam(name = WebConstants.PROD_PRICE) String price,
            @RequestParam(name = WebConstants.PROD_NAME) String productName,
            @RequestParam(name = WebConstants.PROD_QUANITY) String quantity,
            @RequestParam(name = WebConstants.PROD_ID) String productId) {
        ServerResponse resp = new ServerResponse();
        try {
            resp = productService.updateProducts(prodImage, description, price, productName, quantity, productId);
            return new ResponseEntity<>(resp, HttpStatus.OK);
        } catch (Exception e) {
            resp.setStatus(ResponseCode.BAD_REQUEST_CODE);
            resp.setMessage(ResponseCode.BAD_REQUEST_MESSAGE);
            return new ResponseEntity<>(resp, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @DeleteMapping("/delProduct")
    public ResponseEntity<ProductResponse> delProduct(@RequestParam(name = WebConstants.PROD_ID) String productId) {
        ProductResponse resp = new ProductResponse();
        try {
            resp = productService.delProduct(productId);
            return new ResponseEntity<>(resp, HttpStatus.OK);
        } catch (Exception e) {
            resp.setStatus(ResponseCode.BAD_REQUEST_CODE);
            resp.setMessage(ResponseCode.BAD_REQUEST_MESSAGE);
            return new ResponseEntity<>(resp, HttpStatus.NOT_ACCEPTABLE);
        }
    }
}
