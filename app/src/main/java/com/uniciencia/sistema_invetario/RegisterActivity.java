package com.uniciencia.sistema_invetario;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    public EditText editTextEmail,editTextPassword,editTextdepartment;
    public Button UserRegisterBtn;
    public ProgressBar progressBar;

    String email = "";
    String password= "";
    String department = "";

    FirebaseAuth mAuth;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        editTextEmail = findViewById(R.id.emailRegister);
        editTextPassword = findViewById(R.id.passwordRegister);
        editTextdepartment= findViewById(R.id.department);
        UserRegisterBtn= findViewById(R.id.button_register);

        progressBar = findViewById(R.id.progressbar);
        progressBar.setVisibility(View.GONE);


        UserRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = editTextEmail.getText().toString();
                password = editTextPassword.getText().toString();
                department = editTextdepartment.getText().toString();

                if(!email.isEmpty() && !password.isEmpty() && !department.isEmpty()){

                    if(password.length() >= 6){

                        registerUser();

                    }else{
                        Toast.makeText(RegisterActivity.this, "La contraseña debe tener almenos 6 carácteres", Toast.LENGTH_SHORT).show();
                    }


                }else{

                    Toast.makeText(RegisterActivity.this, "Debe completar los campos", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

    private void registerUser() {

        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){

                    Map<String, Object> map = new HashMap<>();
                    map.put("email", email);
                    map.put("password", password);
                    map.put("department",department);


                    String id = mAuth.getCurrentUser().getUid();

                    mDatabase.child("Users").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {

                            if(task2.isSuccessful()){

                                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                finish();

                            }else{

                                Toast.makeText(RegisterActivity.this, "No se pudo registrar el usuario", Toast.LENGTH_SHORT).show();

                            }


                        }
                    });

                }else{

                    Toast.makeText(RegisterActivity.this, "No se pudo registrar este usuario", Toast.LENGTH_SHORT).show();

                }

            }
        });


    }


}