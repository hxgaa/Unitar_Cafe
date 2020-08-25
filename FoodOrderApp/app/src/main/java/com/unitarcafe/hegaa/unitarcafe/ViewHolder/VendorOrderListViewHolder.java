package com.unitarcafe.hegaa.unitarcafe.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.unitarcafe.hegaa.unitarcafe.Interface.ItemClickListener;
import com.unitarcafe.hegaa.unitarcafe.Model.Order;
import com.unitarcafe.hegaa.unitarcafe.Model.Request;
import com.unitarcafe.hegaa.unitarcafe.R;


public class VendorOrderListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView txtItemName, txtItemQty;

    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public VendorOrderListViewHolder(View itemView) {
        super(itemView);

        txtItemName = itemView.findViewById(R.id.order_itemName);
        txtItemQty = itemView.findViewById(R.id.order_itemQty);

        itemView.setOnClickListener(this);
    }

    public void bindData(final Order viewModel) {
            txtItemName.setText(viewModel.getProduName());
            txtItemQty.setText(viewModel.getQuantity());


    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v, getAdapterPosition(), false);
    }
}
