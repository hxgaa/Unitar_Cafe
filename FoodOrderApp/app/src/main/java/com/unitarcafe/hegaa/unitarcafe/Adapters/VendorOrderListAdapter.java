package com.unitarcafe.hegaa.unitarcafe.Adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.unitarcafe.hegaa.unitarcafe.Interface.ItemClickListener;
import com.unitarcafe.hegaa.unitarcafe.Model.Order;
import com.unitarcafe.hegaa.unitarcafe.Model.Request;
import com.unitarcafe.hegaa.unitarcafe.R;
import com.unitarcafe.hegaa.unitarcafe.VendorOrderDetails;
import com.unitarcafe.hegaa.unitarcafe.ViewHolder.VendorOrderListViewHolder;
import com.unitarcafe.hegaa.unitarcafe.ViewHolder.VendorOrderPendingViewHolder;

import java.util.ArrayList;
import java.util.List;

public class VendorOrderListAdapter extends RecyclerView.Adapter {
    private List<Order> models = new ArrayList<>();

    public VendorOrderListAdapter(final List<Order> viewItems) {
        if (viewItems != null) {
            this.models.addAll(viewItems);
        }
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new VendorOrderListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((VendorOrderListViewHolder) holder).bindData(models.get(position));
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    @Override
    public int getItemViewType(final int position) {
        return R.layout.vendor_singleorderlayout;
    }
}
