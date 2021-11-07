package com.nab.payment.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
public class CategoryDTO implements Serializable {
    private Long id;
    private String name;
}
