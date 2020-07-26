package com.unitarcafe.hegaa.unitarcafe;

import android.app.ProgressDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        editUserID = findViewById(R.id.edit_UserID);
        editEmail = findViewById(R.id.edit_UserId);
        editPhone = findViewById(R.id.edit_Phone);
        editName = findViewById(R.id.edit_Name);
        editPassword = findViewById(R.id.edit_PasswordUp);
        btnSignUp = findViewById(R.id.btn_signUp);

        //Init Firebase
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference reg_users = database.getReference("users");

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
                        //Check if user email already exist

                        DataSnapshot registeredUsers = dataSnapshot.child("accounts");

                        if (registeredUsers.child(editUserID.getText().toString()).exists()) {
                            mDialog.dismiss();
                            Toast.makeText(SignUp.this, "User exists!", Toast.LENGTH_SHORT).show();
                        } else {
                            User newUser = new User(editName.getText().toString(), finalPassHash, editEmail.getText().toString(), editPhone.getText().toString());
                            reg_users.child("accounts").child(editUserID.getText().toString()).setValue(newUser);
                            mDialog.dismiss();
                            Toast.makeText(SignUp.this, "Registration Successful!", Toast.LENGTH_SHORT).show();
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
