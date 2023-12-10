package com.jdc.database.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.jdc.database.config.ApplicationConfig;
import com.jdc.database.model.dao.CategoryDao;
import com.jdc.database.model.dto.Category;

@TestMethodOrder(OrderAnnotation.class)
//@SpringJUnitConfig(locations = "/application.xml")
@SpringJUnitConfig(classes = ApplicationConfig.class)
public class TestCategoryDaoWithSpringUsages {

	@Autowired
	private JdbcOperations jdbcOperation;
	
	@Autowired
	private CategoryDao categoryDao;
	
	@Test
	@Order(1)
	void test1() {
		
		List<Object[]> params = new ArrayList<Object[]>();
		params.add(new Object[] {"Food"});
		params.add(new Object[] {"Drink"});
		params.add(new Object[] {"Electronic"});		
		params.add(new Object[] {"Accessory"});
		params.add(new Object[] {"Fashion"});
		
		var size = jdbcOperation.batchUpdate("insert into category(name) values (?)", params);
		assertEquals(5, size.length);
	}
	
	@Test
	@Order(2)
	void test2() {
		var categoryList = jdbcOperation.query("select * from category", new BeanPropertyRowMapper<Category>(Category.class));
		System.out.println(categoryList);
	}
	
	@Test
	@Order(3)
	@Sql(scripts = "/database.sql")
	void test3() {
		var category = new Category();
		category.setName("Food");
		
		var id = categoryDao.create(category);
		assertEquals(1, id);
	}
	
	@Test
	@Order(4)
	@Sql(scripts = "/database.sql")
	void test4() {
		var category = new Category();
		category.setName("Food");
		
		var id = categoryDao.createWithKeyHolder(category);
		assertEquals(1, id);
	}

	@Test
	@Order(5)
	@Sql(scripts = "/database.sql")
	void test5() {
		var category = new Category();
		category.setName("Food");
		
		var id = categoryDao.createWithSimpleInsert(category);
		assertEquals(1, id);
	}
	
	
}









