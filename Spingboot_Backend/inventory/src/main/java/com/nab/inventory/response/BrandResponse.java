package com.nab.inventory.response;

import com.nab.inventory.model.Brand;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BrandResponse {
    private String status;
    private String message;
    private String AUTH_TOKEN;
    private List<Brand> oblist;

}
