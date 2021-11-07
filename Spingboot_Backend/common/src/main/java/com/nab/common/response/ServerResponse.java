package com.nab.common.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServerResponse {
	private String status;
	private String message;
	private String authToken;
	private String userType;

}
