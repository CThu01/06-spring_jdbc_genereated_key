package com.jdc.database.model.dao;

import java.util.HashMap;
import java.util.List;

import javax.swing.tree.RowMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import com.jdc.database.model.dto.Product;

@Repository
public class ProductDao {

	@Autowired
	private NamedParameterJdbcOperations nameJdbcOperation;
	
	@Value("${dml.product.create}")
	private String create;
	
	@Value("${dml.product.update}")
	private String update;
	@Value("${dml.product.delete}")
	private String delete;
	@Value("${dml.product.findById}")
	private String findById;
	@Value("${dml.product.findByCategory}")
	private String findByCategory;
	@Value("${dml.product.search}")
	private String search;
	
	private BeanPropertyRowMapper<Product> rowMapper;
	
	public ProductDao() {
		this.rowMapper = new BeanPropertyRowMapper<Product>(Product.class);
	}
	
	public int create(Product p) {
		var key = new GeneratedKeyHolder();
		var params = new MapSqlParameterSource();
		params.addValue("name", p.getName());
		params.addValue("category_id", p.getCategory_id().getId());
		params.addValue("price", p.getPrice());
		
		nameJdbcOperation.update(create, params, key);
		return key.getKey().intValue();
	}

	public Product findyById(int id) {
		
		var params = new HashMap<String, Object>();
		params.put("id", id);
		return nameJdbcOperation.queryForObject(findById, params, rowMapper);
	}

	public List<Product> findByCategory(int categoryid) {
		var params = new HashMap<String, Object>();
		params.put("category_id", categoryid);
		
		return nameJdbcOperation.query(findById, params, rowMapper);
	}

	public List<Product> search(String keyword) {
		var params = new HashMap<String, Object>();
		params.put("keyword", keyword);
		
		return nameJdbcOperation.query(findById, params, rowMapper);
	}

	public int update(Product product) {
		var params = new HashMap<String, Object>();
		params.put("name", product.getName());
		params.put("price", product.getPrice());
		params.put("id", product.getId());
		
		return nameJdbcOperation.update(update, params);
	}

	public int delete(int id) {
		var params = new HashMap<String, Object>();
		params.put("id", id);
		return nameJdbcOperation.update(delete, params);
	}
	
	
	
}










