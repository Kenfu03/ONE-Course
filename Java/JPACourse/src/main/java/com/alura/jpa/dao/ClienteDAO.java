package com.alura.jpa.dao;

import com.alura.jpa.modelo.Cliente;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

public class ClienteDAO {

  private EntityManager em;

  public ClienteDAO(EntityManager em){
    this.em = em;
  }

  public void guardar(Cliente cliente){
    this.em.persist(cliente);
  }

  public Cliente consultaPorId(Long id){
    return em.find(Cliente.class, id);
  }

  public List<Cliente> consultarTodos(){
    String jpql = "SELECT C FROM Cliente AS C";
    return em.createQuery(jpql, Cliente.class).getResultList();
  }

  public List<Cliente> consultaPorNombre(String nombre){
    String jpql = "SELECT C FROM Cliente AS C WHERE C.nombre=:nombre";
    return  em.createQuery(jpql, Cliente.class).setParameter("nombre", nombre).getResultList();
  }

  public List<Cliente> consultaPorNombreDeCategoria(String nombre){
    String jpql = "SELECT C FROM Cliente AS C WHERE C.categoria_id.nombre=:nombre";
    return em.createQuery(jpql,Cliente.class).setParameter("nombre",nombre).getResultList();
  }

  public BigDecimal consultarPrecioPorNombre(String nombre){
    String jpql = "SELECT C.precio FROM Cliente AS C WHERE C.nombre=:nombre";
    return em.createQuery(jpql, BigDecimal.class).setParameter("nombre", nombre).getSingleResult();
  }
}
