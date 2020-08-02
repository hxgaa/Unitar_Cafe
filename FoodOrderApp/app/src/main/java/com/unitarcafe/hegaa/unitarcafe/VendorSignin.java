package com.unitarcafe.hegaa.unitarcafe;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.unitarcafe.hegaa.unitarcafe.Common.Common;
import com.unitarcafe.hegaa.unitarcafe.Model.User;

public class VendorSignin extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    EditText editEmail, editPassword;
    Button btnSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_sign_in);

        editEmail = findViewById(R.id.edit_Phone);
        editPassword = findViewById(R.id.edit_Password);
        btnSignIn = findViewById(R.id.btn_signIn);

        //init Firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ProgressDialog mDialog = new ProgressDialog(VendorSignin.this);
                mDialog.setMessage("Please wait...");
                mDialog.show();

                table_user.addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        //Check if user not exist in Database
                        if(dataSnapshot.child(editEmail.getText().toString()).exists()) {
                            //Get user information
                            mDialog.dismiss();
                            User user = dataSnapshot.child(editEmail.getText().toString()).getValue(User.class);
                            user.setPhone(editEmail.getText().toString());//set phone
                            if (user.getPassHash().equals(editPassword.getText().toString()+"\n")) {

                                Toast.makeText(VendorSignin.this, "Sign in success!!!", Toast.LENGTH_SHORT).show();
                                Intent homeIntent = new Intent(VendorSignin.this, Home.class);
                                Common.currentUser = user;
                                startActivity(homeIntent);
                                finish();
                            } else {

                                Toast.makeText(VendorSignin.this, "Wrong password!!!", Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            mDialog.dismiss();
                            Toast.makeText(VendorSignin.this, "User doesn't exist!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }
}
