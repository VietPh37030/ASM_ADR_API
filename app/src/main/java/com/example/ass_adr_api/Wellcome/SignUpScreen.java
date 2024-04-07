package com.example.ass_adr_api.Wellcome;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.ass_adr_api.Models.Response;
import com.example.ass_adr_api.Models.User;
import com.example.ass_adr_api.R;
import com.example.ass_adr_api.services.HttpRequest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

public class SignUpScreen extends AppCompatActivity {
    EditText txtuserdk, txtpassdk, txtemail, txtnamedk;
    Button btndangky;
    ImageView imgavatar;
    TextView signup_tx_login;
    File file;
    private HttpRequest httpRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_screen);
        txtuserdk = findViewById(R.id.signup_edt_username);
        txtpassdk = findViewById(R.id.signup_edt_password);
        txtemail = findViewById(R.id.signup_edt_email);
        txtnamedk = findViewById(R.id.signup_edt_hoten);
        btndangky = findViewById(R.id.signup_btn_signup);
        imgavatar = findViewById(R.id.imgavatar);
        httpRequest = new HttpRequest();
        signup_tx_login = findViewById(R.id.signup_tx_login);
        signup_tx_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpScreen.this,LoginScreen.class);
                startActivity(intent);
            }
        });
        btndangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //request để gửi dữ liệu đăng ký người dùng lên server thông qua một REST API sử dụng thư viện Retrofit,tạo các đối tượng RequestBody
                RequestBody _username = RequestBody.create(MediaType.parse("multipart/form-data"), txtuserdk.getText().toString());//RequestBody từ dữ liệu nhập từ trường txtuserdk và sử dụng loại dữ liệu multipart/form-data
                RequestBody _password = RequestBody.create(MediaType.parse("multipart/form-data"), txtpassdk.getText().toString());
                RequestBody _email = RequestBody.create(MediaType.parse("multipart/form-data"), txtemail.getText().toString());
                RequestBody _name = RequestBody.create(MediaType.parse("multipart/form-data"), txtnamedk.getText().toString());
                MultipartBody.Part multipartBody;
                if (file != null) {
                    RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
                    multipartBody = MultipartBody.Part.createFormData("avatar", file.getName(), requestBody);
                } else {
                    multipartBody = null;
                }
                httpRequest.CallApi().register(_username, _password, _email, _name, multipartBody).enqueue(responseUser);//gửi request đăng ký người dùng lên server. Kết quả sẽ được xử lý thông qua responseUser

            }
        });
        ///
        imgavatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage();
            }
        });
    }

    Callback<Response<User>> responseUser = new Callback<Response<User>>() {
        //Phương thức này được gọi khi request đăng ký người dùng được gửi thành công
        @Override
        public void onResponse(Call<Response<User>> call, retrofit2.Response<Response<User>> response) {
            if (response.isSuccessful()) {
                if (response.body().getStatus() == 200) {
                    Toast.makeText(SignUpScreen.this, "Đăng kí thành ", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SignUpScreen.this, LoginScreen.class));
                    finish();
                } else {
                    Toast.makeText(SignUpScreen.this, "Dk fail", Toast.LENGTH_SHORT).show();
                }
            }
        }

        @Override
        public void onFailure(Call<Response<User>> call, Throwable t) {
            Log.e("Lỗi", t.getMessage());
        }
    };

    private void chooseImage() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        getImage.launch(intent);
    }

    ActivityResultLauncher<Intent> getImage = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == Activity.RESULT_OK) {
                Intent data = result.getData();
                Uri imgpath = data.getData();
                file = createFileFromURI(imgpath, "avatar");

                //file avatar biến global
                Glide.with(SignUpScreen.this)
                        .load(file)//load file hình
                        .thumbnail(Glide.with(SignUpScreen.this).load(R.drawable.loading))
                        .centerCrop() //cắt ảnh
                        .circleCrop() //bo tròn hình
                        .diskCacheStrategy(DiskCacheStrategy.NONE)  //clear cach
                        .skipMemoryCache(true)
                        .into(imgavatar);
            }
        }
    });

    private File createFileFromURI(Uri uri, String name) {
        File file = new File(SignUpScreen.this.getCacheDir(), name + ".png");  //Đoạn mã này tạo một tệp mới trong thư mục cache của ứng dụng
        try {
            InputStream inputStream = SignUpScreen.this.getContentResolver().openInputStream(uri);//mở một luồng đầu vào từ URI
            OutputStream outputStream = new FileOutputStream(file);//tạo một luồng đầu ra để ghi dữ liệu vào tệp
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
            outputStream.close();
            inputStream.close();
            return file;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}