package com.alura.jpa.dao;

import com.alura.jpa.modelo.Producto;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

public class ProductoDAO {

  private EntityManager em;

  public ProductoDAO(EntityManager em){
    this.em = em;
  }

  public void guardar(Producto producto){
    this.em.persist(producto);
  }

  public Producto consultaPorId(Long id){
    return em.find(Producto.class, id);
  }

  public List<Producto> consultarTodos(){
    String jpql = "SELECT P FROM Producto AS P";
    return em.createQuery(jpql, Producto.class).getResultList();
  }

  public List<Producto> consultaPorNombre(String nombre){
    String jpql = "SELECT P FROM Producto AS P WHERE P.nombre=:nombre";
    return  em.createQuery(jpql, Producto.class).setParameter("nombre", nombre).getResultList();
  }

  public List<Producto> consultaPorNombreDeCategoria(String nombre){
    String jpql = "SELECT P FROM Producto AS P WHERE P.categoria_id.nombre=:nombre";
    return em.createQuery(jpql,Producto.class).setParameter("nombre",nombre).getResultList();
  }

  public BigDecimal consultarPrecioPorNombre(String nombre){
    String jpql = "SELECT P.precio FROM Producto AS P WHERE P.nombre=:nombre";
    return em.createQuery(jpql, BigDecimal.class).setParameter("nombre", nombre).getSingleResult();
  }
}
