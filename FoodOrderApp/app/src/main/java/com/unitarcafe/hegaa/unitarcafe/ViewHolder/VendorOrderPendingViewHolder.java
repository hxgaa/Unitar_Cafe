package com.unitarcafe.hegaa.unitarcafe.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.unitarcafe.hegaa.unitarcafe.Interface.ItemClickListener;
import com.unitarcafe.hegaa.unitarcafe.Model.Request;
import com.unitarcafe.hegaa.unitarcafe.R;

public class VendorOrderPendingViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView txtOrderId, txtOrderEmail;

    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public VendorOrderPendingViewHolder(View itemView) {
        super(itemView);

        txtOrderId = itemView.findViewById(R.id.order_id);
        txtOrderEmail = itemView.findViewById(R.id.order_email);

        itemView.setOnClickListener(this);
    }

    public void bindData(final Request viewModel) {
        txtOrderId.setText(viewModel.getOrderId());
        txtOrderEmail.setText(viewModel.getUser().getEmail());

    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v, getAdapterPosition(), false);
    }
}
