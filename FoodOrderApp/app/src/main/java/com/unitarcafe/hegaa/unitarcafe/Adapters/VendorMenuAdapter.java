package com.unitarcafe.hegaa.unitarcafe.Adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.unitarcafe.hegaa.unitarcafe.Interface.ItemClickListener;
import com.unitarcafe.hegaa.unitarcafe.ItemList;
import com.unitarcafe.hegaa.unitarcafe.Model.Items;
import com.unitarcafe.hegaa.unitarcafe.Model.Menu;
import com.unitarcafe.hegaa.unitarcafe.R;
import com.unitarcafe.hegaa.unitarcafe.VendorFoodItem;
import com.unitarcafe.hegaa.unitarcafe.ViewHolder.MenuViewHolder;
import com.unitarcafe.hegaa.unitarcafe.ViewHolder.VendorMenuViewHolder;

import java.util.ArrayList;
import java.util.List;

public class VendorMenuAdapter extends RecyclerView.Adapter {
    private List<Items> models = new ArrayList<>();

    public VendorMenuAdapter(final List<Items> viewMenu) {
        if (viewMenu != null) {
            this.models.addAll(viewMenu);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new VendorMenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((VendorMenuViewHolder) holder).bindData(models.get(position));
        ((VendorMenuViewHolder) holder).setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View v, int position, boolean isLongClick) {
                Intent specificItem = new Intent(v.getContext(), VendorFoodItem.class);
                //Because CategoryId is key, so we just get key of this item
                specificItem.putExtra("itemId", models.get(position).getName());
                Toast.makeText(v.getContext(), "Key: "+models.get(position).getName(), Toast.LENGTH_SHORT).show();
                v.getContext().startActivity(specificItem);
            }
        });
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    @Override
    public int getItemViewType(final int position) {
        return R.layout.vendor_fooditemslayout;
    }



}
