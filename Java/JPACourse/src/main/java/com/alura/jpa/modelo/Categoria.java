package com.alura.jpa.modelo;

import javax.persistence.*;

@Entity
@Table(name = "categorias")
public class Categoria {

  @EmbeddedId
  private CategoriaId categoriaId;

  public Categoria(){

  }

  public Categoria(String nombre) {
    this.categoriaId = new CategoriaId(nombre, "456");
  }

  public String getNombre() {
    return this.categoriaId.getNombre();
  }

  public void setNombre(String nombre) {
    this.categoriaId.setNombre(nombre);
  }
}
