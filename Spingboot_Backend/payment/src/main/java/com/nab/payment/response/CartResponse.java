package com.nab.payment.response;

import com.nab.payment.model.CartItem;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CartResponse {
	private String status;
	private String message;
	private String AUTH_TOKEN;
	private List<CartItem> oblist;
}
