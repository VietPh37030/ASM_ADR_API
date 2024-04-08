package com.example.ass_adr_api.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ass_adr_api.Models.Categorie;
import com.example.ass_adr_api.Models.Product;
import com.example.ass_adr_api.Models.ProductClickListener;
import com.example.ass_adr_api.R;
import com.example.ass_adr_api.services.HttpRequest;

import java.util.ArrayList;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ViewHolder> {
    private Context context;
    private final ArrayList<Product> list;
    private HttpRequest httpRequest;
    private ProductClickListener listener;
    // Khai báo SharedPreferences
    private SharedPreferences sharedPreferences;

    public ProductsAdapter(Context context, ArrayList<Product> list) {
        this.context = context;
        this.list = list;
        // Khởi tạo SharedPreferences
        sharedPreferences = context.getSharedPreferences("favorite_products", Context.MODE_PRIVATE);

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.iteam_sanpham, parent,
                false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Product product = list.get(position);
        Glide.with(context).load(product.getImage()).into(holder.imgcay);
        holder.txtnamecay.setText(product.getProduct_name());
        holder.txtloaicay.setText(product.getCategorie().getLoai());
        holder.txtgia.setText(formatPrice(product.getPrice()));
        // Trạng thái ban đầu cho nút favorite
        final boolean[] isFavorite = {product.isFavorite()};
        if (isFavorite[0]) {
            holder.itFavorite.setImageResource(R.drawable.favorite); // Màu đỏ
        } else {
            holder.itFavorite.setImageResource(R.drawable.heart); // Màu đen
        }
        // Xử lý sự kiện khi nhấn vào nút favorite
        holder.itFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Đảo ngược trạng thái khi nhấn vào nút favorite
                isFavorite[0] = !isFavorite[0];
                product.setFavorite(isFavorite[0]);

                // Cập nhật màu cho nút favorite
                if (isFavorite[0]) {
                    holder.itFavorite.setImageResource(R.drawable.favorite); // Màu đỏ
                } else {
                    holder.itFavorite.setImageResource(R.drawable.heart); // Màu đen
                }
                // Lưu trạng thái yêu thích vào SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(product.get_id(), isFavorite[0]);
                editor.apply();
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("ProductsAdapter", "Item clicked: " + position);
                if (listener != null) {
                    listener.onProductClick(product);
                }
            }
        });

    }
    private String formatPrice(int price) {
        // Định dạng giá tiền theo kiểu tiền tệ Việt Nam đồng
        return String.format("%,dđ", price);
    }
    public void setProductClickListener(ProductClickListener listener) {
        this.listener = listener;
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgcay;
        TextView txtnamecay,txtloaicay,txtgia;
        ImageButton itFavorite;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgcay = itemView.findViewById(R.id.ivanhpk);
            txtnamecay = itemView.findViewById(R.id.tvnamecay);
            txtloaicay = itemView.findViewById(R.id.tvloaicay);
            txtgia = itemView.findViewById(R.id.tvpkgia);
            itFavorite = itemView.findViewById(R.id.it_favorite);
        }
    }
}
