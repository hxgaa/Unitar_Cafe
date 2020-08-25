package com.unitarcafe.hegaa.unitarcafe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.unitarcafe.hegaa.unitarcafe.Adapters.VendorMenuAdapter;
import com.unitarcafe.hegaa.unitarcafe.Common.Common;
import com.unitarcafe.hegaa.unitarcafe.Model.Items;

import java.util.ArrayList;
import java.util.List;

public class VendorFoods extends AppCompatActivity {

    RecyclerView recycler_menu;
    RecyclerView.LayoutManager layoutManager;
    Button addItem;

    List<Items> allItems = new ArrayList<>();;

    //Firebase Database
    FirebaseDatabase database;
    DatabaseReference items;
    FirebaseStorage storage;
    StorageReference imageStorage, imgPath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_foods);
        Toolbar toolbar = (Toolbar) findViewById(R.id.vendorToolbar);
        toolbar.setTitle("Manage Items");
        setSupportActionBar(toolbar);
        final TabLayout tabs = (TabLayout) findViewById(R.id.tabs);
        TabLayout.Tab tab = tabs.getTabAt(2);
        tab.select();
        addItem = (Button) findViewById(R.id.btnAddItem);

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Toast.makeText(VendorFoods.this, "Vendor Food", Toast.LENGTH_SHORT).show();
                int selectedTabPosition = tabs.getSelectedTabPosition();
                if (selectedTabPosition == 0) {

                    Intent foods = new Intent(VendorFoods.this, VendorHome.class);
                    startActivity(foods);
                    finish();

                } else if (selectedTabPosition == 1) {

                    Intent foods = new Intent(VendorFoods.this, VendorOrders.class);
                    startActivity(foods);
                    finish();

                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        recycler_menu = findViewById(R.id.recyclerFoodItems);
        recycler_menu.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recycler_menu.setLayoutManager(layoutManager);

        //Firebase
        database = FirebaseDatabase.getInstance();
        items = database.getReference("menu");
        storage = FirebaseStorage.getInstance("gs://unitarcafe.appspot.com/");
        imageStorage = storage.getReference().child("itemImages");

        getItems();

        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent foods = new Intent(VendorFoods.this, VendorFoodItem.class);
                startActivity(foods);
            }
        });


    }


    private void getItems() {
        items.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot item: dataSnapshot.getChildren()) {
                    for (DataSnapshot specificItem : item.getChildren()) {
                        Items currentFood = specificItem.getValue(Items.class);
                        allItems.add(new Items(currentFood.getName(), currentFood.getDescription(), currentFood.getPrice(), currentFood.getDiscount(), currentFood.getImage()));
                    }
                }
                VendorMenuAdapter adapter = new VendorMenuAdapter(allItems);
                recycler_menu.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        Intent foods = new Intent(VendorFoods.this, VendorHome.class);
        startActivity(foods);
        finish();
    }

}
