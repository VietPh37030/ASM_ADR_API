package com.example.ass_adr_api.Wellcome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.ass_adr_api.R;

public class WellcomScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wellcom_screen);
        // Tạo một Handler
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Tạo Intent để chuyển sang màn hình LoginScreen
                Intent intent = new Intent(WellcomScreen.this, LoginScreen.class);
                startActivity(intent);
                finish(); // Kết thúc màn hình hiện tại
            }
        }, 3000); // 3000 milliseconds = 3 giây
    }
}