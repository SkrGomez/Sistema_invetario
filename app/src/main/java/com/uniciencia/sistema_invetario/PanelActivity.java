package com.uniciencia.sistema_invetario;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class PanelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panel);
    }

    public void goToRegistroProductos(View view) {
        // Implementa la acción para el botón "Registro de Productos"
        startActivity(new Intent(this, RegistroProductosActivity.class));
    }

    public void goToActualizacionProductos(View view) {
        // Implementa la acción para el botón "Actualización de Productos"
        startActivity(new Intent(this, ActualizacionProductosActivity.class));
    }

    public void goToBusquedaProductos(View view) {
        // Implementa la acción para el botón "Búsqueda de Productos"
        startActivity(new Intent(this, BusquedaProductosActivity.class));
    }

    public void goToControlInventario(View view) {
        // Implementa la acción para el botón "Control de Inventario"
        startActivity(new Intent(this, ControlInventarioActivity.class));
    }
}
