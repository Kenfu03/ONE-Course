package com.alura.jpa.modelo;

import javax.persistence.Entity;

@Entity
public class Electronico extends Producto{

  private String modelo;
  private String marca;

  public Electronico() {
  }

  public Electronico(String modelo, String marca) {
    this.modelo = modelo;
    this.marca = marca;
  }

  public String getModelo() {
    return modelo;
  }

  public void setModelo(String modelo) {
    this.modelo = modelo;
  }

  public String getMarca() {
    return marca;
  }

  public void setMarca(String marca) {
    this.marca = marca;
  }
}
