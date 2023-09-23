package com.alura.jpa.vo;

import java.time.LocalDate;

public class RelatorioDeVenta {

    private String nombre;
    private Long cantidad;
    private LocalDate fechaUltimaVent;

    public RelatorioDeVenta(String nombre, Long cantidad, LocalDate fechaUltimaVent) {
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.fechaUltimaVent = fechaUltimaVent;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getCantidad() {
        return cantidad;
    }

    public void setCantidad(Long cantidad) {
        this.cantidad = cantidad;
    }

    public LocalDate getFechaUltimaVent() {
        return fechaUltimaVent;
    }

    public void setFechaUltimaVent(LocalDate fechaUltimaVent) {
        this.fechaUltimaVent = fechaUltimaVent;
    }

    @Override
    public String toString() {
        return "RelatorioDeVenta{" +
                "nombre='" + nombre + '\'' +
                ", cantidad=" + cantidad +
                ", fechaUltimaVent=" + fechaUltimaVent +
                '}';
    }
}
