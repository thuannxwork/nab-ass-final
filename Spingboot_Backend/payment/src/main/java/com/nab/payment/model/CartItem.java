package com.nab.payment.model;

import com.nab.payment.dto.ProductDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "cart_item")
public class CartItem implements Serializable {

	private static final long serialVersionUID = 4049687597028261161L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "order_id", nullable = true)
	private int orderId;

	@Column(name = "user_id")
	private Long userId;

	@Column(name = "date_added")
	private Date dateAdded;

	private Long quantity;

	private double price;

	@Column(name = "product_id")
	private int productId;

	@Transient
	private ProductDTO product;

	@Override
	public String toString() {
		return "CartItem [id=" + id + ", orderId=" + orderId + ", userId=" + userId + ", dateAdded="
				+ dateAdded + ", quantity=" + quantity + ", price=" + price + ", productId=" + productId + "]";
	}
}
