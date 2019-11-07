package io.transwarp.dezheng.multidatasourcedemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.annotation.Resource;
import javax.sql.DataSource;

@SpringBootApplication(
//	exclude = {
//			DataSourceAutoConfiguration.class,
//			DataSourceTransactionManagerAutoConfiguration.class,
//			JdbcTemplateAutoConfiguration.class
//	}
	)
@Slf4j
public class MultiDatasourceDemoApplication implements ApplicationRunner {

	public static void main(String[] args) {
		SpringApplication.run(MultiDatasourceDemoApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		log.info("=================================== Springboot Started");
//		fooJdbcTemplate().execute("CREATE TABLE FOO (ID INT, BAR VARCHAR (64));");
//		fooJdbcTemplate().execute("INSERT INTO FOO (ID, BAR) VALUES (1,'foo');");
//		barJdbcTemplate().execute("INSERT INTO FOO (ID, BAR) VALUES (2,'bar');");
		fooJdbcTemplate().queryForList("SELECT * FROM FOO").forEach(row -> log.info(row.toString()));
	}

	@Primary
	@Bean
	@ConfigurationProperties("foo.datasource")
	public DataSourceProperties fooDataSourceProperties() {
		return new DataSourceProperties();
	}

	@Bean
	@ConfigurationProperties("bar.datasource")
	public DataSourceProperties barDataSourceProperties() {
		return new DataSourceProperties();
	}

	@Primary
	@Bean
	public DataSource fooDataSource() {
		DataSourceProperties dataSourceProperties = fooDataSourceProperties();
		log.info("foo datasource: {}", dataSourceProperties.getUrl());
		return dataSourceProperties.initializeDataSourceBuilder().build();
	}

	@Bean
	public DataSource barDataSource() {
		DataSourceProperties dataSourceProperties = barDataSourceProperties();
		log.info("bar datasource: {}", dataSourceProperties.getUrl());
		return dataSourceProperties.initializeDataSourceBuilder().build();
	}

	@Bean
	public JdbcTemplate fooJdbcTemplate() {
		return  new JdbcTemplate(fooDataSource());
	}

	@Bean
	public JdbcTemplate barJdbcTemplate() {
		return  new JdbcTemplate(barDataSource());
	}

	@Bean
	@Resource
	public PlatformTransactionManager fooPlatformTransactionManager(DataSource fooDataSource) {
		return new DataSourceTransactionManager(fooDataSource);
	}

	@Bean
	@Resource
	public PlatformTransactionManager barPlatformTransactionManager(DataSource barDataSource) {
		return new DataSourceTransactionManager(barDataSource);
	}
}
