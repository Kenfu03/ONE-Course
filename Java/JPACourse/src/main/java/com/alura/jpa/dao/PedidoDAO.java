package com.alura.jpa.dao;

import com.alura.jpa.modelo.Pedido;
import com.alura.jpa.vo.RelatorioDeVenta;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

public class PedidoDAO {

  private EntityManager em;

  public PedidoDAO(EntityManager em){
    this.em = em;
  }

  public void guardar(Pedido pedido){
    this.em.persist(pedido);
  }

  public Pedido consultaPorId(Long id){
    return em.find(Pedido.class, id);
  }

  public List<Pedido> consultarTodos(){
    String jpql = "SELECT P FROM Pedido AS P";
    return em.createQuery(jpql, Pedido.class).getResultList();
  }

  public BigDecimal valorTotalVendido() {
    String jpql = "SELECT SUM(p.valorTotal) FROM Pedido p";
    return em.createQuery(jpql, BigDecimal.class).getSingleResult();
  }

  public List<Object[]> relatorioDeVentas(){
    String jpql = "SELECT producto.nombre, " +
            "SUM(item.cantidad), " +
            "MAX(pedido.fecha) " +
            "FROM Pedido pedido " +
            "JOIN pedido.items item " +
            "JOIN item.producto producto " +
            "GROUP BY producto.nombre " +
            "ORDER BY item.cantidad DESC";
    return em.createQuery(jpql,Object[].class).getResultList();
  }

  public List<RelatorioDeVenta> relatorioDeVentasVO(){
    String jpql = "SELECT new com.alura.jpa.vo.RelatorioDeVenta(producto.nombre, " +
            "SUM(item.cantidad), " +
            "MAX(pedido.fecha)) " +
            "FROM Pedido pedido " +
            "JOIN pedido.items item " +
            "JOIN item.producto producto " +
            "GROUP BY producto.nombre " +
            "ORDER BY item.cantidad DESC";
    return em.createQuery(jpql, RelatorioDeVenta.class).getResultList();
  }

  public Pedido consultarPedidoConCliente(Long id) {
    String jpql = "SELECT p FROM Pedido p JOIN FETCH p.cliente WHERE p.id=:id";
    return em.createQuery(jpql, Pedido.class).setParameter("id", id).getSingleResult();
  }
}
