package com.alura.jdbc.dao;

import com.alura.jdbc.modelo.Categoria;
import com.alura.jdbc.modelo.Producto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAO {

  private Connection con;

  public CategoriaDAO(Connection con) {
    this.con = con;
  }

  public List<Categoria> listar() {
    List<Categoria> result = new ArrayList<>();

    try {
      PreparedStatement statement = con.prepareStatement("SELECT ID, NOMBRE FROM categoria");
      try (statement) {
        ResultSet resultSet = statement.executeQuery();
        try (resultSet) {
          while (resultSet.next()) {
            Categoria cat = new Categoria(resultSet.getInt("ID"),
                    resultSet.getString("NOMBRE"));
            result.add(cat);
          }
        }
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    return result;
  }

  public List<Categoria> listarConProductos() {
    List<Categoria> result = new ArrayList<>();

    try {
      PreparedStatement statement = con.prepareStatement("SELECT C.ID, C.NOMBRE, P.ID, P.NOMBRE, P.CANTIDAD " +
              "FROM categoria C " +
              "INNER JOIN PRODUCTO P ON C.ID = P.CATEGORIA_ID");
      try (statement) {
        ResultSet resultSet = statement.executeQuery();
        try (resultSet) {
          while (resultSet.next()) {
            Integer categoriaId = resultSet.getInt("C.ID");
            String categoriaNombre = resultSet.getString("C.NOMBRE");

            Categoria categoria = result.stream().filter(cat ->
              cat.getId().equals(categoriaId)).findAny().orElseGet(() -> {
                Categoria cat = new Categoria(categoriaId, categoriaNombre);
                result.add(cat);

                return cat;
            });
            var producto = new Producto(resultSet.getInt("P.ID"), resultSet.getString("P.NOMBRE"),
                    resultSet.getInt("P.CANTIDAD"));
            categoria.agregar(producto);

          }
        }
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    return result;
  }
}
