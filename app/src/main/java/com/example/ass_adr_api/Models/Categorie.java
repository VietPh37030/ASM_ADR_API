package com.example.ass_adr_api.Models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Categorie implements Serializable {
    @SerializedName("_id")
    private String id;
    private String loai;
    @SerializedName("anh_loai")
    private String anhLoai;
    private String createdAt;
    private String updatedAt;
    @SerializedName("__v")
    private int v;

    public Categorie() {
    }

    public Categorie(String id, String loai, String anhLoai, String createdAt, String updatedAt, int v) {
        this.id = id;
        this.loai = loai;
        this.anhLoai = anhLoai;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.v = v;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLoai() {
        return loai;
    }

    public void setLoai(String loai) {
        this.loai = loai;
    }

    public String getAnhLoai() {
        return anhLoai;
    }

    public void setAnhLoai(String anhLoai) {
        this.anhLoai = anhLoai;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int getV() {
        return v;
    }

    public void setV(int v) {
        this.v = v;
    }
}
