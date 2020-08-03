package com.unitarcafe.hegaa.unitarcafe.Adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.unitarcafe.hegaa.unitarcafe.ItemList;
import com.unitarcafe.hegaa.unitarcafe.Interface.ItemClickListener;
import com.unitarcafe.hegaa.unitarcafe.Model.Menu;
import com.unitarcafe.hegaa.unitarcafe.R;
import com.unitarcafe.hegaa.unitarcafe.ViewHolder.MenuViewHolder;

import java.util.ArrayList;
import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter {
    private List<Menu> models = new ArrayList<>();

    public MenuAdapter(final List<Menu> viewMenu) {
        if (viewMenu != null) {
            this.models.addAll(viewMenu);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new MenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((MenuViewHolder) holder).bindData(models.get(position));
        ((MenuViewHolder) holder).setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View v, int position, boolean isLongClick) {
                Intent foodList = new Intent(v.getContext(), ItemList.class);
                //Because CategoryId is key, so we just get key of this item
                foodList.putExtra("CategoryId", models.get(position).getName());
                Toast.makeText(v.getContext(), "Key: "+models.get(position).getName(), Toast.LENGTH_SHORT).show();
                v.getContext().startActivity(foodList);
            }
        });
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    @Override
    public int getItemViewType(final int position) {
        return R.layout.menu_item;
    }



}
