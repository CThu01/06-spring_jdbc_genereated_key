package com.jdc.database.model.dto;

import lombok.Data;

@Data
public class ProductCategory {

	private Product id;
	private String name;
	private Category category_id;
	private int price;
	private int catID;
	private String catName;
}
