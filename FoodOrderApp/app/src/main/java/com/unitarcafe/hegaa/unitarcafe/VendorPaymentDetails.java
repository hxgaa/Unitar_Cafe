package com.unitarcafe.hegaa.unitarcafe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
import com.unitarcafe.hegaa.unitarcafe.Model.Order;
import com.unitarcafe.hegaa.unitarcafe.Model.Payment;
import com.unitarcafe.hegaa.unitarcafe.Model.Request;

import java.util.ArrayList;
import java.util.List;

public class VendorPaymentDetails extends AppCompatActivity {

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference items = database.getReference("payments");
    final List<Payment> orderList = new ArrayList<>();
    TextView orderId, orderUser, orderStatus, orderTotal, orderDate;
    Button btnCancel, btnComplete;

    private String itemId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_paymentdetails);
        androidx.appcompat.widget.Toolbar toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.vendorToolbar);
        toolbar.setTitle("Manage Orders");
        setSupportActionBar(toolbar);
        final TabLayout tabs = (TabLayout) findViewById(R.id.tabs);
        TabLayout.Tab tab = tabs.getTabAt(1);
        tab.select();


        orderId = (TextView) findViewById(R.id.orderID);
        orderUser = (TextView) findViewById(R.id.orderUserEmail);
        orderStatus = (TextView) findViewById(R.id.orderStatus);
        orderTotal = (TextView) findViewById(R.id.orderTotal);
        orderDate = (TextView) findViewById(R.id.orderPDate);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnComplete = (Button) findViewById(R.id.btnComplete);


        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
//                Toast.makeText(VendorOrderDetails.this, "Vendor Orders", Toast.LENGTH_SHORT).show();
                int selectedTabPosition = tabs.getSelectedTabPosition();
                if (selectedTabPosition == 0) {

                    Intent foods = new Intent(VendorPaymentDetails.this, VendorHome.class);
                    startActivity(foods);
                    finish();

                } else if (selectedTabPosition == 2) {
                    Intent foods = new Intent(VendorPaymentDetails.this, VendorFoods.class);
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
                finish();
            }
        });

        btnComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent specificItem = new Intent(VendorPaymentDetails.this, VendorOrderDetails.class);
                //Because CategoryId is key, so we just get key of this item
                specificItem.putExtra("itemId", itemId);
                specificItem.putExtra("disable", true);
                startActivity(specificItem);

            }
        });

        if (getIntent() != null) {
            itemId = getIntent().getStringExtra("itemId");
            if (!itemId.equals("")) {
                getItems(itemId);
            }
        }
    }

    private void getItems(final String itemId) {
        items.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    Payment payData = data.getValue(Payment.class);
                    if (payData.getOrderID().equals(itemId)) {
                        orderId.setText(payData.getOrderID());
                        orderUser.setText(payData.getCustEmail());
                        orderTotal.setText(payData.getTotal());
                        orderDate.setText(payData.getDatetime());
                        if (payData.getStatus()) {
                            orderStatus.setText("PAID");
                        } else {
                            orderStatus.setText("UNPAID");
                        }
                        break;
                    }
                }

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
