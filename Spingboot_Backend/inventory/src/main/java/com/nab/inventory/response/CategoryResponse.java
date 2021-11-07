package com.nab.inventory.response;

import com.nab.inventory.model.Category;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CategoryResponse {
	private String status;
	private String message;
	private String AUTH_TOKEN;
	private List<Category> oblist;

}
