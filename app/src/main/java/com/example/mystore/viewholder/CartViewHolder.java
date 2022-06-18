package com.example.mystore.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mystore.R;
import com.example.mystore.interFace.ItemClickListener;

public class CartViewHolder extends RecyclerView.ViewHolder implements ItemClickListener {
    public TextView tvProductName, tvProductPrice, tvProductQuantity;
    public ImageView ivProduct,ivDelete;
    private ItemClickListener itemClickListener;

    public CartViewHolder(@NonNull View itemView) {
        super(itemView);

        tvProductName = itemView.findViewById(R.id.cart_product_name);
        tvProductPrice = itemView.findViewById(R.id.cart_product_price);
        tvProductQuantity = itemView.findViewById(R.id.cart_product_quantity);
        ivProduct = itemView.findViewById(R.id.cart_product_iv);
        ivDelete = itemView.findViewById(R.id.iv_delete);
    }



    @Override
    public void onClick(View view, int position, boolean isLongClick) {
        itemClickListener.onClick(view, getAdapterPosition(),false);
    }

    public void setItemClickListener(ItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }
}
