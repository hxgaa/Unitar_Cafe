package com.unitarcafe.hegaa.unitarcafe.ViewHolder;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;
import com.unitarcafe.hegaa.unitarcafe.Model.Items;
import android.widget.ImageView;
import android.widget.TextView;
import com.unitarcafe.hegaa.unitarcafe.Interface.ItemClickListener;
import com.unitarcafe.hegaa.unitarcafe.R;

public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView food_name;
    public ImageView food_image;

    private ItemClickListener itemClickListener;
    private FirebaseStorage storage = FirebaseStorage.getInstance("gs://unitarcafe.appspot.com/");
    private StorageReference imageStorage = storage.getReference().child("itemImages");
    final long ONE_MEGABYTE = 1024 * 1024;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public ItemViewHolder(View itemView) {
        super(itemView);

        food_name = itemView.findViewById(R.id.food_name_fi);
        food_image = itemView.findViewById(R.id.food_image_fi);

        itemView.setOnClickListener(this);
    }

    public void bindData(final Items viewModel) {
        food_name.setText(viewModel.getName());
        StorageReference imgPath = imageStorage.child(viewModel.getImage());
        imgPath.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                food_image.setImageBitmap(bmp);
            }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                food_image.setImageResource(R.drawable.ic_restaurant_black);
            }
        });
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v, getAdapterPosition(), false);
    }
}
