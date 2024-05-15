package com.uniciencia.sistema_invetario;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class ControlInventarioActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.control_inventario);
    }

    // Método para ir a la actividad de Resúmenes del Inventario
    public void goToResumenProductos(View view) {
        Intent intent = new Intent(this, ResumenProductosActivity.class);
        startActivity(intent);
    }

    // Método para ir a la actividad de Salidas de Productos
    public void goToSalidasProductos(View view) {
        Intent intent = new Intent(this, SalidasProductosActivity.class);
        startActivity(intent);
    }

    // Método para ir a la actividad de Novedades del Producto
    public void goToNovedadesProductos(View view) {
        Intent intent = new Intent(this, NovedadesProductosActivity.class);
        startActivity(intent);
    }

    // Método para ir a la actividad de Notificaciones
    public void goToNotificaciones(View view) {
        Intent intent = new Intent(this, NotificacionesActivity.class);
        startActivity(intent);
    }
}
