package com.unitarcafe.hegaa.unitarcafe.Adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.unitarcafe.hegaa.unitarcafe.FoodDetails;
import com.unitarcafe.hegaa.unitarcafe.Interface.ItemClickListener;
import com.unitarcafe.hegaa.unitarcafe.Model.Items;
import com.unitarcafe.hegaa.unitarcafe.Model.Notification;
import com.unitarcafe.hegaa.unitarcafe.R;
import com.unitarcafe.hegaa.unitarcafe.ViewHolder.ItemViewHolder;
import com.unitarcafe.hegaa.unitarcafe.ViewHolder.NotificationViewHolder;

import java.util.ArrayList;
import java.util.List;

public class NotificationsAdapter extends RecyclerView.Adapter {
    private List<Notification> models = new ArrayList<>();
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference notiDB = db.getReference("notifications");

    public NotificationsAdapter(final List<Notification> viewItems) {
        if (viewItems != null) {
            this.models.addAll(viewItems);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((NotificationViewHolder) holder).bindData(models.get(position));
        ((NotificationViewHolder) holder).setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View v, int position, boolean isLongClick) {
                final String id = models.get(position).getNotiID();
                notiDB.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot item : dataSnapshot.getChildren()) {
//                            Notification setNot = item.getValue()
                            if (item.getKey().equals("client")) {
                                for (DataSnapshot childItem : item.getChildren()) {
                                    for (DataSnapshot childNot : childItem.getChildren()) {
                                        String childKey = childItem.getKey();
                                        if (childKey.equals(id)) {
                                            notiDB.child("client").child(childItem.getKey()).child("status").setValue("READ");
                                        }
                                    }
                                }

                            } else if (item.getKey().equals("vendor")) {
                                for (DataSnapshot childItem : item.getChildren()) {
                                    String childKey = childItem.getKey();
                                    if (childKey.equals(id)) {
                                        notiDB.child("vendor").child(childKey).child("status").setValue("READ");
                                    }

                                }

                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    @Override
    public int getItemViewType(final int position) {
        return R.layout.notification_layout;
    }
}
