package com.example.ass_adr_api.Models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class Product implements Serializable {
private String _id,product_name;
private int price,quantity;
private String size;
    private String image;
    private String origin;
    @SerializedName("id_categories")
    private Categorie categorie;
    private String createAt, updateAt;
    private boolean isFavorite; // Thêm trường để lưu trạng thái yêu thích



    public Product() {
    }

    public Product(String _id, String product_name, int price, int quantity, String size, String image, String origin, Categorie categorie, String createAt, String updateAt,boolean isFavorite) {
        this._id = _id;
        this.product_name = product_name;
        this.price = price;
        this.quantity = quantity;
        this.size = size;
        this.image = image;
        this.origin = origin;
        this.categorie = categorie;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.isFavorite = isFavorite;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public String getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(String updateAt) {
        this.updateAt = updateAt;
    }
    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }
}
