package com.uniciencia.sistema_invetario;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText usernameEditText, passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usernameEditText = findViewById(R.id.username_edit_text);
        passwordEditText = findViewById(R.id.password_edit_text);
    }

    // Método para manejar el inicio de sesión
    public void login(View view) {
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        // Aquí deberías agregar la lógica para verificar el nombre de usuario y la contraseña.
        // Por ejemplo, podrías compararlos con valores predefinidos o consultar una base de datos.

        // En este ejemplo simple, solo comprobamos si ambos campos no están vacíos
        if (!username.isEmpty() && !password.isEmpty()) {
            // Inicio de sesión exitoso
            Toast.makeText(this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();
            // Iniciar la actividad del panel principal
            startActivity(new Intent(this, PanelActivity.class));
        } else {
            // Mostrar mensaje de error si uno o ambos campos están vacíos
            Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
        }
    }
}

