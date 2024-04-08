package com.example.ass_adr_api.Fragment;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.ass_adr_api.Activity.EditProfile;
import com.example.ass_adr_api.Activity.QandAActivity;
import com.example.ass_adr_api.Models.Response;
import com.example.ass_adr_api.Models.User;
import com.example.ass_adr_api.R;
import com.example.ass_adr_api.Wellcome.LoginScreen;
import com.example.ass_adr_api.services.ApiService;
import com.example.ass_adr_api.services.HttpRequest;

import retrofit2.Call;
import retrofit2.Callback;

public class Account extends Fragment {

    private TextView txtUsername, txtEmail;
    private TextView txt_btn_chinhsuatt,btn_exit,btn_qana;
    private ImageView imgAvatar;
    private HttpRequest httpRequest;

    public Account() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        txtUsername = view.findViewById(R.id.textView12);
        txtEmail = view.findViewById(R.id.textView14);
        imgAvatar = view.findViewById(R.id.imageView);
        txt_btn_chinhsuatt = view.findViewById(R.id.textView17);
        btn_exit =view.findViewById(R.id.account_tx_exit);
        btn_qana = view.findViewById(R.id.textView21);
        httpRequest = new HttpRequest();
        // Lấy ID người dùng từ SharedPreferences
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("userInfo", MODE_PRIVATE);
        String userId = sharedPreferences.getString("userId", ""); // Lấy ID người dùng từ SharedPreferences
        // Gọi API để lấy thông tin người dùng
        getUserInfo(userId);
        txt_btn_chinhsuatt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EditProfile.class);
                intent.putExtra("userId", userId);
                startActivity(intent);
            }
        });
        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Hiển thị hộp thoại xác nhận trước khi đăng xuất
                new AlertDialog.Builder(getActivity())
                        .setTitle("Xác nhận đăng xuất")
                        .setMessage("Bạn có chắc chắn muốn đăng xuất?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Xóa dữ liệu đăng nhập từ SharedPreferences
                                SharedPreferences.Editor editor = getActivity().getSharedPreferences("userInfo", MODE_PRIVATE).edit();
                                editor.remove("userId");
                                editor.apply();

                                // Chuyển người dùng đến màn hình đăng nhập hoặc màn hình khác tùy thuộc vào yêu cầu của ứng dụng
                                // Ví dụ: chuyển người dùng đến màn hình đăng nhập
                                Intent intent = new Intent(getActivity(), LoginScreen.class);
                                startActivity(intent);
                                getActivity().finish(); // Đóng Fragment hiện tại
                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });
    btn_qana.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getActivity(),QandAActivity.class);
        startActivity(intent);
    }
});

        return view;
    }

    private void getUserInfo( String userId) {
        // Gọi API lấy thông tin người dùng
        ApiService apiService = httpRequest.CallApi();
        Call<Response<User>> call = apiService.getUserInfo(userId);
        call.enqueue(new Callback<Response<User>>() {
            @Override
            public void onResponse(Call<Response<User>> call, retrofit2.Response<Response<User>> response) {
                if (response.isSuccessful()) {
                    Response<User> responseBody = response.body();
                    if (responseBody != null && responseBody.getStatus() == 200) {
                        User user = responseBody.getData();
                        // Hiển thị thông tin người dùng lên giao diện
                        displayUserInfo(user);
                    } else {
                        Toast.makeText(getActivity(), "Lấy thông tin người dùng thất bại", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Lỗi kết nối", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Response<User>> call, Throwable t) {
                Toast.makeText(getActivity(), "Lỗi kết nối", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void displayUserInfo(User user) {
        // Hiển thị thông tin người dùng lên giao diện
        txtUsername.setText(user.getName());
        txtEmail.setText(user.getEmail());
        // Sử dụng thư viện Glide để hiển thị ảnh avatar từ URL
        Glide.with(this).load(user.getAvatar()).into(imgAvatar);
    }
}
