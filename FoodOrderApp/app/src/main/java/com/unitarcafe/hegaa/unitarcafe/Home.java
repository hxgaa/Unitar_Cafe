package com.unitarcafe.hegaa.unitarcafe;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.unitarcafe.hegaa.unitarcafe.Adapters.MenuAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.unitarcafe.hegaa.unitarcafe.Adapters.NotificationsAdapter;
import com.unitarcafe.hegaa.unitarcafe.Common.Common;
import com.unitarcafe.hegaa.unitarcafe.Model.Menu;
import com.unitarcafe.hegaa.unitarcafe.Model.Notification;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    //Name in top of navigation drawer
    TextView txtFullName;

    RecyclerView recycler_menu, recyclerItems;
    RecyclerView.LayoutManager layoutManager, layoutManager1;
    final FirebaseDatabase  database = FirebaseDatabase.getInstance();
    final DatabaseReference notDB = database.getReference("notifications/client");
    List<Notification> allNotifications = new ArrayList<>();
    FloatingActionButton btnNoti;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Menu");
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
        txtFullName = headerView.findViewById(R.id.text_fullName);
        txtFullName.setText("Welcome, "+Common.currentUser.getName());
        btnNoti = (FloatingActionButton) findViewById(R.id.notificationButton);

        //Load menu
        recycler_menu = findViewById(R.id.recycler_menu);
        recycler_menu.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recycler_menu.setLayoutManager(layoutManager);

        // Setting recycler adapter
        MenuAdapter menuAdapter = new MenuAdapter(generateMenuList());
        recycler_menu.setAdapter(menuAdapter);

        btnNoti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dialog dialog = new Dialog(Home.this);
                dialog.setContentView(R.layout.activity_notification);
                dialog.setTitle("Notifications");
                dialog.setCancelable(true);
                recyclerItems = dialog.findViewById(R.id.recyclerNotification);
                recyclerItems.setHasFixedSize(true);
                layoutManager1 = new LinearLayoutManager(dialog.getContext());
                recyclerItems.setLayoutManager(layoutManager1);
                allNotifications.clear();
                notDB.addListenerForSingleValueEvent(new ValueEventListener() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for (DataSnapshot item: dataSnapshot.getChildren()) {
                            for (DataSnapshot childItem : item.getChildren()) {
                                if (item.getKey().equals(Common.currentUser.getUserID())) {
                                    Notification noti = childItem.getValue(Notification.class);
                                    allNotifications.add(new Notification(
                                            childItem.getKey(),
                                            noti.getTitle(),
                                            noti.getStatus(),
                                            noti.getMessage()
                                    ));
                                }
                            }
                        }
                        NotificationsAdapter adapter = new NotificationsAdapter(allNotifications);
                        recyclerItems.setAdapter(adapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                dialog.show();

            }
        });

        checkVendorNotification();

    }

    // Manually creating the first menu and attaching it to Menu Adapter
    private List<Menu> generateMenuList() {
        List<Menu> menuList = new ArrayList<>();

        menuList.add(new Menu("drinks"));
        menuList.add(new Menu("food"));

        for (int i=0;i<menuList.size();i++) {
            System.out.println("Menu: " + menuList.get(i).getName());
        }
        return menuList;
    }

    public void checkVendorNotification() {
        notDB.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot item: dataSnapshot.getChildren()) {
                    for (DataSnapshot childItem : item.getChildren()) {
                        if (childItem.getKey().equals(Common.currentUser.getUserID())) {
                            Notification noti = childItem.getValue(Notification.class);
                            allNotifications.add(new Notification(
                                    item.getKey(),
                                    noti.getTitle(),
                                    noti.getStatus(),
                                    noti.getMessage()
                            ));
                        }
                    }
                }
                btnNoti.getDrawable().mutate().setTint(getResources().getColor(R.color.red));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

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
