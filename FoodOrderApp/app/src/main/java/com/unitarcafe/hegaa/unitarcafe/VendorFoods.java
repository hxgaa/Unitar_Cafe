package com.unitarcafe.hegaa.unitarcafe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

import java.util.List;

public class VendorFoods extends AppCompatActivity {
    TabLayout food, order, home;
    TextView lblWelcome;
    RecyclerView recycler_menu;
    RecyclerView.LayoutManager layoutManager;

    List<Items> allItems;

    //Firebase Database
    FirebaseDatabase database;
    DatabaseReference items;
    FirebaseStorage storage;
    StorageReference imageStorage, imgPath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_foods);

        androidx.appcompat.widget.Toolbar toolbar = (Toolbar) findViewById(R.id.vendorToolbar);
        setSupportActionBar(toolbar);
        TabLayout tabs = (TabLayout) findViewById(R.id.tabs);

        home = findViewById(R.id.tabHome);
        food = findViewById(R.id.tabFoods);
        order = findViewById(R.id.tabOrders);
        lblWelcome = findViewById(R.id.lblWelcome);

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Toast.makeText(VendorFoods.this, "Vendor Foods", Toast.LENGTH_SHORT).show();
                if (tab.equals(home)) {
                    food.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(VendorFoods.this, "Key: Manage Food!", Toast.LENGTH_SHORT).show();
                            Intent foods = new Intent(VendorFoods.this, VendorHome.class);
                            startActivity(foods);
                            finish();
                        }
                    });
                } else if (tab.equals(order)) {


                    order.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(VendorFoods.this, "Key: Manage Orders!", Toast.LENGTH_SHORT).show();
                            Intent foods = new Intent(VendorFoods.this, VendorOrders.class);
                            startActivity(foods);
                            finish();
                        }
                    });
                }
//                } else {
//
//                    home.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            Toast.makeText(VendorHome.this, "Key: Home!", Toast.LENGTH_SHORT).show();
////                VendorHome.this.recreate();
//                        }
//                    });
//                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        lblWelcome.setText("Welcome, "+ Common.currentUser.getName());

        //Load foods
        recycler_menu = findViewById(R.id.recyclerFoodItems);
        recycler_menu.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recycler_menu.setLayoutManager(layoutManager);

        //Firebase
        database = FirebaseDatabase.getInstance();
        items = database.getReference("menu");
        storage = FirebaseStorage.getInstance("gs://unitarcafe.appspot.com/");
        imageStorage = storage.getReference().child("itemImages");

        food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(VendorFoods.this, "Key: Manage Food!", Toast.LENGTH_SHORT).show();
//                VendorFoods.this.recreate();
            }
        });

        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(VendorFoods.this, "Key: Manage Orders!", Toast.LENGTH_SHORT).show();
                Intent foods = new Intent(VendorFoods.this, VendorOrders.class);
                finish();
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(VendorFoods.this, "Key: Home!", Toast.LENGTH_SHORT).show();
                Intent foods = new Intent(VendorFoods.this, VendorHome.class);
                finish();
            }
        });

    }

    private void getItems(final String foodId) {
        items.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot item: dataSnapshot.getChildren()) {
//                    System.out.println("DS2: "+item.toString());
                    for (DataSnapshot specificItem : item.getChildren()) {
//                        System.out.println("DS3: "+specificItem.toString());
                        Items currentFood = specificItem.getValue(Items.class);
                        allItems.add(currentFood);
//                        System.out.println("Item: "+currentFood.getName());

                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        VendorMenuAdapter adapter = new VendorMenuAdapter(allItems);
        recycler_menu.setAdapter(adapter);

    }



}
