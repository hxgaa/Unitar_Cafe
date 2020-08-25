package com.unitarcafe.hegaa.unitarcafe;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.unitarcafe.hegaa.unitarcafe.Common.Common;
import com.unitarcafe.hegaa.unitarcafe.Model.Notification;
import com.unitarcafe.hegaa.unitarcafe.Model.Payment;
import com.unitarcafe.hegaa.unitarcafe.Model.Request;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.UUID;

public class Checkout extends AppCompatActivity {
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference vendorNotification = database.getReference("notifications/vendor");
    final DatabaseReference requests = database.getReference("orders");
    final DatabaseReference payments = database.getReference("payments");
    TextView textTotalPrice;
    Button btnPlace;
    String uniqueId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_checkout);

        //init

        textTotalPrice = (TextView) findViewById(R.id.txtTotalOrder);
        btnPlace = (Button) findViewById(R.id.btnPay);


        btnPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                requests.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        requests.child(uniqueId).child("status").setValue(true);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                payments.addListenerForSingleValueEvent(new ValueEventListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String diffUniqueId = UUID.randomUUID().toString();
                        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
                        Date date = new Date(System.currentTimeMillis());
                        Payment newPay = new Payment(
                                Common.currentUser.getEmail(),
                                Common.currentUser.getName(),
                                uniqueId,
                                formatter.format(date),
                                textTotalPrice.getText().toString()

                        );
                        payments.child(diffUniqueId).setValue(newPay);
                        Notification vendorNoti = new Notification("New Order", "New Order from " + Common.currentUser.getUserID());
                        String notiID = vendorNotification.push().getKey();
                        vendorNotification.child(notiID).setValue(vendorNoti);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                Toast.makeText(Checkout.this, "Thank you, Order Place.", Toast.LENGTH_SHORT).show();
                finish();


            }
        });

        if (getIntent() != null){
            uniqueId = getIntent().getStringExtra("uniqueId");
            getTotalPrice(uniqueId);
        }


    }

    public void getTotalPrice(final String id) {
        requests.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DataSnapshot ds = dataSnapshot.child(id);
                System.out.println("DS :"+ds.toString());
                Request req = ds.getValue(Request.class);
                double price = Double.parseDouble(req.getTotal());
                textTotalPrice.setText(String.format("%.2f", price));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
