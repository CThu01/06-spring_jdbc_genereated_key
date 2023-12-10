package com.jdc.database.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

@Configuration
@ComponentScan(value = "com.jdc.database.model.dao")
@PropertySource(value = "/sql.properties")
public class ApplicationConfig {

	@Bean
	public DataSource dataSource() {
		
		EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
		builder.setName("dataSource");
		builder.setType(EmbeddedDatabaseType.HSQL);
		builder.addScript("classpath:/database.sql");
		return builder.build();
	}
	
	@Bean
	public JdbcOperations template(DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}
	
	@Bean
	public NamedParameterJdbcOperations nameTemplte(DataSource dataSource) {
		return new NamedParameterJdbcTemplate(dataSource);
	}

	@Bean
	public SimpleJdbcInsert insert(DataSource dataSource) {
		var simple = new SimpleJdbcInsert(dataSource);
		simple.setTableName("category");
		simple.setGeneratedKeyName("id");
		
		return simple;
	}


}






