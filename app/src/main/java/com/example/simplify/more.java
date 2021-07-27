package com.example.simplify;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class more extends AppCompatActivity {

    BottomNavigationView bottommenu;
    TextView pEmail, pName, pPhone;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;
    Button pLogout, pEdit;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userID = fAuth.getCurrentUser().getUid();
        userID      = fAuth.getCurrentUser().getUid();
        user        = fAuth.getCurrentUser();

        bottommenu = findViewById(R.id.bottom_navigation);
        bottommenu.setSelectedItemId(R.id.more);

        pEmail = findViewById(R.id.textUEmail);
        pName = findViewById(R.id.textUName);
        pPhone = findViewById(R.id.textUPhone);

        pLogout = findViewById(R.id.btnSignOut);
        pEdit = findViewById(R.id.btnEdit);

        pLogout.setOnClickListener(new View.OnClickListener() {
        @Override
                public void onClick(View v) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getApplicationContext(), Login.class));
            finish();
        }
        });

        pEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), editProfile.class);
                startActivity(intent);
            }
        });


                //retrieve data from firebase firestore
                DocumentReference documentReference = fStore.collection("users").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {

                pEmail.setText(documentSnapshot.getString("Email"));
                pName.setText(documentSnapshot.getString("Name"));
                pPhone.setText(documentSnapshot.getString("Phone Number"));
            }

        });

        bottommenu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.order:
                        startActivity(new Intent(getApplicationContext(), order.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), home.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.more:
                        return true;
                }
                return false;
            }
        });

    }


}