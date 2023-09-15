package com.alura.jdbc.tests;

import com.alura.jdbc.factory.ConnectionFactory;

import java.sql.Connection;
import java.sql.SQLException;

public class TestPoolConnections {
  public static void main(String[] args) throws SQLException {
    ConnectionFactory connectionFactory = new ConnectionFactory();

    for (int i = 0; i < 20; i++){
      Connection con = connectionFactory.getMyConnection();

      System.out.println("Abriendo connection numero " + i);
    }
  }
}
