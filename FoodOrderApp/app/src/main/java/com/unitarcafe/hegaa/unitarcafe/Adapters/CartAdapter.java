package com.unitarcafe.hegaa.unitarcafe.Adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.unitarcafe.hegaa.unitarcafe.Model.Items;
import com.unitarcafe.hegaa.unitarcafe.Model.Order;
import com.unitarcafe.hegaa.unitarcafe.R;
import com.unitarcafe.hegaa.unitarcafe.ViewHolder.CartViewHolder;
import java.util.ArrayList;
import java.util.List;


public class CartAdapter extends RecyclerView.Adapter    {

    private List<Order> listData = new ArrayList<>();

    public CartAdapter(final List<Order> viewItems) {
        if (viewItems != null) {
            this.listData.addAll(viewItems);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
//        LayoutInflater inflater = LayoutInflater.from(context);
//        View itemView = inflater.inflate(R.layout.cart_layout, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((CartViewHolder) holder).bindData(listData.get(position));

    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    @Override
    public int getItemViewType(final int position) {
        return R.layout.cart_layout;
    }
}
