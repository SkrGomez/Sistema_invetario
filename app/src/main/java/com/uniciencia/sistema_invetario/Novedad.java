package com.uniciencia.sistema_invetario;

public class Novedad {
    private String codigoProducto;
    private String nombreProducto;
    private String ubicacionProducto;
    private double precioProducto;
    private String estadoProducto;
    private int cantidadProducto;

    public Novedad() {
        // Constructor vacío requerido por Firebase
    }

    public Novedad(String codigoProducto, String nombreProducto, String ubicacionProducto, double precioProducto, String estadoProducto, int cantidadProducto) {
        this.codigoProducto = codigoProducto;
        this.nombreProducto = nombreProducto;
        this.ubicacionProducto = ubicacionProducto;
        this.precioProducto = precioProducto;
        this.estadoProducto = estadoProducto;
        this.cantidadProducto = cantidadProducto;
    }

    public String getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(String codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getUbicacionProducto() {
        return ubicacionProducto;
    }

    public void setUbicacionProducto(String ubicacionProducto) {
        this.ubicacionProducto = ubicacionProducto;
    }

    public double getPrecioProducto() {
        return precioProducto;
    }

    public void setPrecioProducto(double precioProducto) {
        this.precioProducto = precioProducto;
    }

    public String getEstadoProducto() {
        return estadoProducto;
    }

    public void setEstadoProducto(String estadoProducto) {
        this.estadoProducto = estadoProducto;
    }

    public int getCantidadProducto() {
        return cantidadProducto;
    }

    public void setCantidadProducto(int cantidadProducto) {
        this.cantidadProducto = cantidadProducto;
    }
}
