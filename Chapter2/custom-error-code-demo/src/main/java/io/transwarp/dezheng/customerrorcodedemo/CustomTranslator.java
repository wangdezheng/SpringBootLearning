package io.transwarp.dezheng.customerrorcodedemo;

import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.support.SQLExceptionSubclassTranslator;
import org.springframework.lang.Nullable;

import java.sql.SQLException;
import java.util.Arrays;

@Configuration
public class CustomTranslator extends SQLExceptionSubclassTranslator {
  private static final String customExceptionCodes = "23001,23505";

  @Override
  @Nullable
  protected DataAccessException doTranslate(String task, @Nullable String sql, SQLException ex) {
    if (Arrays.asList(customExceptionCodes.split(","))
        .contains(String.valueOf(ex.getErrorCode()))) {
      return new CustomDuplicateKeyException(buildMessage(task, sql, ex), ex);
    }
    return null;
  }
}
