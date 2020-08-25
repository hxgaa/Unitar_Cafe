package com.unitarcafe.hegaa.unitarcafe;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.unitarcafe.hegaa.unitarcafe.Adapters.VendorOrderCompletedAdapter;
import com.unitarcafe.hegaa.unitarcafe.Adapters.VendorOrderPendingAdapter;
import com.unitarcafe.hegaa.unitarcafe.Model.Payment;
import com.unitarcafe.hegaa.unitarcafe.Model.Request;

import java.util.ArrayList;
import java.util.List;

public class VendorOrders extends AppCompatActivity {

    public RecyclerView recyclerPending, recyclerCompleted;
    public RecyclerView.LayoutManager layoutManager1, layoutManager2;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference requests = database.getReference("orders");
    final DatabaseReference payments = database.getReference("payments");
    final List<Request> pendingList = new ArrayList<>();
    final List<Payment> completedList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_orders);
        androidx.appcompat.widget.Toolbar toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.vendorToolbar);
        toolbar.setTitle("Manage Orders");
        setSupportActionBar(toolbar);
        final TabLayout tabs = (TabLayout) findViewById(R.id.tabs);
        TabLayout.Tab tab = tabs.getTabAt(1);
        tab.select();

        recyclerPending = findViewById(R.id.recyclerPendingOrder);
        recyclerPending.setHasFixedSize(true);
        recyclerCompleted = findViewById(R.id.recyclerCompletedOrder);
        recyclerCompleted.setHasFixedSize(true);
        layoutManager1 = new LinearLayoutManager(this);
        layoutManager2 = new LinearLayoutManager(this);
        recyclerPending.setLayoutManager(layoutManager1);
        recyclerCompleted.setLayoutManager(layoutManager2);

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Toast.makeText(VendorOrders.this, "Vendor Orders", Toast.LENGTH_SHORT).show();
                int selectedTabPosition = tabs.getSelectedTabPosition();
                if (selectedTabPosition == 0) {

                    Intent foods = new Intent(VendorOrders.this, VendorHome.class);
                    startActivity(foods);
                    finish();

                } else if (selectedTabPosition == 2) {
                    Intent foods = new Intent(VendorOrders.this, VendorFoods.class);
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

        getItems();


    }

    private void getItems() {
        requests.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot item: dataSnapshot.getChildren()) {
                        Request orderItem = item.getValue(Request.class);
                        if (!orderItem.getStatus()) {
                            pendingList.add(new Request(
                                    orderItem.getOrderId(),
                                    orderItem.getUser(),
                                    orderItem.getTotal(),
                                    orderItem.getFoods(),
                                    orderItem.getStatus()
                            ));
                        }

                }
                VendorOrderPendingAdapter adapter = new VendorOrderPendingAdapter(pendingList);
                recyclerPending.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        payments.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot item: dataSnapshot.getChildren()) {
                    Payment orderItem = item.getValue(Payment.class);
                    completedList.add(new Payment(
                            orderItem.getCustEmail(),
                            orderItem.getCustName(),
                            orderItem.getOrderID(),
                            orderItem.getDatetime(),
                            orderItem.getTotal()
                    ));

                }
                VendorOrderCompletedAdapter adapter = new VendorOrderCompletedAdapter(completedList);
                recyclerCompleted.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent foods = new Intent(VendorOrders.this, VendorHome.class);
        startActivity(foods);
        finish();
    }
}
