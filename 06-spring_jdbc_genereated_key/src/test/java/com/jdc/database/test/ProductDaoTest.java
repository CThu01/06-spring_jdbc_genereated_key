package com.jdc.database.test;


import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.jdc.database.config.ApplicationConfig;
import com.jdc.database.model.dao.CategoryDao;
import com.jdc.database.model.dao.ProductDao;
import com.jdc.database.model.dto.Product;
import com.jdc.database.model.dto.ProductCategory;

//@SpringJUnitConfig(locations = "/application.xml")
@SpringJUnitConfig(classes = ApplicationConfig.class)
@TestMethodOrder(OrderAnnotation.class)
public class ProductDaoTest {

	@Autowired
	private ProductDao productDao;

	@Autowired
	private CategoryDao categoryDao;
	
	@Test
	@DisplayName("1. create product")
	@Order(1)
	@Sql(statements = {
			"insert into category (name) values ('Food')",
			"insert into category (name) values ('Drink')",
	})
	void test1() {
		var category = categoryDao.findById(1);
		var product = new Product();
		product.setCategory_id(category);
		product.setName("Pizza");
		product.setPrice(8000);
		
		var id = productDao.create(product);
		assertEquals(1, id);
	}
	
	@Test
	@DisplayName("2. find by id")
	@Order(2)
	void test2() {
		
		Product product = productDao.findyById(1);
		assertNotNull(product);
		assertEquals("Pizza", product.getName());
		assertEquals("Food", product.getCategory_id().getName());
		assertEquals(8000, product.getPrice());
		
		assertNull(productDao.findyById(2));
	}
	
	@Test
	@DisplayName("3. find by Category")
	@Order(3)
	void test3() {
		
		List<Product> productList = productDao.findByCategory(1);
		assertNotNull(productList);
		assertEquals(1, productList.size());
		assertTrue(productDao.findByCategory(2).isEmpty());
	}

	@Test
	@DisplayName("4. Search Keyword")
	@Order(4)
	void test4() {
		List<Product> product = productDao.search("Pizza");
		assertEquals(1,product.size());
		assertEquals(1, productDao.search("Food").size());
		assertTrue(productDao.search("Pizzae").isEmpty());
	}
	
	@Test
	@DisplayName("5. update product")
	@Order(5)
	void test5() {
		var product = new Product();
		product.setName("Hambagar");
		product.setPrice(5000);;
		product.setId(1);
		
		int count = productDao.update(product);
		
		var p = productDao.findyById(1);
		assertEquals("Hambagar", p.getName());
		assertEquals(5000, p.getPrice());
	}
	
	@Test
	@DisplayName("6. delete product")
	@Order(6)
	void test6() {
		int count = productDao.delete(1);
		assertEquals(1, count);
		assertNull(productDao.findyById(1));
	}
	

	
	
	
	
	
}












