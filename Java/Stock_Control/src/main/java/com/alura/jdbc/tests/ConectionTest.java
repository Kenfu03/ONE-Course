package com.alura.jdbc.tests;

import com.alura.jdbc.factory.ConnectionFactory;

import java.net.Inet4Address;
import java.sql.Connection;
import java.sql.SQLException;

public class ConectionTest {

    public static void main(String[] args) throws SQLException {
        Connection con = new ConnectionFactory().getMyConnection();

        System.out.println("Close connection");
        con.close();
    }
}
