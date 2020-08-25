package com.unitarcafe.hegaa.unitarcafe;

import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.unitarcafe.hegaa.unitarcafe.Model.Items;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class VendorFoodItem extends AppCompatActivity {
    public static final int PICKFILE_RESULT_CODE = 1;
    Toolbar food, order, home;
    TextView txtItemName, txtItemPrice, txtItemDiscount, txtItemDesc;
    ImageView imgItem;
    Button btnSaveItem, btnDeleteItem, btnEdit;
    List<Items> allItems = new ArrayList<>();

    String itemID = "";
    private Uri fileUri;
    private String imageName, imageType;

    //Firebase Database
    FirebaseDatabase database;
    DatabaseReference items;
    FirebaseStorage storage;
    StorageReference imageStorage, imgPath;

    Items currentFood, selectedFood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vendor_foodview);
        txtItemName = (TextView) findViewById(R.id.txtItemName);
        txtItemPrice = (TextView) findViewById(R.id.txtItemPrice);
        txtItemDiscount = (TextView) findViewById(R.id.txtItemDiscount);
        txtItemDesc = (TextView) findViewById(R.id.txtItemDesc);
        imgItem = (ImageView) findViewById(R.id.imageItemView);
        btnSaveItem = (Button) findViewById(R.id.btnSaveItem);
        btnDeleteItem = (Button) findViewById(R.id.btnDelItem);
        btnEdit = (Button) findViewById(R.id.btnEditItem);
        androidx.appcompat.widget.Toolbar toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.vendorToolbar);
        toolbar.setTitle("Manage Items");
        setSupportActionBar(toolbar);
        final TabLayout tabs = (TabLayout) findViewById(R.id.tabs);
        TabLayout.Tab tab = tabs.getTabAt(2);
        tab.select();

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Toast.makeText(VendorFoodItem.this, "Vendor Food", Toast.LENGTH_SHORT).show();
                int selectedTabPosition = tabs.getSelectedTabPosition();
                if (selectedTabPosition == 0) {

                    Intent foods = new Intent(VendorFoodItem.this, VendorHome.class);
                    startActivity(foods);
                    finish();

                } else if (selectedTabPosition == 1) {

                    Intent foods = new Intent(VendorFoodItem.this, VendorOrders.class);
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


        //Firebase
        database = FirebaseDatabase.getInstance();
        items = database.getReference("menu");
        storage = FirebaseStorage.getInstance("gs://unitarcafe.appspot.com/");
        imageStorage = storage.getReference().child("itemImages");

        btnSaveItem.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                items.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot item : dataSnapshot.getChildren()) {
//                    System.out.println("DS2: "+item.toString());
                            for (DataSnapshot specificItem : item.getChildren()) {
//                        System.out.println("DS3: "+specificItem.toString());
                                selectedFood = specificItem.getValue(Items.class);
                                if (selectedFood.getName().equals(txtItemName.getText().toString())) {
                                    String updatedImageName = uploadItemImage(txtItemName.getText().toString(), fileUri);
                                    Items updateItem = new Items(
                                            txtItemName.getText().toString(),
                                            txtItemDesc.getText().toString(),
                                            txtItemPrice.getText().toString(),
                                            txtItemDiscount.getText().toString(),
                                            updatedImageName
                                    );
                                    items.child(item.toString()).child(specificItem.toString()).setValue(updateItem);
                                    break;
                                } else {
                                    StringBuilder itemInitial = new StringBuilder();
                                    for (String s : txtItemName.getText().toString().toUpperCase().split(" ")) {
                                        itemInitial.append(s.charAt(0));
                                    }
                                    if (dataSnapshot.hasChild(itemInitial.toString())) {

                                    } else {
                                        String newImageName = uploadItemImage(txtItemName.getText().toString(), fileUri);
                                        Items newItem = new Items(
                                                txtItemName.getText().toString(),
                                                txtItemDesc.getText().toString(),
                                                txtItemPrice.getText().toString(),
                                                txtItemDiscount.getText().toString(),
                                                newImageName
                                        );
                                        items.child(item.toString()).child(itemInitial.toString()).setValue(newItem);
                                    }


                                }


                            }

                        }
                        Toast.makeText(VendorFoodItem.this, "Item Saved!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                btnEdit.setEnabled(true);
                btnDeleteItem.setEnabled(true);
                btnSaveItem.setEnabled(false);
                txtItemName.setEnabled(false);
                txtItemPrice.setEnabled(false);
                txtItemDiscount.setEnabled(false);
                txtItemDesc.setEnabled(false);
            }
        }));

        btnDeleteItem.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(VendorFoodItem.this);
                alertDialog.setTitle("Confirm Delete");
                alertDialog.setMessage("Are you sure you want to delete this item ?");

                alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        items.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot item : dataSnapshot.getChildren()) {
//                    System.out.println("DS2: "+item.toString());
                                    for (DataSnapshot specificItem : item.getChildren()) {
//                        System.out.println("DS3: "+specificItem.toString());
                                        selectedFood = specificItem.getValue(Items.class);
                                        if (selectedFood.getName().equals(txtItemName.getText().toString())) {
                                            items.child(item.toString()).child(specificItem.toString()).setValue(null);
                                            break;
                                        }

                                    }

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                        dialog.dismiss();
                    }
                });

                alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alertDialog.show();


            }
        }));

        btnEdit.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                txtItemName.setEnabled(true);
                txtItemPrice.setEnabled(true);
                txtItemDiscount.setEnabled(true);
                txtItemDesc.setEnabled(true);
                imgItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
                        chooseFile.setType("image/*");
                        chooseFile.addCategory(Intent.CATEGORY_OPENABLE);
                        chooseFile = Intent.createChooser(chooseFile, "Select item image");
                        startActivityForResult(chooseFile, PICKFILE_RESULT_CODE);
                    }
                });
                btnSaveItem.setEnabled(true);
                btnEdit.setEnabled(false);
                btnDeleteItem.setEnabled(false);


            }
        }));
        // Loads the menu list


        //Get Food Id from Intent
        if (getIntent() != null) {
            itemID = getIntent().getStringExtra("itemId");
            getMenu();
        } else {
            txtItemName.setEnabled(true);
            txtItemPrice.setEnabled(true);
            txtItemDiscount.setEnabled(true);
            txtItemDesc.setEnabled(true);
            btnEdit.setEnabled(false);
            btnDeleteItem.setEnabled(false);
            imgItem.setImageResource(R.drawable.icon_addimage);
            imgItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
                    chooseFile.setType("image/*");
                    chooseFile.addCategory(Intent.CATEGORY_OPENABLE);
                    chooseFile = Intent.createChooser(chooseFile, "Select item image");
                    startActivityForResult(chooseFile, PICKFILE_RESULT_CODE);
                }
            });
        }


    }

    @Override

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {

            case PICKFILE_RESULT_CODE:
                if (resultCode == -1) {
                    ContentResolver cR = this.getContentResolver();
                    MimeTypeMap mime = MimeTypeMap.getSingleton();

                    fileUri = data.getData();
                    imageName = getFileName(fileUri);
                    imageType = mime.getExtensionFromMimeType(cR.getType(fileUri));
                    imgItem.setImageURI(fileUri);


//                    String conImgName = txtItemName.getText().toString().replace(" ", "_").toLowerCase();
//                    System.out.println("Name: "+conImgName+":"+imageType);
                }
                break;
        }

    }

    private void getMenu() {
        items.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot item : dataSnapshot.getChildren()) {
//                    System.out.println("DS2: "+item.toString());
                    for (DataSnapshot specificItem : item.getChildren()) {
//                        System.out.println("DS3: "+specificItem.toString());
                        currentFood = specificItem.getValue(Items.class);
                        allItems.add(new Items(currentFood.getName(), currentFood.getDescription(), currentFood.getPrice(), currentFood.getDiscount(), currentFood.getImage()));
//                        System.out.println("Item: "+currentFood.getName());

                    }

                }

                    for (int i = 0; i < allItems.size(); i++) {
                        Items item = allItems.get(i);
                        String itemName = item.getName();
//                        System.out.println("Food :"+itemID);
//                        System.out.println("itemName :"+itemName);
                        if (itemName.equals(itemID)) {
//                            System.out.println("Food :"+itemID);
                            double price = Double.parseDouble(item.getPrice());
                            txtItemPrice.setText(String.format("%.2f", price));
                            txtItemDiscount.setText(item.getDiscount());
                            txtItemName.setText(item.getName());
                            txtItemDesc.setText(item.getDescription());
                            imgPath = imageStorage.child(item.getImage());
                            final long ONE_MEGABYTE = 1024 * 1024;
                            imgPath.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                                @Override
                                public void onSuccess(byte[] bytes) {
                                    Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                    imgItem.setImageBitmap(bmp);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    imgItem.setImageResource(R.drawable.ic_restaurant_black);
                                }
                            });
                            break;
                        }
                    }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private String uploadItemImage(String itemName, Uri itemURI) {
        Uri fileUri;
        if (itemURI == null) {
            fileUri  = Uri.parse("android.resource://com.unitarcafe.hegaa.unitarcafe/" + R.drawable.ic_restaurant_black);
        } else {
            fileUri = itemURI;
        }
        String conImgName = itemName.replace(" ", "_").toLowerCase();
        String imgDataName = conImgName + imageType;
        StorageReference imgDataPath = imageStorage.child(imgDataName);
        UploadTask uploadTask = imgDataPath.putFile(fileUri);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                Toast.makeText(VendorFoodItem.this, "Save Failed", Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                System.out.println(taskSnapshot.getMetadata());
                Toast.makeText(VendorFoodItem.this, "Item Saved", Toast.LENGTH_SHORT).show();
//                contains file metadata such as size, content-type, etc.
                // ...
            }
        });
        return imgDataName;
    }

    public String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        Intent foods = new Intent(VendorFoodItem.this, VendorFoods.class);
//        startActivity(foods);
//        finish();
    }
}
