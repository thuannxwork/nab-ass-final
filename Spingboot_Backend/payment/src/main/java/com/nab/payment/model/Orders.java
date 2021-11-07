package com.nab.payment.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class Orders {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "order_id")
	private int orderId;

	@Column(name = "user_id")
	private Long userId;

	@Column(name = "order_status")
	private String orderStatus;

	@Column(name = "order_date")
	private Date orderDate;

	@Column(name = "total")
	private double total;

	@Column(name = "payment_mode_id")
	private Long paymentModeId;

	@Column(name = "payment_status")
	private String paymentStatus;


	public Orders(int orderId, String orderStatus, Date orderDate, double total) {
		super();
		this.orderId = orderId;
		this.orderStatus = orderStatus;
		this.orderDate = orderDate;
		this.total = total;
	}

	public Orders(int orderId, String orderStatus, Date orderDate, double total, Long userId) {
		super();
		this.orderId = orderId;
		this.orderStatus = orderStatus;
		this.orderDate = orderDate;
		this.total = total;
		this.userId = userId;
	}

	public Orders() {
		super();
	}

}
