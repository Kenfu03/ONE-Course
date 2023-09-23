package com.alura.jpa.test;

import com.alura.jpa.dao.CategoriaDAO;
import com.alura.jpa.dao.ProductoDAO;
import com.alura.jpa.modelo.Categoria;
import com.alura.jpa.modelo.CategoriaId;
import com.alura.jpa.modelo.Producto;
import com.alura.jpa.utils.JPAUtils;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

public class ProductRegister {
  public static void main(String[] args) {
    registrarProducto();

    EntityManager em = JPAUtils.getEntityManager();

    ProductoDAO productoDAO = new ProductoDAO(em);

    Producto producto = productoDAO.consultaPorId(1L);

    Categoria celulares = em.find(Categoria.class, new CategoriaId("Celulares", "456"));
    System.out.println(celulares.getNombre());
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
