package com.example.ass_adr_api.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.ass_adr_api.Models.Product;
import com.example.ass_adr_api.R;

public class DeiltaActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deilta);
// Khởi tạo SharedPreferences
        sharedPreferences = getSharedPreferences("favorite_products", Context.MODE_PRIVATE);
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("product")) {
            Product product = (Product) intent.getSerializableExtra("product");
            if (product != null) {
                ImageView imageView = findViewById(R.id.dt_img_product);
                TextView categoryTextView = findViewById(R.id.dt_tx_category);
                TextView nameTextView = findViewById(R.id.textView9);
                TextView updowTextView = findViewById(R.id.dt_tx_soluong);
                ImageView favorite = findViewById(R.id.dt_imb_favorite);
                TextView sizeTextView = findViewById(R.id.dt_tx_size);
                TextView originTextView = findViewById(R.id.dt_tx_quequan);
                TextView quantityTextView = findViewById(R.id.dt_tx_kho);
                TextView priceTextView = findViewById(R.id.dt_tx_price);


                Glide.with(this).load(product.getImage()).into(imageView);
                categoryTextView.setText(product.getCategorie().getLoai());
                nameTextView.setText(product.getProduct_name());
                sizeTextView.setText(product.getSize());
                originTextView.setText(product.getOrigin());
                quantityTextView.setText(String.valueOf(product.getQuantity()));
                priceTextView.setText(String.valueOf(product.getPrice()));
                // Cập nhật trạng thái yêu thích của sản phẩm
                boolean isFavorite = sharedPreferences.getBoolean(product.get_id(), false);
                if (isFavorite) {
                    // Đã thêm vào yêu thích
                    favorite.setImageResource(R.drawable.favorite);
                } else {
                    // Chưa thêm vào yêu thích
                    favorite.setImageResource(R.drawable.heart);
                }

            }
        }
    }
}
