package com.uniciencia.sistema_invetario;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class ActualizacionProductosActivity extends AppCompatActivity {

    private EditText etProductCode, etProductName, etProductPrice, etProductLocation;
    private TextView tvProductDetails, etProductCant, etProductState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actualizar_productos);
        etProductCode = findViewById(R.id.etProductCode);
        etProductName = findViewById(R.id.etProductName);
        etProductPrice = findViewById(R.id.etProductPrice);
        etProductLocation = findViewById(R.id.etProductLocation);
        etProductCant = findViewById(R.id.etProductCant);
        etProductState = findViewById(R.id.etProductState);
        tvProductDetails = findViewById(R.id.tvProductDetails);
        etProductCant.setEnabled(false);
        etProductState.setEnabled(false);
    }

    public void searchProduct(View view) {
        String searchQuery = etProductCode.getText().toString().trim();
        Producto.obtenerProductoPorCodigo(searchQuery, new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Producto producto = dataSnapshot.getChildren().iterator().next().getValue(Producto.class);
                    if (producto != null) {
                        mostrarDetallesProducto(producto);
                    }
                } else {
                    tvProductDetails.setText("Producto no encontrado");
                    limpiarDetallesProducto();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                tvProductDetails.setText("Error al buscar el producto: " + databaseError.getMessage());
            }
        });
    }

    public void actualizarProducto(View view) {
        String codigo = etProductCode.getText().toString().trim();
        String nombre = etProductName.getText().toString().trim();
        double precio = Double.parseDouble(etProductPrice.getText().toString().trim());
        String ubicacion = etProductLocation.getText().toString().trim();

        Producto.obtenerProductoPorCodigo(codigo, new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Producto producto = dataSnapshot.getChildren().iterator().next().getValue(Producto.class);
                    if (producto != null) {
                        // Actualizar los valores del producto
                        producto.setProductName(nombre);
                        producto.setProductPrice(precio);
                        producto.setProductLocation(ubicacion);

                        // Llamar al método actualizarProducto en el objeto producto
                        producto.actualizarProducto(nombre, precio, ubicacion, producto.getProductCant(), producto.getProductState());

                        // Mostrar mensaje de éxito
                        tvProductDetails.setText("Producto actualizado correctamente");
                    }
                } else {
                    tvProductDetails.setText("Producto no encontrado");
                    limpiarDetallesProducto();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                tvProductDetails.setText("Error al buscar el producto: " + databaseError.getMessage());
            }
        });
    }

    private void mostrarDetallesProducto(Producto producto) {
        if (producto != null) {
            etProductName.setText(producto.getProductName());
            etProductPrice.setText(String.valueOf(producto.getProductPrice()));
            etProductLocation.setText(producto.getProductLocation());
            etProductCant.setText(String.valueOf(producto.getProductCant())); // Mostramos el valor del nuevo campo
            etProductState.setText(producto.getProductState()); // Mostramos el valor del nuevo campo
        }
    }

    private void limpiarDetallesProducto() {
        etProductName.setText("");
        etProductPrice.setText("");
        etProductLocation.setText("");
        etProductCant.setText("");
        etProductState.setText("");
    }
}