package com.example.tienda.Clase;

import java.io.Serializable;

public class Categoria implements Serializable {
    int codigoCategoria;
    String nombre, detalle;

    public Categoria() {
        codigoCategoria = 0;
        nombre = "";
        detalle = "";
    }

    public Categoria(int codigoCategoria, String nombre, String detalle) {
        this.codigoCategoria = codigoCategoria;
        this.nombre = nombre;
        this.detalle = detalle;
    }

    public int getCodigoCategoria() {
        return codigoCategoria;
    }

    public void setCodigoCategoria(int codigoCategoria) {
        this.codigoCategoria = codigoCategoria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }
}
