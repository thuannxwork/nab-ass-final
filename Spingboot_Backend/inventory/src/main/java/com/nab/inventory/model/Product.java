package com.nab.inventory.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Product")
@Getter
@Setter
public class Product implements Serializable {

	private static final long serialVersionUID = -7446162716367847201L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String description;

	private String name;

	private double price;

	private int quantity;

	@Lob
	private byte[] image;

	@Column(name = "category_id")
	private long categoryId;

	@ManyToOne
	@JoinColumn(name = "category_id", referencedColumnName = "id", insertable = false, updatable = false)
	private Category category;

	@Column(name = "brand_id")
	private long brandId;

	@ManyToOne
	@JoinColumn(name = "brand_id", referencedColumnName = "id", insertable = false, updatable = false)
	private Brand brand;

	private String color;

	@Column(name = "user_id")
	private Long userId;

	public Product() {
		super();
	}

	public Product(int id, String description, String name, double price, int quantity,
				   byte[] image) {
		super();
		this.id = id;
		this.description = description;
		this.name = name;
		this.price = price;
		this.quantity = quantity;
		this.image = image;
	}
}
