package com.uniciencia.sistema_invetario;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class RegistroProductosActivity extends AppCompatActivity {

    private EditText etProductName, etProductPrice, etProductCode, etProductLocation, etProductCant, etProductState;
    private DatabaseReference productosRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registrar_productos);

        etProductName = findViewById(R.id.etProductName);
        etProductPrice = findViewById(R.id.etProductPrice);
        etProductCode = findViewById(R.id.etProductCode);
        etProductLocation = findViewById(R.id.etProductLocation);
        etProductCant = findViewById(R.id.etProductCant);
        etProductState = findViewById(R.id.etProductState);

        // Establecer el valor predeterminado para etProductState
        etProductState.setText("Disponible");
        // Deshabilitar la edición de etProductState
        etProductState.setEnabled(false);

        // Obtener la referencia a la base de datos
        productosRef = FirebaseDatabase.getInstance().getReference().child("productos");
    }

    public void saveProduct(View view) {
        String productName = etProductName.getText().toString();
        double productPrice = Double.parseDouble(etProductPrice.getText().toString());
        String productCode = etProductCode.getText().toString();
        String productLocation = etProductLocation.getText().toString();
        int productCant = Integer.parseInt(etProductCant.getText().toString());

        // Verificar si el productCode ya existe en la base de datos
        Query query = productosRef.orderByChild("productCode").equalTo(productCode);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // El productCode ya está en uso
                    Toast.makeText(RegistroProductosActivity.this, "¡El código del producto ya está en uso!", Toast.LENGTH_SHORT).show();
                } else {
                    // Crear un objeto Producto
                    Producto producto = new Producto(productName, productPrice, productCode, productLocation, productCant, "Disponible");

                    // Guardar el producto en la base de datos con el productCode como clave
                    productosRef.child(productCode).setValue(producto);

                    // Limpiar los campos después de guardar
                    etProductName.setText("");
                    etProductPrice.setText("");
                    etProductCode.setText("");
                    etProductLocation.setText("");
                    etProductCant.setText("");

                    Toast.makeText(RegistroProductosActivity.this, "¡Producto registrado exitosamente!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Manejar errores de base de datos
                Toast.makeText(RegistroProductosActivity.this, "Error al registrar el producto: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
