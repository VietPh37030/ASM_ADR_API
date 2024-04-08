package com.example.ass_adr_api.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.ass_adr_api.Adapter.CategoriesAdapter;
import com.example.ass_adr_api.Adapter.FavoriteApdater;
import com.example.ass_adr_api.Models.Categorie;
import com.example.ass_adr_api.Models.Product;
import com.example.ass_adr_api.Models.Response;
import com.example.ass_adr_api.R;
import com.example.ass_adr_api.services.ApiService;
import com.example.ass_adr_api.services.HttpRequest;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class Favorite extends Fragment {
    private RecyclerView rcyFavorite;
    private FavoriteApdater favoriteAdapter;
    private List<Product> list ;
    private  HttpRequest httpRequest;
    public Favorite() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_favorite, container, false);
        rcyFavorite = rootView.findViewById(R.id.rcyfavorite);
        list = new ArrayList<>();
        httpRequest = new HttpRequest();
        httpRequest.CallApi()
                .getListProduct()
                .enqueue(getListCategoriesApi);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
    private void getData(ArrayList<Product> ds) {
        favoriteAdapter = new FavoriteApdater(getContext(), ds);
        rcyFavorite.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rcyFavorite.setHasFixedSize(true);
        rcyFavorite.setAdapter(favoriteAdapter);
    }
    Callback<Response<ArrayList<Product>>> getListCategoriesApi = new Callback<Response<ArrayList<Product>>>() {
        @Override
        public void onResponse(@NonNull Call<Response<ArrayList<Product>>> call, @NonNull retrofit2.Response<Response<ArrayList<Product>>> response) {
            if (response.isSuccessful()) {
                if (response.body().getStatus() == 200) {
                    list = response.body().getData();
                    getData((ArrayList<Product>) list);
                }
            }
        }

        @Override
        public void onFailure(@NonNull Call<Response<ArrayList<Product>>> call, @NonNull Throwable t) {
            Log.d(">>>> Distributor", "onFailure: " + t.getMessage());
        }
    };
}
