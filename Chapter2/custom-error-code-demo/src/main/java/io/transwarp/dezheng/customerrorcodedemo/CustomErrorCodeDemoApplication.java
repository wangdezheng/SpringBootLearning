package io.transwarp.dezheng.customerrorcodedemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@SpringBootApplication
public class CustomErrorCodeDemoApplication {

	@Autowired
	CustomTranslator customTranslator;
	@Autowired
	DataSource dataSource;

	public static void main(String[] args) {
		SpringApplication.run(CustomErrorCodeDemoApplication.class, args);
	}

	@Bean
	public JdbcTemplate myJdbcTemplate() {
		JdbcTemplate jdbcTemplate = new JdbcTemplate();
		jdbcTemplate.setDataSource(dataSource);
		jdbcTemplate.setExceptionTranslator(customTranslator);
		return jdbcTemplate;
	}
}
