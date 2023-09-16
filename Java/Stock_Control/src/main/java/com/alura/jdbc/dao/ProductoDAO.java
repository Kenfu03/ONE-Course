package com.alura.jdbc.dao;

import com.alura.jdbc.modelo.Producto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {

  private Connection con;

  public ProductoDAO(Connection con) {
    this.con = con;
  }

  public void guardar(Producto producto) {
    try {
      PreparedStatement statement = con.prepareStatement("INSERT INTO " +
                      "PRODUCTO(nombre, descripcion, cantidad, categoria_id) " +
                      "VALUES(?, ?, ?, ?)",
              Statement.RETURN_GENERATED_KEYS);

      try (statement) {
        statement.setString(1, producto.getNombre());
        statement.setString(2, producto.getDescripcion());
        statement.setInt(3, producto.getCantidad());
        statement.setInt(4, producto.getCategoriaId());
        statement.execute();

        ResultSet resultSet = statement.getGeneratedKeys();

        try (resultSet) {
          while (resultSet.next()) {
            producto.setId(resultSet.getInt(1));
            System.out.println("Fue insertado el producto " + producto.toString());
          }
        }
      }

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public List<Producto> listar() {
    List<Producto> resultado = new ArrayList<>();
    try {
      final PreparedStatement statement = con.prepareStatement
              ("SELECT ID, NOMBRE, DESCRIPCION, CANTIDAD FROM PRODUCTO");

      try (statement) {
        statement.execute();

        ResultSet resultSet = statement.getResultSet();

        try (resultSet) {

          while (resultSet.next()) {
            Producto fila = new Producto(resultSet.getInt("ID"),
                    resultSet.getString("NOMBRE"),
                    resultSet.getString("DESCRIPCION"),
                    resultSet.getInt("CANTIDAD"));

            resultado.add(fila);
          }
        }
      }
      return resultado;
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public int eliminar(Integer id) {
    try {
      final PreparedStatement statement = con.prepareStatement("DELETE FROM producto WHERE ID = ?");

      try (statement) {
        statement.setInt(1, id);

        statement.execute();

        return statement.getUpdateCount();
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public int modificar(Producto producto) {
    try {
      PreparedStatement statement = con.prepareStatement("UPDATE producto SET " +
              "nombre = ? , " +
              "descripcion = ? , " +
              "cantidad = ? " +
              "WHERE id = ? ");
      try (statement) {
        statement.setString(1, producto.getNombre());
        statement.setString(2, producto.getDescripcion());
        statement.setInt(3, producto.getCantidad());
        statement.setInt(4, producto.getId());

        statement.execute();

        return statement.getUpdateCount();
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public List<Producto> listar(Integer categoriaId) {
    List<Producto> resultado = new ArrayList<>();
    try {
      final PreparedStatement statement = con.prepareStatement
              ("SELECT ID, NOMBRE, DESCRIPCION, CANTIDAD FROM PRODUCTO " +
                      "WHERE CATEGORIA_ID = ? ");

      try (statement) {
        statement.setInt(1, categoriaId);
        statement.execute();

        ResultSet resultSet = statement.getResultSet();

        try (resultSet) {

          while (resultSet.next()) {
            Producto fila = new Producto(resultSet.getInt("ID"),
                    resultSet.getString("NOMBRE"),
                    resultSet.getString("DESCRIPCION"),
                    resultSet.getInt("CANTIDAD"));

            resultado.add(fila);
          }
        }
      }
      return resultado;
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

}
