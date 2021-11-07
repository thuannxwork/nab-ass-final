package com.nab.inventory.controller;

import com.nab.common.constants.WebConstants;
import com.nab.inventory.model.Product;
import com.nab.inventory.response.BrandResponse;
import com.nab.inventory.response.CategoryResponse;
import com.nab.inventory.response.ProductResponse;
import com.nab.inventory.service.BrandService;
import com.nab.inventory.service.CategoryService;
import com.nab.inventory.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@CrossOrigin(origins = WebConstants.ALLOWED_URL)
@RestController
@RequestMapping("/user")
public class UserController {

    private static Logger logger = Logger.getLogger(UserController.class.getName());


    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    BrandService brandService;


    @GetMapping("/getProducts")
    public ResponseEntity<ProductResponse> getProducts() {
        return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
    }


    @GetMapping("/findProducts")
    public ResponseEntity<ProductResponse> findProducts(@RequestParam(name = WebConstants.PROD_CATEGORY) String categoryId,
                                                        @RequestParam(name = WebConstants.PROD_BRAND) String brandId,
                                                        @RequestParam(name = WebConstants.PROD_COLOR) String color,
                                                        @RequestParam(name = WebConstants.PROD_FROM_PRICE) String fromPrice,
                                                        @RequestParam(name = WebConstants.PROD_TO_PRICE) String toPrice) {
        Long inputCateId = StringUtils.isEmpty(categoryId) || categoryId.equals("null") ? null : Long.parseLong(categoryId);
        Long inputBrandId = StringUtils.isEmpty(brandId) || brandId.equals("null") ? null : Long.parseLong(brandId);
        Long inputFromPrice = StringUtils.isEmpty(fromPrice) || fromPrice.equals("null") ? null : Long.parseLong(fromPrice);
        Long inputToPrice = StringUtils.isEmpty(toPrice) || toPrice.equals("null") ? null : Long.parseLong(toPrice);
        String inputColor = StringUtils.isEmpty(color) || color.equals("null") ? null : color.trim();
        return new ResponseEntity<>(productService.findProducts(inputCateId, inputBrandId, inputColor, inputFromPrice, inputToPrice), HttpStatus.OK);
    }

    @GetMapping("/getAllCategory")
    public ResponseEntity<CategoryResponse> getAllCategory() {
        return new ResponseEntity<>(categoryService.getAllCategory(), HttpStatus.OK);
    }

    @GetMapping("/getBrandByCategoryId")
    public ResponseEntity<BrandResponse> getBrandByCategoryId(@RequestParam(WebConstants.PROD_CATEGORY) Long categoryId) {
        return new ResponseEntity<>(brandService.findAllByCategoryId(categoryId), HttpStatus.OK);
    }

    @GetMapping("/getProductById")
    public Product getProductById(@RequestParam(WebConstants.PROD_ID) int productId) {
        return productService.findById(productId);
    }
}
