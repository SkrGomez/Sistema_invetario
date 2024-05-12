package com.uniciencia.sistema_invetario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Producto {
    private String productName;
    private double productPrice;
    private String productCode;
    private String productLocation;

    private static DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("productos");

    public Producto() {

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

    // Método para obtener un producto por su código
    public static void obtenerProductoPorCodigo(String codigo, ValueEventListener listener) {
        mDatabase.orderByChild("productCode").equalTo(codigo).addListenerForSingleValueEvent(listener);
    }

    // Método para actualizar los datos del producto en la base de datos Firebase
    public void actualizarProducto(String nuevoNombre, double nuevoPrecio, String nuevaRegion) {
        this.productName = nuevoNombre;
        this.productPrice = nuevoPrecio;
        this.productLocation = nuevaRegion;

        mDatabase.child(productCode).setValue(this).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                System.out.println("Producto actualizado correctamente en la base de datos.");
            } else {
                System.out.println("Error al actualizar el producto en la base de datos: " + task.getException().getMessage());
            }
        });
    }
}
