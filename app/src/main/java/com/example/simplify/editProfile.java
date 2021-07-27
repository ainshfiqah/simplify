package com.example.simplify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class editProfile extends AppCompatActivity {
    public static final String TAG = "TAG";
    EditText mEmail, mName, mNum;
    Button buttonSave;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        Intent data = getIntent();
        String mmEmail = data.getStringExtra("Email");
        String mmName = data.getStringExtra("Name");
        String mmNum = data.getStringExtra("Phone Number");

        mEmail = findViewById(R.id.editPEmail);
        mName = findViewById(R.id.editPName);
        mNum = findViewById(R.id.editPNum);
        buttonSave = findViewById(R.id.btnSave);

        mEmail.setText(mmEmail);
        mName.setText(mmName);
        mNum.setText(mmNum);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        user = fAuth.getCurrentUser();

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mEmail.getText().toString().isEmpty() || mName.getText().toString().isEmpty() || mNum.getText().toString().isEmpty()) {
                    Toast.makeText(editProfile.this, "One or many fields are empty", Toast.LENGTH_SHORT).show();
                }

                //change email
                String email = mEmail.getText().toString();
                user.updateEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        DocumentReference docRef = fStore.collection("users").document(user.getUid());
                        Map<String, Object> edited = new HashMap<>();
                        edited.put("Email", email);
                        edited.put("Name", mName.getText().toString());
                        edited.put("Phone Number", mNum.getText().toString());
                        docRef.update(edited).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(editProfile.this, "Profile updated", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(),more.class));
                                finish();
                            }
                        });
                        Toast.makeText(editProfile.this, "Email has been successfully changed", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(editProfile.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }
}