package com.unitarcafe.hegaa.unitarcafe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.unitarcafe.hegaa.unitarcafe.Adapters.OrderStatusAdapter;
import com.unitarcafe.hegaa.unitarcafe.Common.Common;
import com.unitarcafe.hegaa.unitarcafe.Model.Request;
import com.unitarcafe.hegaa.unitarcafe.ViewHolder.OrderViewHolder;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class OrderStatus extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Orders");
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        //Set Name for user
        View headerView = navigationView.getHeaderView(0);
        TextView txtFullName = headerView.findViewById(R.id.text_fullName);
        txtFullName.setText("Welcome, "+ Common.currentUser.getName());

        //Firebase
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference requests = database.getReference("orders");
        final List<Request> requestList = new ArrayList<>();

        recyclerView = findViewById(R.id.listOrder);
//        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        requests.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot requestOrders : dataSnapshot.getChildren()) {
                        System.out.println("Requests: "+requestOrders.toString());
                        Request ord = requestOrders.getValue(Request.class);
                        requestList.add(new Request(ord.getOrderId(), ord.getUser(), ord.getTotal(), ord.getFoods()));
                    }
                    OrderStatusAdapter osAdapted = new OrderStatusAdapter(requestList);
                    recyclerView.setAdapter(osAdapted);
                } else {
                    Toast.makeText(OrderStatus.this, "Datasnapshot missing!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_menu) {
            // Handle the camera action
        } else if (id == R.id.nav_cart) {
            Intent cartIntent = new Intent(OrderStatus.this, Cart.class);
            startActivity(cartIntent);

        } else if (id == R.id.nav_order) {
            Intent orderIntent = new Intent(OrderStatus.this, OrderStatus.class);
            startActivity(orderIntent);

        } else if (id == R.id.nav_log_out) {
            //Logout
            Intent signIn = new Intent(OrderStatus.this, MainActivity.class);
            signIn.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(signIn);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
