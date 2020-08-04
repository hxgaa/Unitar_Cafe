package com.unitarcafe.hegaa.unitarcafe;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.unitarcafe.hegaa.unitarcafe.Database.Database;
import com.unitarcafe.hegaa.unitarcafe.Model.Items;
import com.unitarcafe.hegaa.unitarcafe.Model.Order;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FoodDetails extends AppCompatActivity {

    TextView foodName, foodPrice, foodDescription;
    ImageView foodImage;
    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton btnCart;
    ElegantNumberButton numberButton;

    String itemId = "";

    //Firebase Database
    FirebaseDatabase database;
    DatabaseReference items;

    Items currentFood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_details);

        //Firebase
        database = FirebaseDatabase.getInstance();
        items = database.getReference("menu");

        //Init view
        numberButton = (ElegantNumberButton)findViewById(R.id.number_button);
        btnCart = findViewById(R.id.btn_Cart);

        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Database(getBaseContext()).addToCart(new Order(
                        itemId,
                        numberButton.getNumber(),
                        currentFood.getPrice(),
                        currentFood.getDiscount()
                ));
                Toast.makeText(FoodDetails.this, "Add to cart", Toast.LENGTH_SHORT).show();
            }
        });

        foodDescription = findViewById(R.id.food_description);
        foodName = findViewById(R.id.food_name);
        foodPrice = findViewById(R.id.food_price);
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
        items.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
//                System.out.println("DS1: "+dataSnapshot.toString());
                for (DataSnapshot item: dataSnapshot.getChildren()) {
//                    System.out.println("DS2: "+item.toString());
                    for (DataSnapshot specificItem : item.getChildren()) {
//                        System.out.println("DS3: "+specificItem.toString());
                         currentFood = specificItem.getValue(Items.class);
//                        System.out.println("Item: "+currentFood.getName());
                        if (currentFood.getName().equals(foodId)) {

//                            Picasso.get().load(currentFood.getImage()).into(foodImage);
                            if (currentFood.getImage().equals("teh_tarik.jpg")) {
                                foodImage.setImageResource(R.mipmap.ic_tehtarik_foreground);
                            } else if (currentFood.getImage().equals("nasi_goreng.jpg")) {
                                foodImage.setImageResource(R.mipmap.ic_nasigoreng_foreground);
                            } else if (currentFood.getImage().equals("roti_canai.jpg")) {
                                foodImage.setImageResource(R.mipmap.ic_roticanai_foreground);
                            } else if (currentFood.getImage().equals("maggi_goreng.jpg")) {
                                foodImage.setImageResource(R.mipmap.ic_maggigoreng_foreground);
                            }
                            collapsingToolbarLayout.setTitle(currentFood.getName());
                            foodPrice.setText(String.valueOf(currentFood.getPrice()));
                            foodName.setText(currentFood.getName());
                            foodDescription.setText(currentFood.getDescription());
                        }
                    }

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
