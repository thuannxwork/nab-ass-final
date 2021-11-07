package com.nab.payment.dto;

import com.nab.payment.model.OrderItem;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class OrderDTO {
	private int orderId;
	private String orderBy;
	private String orderStatus;
	private List<OrderItem> products = new ArrayList<>();

}
