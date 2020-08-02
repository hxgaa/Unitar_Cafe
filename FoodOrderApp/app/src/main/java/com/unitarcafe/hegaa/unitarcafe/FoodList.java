package com.unitarcafe.hegaa.unitarcafe;

import android.content.Intent;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.unitarcafe.hegaa.unitarcafe.Common.Common;
import com.unitarcafe.hegaa.unitarcafe.Interface.ItemClickListener;
import com.unitarcafe.hegaa.unitarcafe.Model.Foods;
import com.unitarcafe.hegaa.unitarcafe.ViewHolder.FoodViewHolder;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class FoodList extends AppCompatActivity {

    private static final String TAG = "FoodList";

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference foodList;

    String categoryId = "";

    //FirebaseRecyclerAdapter
    FirebaseRecyclerAdapter<Foods, FoodViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);

        //Firebase
        database = FirebaseDatabase.getInstance();
        foodList = database.getReference().child(getIntent().getStringExtra("CategoryId"));
        System.out.println("Category: "+getIntent().getStringExtra("CategoryId"));


        recyclerView = findViewById(R.id.recycler_food);
        //recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //Get intent here from Home_Activity to get CategoryId
//        if (getIntent() != null){
//            foodList.child(getIntent().getStringExtra("CategoryId"));
//        }

//        if(!categoryId.isEmpty() && categoryId != null){
//            loadListFood(categoryId);
//        }
        System.out.println("PASS1");
        loadListFood();

    }

    //loadadListFood() method implementation
    private void loadListFood() {
        System.out.println("PASS2");
        adapter = new
                FirebaseRecyclerAdapter<Foods, FoodViewHolder>
                        (Foods.class, R.layout.food_item, FoodViewHolder.class,
//                                foodList
                foodList.orderByChild(getIntent().getStringExtra("CategoryId")) ////Like : Select * from Foods where MenuId =
                ){
            @Override
            protected void populateViewHolder(FoodViewHolder viewHolder, Foods model, int position) {
                System.out.println("Key: "+adapter.getRef(position).getKey());
                viewHolder.food_name.setText(adapter.getRef(position).getKey());
                Picasso.get().load(model.getImage()).into(viewHolder.food_image);

                final Foods local = model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        //Start new Activity
                        Intent foodDetail = new Intent(FoodList.this, FoodDetails.class);
                        //Save food id to activity
                        foodDetail.putExtra("FoodId", adapter.getRef(position).getKey());
                        startActivity(foodDetail);
                    }
                });
            }
        };

        //Set Adapter
        Log.d(TAG, "loadadListFood: "+adapter.getItemCount());
        Toast.makeText(this, "loadadListFood: "+adapter.getItemCount(), Toast.LENGTH_SHORT).show();
        recyclerView.setAdapter(adapter);
    }
}
