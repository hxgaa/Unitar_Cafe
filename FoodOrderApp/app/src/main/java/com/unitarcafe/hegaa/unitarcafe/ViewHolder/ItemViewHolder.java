package com.unitarcafe.hegaa.unitarcafe.ViewHolder;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.squareup.picasso.Picasso;
import com.unitarcafe.hegaa.unitarcafe.Model.Items;
import android.widget.ImageView;
import android.widget.TextView;
import com.unitarcafe.hegaa.unitarcafe.Interface.ItemClickListener;
import com.unitarcafe.hegaa.unitarcafe.R;

public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView food_name;
    public ImageView food_image;

    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public ItemViewHolder(View itemView) {
        super(itemView);

        food_name = itemView.findViewById(R.id.food_name_fi);
        food_image = itemView.findViewById(R.id.food_image_fi);

        itemView.setOnClickListener(this);
    }

    public void bindData(final Items viewModel) {
        food_name.setText(viewModel.getName());
//        Picasso.get().load(viewModel.getImage()).into(food_image);
        if (viewModel.getImage().equals("teh_tarik.jpg")) {
            food_image.setImageResource(R.mipmap.ic_tehtarik_foreground);
        } else if (viewModel.getImage().equals("nasi_goreng.jpg")) {
            food_image.setImageResource(R.mipmap.ic_nasigoreng_foreground);
        } else if (viewModel.getImage().equals("roti_canai.jpg")) {
            food_image.setImageResource(R.mipmap.ic_roticanai_foreground);
        } else if (viewModel.getImage().equals("maggi_goreng.jpg")) {
            food_image.setImageResource(R.mipmap.ic_maggigoreng_foreground);
        }
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v, getAdapterPosition(), false);
    }
}
