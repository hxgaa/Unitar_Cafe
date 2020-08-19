package com.unitarcafe.hegaa.unitarcafe;

import android.content.DialogInterface;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.unitarcafe.hegaa.unitarcafe.Common.Common;
import com.unitarcafe.hegaa.unitarcafe.Model.Order;
import com.unitarcafe.hegaa.unitarcafe.Model.Request;
import com.unitarcafe.hegaa.unitarcafe.Adapters.CartAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Cart extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    TextView textTotalPrice;
    Button btnPlace;
    String uniqueId;

    List<Order> cart = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        //init
        recyclerView = findViewById(R.id.listCart);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        textTotalPrice = findViewById(R.id.total_price);
        btnPlace = findViewById(R.id.btn_place_order);
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference orderQue = database.getReference("cartItems").child(Common.currentUser.getUserID());
        final DatabaseReference requests = database.getReference("orders");

        btnPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uniqueId = UUID.randomUUID().toString();
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(Cart.this);
                alertDialog.setTitle("One more step!");
                alertDialog.setMessage("Enter your table No: ");

                final EditText editTableNo = new EditText(Cart.this);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT
                );
                editTableNo.setLayoutParams(lp);
                alertDialog.setView(editTableNo); //Add edit Text to aleart dialog
                alertDialog.setIcon(R.drawable.ic_shopping_cart);

                alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //Create new Request
                        Request request = new Request(
                                uniqueId,
                                Common.currentUser,
                                textTotalPrice.getText().toString(),
                                cart
                        );

                        //Submit to Firebase
                        //We will using System.CurrentMills to key
                        requests.child(uniqueId)
                                .setValue(request);

                        Toast.makeText(Cart.this, "Thank you, Order Place.", Toast.LENGTH_SHORT).show();

                        finish();
                    }
                });

                alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                alertDialog.show();

                requests.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });


        orderQue.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    DataSnapshot currentItems = dataSnapshot.child("items");
                    for (DataSnapshot item : currentItems.getChildren()) {
                        Order order = item.getValue(Order.class);
                        cart.add(order);
                    }
                    CartAdapter adapter = new CartAdapter(cart);
                    recyclerView.setAdapter(adapter);

                    //Calculate total price
                    double total= 0, discPrice, itemPrice;
//                    int disc = 0;
                    for (int i=0;i<cart.size();i++){
                        if (Integer.parseInt(cart.get(i).getDiscount()) ==0) {
                            discPrice = Double.parseDouble(cart.get(i).getPrice());
                        } else {
                            int discount = Integer.parseInt(cart.get(i).getDiscount());
                            String dsc;
                            if (discount < 10) {
                                dsc = "0.0"+discount;
                            } else {
                                dsc  = "0."+discount;
                            }
                            discPrice = (Double.parseDouble(cart.get(i).getPrice()))-(Double.parseDouble(cart.get(i).getPrice())*Double.parseDouble(dsc));
                        }
                        itemPrice = discPrice*Integer.parseInt(cart.get(i).getQuantity());
                        total = total + itemPrice;
                    }
                    textTotalPrice.setText(String.format("%.2f", total));
                }else {
                    Toast.makeText(Cart.this, "Datasnapshot missing!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}
