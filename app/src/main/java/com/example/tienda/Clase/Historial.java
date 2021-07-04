package com.example.tienda.Clase;

import java.io.Serializable;

public class Historial implements Serializable {
    int codVenta, codCli, codDetalle, cantidad, codProd;
    String estado, fecha, DNI, nomProd, nomCat;
    Double total, subtotal, precio_unit;

    public Historial() {
    }

    public Historial(int codVenta, int codCli, int codDetalle, int cantidad, int codProd, String estado,
                     String fecha, String DNI, String nomProd, String nomCat, Double total, Double subtotal,
                     Double precio_unit) {
        this.codVenta = codVenta;
        this.codCli = codCli;
        this.codDetalle = codDetalle;
        this.cantidad = cantidad;
        this.codProd = codProd;
        this.estado = estado;
        this.fecha = fecha;
        this.DNI = DNI;
        this.nomProd = nomProd;
        this.nomCat = nomCat;
        this.total = total;
        this.subtotal = subtotal;
        this.precio_unit = precio_unit;
    }

    public int getCodVenta() {
        return codVenta;
    }

    public void setCodVenta(int codVenta) {
        this.codVenta = codVenta;
    }

    public int getCodCli() {
        return codCli;
    }

    public void setCodCli(int codCli) {
        this.codCli = codCli;
    }

    public int getCodDetalle() {
        return codDetalle;
    }

    public void setCodDetalle(int codDetalle) {
        this.codDetalle = codDetalle;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getCodProd() {
        return codProd;
    }

    public void setCodProd(int codProd) {
        this.codProd = codProd;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getDNI() {
        return DNI;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

    public String getNomProd() {
        return nomProd;
    }

    public void setNomProd(String nomProd) {
        this.nomProd = nomProd;
    }

    public String getNomCat() {
        return nomCat;
    }

    public void setNomCat(String nomCat) {
        this.nomCat = nomCat;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public Double getPrecio_unit() {
        return precio_unit;
    }

    public void setPrecio_unit(Double precio_unit) {
        this.precio_unit = precio_unit;
    }
}
