package com.unitarcafe.hegaa.unitarcafe.ViewHolder;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageException;
import com.google.firebase.storage.StorageReference;
import com.unitarcafe.hegaa.unitarcafe.Interface.ItemClickListener;
import com.unitarcafe.hegaa.unitarcafe.Model.Items;
import com.unitarcafe.hegaa.unitarcafe.R;

public class VendorMenuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    private TextView txtItemName, txtItemPrice;
    private ImageView imageView;

    private ItemClickListener itemClickListener;
    private FirebaseStorage storage = FirebaseStorage.getInstance("gs://unitarcafe.appspot.com/");
    private StorageReference imageStorage = storage.getReference().child("itemImages");
    final long ONE_MEGABYTE = 1024 * 1024;

    public VendorMenuViewHolder(final View itemView) {
        super(itemView);

        txtItemName = (TextView) itemView.findViewById(R.id.txtFoodName);
        txtItemPrice = (TextView) itemView.findViewById(R.id.txtFoodPrice);
        imageView = (ImageView) itemView.findViewById(R.id.imgFood);
        itemView.setOnClickListener(this);


    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void bindData(final Items viewModel) {
        txtItemName.setText(viewModel.getName());
        double price = Double.parseDouble(viewModel.getPrice());
        txtItemPrice.setText(String.format("%.2f", price));
        StorageReference imgPath = imageStorage.child(viewModel.getImage());
        imgPath.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

                imageView.setImageBitmap(bmp);
            }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                imageView.setImageResource(R.drawable.ic_restaurant_black);
            }
        });
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v, getAdapterPosition(), false);
    }

}
