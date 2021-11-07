package com.nab.payment.response;

import com.nab.payment.model.CartItem;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderResponse {

	private int orderId;

	private List<CartItem> cartItemList;

	@Override
	public String toString() {
		return "orderResp [orderId=" + orderId + ", cartList=" + cartItemList + "]";
	}
}
