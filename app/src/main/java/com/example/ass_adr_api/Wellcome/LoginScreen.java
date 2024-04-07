package com.example.ass_adr_api.Wellcome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ass_adr_api.MainActivity;
import com.example.ass_adr_api.Models.Response;
import com.example.ass_adr_api.Models.User;
import com.example.ass_adr_api.R;
import com.example.ass_adr_api.services.HttpRequest;

import retrofit2.Call;
import retrofit2.Callback;

public class LoginScreen extends AppCompatActivity {
    TextView login_tx_singup, txtquenmk;
    EditText txtuser, txtpass;
    Button btnlogin;
    CheckBox login_chk_tk;
    private HttpRequest httpRequest;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private static final String PREF_NAME = "loginPrefs";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_CHECKBOX = "checkbox";
    private boolean isPasswordVisible = false; // Khai báo biến isPasswordVisible và gán giá trị mặc định là false

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        anhxa();
        httpRequest = new HttpRequest();
        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        editor = sharedPreferences.edit();

        // Kiểm tra checkbox nếu đã được lưu trạng thái trước đó
        if (sharedPreferences.getBoolean(KEY_CHECKBOX, false)) {
            login_chk_tk.setChecked(true);
            txtuser.setText(sharedPreferences.getString(KEY_USERNAME, ""));
            txtpass.setText(sharedPreferences.getString(KEY_PASSWORD, ""));
        }

        login_tx_singup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginScreen.this, SignUpScreen.class);
                startActivity(intent);
            }
        });

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User();
                String username = txtuser.getText().toString().trim();
                String password = txtpass.getText().toString().trim();
                user.setUsername(username);
                user.setPassword(password);
                httpRequest.CallApi().login(user).enqueue(responseUser);

                // Lưu thông tin tài khoản và mật khẩu nếu checkbox được tích vào
                if (login_chk_tk.isChecked()) {
                    editor.putString(KEY_USERNAME, username);
                    editor.putString(KEY_PASSWORD, password);
                    editor.putBoolean(KEY_CHECKBOX, true);
                    editor.apply();
                } else {
                    editor.remove(KEY_USERNAME);
                    editor.remove(KEY_PASSWORD);
                    editor.remove(KEY_CHECKBOX);
                    editor.apply();
                }
            }
        });

    }

    private void anhxa() {
        login_tx_singup = findViewById(R.id.login_tx_signup);
        txtuser = findViewById(R.id.login_edt_username);
        txtpass = findViewById(R.id.login_edt_password);
        btnlogin = findViewById(R.id.login_btn_Login);
        login_chk_tk = findViewById(R.id.login_chk_tk);
        txtquenmk = findViewById(R.id.login_tx_qmk);
    }

    Callback<Response<User>> responseUser = new Callback<Response<User>>() {
        @Override
        public void onResponse(Call<Response<User>> call, retrofit2.Response<Response<User>> response) {
            if (response.isSuccessful()) {
                if (response.body().getStatus() == 200) {
                    Toast.makeText(LoginScreen.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                    SharedPreferences sharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("userId", response.body().getData().get_id());
                    editor.putString("token", response.body().getToken());
                    editor.putString("refreshToken", response.body().getRefreshToken());
                    editor.putString("id", response.body().getData().get_id());
                    editor.apply();
                    startActivity(new Intent(LoginScreen.this, MainActivity.class));
                } else {
                    Toast.makeText(LoginScreen.this, "Login fail", Toast.LENGTH_SHORT).show();
                }
            }
        }

        @Override
        public void onFailure(Call<Response<User>> call, Throwable t) {
            Toast.makeText(LoginScreen.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
        }
    };
}
