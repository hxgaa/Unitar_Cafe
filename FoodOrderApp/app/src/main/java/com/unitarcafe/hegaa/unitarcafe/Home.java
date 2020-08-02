package com.unitarcafe.hegaa.unitarcafe;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.unitarcafe.hegaa.unitarcafe.Common.Common;
import com.unitarcafe.hegaa.unitarcafe.Interface.ItemClickListener;
import com.unitarcafe.hegaa.unitarcafe.Model.Menu;
import com.unitarcafe.hegaa.unitarcafe.ViewHolder.MenuViewHolder;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "FoodList";

    FirebaseDatabase database;
    DatabaseReference menu;

    //Name in top of navigation drawer
    TextView txtFullName;

    RecyclerView recycler_menu;
    RecyclerView.LayoutManager layoutManager;

    FirebaseRecyclerAdapter<Menu, MenuViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Menu");
        setSupportActionBar(toolbar);

        //Init Firebase
        database = FirebaseDatabase.getInstance();
        menu = database.getReference("menu");


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cartIntent = new Intent(Home.this, Cart.class);
                startActivity(cartIntent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        //Set Name for user
        View headerView = navigationView.getHeaderView(0);
        txtFullName = headerView.findViewById(R.id.text_fullName);
        txtFullName.setText("Welcome, "+Common.currentUser.getName());

        //Load menu
        recycler_menu = findViewById(R.id.recycler_menu);
        recycler_menu.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recycler_menu.setLayoutManager(layoutManager);

        loadMenu();
    }

    //
    private void loadMenu() {
        adapter = new
                FirebaseRecyclerAdapter<Menu, MenuViewHolder>
                        (Menu.class, R.layout.menu_item, MenuViewHolder.class, menu) {
            @Override
            protected void populateViewHolder(MenuViewHolder viewHolder, Menu model, int position) {
                viewHolder.txtMenuName.setText(adapter.getRef(position).getKey());
                if (adapter.getRef(position).getKey().equals("drinks")) {
                    viewHolder.imageView.setImageResource(R.mipmap.ic_bannerdrinks_foreground);
                } else if (adapter.getRef(position).getKey().equals("food")) {
                    viewHolder.imageView.setImageResource(R.mipmap.ic_bannerfood_foreground);
                }
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        //Get CategoryId and send to FoodList Activity
                        Intent foodList = new Intent(Home.this, FoodList.class);
                        //Because CategoryId is key, so we just get key of this item
                        foodList.putExtra("CategoryId", adapter.getRef(position).getKey());
                        Log.d(TAG, "HomeActivity Value of Key: "+adapter.getRef(position).getKey());
                        Toast.makeText(Home.this, "Value of Key: "+adapter.getRef(position).getKey(), Toast.LENGTH_SHORT).show();
                        startActivity(foodList);
                    }
                });
            }
        };
        recycler_menu.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_menu) {
            // Handle the camera action
        } else if (id == R.id.nav_cart) {
            Intent cartIntent = new Intent(Home.this, Cart.class);
            startActivity(cartIntent);

        } else if (id == R.id.nav_order) {
            Intent orderIntent = new Intent(Home.this, OrderStatus.class);
            startActivity(orderIntent);

        } else if (id == R.id.nav_log_out) {
            //Logout
            Intent signIn = new Intent(Home.this, MainActivity.class);
            signIn.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(signIn);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
