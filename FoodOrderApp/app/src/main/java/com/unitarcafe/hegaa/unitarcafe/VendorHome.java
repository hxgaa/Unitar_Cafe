package com.unitarcafe.hegaa.unitarcafe;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class VendorHome extends AppCompatActivity {

    ImageView food, order;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendorhome);
        food = findViewById(R.id.vendorFood);
        order = findViewById(R.id.vendorOrder);

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
