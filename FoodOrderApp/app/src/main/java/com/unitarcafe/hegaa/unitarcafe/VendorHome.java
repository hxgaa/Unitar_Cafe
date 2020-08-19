package com.unitarcafe.hegaa.unitarcafe;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.tabs.TabLayout;


public class VendorHome extends AppCompatActivity {

    Toolbar food, order, home;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendorhome);
        home = findViewById(R.id.tabHome);
        food = findViewById(R.id.tabFoods);
        order = findViewById(R.id.tabOrders);

        food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(VendorHome.this, "Key: Manage Food!", Toast.LENGTH_SHORT).show();
            }
        });

        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(VendorHome.this, "Key: Manage Orders!", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
