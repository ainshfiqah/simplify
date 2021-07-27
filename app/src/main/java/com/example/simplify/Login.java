package com.example.simplify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    EditText email, password;
    TextView signUP;
    Button buttonLogin;
    FirebaseAuth fAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.editEmail);
        password = findViewById(R.id.editPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        signUP = findViewById(R.id.signup);

        fAuth = FirebaseAuth.getInstance();

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String editEmail = email.getText().toString().trim();
                String editPassword = password.getText().toString().trim();

                if (TextUtils.isEmpty(editEmail)) {
                    email.setError("Email is required");
                    return;
                }

                if (TextUtils.isEmpty(editPassword)) {
                    password.setError("Password is required");
                    return;
                }

                if (password.length() < 7) {
                    password.setError("Password must be more than 6 characters");
                    return;
                }

                //authenticate user
                fAuth.signInWithEmailAndPassword(editEmail, editPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Login.this, "Logged in successfully", Toast.LENGTH_SHORT).show();
                            //then, redirect user to mainactivity
                            startActivity(new Intent(getApplicationContext(), home.class));

                        } else {
                            Toast.makeText(Login.this, "Error" + task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        signUP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //when user click on the "Already have an account? Sign in here" text
                startActivity(new Intent(getApplicationContext(), Register.class));
            }
        });

    }
}