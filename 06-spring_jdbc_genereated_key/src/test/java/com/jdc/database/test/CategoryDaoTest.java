package com.jdc.database.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
import com.jdc.database.model.dto.Category;

@TestMethodOrder(OrderAnnotation.class)
@SpringJUnitConfig(classes = ApplicationConfig.class)
public class CategoryDaoTest {

	@Autowired
	private CategoryDao categoryDao;
	
	@Test
	@DisplayName("1 create Test")
	@Order(1)
	@Sql(scripts = "/database.sql")
	void createTest() {
		
		var c = new Category();
		c.setName("Food");
		
		var result = categoryDao.create(c);
		assertEquals(1, result);
	}
	
	@Test
	@DisplayName("2 update category")
	@Order(2)
	void test2() {
		var c = new Category();
		c.setId(1);
		c.setName("Drink");
		
		int result = categoryDao.update(c);
		assertEquals(1, result);
	}	
	
	@Test
	@DisplayName("3 find by id")
	@Order(3)
	void test3() {
		
		Category c = categoryDao.findById(1);
		assertEquals("Drink", c.getName());
	}
	
	@Test
	@DisplayName("4 find By name Like")
	@Order(4)
	void test4() {
		
		List<Category> categoryList = categoryDao.findByNameLike("Dri".toLowerCase());
		assertEquals(1, categoryList.size());
	}
	
	@Test
	@DisplayName("5 find count by name like")
	@Order(5)
	void test5() {
		int count = categoryDao.findCountByNameLike("dri");
		assertEquals(1, count);
	}
	
	@Test
	@DisplayName("6 delete By id")
	@Order(6)
	void test6() {
		int result = categoryDao.deleteById(1);
		assertEquals(1, result);
	}
	
	
	
	
}










