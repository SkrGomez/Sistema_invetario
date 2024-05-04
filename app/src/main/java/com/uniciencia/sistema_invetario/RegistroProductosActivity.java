package com.uniciencia.sistema_invetario;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class RegistroProductosActivity extends AppCompatActivity {

    private EditText etProductName, etProductPrice, etProductCode, etProductLocation;
    private DatabaseReference productosRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registrar_productos);

        etProductName = findViewById(R.id.etProductName);
        etProductPrice = findViewById(R.id.etProductPrice);
        etProductCode = findViewById(R.id.etProductCode);
        etProductLocation = findViewById(R.id.etProductLocation);

        // Obtener la referencia a la base de datos
        productosRef = FirebaseDatabase.getInstance().getReference().child("productos");
    }

    public void saveProduct(View view) {
        String productName = etProductName.getText().toString();
        double productPrice = Double.parseDouble(etProductPrice.getText().toString());
        String productCode = etProductCode.getText().toString();
        String productLocation = etProductLocation.getText().toString();

        // Crear un objeto Producto
        Producto Producto  = new Producto(productName, productPrice, productCode, productLocation);

        // Guardar el producto en la base de datos
        productosRef.push().setValue(Producto);

        // Limpiar los campos después de guardar
        etProductName.setText("");
        etProductPrice.setText("");
        etProductCode.setText("");
        etProductLocation.setText("");
    }
}
