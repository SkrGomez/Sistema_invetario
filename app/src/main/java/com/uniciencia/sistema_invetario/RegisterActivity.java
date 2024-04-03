package com.uniciencia.sistema_invetario;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

public class RegisterActivity extends AppCompatActivity {

    public EditText editTextEmail,editTextPassword,editTextcPassword;
    public Button UserRegisterBtn;
    public ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        editTextEmail = findViewById(R.id.emailRegister);
        editTextPassword = findViewById(R.id.passwordRegister);
        editTextcPassword= findViewById(R.id.confirmPassword);
        UserRegisterBtn= findViewById(R.id.button_register);

        progressBar = findViewById(R.id.progressbar);
        progressBar.setVisibility(View.GONE);


        UserRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });
    }


}