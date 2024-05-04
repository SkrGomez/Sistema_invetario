
package com.uniciencia.sistema_invetario;

public class Producto {
    private String productName;
    private double productPrice;
    private String productCode;
    private String productLocation;

    public Producto() {
        // Constructor vacío requerido para Firebase
    }

    public Producto(String productName, double productPrice, String productCode, String productLocation) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.productCode = productCode;
        this.productLocation = productLocation;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductLocation() {
        return productLocation;
    }

    public void setProductLocation(String productLocation) {
        this.productLocation = productLocation;
    }
}