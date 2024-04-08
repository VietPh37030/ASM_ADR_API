package com.example.ass_adr_api.Activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.ass_adr_api.Models.Product;
import com.example.ass_adr_api.R;

public class DeiltaActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private int UpdatePricefeature;
    Button btn_addcart ;
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
                ImageView favorite = findViewById(R.id.dt_imb_favorite);
                TextView sizeTextView = findViewById(R.id.dt_tx_size);
                TextView originTextView = findViewById(R.id.dt_tx_quequan);
                TextView quantityTextView = findViewById(R.id.dt_tx_kho);
                TextView priceTextView = findViewById(R.id.dt_tx_price);
                btn_addcart = findViewById(R.id.dt_btn_addcart);
/// tăng giảm số lượng
                TextView upTextView = findViewById(R.id.dt_bt_up);
                TextView dowTextView = findViewById(R.id.dt_bt_dow);
                //close tăng giảm số lượng

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

                /////khởi tạo số lượng cấp nhật giá
                UpdatePricefeature =1;
                updateQuantityPrice();
                // Sự kiện click cho nút tăng và giảm số lượng
                upTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        UpdatePricefeature++;
                        updateQuantityPrice();
                    }
                });
                dowTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (UpdatePricefeature > 1 ){
                            UpdatePricefeature--;
                            updateQuantityPrice1();
                        } else {
                        // Hiển thị thông báo Toast khi số lượng đã là 1
                        Toast.makeText(DeiltaActivity.this, "Số lượng phải lớn hơn 0", Toast.LENGTH_SHORT).show();
                    }

                    }
                });

                //// su li nut them vao gio hang 
                btn_addcart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    addCart(Gravity.BOTTOM);
                    }
                });
            }
        }
    }

    private void addCart(int gravity) {
        final Dialog dialog  = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.item_dialog_alert);
        Window window = dialog.getWindow();
        if (window==null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = gravity;
        window.setAttributes(windowAttributes);
        if (Gravity.BOTTOM == gravity){
            dialog.setCancelable(true);
        } else {
            dialog.setCancelable(false);
        }

        // Ánh xạ các đối tượng Button trong dialog
        Button btn_no = dialog.findViewById(R.id.btn_no);
        Button btn_yes = dialog.findViewById(R.id.btn_yes);

        btn_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DeiltaActivity.this, "Nhấn thành công", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();
    }




    private void updateQuantityPrice() {
        TextView quantityTextView = findViewById(R.id.dt_tx_soluong);
        TextView priceTextView = findViewById(R.id.dt_tx_price);

        // Cập nhật số lượng
        int newQuantity = UpdatePricefeature;
        quantityTextView.setText(String.valueOf(newQuantity));

        // Cập nhật giá tiền dựa trên số lượng mới
        int price = Integer.parseInt(priceTextView.getText().toString().replaceAll("[^\\d]", ""));
        int newPrice = price * newQuantity;
        Log.d("DeiltaActivity", "Tăng: " + newPrice);
        priceTextView.setText(String.format("%,dđ", newPrice));

    }
    private void updateQuantityPrice1() {
        TextView quantityTextView = findViewById(R.id.dt_tx_soluong);
        TextView priceTextView = findViewById(R.id.dt_tx_price);

        // Cập nhật số lượng mới
        int newQuantity = UpdatePricefeature;
        quantityTextView.setText(String.valueOf(newQuantity));

        // Lấy giá tiền hiện tại và loại bỏ ký tự không phải số
        String priceString = priceTextView.getText().toString().replaceAll("[^\\d]", "");
        int price = Integer.parseInt(priceString);

        // Kiểm tra nếu số lượng mới là 0 thì giá tiền mới cũng là 0
        int newPrice = (newQuantity == 0) ? 0 : price / newQuantity;
        Log.d("DeiltaActivity", "Giảm: " + newPrice);
        // Hiển thị giá tiền mới
        priceTextView.setText(String.format("%,dđ", newPrice));
    }


}
