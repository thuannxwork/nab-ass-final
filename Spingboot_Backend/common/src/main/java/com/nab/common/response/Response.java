package com.nab.common.response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashMap;

@Getter
@Setter
public class Response implements Serializable {

	private static final long serialVersionUID = 1928909901056236719L;
	private String status;
	private String message;
	private String AUTH_TOKEN;
	private HashMap<String, String> map;

}
