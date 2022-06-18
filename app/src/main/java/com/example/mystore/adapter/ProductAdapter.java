package com.example.mystore.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.mystore.ProductDetailActivity;
import com.example.mystore.R;
import com.example.mystore.interFace.ItemClickListener;
import com.example.mystore.model.Product;
import com.example.mystore.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private final ArrayList<Product> listProduct;
    private final Activity activity;
    private Context context;

    public ProductAdapter(ArrayList<Product> listProduct, Activity activity) {
        this.listProduct = listProduct;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        final Product product = listProduct.get(position);
        String id = product.getProductID();
        String name = product.getProductName();
        String price = Utils.formatNumberToMoney(Long.parseLong(product.getPriceProduct())) + " đ";
        holder.tvNameProduct.setText(name);
        holder.tvPriceProduct.setText(price);
        HashMap<String, String> hashMap = product.getImgThumnal();
        Map.Entry<String, String> entry = hashMap.entrySet().iterator().next();
        String value = entry.getValue();
        Glide.with(activity).load(value)
                .asBitmap()
                .atMost()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .override(300, 300)
                .centerCrop()
                .approximate()
                .into(holder.imgProduct);

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                if (isLongClick){
                    Toast.makeText(view.getContext(), "Long Click: "+id,Toast.LENGTH_SHORT).show();
                }else {
                    context = view.getContext();
                    Intent intent = new Intent(context,ProductDetailActivity.class);
                    intent.putExtra("ID",id);
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listProduct.size();
    }


    public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView btnDetail;
        TextView tvPriceProduct,tvNameProduct;
        ImageView imgProduct;

        private ItemClickListener itemClickListener;

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        ProductViewHolder(View itemView) {
            super(itemView);
            tvNameProduct = itemView.findViewById(R.id.tvNameProduct);
            tvPriceProduct = itemView.findViewById(R.id.tvPriceProduct);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            int imageWidth = initializeGridLayout();
            imgProduct.setLayoutParams(new LinearLayout.LayoutParams(imageWidth, imageWidth * 15 / 15));

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v,getAdapterPosition(),false);
        }

        public boolean onLongClick(View v) {
            itemClickListener.onClick(v,getAdapterPosition(),true);
            return true;
        }
    }

    private int initializeGridLayout() {
        float padding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                1, activity.getResources().getDisplayMetrics());

        return (int) ((Utils.getScreenWidth(activity) - ((3 + 1) * padding)) / 2.2);

    }



}
