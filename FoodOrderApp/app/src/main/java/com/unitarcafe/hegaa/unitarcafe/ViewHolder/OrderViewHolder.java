package com.unitarcafe.hegaa.unitarcafe.ViewHolder;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.unitarcafe.hegaa.unitarcafe.Interface.ItemClickListener;
import com.unitarcafe.hegaa.unitarcafe.Model.Request;
import com.unitarcafe.hegaa.unitarcafe.R;

public class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView txtOrderId, txtOrderStatus, txtOrderPhone, txtOrderEmail;

    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public OrderViewHolder(View itemView) {
        super(itemView);

        txtOrderId = itemView.findViewById(R.id.order_id);
        txtOrderStatus = itemView.findViewById(R.id.order_status);
        txtOrderPhone = itemView.findViewById(R.id.order_phone);
        txtOrderEmail = itemView.findViewById(R.id.order_email);

        itemView.setOnClickListener(this);
    }

    public void bindData(final Request viewModel) {
        txtOrderId.setText(viewModel.getOrderId());
        if (viewModel.getStatus() == false) {
            txtOrderStatus.setText("PENDING");
        } else {
            txtOrderStatus.setText("COMPLETED");
        }

        txtOrderPhone.setText(viewModel.getUser().getPhone());
        txtOrderEmail.setText(viewModel.getUser().getEmail());

    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v, getAdapterPosition(), false);
    }
}
