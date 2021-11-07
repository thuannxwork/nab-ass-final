package com.nab.payment.model;

import com.nab.payment.dto.ProductDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "order_item")
@Getter
@Setter
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "order_id")
    private int orderId;

    @Column(name = "product_id")
    private Long productId;

    @Transient
    private ProductDTO product;

    private double price;

    private Long quantity;

    private double total;

    public OrderItem() {
        super();
    }

}
