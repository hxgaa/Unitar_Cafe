package com.unitarcafe.hegaa.unitarcafe;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.unitarcafe.hegaa.unitarcafe.Model.Request;
import com.unitarcafe.hegaa.unitarcafe.ViewHolder.OrderViewHolder;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class OrderStatus extends AppCompatActivity {

    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;

    //Firebase Database
    FirebaseDatabase database;
    DatabaseReference requests;

    FirebaseRecyclerAdapter<Request, OrderViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status);

        //Fitebase
        database = FirebaseDatabase.getInstance();
        requests = database.getReference("Requests");

        recyclerView = findViewById(R.id.listOrder);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //I change the value from
//        loadOrders(Common.currentUser.getPhone());
    }

    //loadOrders() method
//    private void loadOrders(String phone) {
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
//                viewHolder.txtOrderPhone.setText(model.getPhone());
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
