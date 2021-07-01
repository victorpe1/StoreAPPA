package com.example.tienda.Clase;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Producto implements Serializable {
    String nombre, imagen = "default";
    Double costo, precio_unit;
    int stock, cod_pro;
    String categoria;

    public Producto() {
    }

    public Producto(String nombre, String imagen, Double costo, Double precio_unit, int stock, String categoria) {
        this.nombre = nombre;
        this.imagen = imagen;
        this.costo = costo;
        this.precio_unit = precio_unit;
        this.stock = stock;
        this.categoria = categoria;
    }

    public int getCod_pro() {
        return cod_pro;
    }

    public void setCod_pro(int cod_pro) {
        this.cod_pro = cod_pro;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public Double getCosto() {
        return costo;
    }

    public void setCosto(Double costo) {
        this.costo = costo;
    }

    public Double getPrecio_unit() {
        return precio_unit;
    }

    public void setPrecio_unit(Double precio_unit) {
        this.precio_unit = precio_unit;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }


}
