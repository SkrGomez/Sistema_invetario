package com.uniciencia.sistema_invetario;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    public EditText username, password;
    public Button Login;
    public ProgressBar progressBar;

    private FirebaseAuth mAuth;
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
        mAuth = FirebaseAuth.getInstance();
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

        //Lógica para verificar el nombre de usuario y la contraseña.

        if (!userEmail.isEmpty() && !userPassword.isEmpty()) {
            if (userPassword.length() >= 6) {
                mAuth.signInWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            processDialog.dismiss();
                            Intent intent = new Intent(LoginActivity.this, PanelActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(LoginActivity.this, "La contraseña o el usuario son incorrectos", Toast.LENGTH_SHORT).show();
                            processDialog.dismiss();
                        }
                    }
                });
            }
            else {
                Toast.makeText(this, "La contraseña debe tener más de 6 caracteres", Toast.LENGTH_SHORT).show();
                processDialog.dismiss();
            }
        }
        else {
            Toast.makeText(this, "La contraseña y el email son obligatorios", Toast.LENGTH_SHORT).show();
            processDialog.dismiss();
        }

    }

}