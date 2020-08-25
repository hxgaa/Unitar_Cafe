package com.unitarcafe.hegaa.unitarcafe.Adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.unitarcafe.hegaa.unitarcafe.Interface.ItemClickListener;
import com.unitarcafe.hegaa.unitarcafe.Model.Payment;
import com.unitarcafe.hegaa.unitarcafe.Model.Request;
import com.unitarcafe.hegaa.unitarcafe.R;
import com.unitarcafe.hegaa.unitarcafe.VendorFoodItem;
import com.unitarcafe.hegaa.unitarcafe.VendorOrderDetails;
import com.unitarcafe.hegaa.unitarcafe.VendorPaymentDetails;
import com.unitarcafe.hegaa.unitarcafe.ViewHolder.VendorOrderCompletedViewHolder;
import com.unitarcafe.hegaa.unitarcafe.ViewHolder.VendorOrderPendingViewHolder;

import java.util.ArrayList;
import java.util.List;

public class VendorOrderCompletedAdapter extends RecyclerView.Adapter {
    private List<Payment> models = new ArrayList<>();

    public VendorOrderCompletedAdapter(final List<Payment> viewItems) {
        if (viewItems != null) {
            this.models.addAll(viewItems);
        }
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new VendorOrderCompletedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((VendorOrderCompletedViewHolder) holder).bindData(models.get(position));
        ((VendorOrderCompletedViewHolder) holder).setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View v, int position, boolean isLongClick) {
                Intent specificItem = new Intent(v.getContext(), VendorPaymentDetails.class);
                //Because CategoryId is key, so we just get key of this item
                specificItem.putExtra("itemId", models.get(position).getOrderID());
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
        return R.layout.vendor_mainorderlayout;
    }
}
