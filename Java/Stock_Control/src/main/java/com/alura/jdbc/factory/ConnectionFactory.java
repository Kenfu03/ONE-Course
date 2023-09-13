package com.alura.jdbc.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
  public Connection getMyConnection() throws SQLException {
    return DriverManager.getConnection("jdbc:mysql://localhost/control_stock?useTimeZone=true&serverTimeZone=UTC",
            "root",
            "1234");
  }
}
