package com.alura.jdbc.controller;

import com.alura.jdbc.factory.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductoController {

	public int modificar(String nombre, String descripcion, Integer cantidad, Integer id) throws SQLException {
		Connection con = new ConnectionFactory().getMyConnection();

		PreparedStatement statement = con.prepareStatement("UPDATE producto SET " +
						"nombre = ? , " +
						"descripcion = ? , " +
						"cantidad = ? " +
						"WHERE id = ?");
		statement.setString(1, nombre);
		statement.setString(2, descripcion);
		statement.setInt(3, cantidad);
		statement.setInt(4, id);

		statement.execute();

		int updateCount = statement.getUpdateCount();

		con.close();

		return updateCount;
	}

	public int eliminar(Integer id) throws SQLException {
		Connection con = new ConnectionFactory().getMyConnection();

		PreparedStatement statement = con.prepareStatement("DELETE FROM producto WHERE ID = ?");
		statement.setInt(1, id);

		statement.execute();

		int updateCount = statement.getUpdateCount();

		con.close();

		return updateCount;
	}

	public List<Map<String, String>> listar() throws SQLException {
		Connection con = new ConnectionFactory().getMyConnection();

		PreparedStatement statement = con.prepareStatement("SELECT ID, NOMBRE, DESCRIPCION, CANTIDAD FROM PRODUCTO");

		statement.execute();

		ResultSet resultSet = statement.getResultSet();

		List<Map<String, String>> resultado = new ArrayList<>();

		while(resultSet.next()){
			Map<String, String> fila = new HashMap<>();
			fila.put("ID", String.valueOf(resultSet.getInt("ID")));
			fila.put("NOMBRE", resultSet.getString("NOMBRE"));
			fila.put("DESCRIPCION", resultSet.getString("DESCRIPCION"));
			fila.put("CANTIDAD", String.valueOf(resultSet.getInt("CANTIDAD")));

			resultado.add(fila);
		}

		con.close();

		return resultado;
	}

    public void guardar(Map<String, String> producto) throws SQLException {
			String nombre = producto.get("NOMBRE");
			String descripcion = producto.get("DESCRIPCION");
			Integer cantidad = Integer.valueOf(producto.get("CANTIDAD"));
			Integer cantidadMaxima = 50;
				
		
			Connection con = new ConnectionFactory().getMyConnection();

			PreparedStatement statement = con.prepareStatement("INSERT INTO " +
							"PRODUCTO(nombre, descripcion, cantidad) " +
							"VALUES(?, ?, ?)",
							Statement.RETURN_GENERATED_KEYS);
			do {
				int cantidadToSave = Math.min(cantidad, cantidadMaxima);
				executeGuardar(statement, nombre, descripcion, cantidadToSave);
				cantidad -= cantidadMaxima;
			} while (cantidad > 0);

			con.close();
		}

	private void executeGuardar(PreparedStatement statement, String nombre, String descripcion,
															Integer cantidad) throws SQLException {
		statement.setString(1, nombre);
		statement.setString(2, descripcion);
		statement.setInt(3, cantidad);

		statement.execute();

		ResultSet resultSet = statement.getGeneratedKeys();

		while(resultSet.next()){
			System.out.println("Fue insertado el producto de ID " + resultSet.getInt(1));
		}
	}

}
