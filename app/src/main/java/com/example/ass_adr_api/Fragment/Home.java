package com.example.ass_adr_api.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ass_adr_api.Activity.DeiltaActivity;
import com.example.ass_adr_api.Adapter.CategoriesAdapter;
import com.example.ass_adr_api.Adapter.ProductsAdapter;
import com.example.ass_adr_api.Models.Categorie;
import com.example.ass_adr_api.Models.Product;
import com.example.ass_adr_api.Models.ProductClickListener;
import com.example.ass_adr_api.Models.Response;
import com.example.ass_adr_api.R;
import com.example.ass_adr_api.services.HttpRequest;
import com.example.ass_adr_api.services.HttpRequest2;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;

public class Home extends Fragment implements ProductClickListener {
    private RecyclerView recloai;
    private RecyclerView rcyproduct;
    private HttpRequest httpRequest;
    private HttpRequest2 httpRequest2;
    private CategoriesAdapter adapter;
    private ProductsAdapter productAdapter;
    private ArrayList<Categorie> list;
    private ArrayList<Product> listProduct;

    public Home() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        initLocation(rootView);
        recloai = rootView.findViewById(R.id.rcvLoai);
        rcyproduct = rootView.findViewById(R.id.rcyproduct);
        list = new ArrayList<>();
        listProduct = new ArrayList<>();
        httpRequest = new HttpRequest();
        httpRequest.CallApi()
                .getListCategories()
                .enqueue(getListCategoriesApi);
        httpRequest2 = new HttpRequest2();
        httpRequest2.CallApi()
                .getListProduct()
                .enqueue(getListProductApi);
        return rootView;
    }

    private void initLocation(View rootView) {
        String[] items = {"Hà Nội", "Hồ Chí Minh", "Đà Nẵng"};
        final Spinner locationSpinner = rootView.findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        locationSpinner.setAdapter(adapter);
    }

    private void getData(ArrayList<Categorie> ds) {
        adapter = new CategoriesAdapter(getContext(), ds);
        recloai.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recloai.setHasFixedSize(true);
        recloai.setAdapter(adapter);
    }

    private void getData2(ArrayList<Product> pr) {
        productAdapter = new ProductsAdapter(getContext(), pr);
        productAdapter.setProductClickListener(this);
        rcyproduct.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rcyproduct.setHasFixedSize(true);
        rcyproduct.setAdapter(productAdapter);
    }

    @Override
    public void onProductClick(Product product) {
        Intent intent = new Intent(getActivity(), DeiltaActivity.class);
        intent.putExtra("product", product);
        startActivity(intent);
    }

    Callback<Response<ArrayList<Categorie>>> getListCategoriesApi = new Callback<Response<ArrayList<Categorie>>>() {
        @Override
        public void onResponse(@NonNull Call<Response<ArrayList<Categorie>>> call, @NonNull retrofit2.Response<Response<ArrayList<Categorie>>> response) {
            if (response.isSuccessful()) {
                if (response.body().getStatus() == 200) {
                    list = response.body().getData();
                    getData(list);
                }
            }
        }

        @Override
        public void onFailure(@NonNull Call<Response<ArrayList<Categorie>>> call, @NonNull Throwable t) {
            Log.d(">>>> Distributor", "onFailure: " + t.getMessage());
        }
    };

    Callback<Response<ArrayList<Product>>> getListProductApi = new Callback<Response<ArrayList<Product>>>() {
        @Override
        public void onResponse(@NonNull Call<Response<ArrayList<Product>>> call, @NonNull retrofit2.Response<Response<ArrayList<Product>>> response) {
            if (response.isSuccessful()) {
                if (response.body() != null && response.body().getStatus() == 200) {
                    listProduct = response.body().getData();
                    getData2(listProduct);
                } else {
                    Log.d("getListProductApi", "Empty response body or status is not 200");
                    showAlert("Empty response body or status is not 200 for products");
                }
            } else {
                Log.d("getListProductApi", "Unsuccessful response");
                showAlert("Unsuccessful response for products");
            }
        }

        @Override
        public void onFailure(@NonNull Call<Response<ArrayList<Product>>> call, @NonNull Throwable t) {
            Log.d("getListProductApi", "onFailure: " + t.getMessage());
            showAlert("Failed to retrieve products: " + t.getMessage());
        }
    };

    private void showAlert(String message) {
        new AlertDialog.Builder(getContext())
                .setTitle("Error")
                .setMessage(message)
                .setPositiveButton("OK", null)
                .show();
    }
}
