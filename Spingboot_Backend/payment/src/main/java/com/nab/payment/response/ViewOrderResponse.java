package com.nab.payment.response;

import com.nab.payment.dto.OrderDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ViewOrderResponse {
	private String status;
	private String message;
	private String AUTH_TOKEN;
	private List<OrderDTO> orderlist = new ArrayList<>();

	@Override
	public String toString() {
		return "viewOrdResp [status=" + status + ", message=" + message + ", AUTH_TOKEN=" + AUTH_TOKEN + ", orderlist="
				+ orderlist + "]";
	}

}
