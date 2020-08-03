package com.unitarcafe.hegaa.unitarcafe.Adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.unitarcafe.hegaa.unitarcafe.FoodDetails;
import com.unitarcafe.hegaa.unitarcafe.Interface.ItemClickListener;
import com.unitarcafe.hegaa.unitarcafe.Model.Items;
import com.unitarcafe.hegaa.unitarcafe.R;
import com.unitarcafe.hegaa.unitarcafe.ViewHolder.ItemViewHolder;

import java.util.ArrayList;
import java.util.List;

public class ItemsAdapter extends RecyclerView.Adapter {
    private List<Items> models = new ArrayList<>();

    public ItemsAdapter(final List<Items> viewItems) {
        if (viewItems != null) {
            this.models.addAll(viewItems);
        }
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ItemViewHolder) holder).bindData(models.get(position));
        ((ItemViewHolder) holder).setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View v, int position, boolean isLongClick) {
                //Start new Activity
                Intent foodDetail = new Intent(v.getContext(), FoodDetails.class);
                //Save food id to activity
                foodDetail.putExtra("FoodId", models.get(position).getName());
                v.getContext().startActivity(foodDetail);
            }
        });
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    @Override
    public int getItemViewType(final int position) {
        return R.layout.item_list;
    }
}
