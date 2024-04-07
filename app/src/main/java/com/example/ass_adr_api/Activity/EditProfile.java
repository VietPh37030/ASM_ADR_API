package com.example.ass_adr_api.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ass_adr_api.Models.Response;
import com.example.ass_adr_api.Models.User;
import com.example.ass_adr_api.R;
import com.example.ass_adr_api.handle.Item_user_handle;
import com.example.ass_adr_api.services.ApiService;
import com.example.ass_adr_api.services.HttpRequest;

import retrofit2.Call;
import retrofit2.Callback;

public class EditProfile extends AppCompatActivity {

    private EditText edtUsername, edtEmail, edtHoten, edtPassword;
    private Button btnSave;
    private ImageView imgAvatar;
    private HttpRequest httpRequest;
    private User currentUser; // Đối tượng User hiện tại

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        // Ánh xạ view
        edtUsername = findViewById(R.id.edit_edt_username);
        edtEmail = findViewById(R.id.edit_edt_email);
        edtHoten = findViewById(R.id.edit_edt_hoten);
        edtPassword = findViewById(R.id.edit_edt_pass);
        btnSave = findViewById(R.id.button3);
        imgAvatar = findViewById(R.id.edit_img_avatar);
        httpRequest = new HttpRequest();

        // Nhận dữ liệu người dùng từ Intent
        String userId = getIntent().getStringExtra("userId");

        // Gọi API để lấy thông tin người dùng dựa trên ID
        getUserInfo(userId);

        // Sự kiện click cho nút btnSave
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lưu thông tin người dùng đã chỉnh sửa
                saveUserInfo();
            }
        });
    }

    private void getUserInfo(String userId) {
        // Gọi API lấy thông tin người dùng
        ApiService apiService = httpRequest.CallApi();
        Call<Response<User>> call = apiService.getUserInfo(userId);
        call.enqueue(new Callback<Response<User>>() {
            @Override
            public void onResponse(Call<Response<User>> call, retrofit2.Response<Response<User>> response) {
                if (response.isSuccessful()) {
                    Response<User> responseBody = response.body();
                    if (responseBody != null && responseBody.getStatus() == 200) {
                        currentUser = responseBody.getData();
                        // Hiển thị thông tin người dùng lên giao diện
                        displayUserInfo(currentUser);
                    } else {
                        Toast.makeText(EditProfile.this, "Lấy thông tin người dùng thất bại", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(EditProfile.this, "Lỗi kết nối", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Response<User>> call, Throwable t) {
                Toast.makeText(EditProfile.this, "Lỗi kết nối", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void displayUserInfo(User user) {
        // Hiển thị thông tin người dùng lên giao diện
        edtUsername.setText(user.getUsername());
        edtEmail.setText(user.getEmail());
        edtHoten.setText(user.getName());
        // Không nên hiển thị mật khẩu ở đây
        edtPassword.setText(user.getPassword());
        // Sử dụng thư viện Glide để hiển thị ảnh avatar từ URL
        Glide.with(EditProfile.this).load(user.getAvatar()).into(imgAvatar);
    }

    private void saveUserInfo() {
        // Lấy dữ liệu từ các EditText
        String newUsername = edtUsername.getText().toString();
        String newEmail = edtEmail.getText().toString();
        String newHoten = edtHoten.getText().toString();
        String newPassword = edtPassword.getText().toString(); // Lưu ý: không nên sửa mật khẩu trực tiếp từ giao diện

        // Cập nhật thông tin người dùng mới
        currentUser.setUsername(newUsername);
        currentUser.setEmail(newEmail);
        currentUser.setName(newHoten);
        currentUser.setPassword(newPassword);

        // Gọi API để cập nhật thông tin người dùng
        ApiService apiService = httpRequest.CallApi();
        Call<Response<User>> call = apiService.editUserApi(currentUser.get_id(), currentUser);
        call.enqueue(new Callback<Response<User>>() {
            @Override
            public void onResponse(Call<Response<User>> call, retrofit2.Response<Response<User>> response) {
                if (response.isSuccessful()) {
                    Response<User> responseBody = response.body();
                    if (responseBody != null && responseBody.getStatus() == 200) {
                        Toast.makeText(EditProfile.this, "Cập nhật thông tin người dùng thành công", Toast.LENGTH_SHORT).show();
                        // Refresh lại thông tin người dùng sau khi cập nhật thành công
                        getUserInfo(currentUser.get_id());
                    } else {
                        Toast.makeText(EditProfile.this, "Cập nhật thông tin người dùng thất bại", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(EditProfile.this, "Lỗi kết nối", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Response<User>> call, Throwable t) {
                Toast.makeText(EditProfile.this, "Lỗi kết nối", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
