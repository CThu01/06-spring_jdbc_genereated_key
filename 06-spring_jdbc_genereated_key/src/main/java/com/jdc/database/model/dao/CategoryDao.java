package com.jdc.database.model.dao;

import java.sql.Statement;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Service;

import com.jdc.database.model.dto.Category;

@Service
public class CategoryDao {

	@Autowired
	private JdbcOperations jdbcOperation;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private SimpleJdbcInsert insert;
	
	@Value("${dml.category.insert}")
	private String insertSql;
	
	@Value("${dml.category.update}")
	private String update;
	@Value("${dml.category.findById}")
	private String findById;
	@Value("${dml.category.findByNameLike}")
	private String findByNameLike;
	@Value("${dml.category.findCountByNameLike}")
	private String findCountByNameLike;
	@Value("${dml.category.deleteById}")
	private String deleteById;
	
	public BeanPropertyRowMapper<Category> rowMapper() {
		return new BeanPropertyRowMapper<Category>(Category.class);
	}
	
	
	public int create(Category c) {
		
		PreparedStatementCreatorFactory factory = new PreparedStatementCreatorFactory(insertSql, Types.VARCHAR);
		factory.setReturnGeneratedKeys(true);
		PreparedStatementCreator creator = factory.newPreparedStatementCreator(List.of(c.getName()));
		
//		PreparedStatementCreator creator = conn -> {
//			var stmt = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
//			stmt.setString(1, c.getName());
//			return stmt;
//		};
		
		PreparedStatementCallback<Integer> callBack = stmt -> {
			var res = stmt.executeUpdate();
			var keys = stmt.getGeneratedKeys();
			while(keys.next()) {
				return keys.getInt(1);
			}
			return 0;
		};
		
		return jdbcOperation.execute(creator, callBack);
	}
	
	public int createWithKeyHolder(Category c) {
		
		PreparedStatementCreatorFactory factory = new PreparedStatementCreatorFactory(insertSql, Types.VARCHAR);
		factory.setReturnGeneratedKeys(true);
		var creator = factory.newPreparedStatementCreator(List.of(c.getName()));
		
		var keyHolder = new GeneratedKeyHolder();
		jdbcOperation.update(creator, keyHolder);
		
		return keyHolder.getKey().intValue();
	}
	
	public int createWithSimpleInsert(Category c) {

//		SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate);
//		insert.setTableName("category");
//		insert.setGeneratedKeyName("id");
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("name", c.getName());
		
		return insert.executeAndReturnKey(params).intValue();
	}

	public Category getCategory() {
		return insert.getJdbcTemplate().query("select * from category", rs -> {
			var c = new Category();
			while(rs.next()) {
				c.setId(rs.getInt(1));
				c.setName(rs.getString(2));
				return c;				
			}
			return null;
		});
	}
	
	public int update(Category category) {
		return insert.getJdbcTemplate().update(update, category.getName(),category.getId());
	}

	public Category findById(int id) {
		return insert.getJdbcTemplate().queryForObject(findById, rowMapper(), id);
	}

	public List<Category> findByNameLike(String name) {
		return insert.getJdbcTemplate().query(findByNameLike, rowMapper(),name.concat("%"));
	}
	
	public int findCountByNameLike(String name) {
		return insert.getJdbcTemplate().queryForObject(findCountByNameLike, Integer.class, name.toLowerCase().concat("%"));
	}

	public int deleteById(int id) {
		return insert.getJdbcTemplate().update(deleteById, id);
	}

	
}








