package com.unitarcafe.hegaa.unitarcafe;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.unitarcafe.hegaa.unitarcafe.Common.Common;
import com.unitarcafe.hegaa.unitarcafe.Model.Items;
import com.unitarcafe.hegaa.unitarcafe.Model.Order;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FoodDetails extends AppCompatActivity {

    TextView foodName, foodPrice, foodDescription, foodDiscount;
    ImageView foodImage;
    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton btnCart;
    ElegantNumberButton numberButton;

    String itemId = "";

    //Firebase Database
    FirebaseDatabase database;
    DatabaseReference items, cart;
    FirebaseStorage storage;
    StorageReference imageStorage, imgPath;

    Items currentFood;

    List<Items> allItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_details);

        //Firebase
        database = FirebaseDatabase.getInstance();
        items = database.getReference("menu");
        storage = FirebaseStorage.getInstance("gs://unitarcafe.appspot.com/");
        imageStorage = storage.getReference().child("itemImages");
        cart = database.getReference("cartItems");

        //Init view
        numberButton = (ElegantNumberButton)findViewById(R.id.number_button);
        btnCart = findViewById(R.id.btn_Cart);
        final String userID = Common.currentUser.getUserID();

        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cart.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        System.out.println("Entered datasnp: "+dataSnapshot.toString());
                        DataSnapshot curUserSnapshot = dataSnapshot.child(userID).child("items");
                        System.out.println("User 1:"+userID);
                        System.out.println("User datasnp: "+curUserSnapshot.toString());
                        if (curUserSnapshot.hasChildren()) {
                            Order cartItem = null;
                            String itemKey = null;
                            int currentCount = 0;
                            for (DataSnapshot item : curUserSnapshot.getChildren()) {
                                cartItem = item.getValue(Order.class);
                                System.out.println("Item :"+itemId);
                                System.out.println("Children :"+curUserSnapshot.getChildrenCount());
                                if (cartItem.getProduName().equals(itemId)) {
                                    itemKey = item.getKey();
                                    currentCount = Integer.parseInt(cartItem.getQuantity());
                                    int newTotal = currentCount + Integer.parseInt(numberButton.getNumber());
                                    cartItem.setQuantity(String.valueOf(newTotal));
                                    System.out.println("User 2:"+userID);
                                    cart.child(userID).child("items").child(itemKey).setValue(cartItem);
                                    Toast.makeText(FoodDetails.this, "Modified at cart", Toast.LENGTH_SHORT).show();
                                } else {
                                    Order newOrder = new Order(itemId, numberButton.getNumber(), foodPrice.getText().toString(), foodDiscount.getText().toString());
                                    String randomItemID = UUID.randomUUID().toString();
                                    cart.child(userID).child("items").child(randomItemID).setValue(newOrder);
                                    Toast.makeText(FoodDetails.this, "Added to cart", Toast.LENGTH_SHORT).show();
                                }
                                break;
                            }

                        } else {
                            Order newOrder = new Order(itemId, numberButton.getNumber(), foodPrice.getText().toString(), foodDiscount.getText().toString());
                            String randomItemID = UUID.randomUUID().toString();
                            cart.child(userID).child("items").child(randomItemID).setValue(newOrder);
                            Toast.makeText(FoodDetails.this, "Added to cart", Toast.LENGTH_SHORT).show();
                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });

        foodDescription = findViewById(R.id.food_description);
        foodName = findViewById(R.id.food_name);
        foodPrice = findViewById(R.id.food_price);
        foodDiscount = findViewById(R.id.food_discount);
        foodImage = findViewById(R.id.food_image);

        collapsingToolbarLayout = findViewById(R.id.collapsing);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppbar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapseAppbar);

        //Get Food Id from Intent
        if (getIntent() != null){
            itemId = getIntent().getStringExtra("ItemId");
        }
        if(!itemId.isEmpty()){
            getDetailsFood(itemId);
        }else {
            Toast.makeText(this, "Error: foodId = "+ itemId, Toast.LENGTH_SHORT).show();
        }
    }

    // getDetailsFood() method
    private void getDetailsFood(final String foodId) {
        items.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
//                System.out.println("DS1: "+dataSnapshot.toString());
                for (DataSnapshot item: dataSnapshot.getChildren()) {
//                    System.out.println("DS2: "+item.toString());
                    for (DataSnapshot specificItem : item.getChildren()) {
//                        System.out.println("DS3: "+specificItem.toString());
                         currentFood = specificItem.getValue(Items.class);
                         allItems.add(new Items(currentFood.getName(), currentFood.getDescription(), currentFood.getPrice(), currentFood.getDiscount(), currentFood.getImage()));


                    }

                }
                for (Items item: allItems) {
                    if (item.getName().equals(foodId)) {
                        System.out.println("Food :"+foodId);
                        double price = Double.parseDouble(item.getPrice());
                        collapsingToolbarLayout.setTitle(item.getName());
                        foodPrice.setText(String.format("%.2f", price));
                        foodDiscount.setText(item.getDiscount());
                        foodName.setText(item.getName());
                        foodDescription.setText(item.getDescription());
                        imgPath = imageStorage.child(item.getImage());
                        final long ONE_MEGABYTE = 1024 * 1024;
                        imgPath.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                            @Override
                            public void onSuccess(byte[] bytes) {
                                Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                foodImage.setImageBitmap(bmp);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                foodImage.setImageResource(R.drawable.ic_restaurant_black);
                            }
                        });
                        break;
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }
}
