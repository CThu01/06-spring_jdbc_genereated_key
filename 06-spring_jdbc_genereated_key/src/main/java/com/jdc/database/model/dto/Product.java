package com.jdc.database.model.dto;

import lombok.Data;

@Data
public class Product {

	private int id;
	private String name;
	private Category category_id;
	private int price;
}
