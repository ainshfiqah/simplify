package com.example.simplify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class order extends AppCompatActivity {

    BottomNavigationView bottommenu;

    RecyclerView recyclerView;
    ordersAdapter mordersAdapter;
    DatabaseReference mbase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        mbase = FirebaseDatabase.getInstance().getReference("Order List");

        recyclerView = findViewById(R.id.recyclerviewSystem);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        bottommenu = findViewById(R.id.bottom_navigation);
        bottommenu.setSelectedItemId(R.id.home);

        bottommenu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), home.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.more:
                        startActivity(new Intent(getApplicationContext(), more.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.order:
                        return true;
                }
                return false;
            }
        });


        FirebaseRecyclerOptions<orders> options = new FirebaseRecyclerOptions.Builder<orders>()
                .setQuery(mbase, new SnapshotParser<orders>() {
                    @NonNull
                    @Override
                    public com.example.simplify.orders parseSnapshot(@NonNull DataSnapshot snapshot) {
                        orders orderdata = new orders();
                        orderdata.setName(snapshot.child("Cust_name").getValue().toString());
                        orderdata.setAddress(snapshot.child("Cust_address").getValue().toString());
                        orderdata.setStatus(snapshot.child("Status").getValue().toString());
                        return orderdata;
                    }
                })
                .build();

        mordersAdapter = new ordersAdapter(options);
        recyclerView.setAdapter(mordersAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mordersAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mordersAdapter.stopListening();
    }


}