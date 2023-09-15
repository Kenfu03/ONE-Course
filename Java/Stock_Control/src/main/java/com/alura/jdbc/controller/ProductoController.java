package com.alura.jdbc.controller;

import com.alura.jdbc.factory.ConnectionFactory;
import com.alura.jdbc.modelo.Producto;

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

  public void guardar(Producto producto) throws SQLException {

    final Connection con = new ConnectionFactory().getMyConnection();
    try (con) {
      con.setAutoCommit(false);

      final PreparedStatement statement = con.prepareStatement("INSERT INTO " +
                      "PRODUCTO(nombre, descripcion, cantidad) " +
                      "VALUES(?, ?, ?)",
              Statement.RETURN_GENERATED_KEYS);

      try (statement) {
        executeGuardar(statement, producto);

        con.commit();
      } catch (Exception e) {
        con.rollback();
      }
    }
  }

  private void executeGuardar(PreparedStatement statement, Producto producto) throws SQLException {
    statement.setString(1, producto.getNombre());
    statement.setString(2, producto.getDescripcion());
    statement.setInt(3, producto.getCantidad());

    statement.execute();

    final ResultSet resultSet = statement.getGeneratedKeys();

    try (resultSet) {
      while (resultSet.next()) {
        producto.setId(resultSet.getInt(1));
        System.out.println("Fue insertado el producto " + producto.toString());
      }
    }
  }

}
