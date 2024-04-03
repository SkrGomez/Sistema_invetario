package com.uniciencia.sistema_invetario;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    public EditText username, password;
    public Button Login;
    public ProgressBar progressBar;

    private ProgressDialog processDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.emailSignIn);
        password = findViewById(R.id.password);
        Login = findViewById(R.id.Login);

        progressBar = (ProgressBar) findViewById(R.id.progressbars);
        progressBar.setVisibility(View.GONE);

        processDialog = new ProgressDialog(this);


        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateLogin(username.getText().toString(), password.getText().toString());
            }

        });


    }

    // Método para manejar el inicio de sesión
    public void validateLogin(String userEmail, String userPassword) {

        processDialog.setMessage("................Por favor esperar.............");
        processDialog.show();

        // Aquí deberías agregar la lógica para verificar el nombre de usuario y la contraseña.
        // Por ejemplo, podrías compararlos con valores predefinidos o consultar una base de datos.

        // En este ejemplo simple, solo comprobamos si ambos campos no están vacíos
        if (!userEmail.isEmpty() && !userPassword.isEmpty()) {
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