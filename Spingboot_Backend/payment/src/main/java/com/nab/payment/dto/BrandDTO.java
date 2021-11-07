package com.nab.payment.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
public class BrandDTO implements Serializable {

    private Long id;
    private String name;
    private long categoryId;
}
