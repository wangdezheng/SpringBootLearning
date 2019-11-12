package io.transwarp.dezheng.customerrorcodedemo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomErrorCodeDemoApplicationTests {

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Test( expected = CustomDuplicateKeyException.class)
	public void testCustomExceptionErrorCode() {
		jdbcTemplate.execute("INSERT INTO FOO VALUES (1, 'test')");
		jdbcTemplate.execute("INSERT INTO FOO VALUES (1, 'test1')");
	}

}
