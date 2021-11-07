package com.nab.payment.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ProductDTO implements Serializable {

    private int id;
    private String description;
    private String name;
    private double price;
    private int quantity;
    private byte[] image;
    private long categoryId;
    private CategoryDTO category;
    private long brandId;
    private BrandDTO brand;
    private String color;
    private Long userId;
}
