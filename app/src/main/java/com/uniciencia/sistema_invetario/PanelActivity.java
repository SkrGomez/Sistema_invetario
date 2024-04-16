package com.uniciencia.sistema_invetario;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class PanelActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panel);
        mAuth = FirebaseAuth.getInstance();
    }

    // logout below
    private void Logout()
    {
        mAuth.signOut();
        finish();
        startActivity(new Intent(PanelActivity.this,LoginActivity.class));Toast.makeText(PanelActivity.this,"Cierre de sesión exitoso", Toast.LENGTH_SHORT).show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case  R.id.logoutMenu:{
                Logout();
            }
        }
        return super.onOptionsItemSelected(item);
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
