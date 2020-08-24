package com.unitarcafe.hegaa.unitarcafe;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.unitarcafe.hegaa.unitarcafe.Model.Items;

import java.util.List;

public class VendorFoodItem extends AppCompatActivity {
    Toolbar food, order, home;
    TextView txtItemName, txtItemPrice, txtItemDiscount, txtItemDesc;
    ImageView imgItem;
    Button btnSaveItem, btnDeleteItem, btnEdit;
    List<Items> allItems;

    String itemID = "";

    //Firebase Database
    FirebaseDatabase database;
    DatabaseReference items;
    FirebaseStorage storage;
    StorageReference imageStorage, imgPath;

    Items currentFood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vendor_foodview);
        home = findViewById(R.id.tabHome);
        food = findViewById(R.id.tabFoods);
        order = findViewById(R.id.tabOrders);
        txtItemName = (TextView) findViewById(R.id.txtItemName);
        txtItemPrice = (TextView) findViewById(R.id.txtItemPrice);
        txtItemDiscount = (TextView) findViewById(R.id.txtItemDiscount);
        txtItemDesc = (TextView) findViewById(R.id.txtItemDesc);
        imgItem = (ImageView) findViewById(R.id.imageItemView);
        btnSaveItem = (Button) findViewById(R.id.btnSaveItem);
        btnDeleteItem = (Button) findViewById(R.id.btnDelItem);
        btnEdit = (Button) findViewById(R.id.btnEditItem);


        //Firebase
        database = FirebaseDatabase.getInstance();
        items = database.getReference("menu");
        storage = FirebaseStorage.getInstance("gs://unitarcafe.appspot.com/");
        imageStorage = storage.getReference().child("itemImages");

        btnSaveItem.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        }));

        btnDeleteItem.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        }));

        btnEdit.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        }));

        //Get Food Id from Intent
        if (getIntent() != null){
            itemID = getIntent().getStringExtra("itemId");
        }
        if(!itemID.isEmpty()){
            getItemDetails(itemID);
        }else {
            Toast.makeText(this, "Error: foodId = "+ itemID, Toast.LENGTH_SHORT).show();
        }

    }

    private void getItemDetails(final String foodId) {
        items.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot item: dataSnapshot.getChildren()) {
//                    System.out.println("DS2: "+item.toString());
                    for (DataSnapshot specificItem : item.getChildren()) {
//                        System.out.println("DS3: "+specificItem.toString());
                        currentFood = specificItem.getValue(Items.class);
                        allItems.add(currentFood);
//                        System.out.println("Item: "+currentFood.getName());

                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        for (Items item: allItems) {
            if (item.getName().equals(foodId)) {
                System.out.println("Food :"+foodId);
                txtItemPrice.setText(item.getPrice());
                txtItemDiscount.setText(item.getDiscount());
                txtItemName.setText(item.getName());
                txtItemDesc.setText(item.getDescription());
                imgPath = imageStorage.child(item.getImage());
                Glide.with(VendorFoodItem.this).load(imgPath).into(imgItem);
                break;
            }
        }

    }
}
