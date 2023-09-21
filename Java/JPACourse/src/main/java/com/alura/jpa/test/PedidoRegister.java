package com.alura.jpa.test;

import com.alura.jpa.dao.CategoriaDAO;
import com.alura.jpa.dao.ClienteDAO;
import com.alura.jpa.dao.PedidoDAO;
import com.alura.jpa.dao.ProductoDAO;
import com.alura.jpa.modelo.*;
import com.alura.jpa.utils.JPAUtils;

import javax.persistence.EntityManager;
import java.math.BigDecimal;

public class PedidoRegister {
  public static void main(String[] args) {
    registrarProducto();

    EntityManager em = JPAUtils.getEntityManager();
    ProductoDAO productoDAO = new ProductoDAO(em);
    Producto producto = productoDAO.consultaPorId(1L);

    ClienteDAO clienteDAO = new ClienteDAO(em);
    PedidoDAO pedidoDAO = new PedidoDAO(em);

    Cliente cliente = new Cliente("Carlos","123456");
    Pedido pedido = new Pedido(cliente);
    pedido.agregarItems(new ItemsPedido(5,producto, pedido));

    em.getTransaction().begin();
    clienteDAO.guardar(cliente);
    pedidoDAO.guardar(pedido);
    em.getTransaction().commit();
    em.close();
  }

  public static void registrarProducto(){

    Categoria celulares = new Categoria("Celulares");
    Producto celular = new Producto("Samsumg", "Telefono usado",
            new BigDecimal("1000"), celulares);

    EntityManager em = JPAUtils.getEntityManager();

    ProductoDAO productoDAO = new ProductoDAO(em);
    CategoriaDAO categoriaDAO = new CategoriaDAO(em);

    em.getTransaction().begin();

    categoriaDAO.guardar(celulares);
    productoDAO.guardar(celular);

    em.getTransaction().commit();
    em.close();
  }
}
