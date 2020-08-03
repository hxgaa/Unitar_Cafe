package com.unitarcafe.hegaa.unitarcafe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.unitarcafe.hegaa.unitarcafe.Adapters.OrderStatusAdapter;
import com.unitarcafe.hegaa.unitarcafe.Common.Common;
import com.unitarcafe.hegaa.unitarcafe.Model.Items;
import com.unitarcafe.hegaa.unitarcafe.Model.Request;
import com.unitarcafe.hegaa.unitarcafe.ViewHolder.OrderViewHolder;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class OrderStatus extends AppCompatActivity {

    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;


    FirebaseRecyclerAdapter<Request, OrderViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status);

        //Firebase
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference requests = database.getReference("orders");
        final List<Request> requestList = new ArrayList<>();

        recyclerView = findViewById(R.id.listOrder);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        requests.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot requestOrders : dataSnapshot.getChildren()) {
                        System.out.println("Requests: "+requestOrders.toString());
                        Request ord = requestOrders.getValue(Request.class);
                        requestList.add(new Request(ord.getOrderID(), ord.getUser(), ord.getTotal(), ord.getFoods()));
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
        //I change the value from
//        loadOrders(Common.currentUser.getEmail());
    }

//    loadOrders() method
//    private void loadOrders(String email) {
////        Query query = FirebaseDatabase.getInstance()
////                .getReference("Requests");
////
////        FirebaseRecyclerOptions<Request> options =
////                new FirebaseRecyclerOptions.Builder<Request>()
////                        .setQuery(query, new SnapshotParser<Request>() {
////                            @NonNull
////                            @Override
////                            public Request parseSnapshot(@NonNull DataSnapshot snapshot) {
////                                return new Request (snapshot.child("phone").getValue().toString(),
////                                        snapshot.child("name").getValue().toString(),
////                                        snapshot.child("address").getValue().toString(),
////                                        snapshot.child("total").getValue().toString(),
////                                        snapshot.child("total").getValue().toString()
////                                );
////                            }
////                        })
////                        .build();
//        adapter = new FirebaseRecyclerAdapter<Request, OrderViewHolder>(
//                Request.class, R.layout.item_list, FoodViewHolder.class, requests
//        ) {
//            @Override
//            protected void populateViewHolder(OrderViewHolder viewHolder, Request model, int position) {
//                viewHolder.txtOrderId.setText(adapter.getRef(position).getKey());
//                viewHolder.txtOrderStatus.setText(convertCodeToStatus(model.getStatus()));
//                viewHolder.txtOrderAddress.setText(model.getAddress());
//                viewHolder.txtOrderPhone.setText(model.getUser().getEmail());
//            }
//        };
//        Toast.makeText(this, "Value: ", Toast.LENGTH_SHORT).show();
//        recyclerView.setAdapter(adapter);
//    }

    private String convertCodeToStatus(String status) {
        if (status.equals("0")){
            return "Placed";
        }
        else if(status.equals("1")){
            return "On my way";
        }
        else {
            return "Shipped";
        }
    }
}
