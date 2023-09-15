package com.alura.jdbc.controller;

import com.alura.jdbc.factory.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductoController {

  public int modificar(String nombre, String descripcion, Integer cantidad, Integer id) throws SQLException {
    final Connection con = new ConnectionFactory().getMyConnection();

    try (con) {
      final PreparedStatement statement = con.prepareStatement("UPDATE producto SET " +
              "nombre = ? , " +
              "descripcion = ? , " +
              "cantidad = ? " +
              "WHERE id = ?");
      try (statement) {
        statement.setString(1, nombre);
        statement.setString(2, descripcion);
        statement.setInt(3, cantidad);
        statement.setInt(4, id);

        statement.execute();

        return statement.getUpdateCount();
      }
    }
  }

  public int eliminar(Integer id) throws SQLException {
    final Connection con = new ConnectionFactory().getMyConnection();

    try (con) {
      final PreparedStatement statement = con.prepareStatement("DELETE FROM producto WHERE ID = ?");

      try (statement) {
        statement.setInt(1, id);

        statement.execute();

        return statement.getUpdateCount();
      }
    }
  }

  public List<Map<String, String>> listar() throws SQLException {
    final Connection con = new ConnectionFactory().getMyConnection();

    try (con) {
      final PreparedStatement statement = con.prepareStatement("SELECT ID, NOMBRE, DESCRIPCION, CANTIDAD FROM PRODUCTO");

      try (statement) {
        statement.execute();

        ResultSet resultSet = statement.getResultSet();

        List<Map<String, String>> resultado = new ArrayList<>();

        while (resultSet.next()) {
          Map<String, String> fila = new HashMap<>();
          fila.put("ID", String.valueOf(resultSet.getInt("ID")));
          fila.put("NOMBRE", resultSet.getString("NOMBRE"));
          fila.put("DESCRIPCION", resultSet.getString("DESCRIPCION"));
          fila.put("CANTIDAD", String.valueOf(resultSet.getInt("CANTIDAD")));

          resultado.add(fila);
        }
        return resultado;
      }
    }
  }

  public void guardar(Map<String, String> producto) throws SQLException {
    String nombre = producto.get("NOMBRE");
    String descripcion = producto.get("DESCRIPCION");
    Integer cantidad = Integer.valueOf(producto.get("CANTIDAD"));
    Integer cantidadMaxima = 50;


    final Connection con = new ConnectionFactory().getMyConnection();
    try (con) {
      con.setAutoCommit(false);

      final PreparedStatement statement = con.prepareStatement("INSERT INTO " +
                      "PRODUCTO(nombre, descripcion, cantidad) " +
                      "VALUES(?, ?, ?)",
              Statement.RETURN_GENERATED_KEYS);

      try (statement) {
        do {
          int cantidadToSave = Math.min(cantidad, cantidadMaxima);
          executeGuardar(statement, nombre, descripcion, cantidadToSave);
          cantidad -= cantidadMaxima;
        } while (cantidad > 0);

        con.commit();
      } catch (Exception e) {
        con.rollback();
      }
    }
  }

  private void executeGuardar(PreparedStatement statement, String nombre, String descripcion,
                              Integer cantidad) throws SQLException {
    statement.setString(1, nombre);
    statement.setString(2, descripcion);
    statement.setInt(3, cantidad);

    statement.execute();

    final ResultSet resultSet = statement.getGeneratedKeys();

    try (resultSet) {
      while (resultSet.next()) {
        System.out.println("Fue insertado el producto de ID " + resultSet.getInt(1));
      }
    }
  }

}
