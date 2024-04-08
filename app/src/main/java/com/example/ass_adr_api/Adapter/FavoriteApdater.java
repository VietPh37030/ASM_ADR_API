package com.example.ass_adr_api.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ass_adr_api.Models.Product;
import com.example.ass_adr_api.Models.ProductClickListener;
import com.example.ass_adr_api.R;

import java.util.ArrayList;

public class FavoriteApdater extends RecyclerView.Adapter<FavoriteApdater.ViewHolder> {
    private Context context;
    private final ArrayList<Product> favoriteProducts;
    private ProductClickListener listener;

    public FavoriteApdater(Context context, ArrayList<Product> favoriteProducts) {
        this.context = context;
        this.favoriteProducts = favoriteProducts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favorite, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = favoriteProducts.get(position);
        Glide.with(context).load(product.getImage()).into(holder.imgcaylon);
    }

    @Override
    public int getItemCount() {
        return favoriteProducts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgcaylon,imgfavorite;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgcaylon = itemView.findViewById(R.id.imageView8);
            imgfavorite = itemView.findViewById(R.id.imageView9);
        }
    }
}
