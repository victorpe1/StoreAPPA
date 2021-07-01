package com.example.tienda.Clase;

import java.io.Serializable;

public class Empleado implements Serializable {
    String nombre, imagen = "default", dni, telef, sexo, direcc, fecha_in;
    Double salario;
    int codEmpleado;
    String contra, cargo;

    public Empleado() {
        this.nombre = "";
        this.imagen = "";
        this.dni = "";
        this.telef = "";
        this.sexo = "";
        this.direcc = "";
        this.fecha_in = "";
        this.salario = 0.0;
        this.codEmpleado = 0;
        this.contra = "";
        this.cargo = "";

    }

    public Empleado(String nombre, String imagen, String dni, String telef, String sexo,
                    String direcc, String fecha_in, Double salario, int codEmpleado,
                    String contra, String cargo) {
        this.nombre = nombre;
        this.imagen = imagen;
        this.dni = dni;
        this.telef = telef;
        this.sexo = sexo;
        this.direcc = direcc;
        this.fecha_in = fecha_in;
        this.salario = salario;
        this.codEmpleado = codEmpleado;
        this.contra = contra;
        this.cargo = cargo;
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

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getTelef() {
        return telef;
    }

    public void setTelef(String telef) {
        this.telef = telef;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getDirecc() {
        return direcc;
    }

    public void setDirecc(String direcc) {
        this.direcc = direcc;
    }

    public String getFecha_in() {
        return fecha_in;
    }

    public void setFecha_in(String fecha_in) {
        this.fecha_in = fecha_in;
    }

    public Double getSalario() {
        return salario;
    }

    public void setSalario(Double salario) {
        this.salario = salario;
    }

    public int getCodEmpleado() {
        return codEmpleado;
    }

    public void setCodEmpleado(int codEmpleado) {
        this.codEmpleado = codEmpleado;
    }

    public String getContra() {
        return contra;
    }

    public void setContra(String contra) {
        this.contra = contra;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }
}
