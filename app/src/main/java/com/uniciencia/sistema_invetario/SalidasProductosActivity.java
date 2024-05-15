package com.uniciencia.sistema_invetario;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SalidasProductosActivity extends AppCompatActivity {

    private EditText etCodigoProducto, etCantidadVenta;
    private TextView tvNombreProducto, tvCantidadDisponible, tvPrecioUnitario, tvTotalVenta, tvMensajeError;
    private DatabaseReference productosRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.salidas_productos);

        etCodigoProducto = findViewById(R.id.etCodigoProducto);
        etCantidadVenta = findViewById(R.id.etCantidadVenta);
        tvNombreProducto = findViewById(R.id.tvNombreProducto);
        tvCantidadDisponible = findViewById(R.id.tvCantidadDisponible);
        tvPrecioUnitario = findViewById(R.id.tvPrecioUnitario);
        tvTotalVenta = findViewById(R.id.tvTotalVenta);
        tvMensajeError = findViewById(R.id.tvMensajeError);

        productosRef = FirebaseDatabase.getInstance().getReference().child("productos");
    }

    public void buscarProducto(View view) {
        String codigoProducto = etCodigoProducto.getText().toString().trim();

        if (TextUtils.isEmpty(codigoProducto)) {
            mostrarMensajeError("Por favor ingrese el código del producto.");
            return;
        }

        Producto.obtenerProductoPorCodigo(codigoProducto, new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Producto producto = dataSnapshot.getChildren().iterator().next().getValue(Producto.class);
                    if (producto != null) {
                        String estado = producto.getProductState();
                        if (producto.getProductState() != null && producto.getProductState().equals("Disponible")) {
                            // Mostrar los detalles del producto
                            tvNombreProducto.setText("Nombre del Producto: " + producto.getProductName());
                            tvCantidadDisponible.setText("Cantidad Disponible: " + producto.getProductCant());
                            tvPrecioUnitario.setText("Precio Unitario: " + producto.getProductPrice());
                        } else {
                            // El producto no está disponible
                            mostrarMensajeError("El producto no está disponible.");
                        }
                    }
                } else {
                    // Si no se encontró ningún producto, mostrar el mensaje de error y limpiar los campos
                    mostrarMensajeError("No se encontró ningún producto con ese código.");
                    limpiarCampos();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                mostrarMensajeError("Error al buscar el producto: " + databaseError.getMessage());
            }
        });
    }

    public void calcularTotalVenta(View view) {
        String cantidadStr = etCantidadVenta.getText().toString().trim();
        String precioUnitarioStr = tvPrecioUnitario.getText().toString().trim().replace("Precio Unitario: ", "");

        if (TextUtils.isEmpty(cantidadStr) || TextUtils.isEmpty(precioUnitarioStr)) {
            mostrarMensajeError("Por favor ingrese la cantidad a vender y asegúrese de haber buscado un producto.");
            return;
        }

        int cantidad = Integer.parseInt(cantidadStr);
        double precioUnitario = Double.parseDouble(precioUnitarioStr);

        double totalVenta = cantidad * precioUnitario;
        tvTotalVenta.setText("Total de la Venta: " + totalVenta);
        tvTotalVenta.setVisibility(View.VISIBLE);
    }

    public void venderProducto(View view) {
        String codigoProducto = etCodigoProducto.getText().toString().trim();
        String cantidadVentaStr = etCantidadVenta.getText().toString().trim();

        if (TextUtils.isEmpty(codigoProducto) || TextUtils.isEmpty(cantidadVentaStr)) {
            mostrarMensajeError("Por favor ingrese el código del producto y la cantidad a vender.");
            return;
        }

        int cantidadVenta = Integer.parseInt(cantidadVentaStr);

        Producto.obtenerProductoPorCodigo(codigoProducto, new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Producto producto = dataSnapshot.getChildren().iterator().next().getValue(Producto.class);
                    if (producto != null) {
                        int cantidadDisponible = producto.getProductCant();
                        if (cantidadVenta > cantidadDisponible) {
                            mostrarMensajeError("Cantidad no disponible, ingrese otro valor.");
                        } else {
                            // Actualizar la cantidad y el estado del producto
                            int nuevaCantidad = cantidadDisponible - cantidadVenta;
                            if (nuevaCantidad == 0) {
                                producto.setProductState("Agotado");
                            }
                            producto.setProductCant(nuevaCantidad);

                            // Actualizar el producto en la base de datos
                            producto.actualizarProducto(producto.getProductName(), producto.getProductPrice(),
                                    producto.getProductLocation(), nuevaCantidad, producto.getProductState());

                            // Mostrar mensaje de éxito y limpiar campos después de 3 segundos
                            Toast.makeText(SalidasProductosActivity.this, "Venta realizada con éxito.", Toast.LENGTH_SHORT).show();
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    limpiarCampos();
                                }
                            }, 3000);
                        }
                    }
                } else {
                    // Si no se encontró ningún producto, mostrar el mensaje de error
                    mostrarMensajeError("No se encontró ningún producto con ese código.");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                mostrarMensajeError("Error al buscar el producto: " + databaseError.getMessage());
            }
        });
    }

    private void mostrarMensajeError(String mensaje) {
        tvMensajeError.setVisibility(View.VISIBLE);
        tvMensajeError.setText(mensaje);
        // Ocultar el mensaje después de 3 segundos
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                tvMensajeError.setVisibility(View.GONE);
            }
        }, 3000);
    }

    private void limpiarCampos() {
        etCodigoProducto.setText("");
        etCantidadVenta.setText("");
        tvPrecioUnitario.setText("");
        tvNombreProducto.setText("");
        tvCantidadDisponible.setText("");
        tvPrecioUnitario.setText("");
        tvTotalVenta.setText("");
        tvMensajeError.setVisibility(View.GONE);
    }
}