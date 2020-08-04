package com.unitarcafe.hegaa.unitarcafe;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.unitarcafe.hegaa.unitarcafe.Common.Common;
import com.unitarcafe.hegaa.unitarcafe.Common.PassHash;
import com.unitarcafe.hegaa.unitarcafe.Model.User;

public class VendorSignin extends AppCompatActivity {

    EditText edUserid, edPass;
    Button btnSignIn, btnSignUp;
    TextView textSlogan, textusersignin;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_sign_in);

        btnSignUp = findViewById(R.id.btnSignUp);
        btnSignIn = findViewById(R.id.btnSignIn);
        edUserid = findViewById(R.id.edit_UserId);
        edPass = findViewById(R.id.edit_Pass);
        textusersignin = findViewById(R.id.textUserLogin);

        textSlogan = findViewById(R.id.textSlogan);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/NABILA.TTF");
        textSlogan.setTypeface(typeface);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signUpIntent = new Intent(VendorSignin.this, SignUp.class);
                startActivity(signUpIntent);
            }
        });

        textusersignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = new Intent(VendorSignin.this, MainActivity.class);
                startActivity(signInIntent);
            }
        });

        //init Firebase
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference user_account = database.getReference("users/accounts");

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database.goOnline();

                final ProgressDialog mDialog = new ProgressDialog(VendorSignin.this);
                mDialog.setMessage("Logging in...");
                mDialog.show();
                String passHash = "";

                // Hashing password to avoid plaintext password
                try {
                    passHash = PassHash.getSaltedHash(edPass.getText().toString());
                } catch (Exception e) {
                    Toast.makeText(VendorSignin.this, "Password hashing failed!", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

                final String finalPassHash = passHash;

                user_account.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Check if user not exist in Database
                        if(dataSnapshot.child(edUserid.getText().toString()).exists()) {

                            mDialog.dismiss();
                            User user = dataSnapshot.child(edUserid.getText().toString()).getValue(User.class);
                            try {
//                                            Log.d("Passed Hash ::", finalPassHash);
//                                            Log.d("Sent Hash ::", user.getPassHash());
//                                            if (PassHash.check(user.getPassHash(), finalPassHash))
                                if (finalPassHash.compareTo(user.getPassHash()) == 0) {
                                    Toast.makeText(VendorSignin.this, "Sign in success!", Toast.LENGTH_SHORT).show();
                                    Intent homeIntent = new Intent(VendorSignin.this, VendorHome.class);
                                    Common.currentUser = user;
                                    startActivity(homeIntent);
                                    finish();
                                } else {
                                    Toast.makeText(VendorSignin.this, "Incorrect password!", Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }



                            // If user does not exist
                        }else {
                            mDialog.dismiss();
                            Toast.makeText(VendorSignin.this, "Invalid User ID!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
        database.goOffline();
    }
}
