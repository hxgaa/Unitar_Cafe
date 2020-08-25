package com.unitarcafe.hegaa.unitarcafe.ViewHolder;

import android.view.View;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.unitarcafe.hegaa.unitarcafe.Interface.ItemClickListener;
import com.unitarcafe.hegaa.unitarcafe.Model.Payment;
import com.unitarcafe.hegaa.unitarcafe.R;

public class VendorOrderCompletedViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView txtOrderId, txtOrderEmail;

    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public VendorOrderCompletedViewHolder(View itemView) {
        super(itemView);

        txtOrderId = itemView.findViewById(R.id.order_id);
        txtOrderEmail = itemView.findViewById(R.id.order_email);

        itemView.setOnClickListener(this);
    }

    public void bindData(final Payment viewModel) {
        txtOrderId.setText(viewModel.getOrderID());
        txtOrderEmail.setText(viewModel.getCustEmail());

    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v, getAdapterPosition(), false);
    }
}
