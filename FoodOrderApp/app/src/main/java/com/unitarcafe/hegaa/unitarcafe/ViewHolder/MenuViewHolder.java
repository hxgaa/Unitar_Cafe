package com.unitarcafe.hegaa.unitarcafe.ViewHolder;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.unitarcafe.hegaa.unitarcafe.Interface.ItemClickListener;
import com.unitarcafe.hegaa.unitarcafe.Model.Menu;
import com.unitarcafe.hegaa.unitarcafe.R;

import static androidx.core.content.ContextCompat.startActivity;

public class MenuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    private TextView txtMenuName;
    private ImageView imageView;

    private ItemClickListener itemClickListener;

    public MenuViewHolder(final View itemView) {
        super(itemView);

        txtMenuName = (TextView) itemView.findViewById(R.id.menu_name);
        imageView = (ImageView) itemView.findViewById(R.id.menu_image);
        itemView.setOnClickListener(this);


    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void bindData(final Menu viewModel) {
        txtMenuName.setText(viewModel.getName());
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
