package com.example.simplify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;  //missing
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    String TAG;
    EditText companyName, fullName, email, phoneNum, password;
    Button btnReg;
    TextView signin;
    FirebaseAuth fAuth;
    String userId;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        companyName = findViewById(R.id.editCompanyName);
        fullName    = findViewById(R.id.editFullName);
        email       = findViewById(R.id.editEmail);
        phoneNum    = findViewById(R.id.editPhone);
        password    = findViewById(R.id.editPassword);
        btnReg      = findViewById(R.id.buttonReg);
        signin      = findViewById(R.id.signin);

        fAuth       = FirebaseAuth.getInstance();
        fStore      = FirebaseFirestore.getInstance(); //missing

        if (fAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), home.class));
            finish();
        }

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String editCompanyName = companyName.getText().toString();
                String editFullName    = fullName.getText().toString();
                String editPhone       = phoneNum.getText().toString();
                String editEmail       = email.getText().toString().trim();
                String editPassword    = password.getText().toString().trim();


                if (TextUtils.isEmpty(editCompanyName)) {
                    companyName.setError("Company Name is required");
                    return;
                }

                if (TextUtils.isEmpty(editFullName)) {
                    fullName.setError("Full Name is required");
                    return;
                }

                if (TextUtils.isEmpty(editEmail)) {
                    email.setError("Email is required");
                    return;
                }

                if (TextUtils.isEmpty(editPhone)) {
                    phoneNum.setError("Phone Number is required");
                    return;
                }

                if (TextUtils.isEmpty(editPassword)) {
                    password.setError("Password is required");
                    return;
                }

                if (editPassword.length() < 7) {
                    password.setError("Password must be more than 6 characters");
                    return;
                }

                fAuth.createUserWithEmailAndPassword(editEmail, editPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Register.this, "User created", Toast.LENGTH_SHORT).show();
                            userId = fAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = fStore.collection("users").document(userId);

                            //store the data using hashmap
                            //wrong declaration
                            Map<String, Object> user = new HashMap<>();
                            user.put("Company Name" , editCompanyName);
                            user.put("Full Name"    , editFullName);
                            user.put("Email Address", editEmail);
                            user.put("Phone Number" , editPhone);
                            user.put("Phone Number" , editPassword);

                            //then, redirect user to homeactivity
                            startActivity(new Intent(getApplicationContext(), home.class));
                        } else {
                            Toast.makeText(Register.this, "Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });


        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //when user click on the "Already have an account? Sign in here" text
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });

    }
}