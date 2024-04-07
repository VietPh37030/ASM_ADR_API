package com.example.ass_adr_api.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ass_adr_api.Models.Categorie;
import com.example.ass_adr_api.R;
import com.example.ass_adr_api.services.HttpRequest;

import java.util.ArrayList;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder> {
    private Context context;
    private final ArrayList<Categorie> list;
    private HttpRequest httpRequest;

    public CategoriesAdapter(Context context, ArrayList<Categorie> list) {
        this.context = context;
        this.list = list;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_category, parent,
                false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Categorie categorie = list.get(position);
        holder.txtloai.setText(categorie.getLoai());
        Glide.with(context).load(categorie.getAnhLoai()).into(holder.imgloai);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgloai;
        TextView txtloai;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtloai = itemView.findViewById(R.id.txtloai);
            imgloai = itemView.findViewById(R.id.imageView5);
        }
    }
}
