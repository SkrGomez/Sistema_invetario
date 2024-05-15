package com.uniciencia.sistema_invetario;
public class Notificacion {
    private String accion;
    private String fecha;
    private String hora;
    private String nombre;
    private String productoAfectado;
    private String usuario;

    // Constructor vacío requerido por Firebase
    public Notificacion() {
    }

    // Constructor con parámetros
    public Notificacion(String accion, String fecha, String hora, String nombre, String productoAfectado, String usuario) {
        this.accion = accion;
        this.fecha = fecha;
        this.hora = hora;
        this.nombre = nombre;
        this.productoAfectado = productoAfectado;
        this.usuario = usuario;
    }

    // Getters y setters
    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getProductoAfectado() {
        return productoAfectado;
    }

    public void setProductoAfectado(String productoAfectado) {
        this.productoAfectado = productoAfectado;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
}