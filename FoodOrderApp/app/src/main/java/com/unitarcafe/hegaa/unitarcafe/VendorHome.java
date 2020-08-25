package com.unitarcafe.hegaa.unitarcafe;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;


import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.unitarcafe.hegaa.unitarcafe.Adapters.NotificationsAdapter;
import com.unitarcafe.hegaa.unitarcafe.Common.Common;
import com.unitarcafe.hegaa.unitarcafe.Model.ChartModel;
import com.unitarcafe.hegaa.unitarcafe.Model.Notification;

import org.achartengine.chart.PointStyle;
//import org.achartengine.ChartFactory;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class VendorHome extends AppCompatActivity {

    TextView lblWelcome;
    FloatingActionButton btnNoti;
    public RecyclerView recyclerItems;
    public RecyclerView.LayoutManager layoutManager;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference notDB = database.getReference("notifications/vendor");
    List<Notification> allNotifications = new ArrayList<>();


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendorhome);
        Toolbar toolbar = (Toolbar) findViewById(R.id.vendorToolbar);
        toolbar.setTitle("Vendor Home");
        setSupportActionBar(toolbar);
        final TabLayout tabs = (TabLayout) findViewById(R.id.tabs);
        TabLayout.Tab tab = tabs.getTabAt(0);
        tab.select();

        lblWelcome = findViewById(R.id.lblWelcome);
        btnNoti = (FloatingActionButton) findViewById(R.id.notificationButton);

        lblWelcome.setText("Welcome, " + Common.currentUser.getName());

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Toast.makeText(VendorHome.this, "Vendor Home", Toast.LENGTH_SHORT).show();
                int selectedTabPosition = tabs.getSelectedTabPosition();
                if (selectedTabPosition == 2) {

                    Intent foods = new Intent(VendorHome.this, VendorFoods.class);
                    startActivity(foods);
                    finish();

                } else if (selectedTabPosition == 1) {

                    Intent foods = new Intent(VendorHome.this, VendorOrders.class);
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

        btnNoti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dialog dialog = new Dialog(VendorHome.this);
                dialog.setContentView(R.layout.activity_notification);
                dialog.setTitle("Notifications");
                dialog.setCancelable(true);
                recyclerItems = dialog.findViewById(R.id.recyclerNotification);
                recyclerItems.setHasFixedSize(true);
                layoutManager = new LinearLayoutManager(dialog.getContext());
                recyclerItems.setLayoutManager(layoutManager);
                allNotifications.clear();
                notDB.addListenerForSingleValueEvent(new ValueEventListener() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot item: dataSnapshot.getChildren()) {
                            Notification noti = item.getValue(Notification.class);
                            allNotifications.add(new Notification(
                                    item.getKey(),
                                    noti.getTitle(),
                                    noti.getStatus(),
                                    noti.getMessage()
                            ));
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

    public void checkVendorNotification() {
        notDB.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot item: dataSnapshot.getChildren()) {
                    Notification noti = item.getValue(Notification.class);
                    allNotifications.add(new Notification(
                            item.getKey(),
                            noti.getTitle(),
                            noti.getStatus(),
                            noti.getMessage()
                    ));
                }
                btnNoti.getDrawable().mutate().setTint(getResources().getColor(R.color.red));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


}
