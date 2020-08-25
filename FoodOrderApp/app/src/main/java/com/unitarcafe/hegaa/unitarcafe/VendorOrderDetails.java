package com.unitarcafe.hegaa.unitarcafe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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
import com.unitarcafe.hegaa.unitarcafe.Adapters.VendorOrderListAdapter;
import com.unitarcafe.hegaa.unitarcafe.Common.Common;
import com.unitarcafe.hegaa.unitarcafe.Model.Notification;
import com.unitarcafe.hegaa.unitarcafe.Model.Order;
import com.unitarcafe.hegaa.unitarcafe.Model.Payment;
import com.unitarcafe.hegaa.unitarcafe.Model.Request;
import com.unitarcafe.hegaa.unitarcafe.Model.User;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class VendorOrderDetails extends AppCompatActivity {

    public RecyclerView recyclerItems;
    public RecyclerView.LayoutManager layoutManager;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference items = database.getReference("orders");
    DatabaseReference payments = database.getReference("payments");
    final DatabaseReference clientNotification = database.getReference("notifications/client");
    final List<Order> orderList = new ArrayList<>();
    TextView orderId, orderUser, orderStatus, orderTotal;
    Button btnCancel, btnComplete;

    private String itemId;
    private boolean disable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_orderdetails);
        androidx.appcompat.widget.Toolbar toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.vendorToolbar);
        toolbar.setTitle("Manage Orders");
        setSupportActionBar(toolbar);
        final TabLayout tabs = (TabLayout) findViewById(R.id.tabs);
        TabLayout.Tab tab = tabs.getTabAt(1);
        tab.select();

        recyclerItems = findViewById(R.id.singleItemList);
        recyclerItems.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerItems.setLayoutManager(layoutManager);

        orderId = (TextView) findViewById(R.id.orderID);
        orderUser = (TextView) findViewById(R.id.orderUserEmail);
        orderStatus = (TextView) findViewById(R.id.orderStatus);
        orderTotal = (TextView) findViewById(R.id.orderTotal);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnComplete = (Button) findViewById(R.id.btnComplete);


        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
//                Toast.makeText(VendorOrderDetails.this, "Vendor Orders", Toast.LENGTH_SHORT).show();
                int selectedTabPosition = tabs.getSelectedTabPosition();
                if (selectedTabPosition == 0) {

                    Intent foods = new Intent(VendorOrderDetails.this, VendorHome.class);
                    startActivity(foods);
                    finish();

                } else if (selectedTabPosition == 2) {
                    Intent foods = new Intent(VendorOrderDetails.this, VendorFoods.class);
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

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                items.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        items.child(orderId.getText().toString()).child("status").setValue(false);
                        Notification clientNotif = new Notification("Order Cancelled", "Your order, Order ID: "+orderId.getText().toString()+ " has been cancelled!");
                        String notiID = clientNotification.child(orderUser.getText().toString()).push().getKey();
                        clientNotification.child(orderUser.getText().toString()).child(notiID).setValue(clientNotif);
                        Toast.makeText(VendorOrderDetails.this, "Order Cancelled.", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        btnComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                items.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        items.child(orderId.getText().toString()).child("status").setValue(true);
                        User user = dataSnapshot.child(orderId.getText().toString()).child("user").getValue(User.class);
                        String diffUniqueId = UUID.randomUUID().toString();
                        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
                        Date date = new Date(System.currentTimeMillis());
                        Payment newPay = new Payment(
                                user.getEmail(),
                                orderUser.getText().toString(),
                                orderId.getText().toString(),
                                formatter.format(date),
                                orderTotal.getText().toString()
                        );
                        payments.child(diffUniqueId).setValue(newPay);
                        Notification clientNotif = new Notification("Order Ready", "Your order, Order ID: "+orderId.getText().toString()+ " is ready to be collected!");
                        String notiID = clientNotification.child(orderUser.getText().toString()).push().getKey();
                        clientNotification.child(orderUser.getText().toString()).child(notiID).setValue(clientNotif);
                        Toast.makeText(VendorOrderDetails.this, "Order Confirmed.", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        if (getIntent() != null) {
            itemId = getIntent().getStringExtra("itemId");
            disable = getIntent().getBooleanExtra("disable", false);
            if (!itemId.equals("")) {
                getItems(itemId);
            }
            if (disable) {
                btnCancel.setEnabled(false);
                btnComplete.setEnabled(false);
            }
        }
    }

    private void getItems(final String itemId) {
        items.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DataSnapshot curItem = dataSnapshot.child(itemId);
                Request orderItem = curItem.getValue(Request.class);
                for (Order ord : orderItem.getFoods()) {
                    orderList.add(new Order(
                            ord.getProduName(),
                            ord.getQuantity(),
                            ord.getPrice(),
                            ord.getDiscount()
                    ));
                }
                orderId.setText(orderItem.getOrderId());
                orderUser.setText(orderItem.getUser().getUserID());
                orderTotal.setText(orderItem.getTotal());
                if (orderItem.getStatus()) {
                    orderStatus.setText("COMPLETED");
                } else {
                    orderStatus.setText("PENDING");
                }

                VendorOrderListAdapter adapter = new VendorOrderListAdapter(orderList);
                recyclerItems.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
