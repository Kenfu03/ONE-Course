package com.alura.jpa.test;

import com.alura.jpa.dao.PedidoDAO;
import com.alura.jpa.dao.ProductoDAO;
import com.alura.jpa.modelo.Pedido;
import com.alura.jpa.modelo.Producto;
import com.alura.jpa.utils.JPAUtils;

import javax.persistence.EntityManager;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.List;

public class RendimientoTest {
  public static void main(String[] args) throws FileNotFoundException {
    LoadRecords.cargarRegistros();

    EntityManager em = JPAUtils.getEntityManager();

    PedidoDAO pedidoDAO = new PedidoDAO(em);
    Pedido pedido = pedidoDAO.consultarPedidoConCliente(3L);

    em.close();

    System.out.println(pedido.getCliente().getNombre());

//    ProductoDAO productoDAO = new ProductoDAO(em);
//    List<Producto> result = productoDAO.consultaPorParametrosAPI("Samsung", new BigDecimal(2500), null);
//
//    result.forEach(producto -> System.out.println(producto.getDescripcion()));
  }
}
