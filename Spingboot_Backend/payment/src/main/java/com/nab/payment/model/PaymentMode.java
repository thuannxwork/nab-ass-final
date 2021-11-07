package com.nab.payment.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "payment_mode")
@Getter
@Setter
public class PaymentMode implements Serializable {

    private static final long serialVersionUID = 4265352674204944987L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;


    @Override
    public String toString() {
        return "PaymentMode [id=" + id + ", name=" + name + "]";
    }
}
