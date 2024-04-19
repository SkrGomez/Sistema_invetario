package com.uniciencia.sistema_invetario;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class RegistroProductosActivity extends AppCompatActivity {

    private EditText etProductName, etProductPrice, etProductCode, etProductLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registrar_productos);

        etProductName = findViewById(R.id.etProductName);
        etProductPrice = findViewById(R.id.etProductPrice);
        etProductCode = findViewById(R.id.etProductCode);
        etProductLocation = findViewById(R.id.etProductLocation);
    }

    public void saveProduct(View view) {
        String productName = etProductName.getText().toString();
        double productPrice = Double.parseDouble(etProductPrice.getText().toString());
        String productCode = etProductCode.getText().toString();
        String productLocation = etProductLocation.getText().toString();


        // Aquí  la lógica para guardar la información del producto
        etProductName.setText("");
        etProductPrice.setText("");
        etProductCode.setText("");
        etProductLocation.setText("");
    }
}