package com.uniciencia.sistema_invetario;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class BusquedaProductosActivity extends AppCompatActivity {

    private EditText etSearchProduct;
    private TextView tvProductDetails;
    private DatabaseReference productosRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.busqueda_productos);

        etSearchProduct = findViewById(R.id.etSearchProduct);
        tvProductDetails = findViewById(R.id.tvProductDetails);

        // Obtener la referencia a la base de datos
        productosRef = FirebaseDatabase.getInstance().getReference().child("productos");
    }

    public void searchProduct(View view) {
        String searchQuery = etSearchProduct.getText().toString().trim();

        // Realizar la consulta en la base de datos
        Query query = productosRef.orderByChild("productName")
                .startAt(searchQuery)
                .endAt(searchQuery + "\uf8ff");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    StringBuilder details = new StringBuilder();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Producto producto = snapshot.getValue(Producto.class);
                        if (producto != null) {
                            details.append("Nombre: ").append(producto.getProductName()).append("\n")
                                    .append("Precio: ").append(producto.getProductPrice()).append("\n")
                                    .append("Código: ").append(producto.getProductCode()).append("\n")
                                    .append("Ubicación: ").append(producto.getProductLocation()).append("\n")
                                    .append("Cantidad: ").append(producto.getProductCant()).append("\n")
                                    .append("Estado: ").append(producto.getProductState()).append("\n\n");
                        }
                    }
                    tvProductDetails.setText(details.toString());
                } else {
                    tvProductDetails.setText("Producto no encontrado");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Manejar error en la consulta
                tvProductDetails.setText("Error al buscar el producto: " + databaseError.getMessage());
            }
        });
    }
}