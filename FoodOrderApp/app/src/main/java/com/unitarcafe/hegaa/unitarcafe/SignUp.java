package com.unitarcafe.hegaa.unitarcafe;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;
import com.unitarcafe.hegaa.unitarcafe.Common.PassHash;
import com.unitarcafe.hegaa.unitarcafe.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignUp extends AppCompatActivity {

    EditText editUserID, editEmail, editName, editPassword, editPhone;
    Button btnSignUp;
    RadioButton radioVendor, radioUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        editUserID = findViewById(R.id.edit_UserID);
        editEmail = findViewById(R.id.edit_Email);
        editPhone = findViewById(R.id.edit_Phone);
        editName = findViewById(R.id.edit_Name);
        editPassword = findViewById(R.id.edit_PasswordUp);
        btnSignUp = findViewById(R.id.btn_signUp);
        radioUser = findViewById(R.id.radioUser);
        radioVendor = findViewById(R.id.radioVendor);

        //Init Firebase
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference reg_users = database.getReference("users");
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog mDialog = new ProgressDialog(SignUp.this);
                database.goOnline();
                mDialog.setMessage("Registering user...");
                mDialog.show();
                String passHash="";
                // Hashing password
                try {
                    passHash = PassHash.getSaltedHash(editPassword.getText().toString());
                } catch (Exception e) {
                    Toast.makeText(SignUp.this, "Password hashing failed!", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
                final String finalPassHash = passHash;

                reg_users.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        if (radioUser.isChecked()) {
                            DataSnapshot registeredUsers = dataSnapshot.child("clients");

                            if (registeredUsers.child(editUserID.getText().toString()).exists()) {
                                mDialog.dismiss();
                                alertDialog.setTitle("User Exist");
                                alertDialog.setMessage("The user "+editUserID.getText().toString()+" already exists!");
                                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        });
                                alertDialog.show();
                            } else {
                                User newUser = new User(editUserID.getText().toString(), editName.getText().toString(), finalPassHash, editEmail.getText().toString(), editPhone.getText().toString());
                                reg_users.child("clients").child(editUserID.getText().toString()).setValue(newUser);
                                mDialog.dismiss();
                                alertDialog.setTitle("User Registration");
                                alertDialog.setMessage("The user account has been registered successfully");
                                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                                finish();
                                            }
                                        });
                                alertDialog.show();
                            }
                        } else if (radioVendor.isChecked()) {
                            DataSnapshot registeredUsers = dataSnapshot.child("vendors");

                            if (registeredUsers.child(editUserID.getText().toString()).exists()) {
                                mDialog.dismiss();
                                alertDialog.setTitle("User Exist");
                                alertDialog.setMessage("The user "+editUserID.getText().toString()+" already exists!");
                                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        });
                                alertDialog.show();
                            } else {
                                User newUser = new User(editUserID.getText().toString(), editName.getText().toString(), finalPassHash, editEmail.getText().toString(), editPhone.getText().toString());
                                reg_users.child("vendors").child(editUserID.getText().toString()).setValue(newUser);
                                mDialog.dismiss();
                                alertDialog.setTitle("Vendor Registration");
                                alertDialog.setMessage("The vendor account has been registered successfully");
                                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                                finish();
                                            }
                                        });
                                alertDialog.show();
                            }
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
