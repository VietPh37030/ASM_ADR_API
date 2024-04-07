package com.example.ass_adr_api.Models;

public class Response<T> {
    private int status;
    private String messenger;
    private T data;//T là kiểu general
    private String token;
    private String refreshToken;

    public Response() {
    }

    public Response(int status, String messenger, T data, String token, String refreshToken) {
        this.status = status;
        this.messenger = messenger;
        this.data = data;
        this.token = token;
        this.refreshToken = refreshToken;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessenger() {
        return messenger;
    }

    public void setMessenger(String messenger) {
        this.messenger = messenger;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public T getData2() {
        return data;
    }
}
