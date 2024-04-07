package com.example.ass_adr_api.services;

import static com.example.ass_adr_api.services.ApiService.BASE_URL;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpRequest2 {
    private ApiService requestInterface;//bien interface, ApiServices sử dụng để gửi
    // các yêu cầu API thông qua Retrofit.
    //tạo và quản lý một đối tượng Retrofit để gửi các yêu cầu API,
// và cung cấp một cách để lấy đối tượng ApiServices đã được cấu hình sẵn để gửi các yêu cầu API.
    public HttpRequest2() {
        requestInterface = new Retrofit.Builder() //Khởi tạo một đối tượng Retrofit thông qua Retrofit.Builder
                .baseUrl(BASE_URL) //cấu hình các thông số như base URL
                .addConverterFactory(GsonConverterFactory.create()) //chuyển đổi đối tượng gson sang đôi tuong java
                .build()
                .create(ApiService.class);
    }
    //get retrofit
    public ApiService CallApi() {
        return requestInterface;
    }
}
