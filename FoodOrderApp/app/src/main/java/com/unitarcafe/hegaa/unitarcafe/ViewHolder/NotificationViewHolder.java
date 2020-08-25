package com.unitarcafe.hegaa.unitarcafe.ViewHolder;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.unitarcafe.hegaa.unitarcafe.Interface.ItemClickListener;
import com.unitarcafe.hegaa.unitarcafe.Model.Items;
import com.unitarcafe.hegaa.unitarcafe.Model.Notification;
import com.unitarcafe.hegaa.unitarcafe.R;

public class NotificationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView title, message;;

    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public NotificationViewHolder(View itemView) {
        super(itemView);

        title = itemView.findViewById(R.id.not_Title);
        message = itemView.findViewById(R.id.not_Message);

        itemView.setOnClickListener(this);
    }

    public void bindData(final Notification viewModel) {
        if (viewModel.getStatus().equals("NEW")) {
            title.setTextColor(Color.RED);
        } else {
            title.setTextColor(Color.GRAY);
        }
        title.setText(viewModel.getTitle());
        message.setText(viewModel.getMessage());
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v, getAdapterPosition(), false);
    }
}
