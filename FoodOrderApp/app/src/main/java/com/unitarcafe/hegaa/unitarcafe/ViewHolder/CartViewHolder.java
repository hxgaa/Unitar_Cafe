package com.unitarcafe.hegaa.unitarcafe.ViewHolder;

import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.TextView;

import com.unitarcafe.hegaa.unitarcafe.Interface.ItemClickListener;
import com.unitarcafe.hegaa.unitarcafe.Model.Order;
import com.unitarcafe.hegaa.unitarcafe.R;

public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView txt_cart_name, txt_price, txt_cart_count;


    private ItemClickListener itemClickListener;

    public void setTxt_cart_name(TextView txt_cart_name) {
        this.txt_cart_name = txt_cart_name;
    }

    public CartViewHolder(View itemView) {
        super(itemView);
        txt_cart_name = itemView.findViewById(R.id.cart_item_name);
        txt_price = itemView.findViewById(R.id.cart_item_price);
        txt_cart_count = itemView.findViewById(R.id.cart_item_count);

    }

    public void bindData(final Order viewModel) {
        txt_cart_name.setText(viewModel.getProduName());
        txt_price.setText("RM "+String.valueOf(viewModel.getPrice()));
        txt_cart_count.setText(viewModel.getQuantity());
    }

    @Override
    public void onClick(View v) {

    }
}

