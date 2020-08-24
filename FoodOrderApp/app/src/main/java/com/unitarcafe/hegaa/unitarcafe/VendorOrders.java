package com.unitarcafe.hegaa.unitarcafe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import com.unitarcafe.hegaa.unitarcafe.Common.Common;

public class VendorOrders extends AppCompatActivity {

    Toolbar food, order, home;
    TextView lblWelcome;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_orders);
        home = findViewById(R.id.tabHome);
        food = findViewById(R.id.tabFoods);
        order = findViewById(R.id.tabOrders);
        lblWelcome = findViewById(R.id.lblWelcome);

        lblWelcome.setText("Welcome, "+ Common.currentUser.getName());

        food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(VendorOrders.this, "Key: Manage Food!", Toast.LENGTH_SHORT).show();
                Intent foods = new Intent(VendorOrders.this, VendorFoods.class);
                finish();

            }
        });

        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(VendorOrders.this, "Key: Manage Orders!", Toast.LENGTH_SHORT).show();
//                VendorOrders.this.recreate();
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(VendorOrders.this, "Key: Home!", Toast.LENGTH_SHORT).show();
                Intent foods = new Intent(VendorOrders.this, VendorHome.class);
                finish();
            }
        });
    }
}
