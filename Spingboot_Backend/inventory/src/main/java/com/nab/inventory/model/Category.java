package com.nab.inventory.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "category")
@Getter
@Setter
public class Category implements Serializable {

    private static final long serialVersionUID = 4265352674204944987L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;


    @Override
    public String toString() {
        return "Category [id=" + id + ", name=" + name + "]";
    }
}
