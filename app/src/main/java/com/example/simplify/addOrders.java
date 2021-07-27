package com.example.simplify;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class addOrders extends AppCompatActivity {

    private TextView getCategory;

    private EditText custname, custphone, custaddress, custordate, custship, custtrack;
    private String mname, mphone, maddress, mcustordate, mcustship, mcusttrack;
    private Button addOrderBtn;
    private DatabaseReference orderRef;
    private String saveCurrentDate, saveCurrentTime, productRandomKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_orders);

        orderRef = FirebaseDatabase.getInstance().getReference("Order");
        addOrderBtn = findViewById(R.id.addord);

        custname = findViewById(R.id.cust_name);
        custphone = findViewById(R.id.cust_phone);
        custaddress = findViewById(R.id.cust_address);
        custordate = findViewById(R.id.order_date);
        custship = findViewById(R.id.ship_date);
        custtrack= findViewById(R.id.tracknum);

        addOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValidateProductData();
            }
        });
    }

    private void ValidateProductData() {
        mname = custname.getText().toString().trim();
        mphone = custphone.getText().toString().trim();
        maddress = custaddress.getText().toString().trim();
        mcustordate = custordate.getText().toString().trim();
        mcustship = custship.getText().toString().trim();
        mcusttrack = custtrack.getText().toString().trim();

        if (TextUtils.isEmpty(mname)) {
            Toast.makeText(this, "Customer name required", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(mphone)) {
            Toast.makeText(this, "Phone number required", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(maddress)) {
            Toast.makeText(this, "Address required", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(mcustordate)) {
            Toast.makeText(this, "Order date required", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(mcustship)) {
            Toast.makeText(this, "Shipment date required", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(mcusttrack)) {
            Toast.makeText(this, "Tracking number required", Toast.LENGTH_SHORT).show();
        }
        else {
            StoreOrderInformation(mname, mphone, maddress, mcustordate, mcustship, mcusttrack);
        }
    }

    private void StoreOrderInformation(String mname, String mphone, String maddress, String mcustordate, String mcustship, String mcusttrack) {
        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMMM dd, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        //To create a unique product random key, so that it doesn't overwrite other product
        productRandomKey = saveCurrentDate + saveCurrentTime;

    }

    private void SaveOrderInfoToDatabase(String mname, String mphone, String maddress, String mcustordate, String mcustship, String mcusttrack) {
        HashMap<String, Object> productMap = new HashMap<>();
        productMap.put("name", mname);
        productMap.put("phone", mphone);
        productMap.put("address", maddress);
        productMap.put("order_date", mcustordate);
        productMap.put("ship_date", mcustship);
        productMap.put("track_num", mcusttrack);

        orderRef.child(productRandomKey).updateChildren(productMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(addOrders.this,order.class);
                            startActivity(intent);
                            finish();

                            // loadingBar.dismiss();
                            Toast.makeText(addOrders.this, "Order is added successfully..", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            // loadingBar.dismiss();
                            String message = task.getException().toString();
                            Toast.makeText(addOrders.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}