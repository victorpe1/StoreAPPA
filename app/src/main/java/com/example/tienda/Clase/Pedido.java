package com.example.tienda.Clase;

import java.io.Serializable;

public class Pedido  implements Serializable {

    String pago, fecha;
    int cod_prod, cod_venta, cantidad;
    String producto, categoria, DNI;
    Double precio, subprecio, total;

    public Pedido() {
    }

    public Pedido(String pago, String fecha, int cod_prod, int cod_venta, int cantidad, String producto, String categoria, String DNI, Double precio, Double subprecio, Double total) {
        this.pago = pago;
        this.fecha = fecha;
        this.cod_prod = cod_prod;
        this.cod_venta = cod_venta;
        this.cantidad = cantidad;
        this.producto = producto;
        this.categoria = categoria;
        this.DNI = DNI;
        this.precio = precio;
        this.subprecio = subprecio;
        this.total = total;
    }

    public String getPago() {
        return pago;
    }

    public void setPago(String pago) {
        this.pago = pago;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getCod_prod() {
        return cod_prod;
    }

    public void setCod_prod(int cod_prod) {
        this.cod_prod = cod_prod;
    }

    public int getCod_venta() {
        return cod_venta;
    }

    public void setCod_venta(int cod_venta) {
        this.cod_venta = cod_venta;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDNI() {
        return DNI;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Double getSubprecio() {
        return subprecio;
    }

    public void setSubprecio(Double subprecio) {
        this.subprecio = subprecio;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}
