package com.unitarcafe.hegaa.unitarcafe.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.unitarcafe.hegaa.unitarcafe.Interface.ItemClickListener;
import com.unitarcafe.hegaa.unitarcafe.Model.Items;
import com.unitarcafe.hegaa.unitarcafe.R;

public class VendorMenuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    private TextView txtItemName, txtItemPrice;
    private ImageView imageView;

    private ItemClickListener itemClickListener;

    public VendorMenuViewHolder(final View itemView) {
        super(itemView);

        txtItemName = (TextView) itemView.findViewById(R.id.txtFoodName);
        txtItemPrice = (TextView) itemView.findViewById(R.id.txtFoodPrice);
        imageView = (ImageView) itemView.findViewById(R.id.imgFood);
        itemView.setOnClickListener(this);


    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void bindData(final Items viewModel) {
        txtItemName.setText(viewModel.getName());
        if (viewModel.getName().equals("drinks")) {
            imageView.setImageResource(R.mipmap.ic_bannerdrinks_foreground);
        } else if (viewModel.getName().equals("food")) {
            imageView.setImageResource(R.mipmap.ic_bannerfood_foreground);
        }
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v, getAdapterPosition(), false);
    }

}
