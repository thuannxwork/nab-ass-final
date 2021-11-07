package com.nab.inventory.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "brand")
@Getter
@Setter
public class Brand implements Serializable {

    private static final long serialVersionUID = 4265352674204944987L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Column(name = "category_id")
    private long categoryId;


    @Override
    public String toString() {
        return "Brand [id=" + id + ", name=" + name + ", categoryId=" + categoryId + "]";
    }
}
