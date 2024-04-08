package com.example.ass_adr_api.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.ass_adr_api.Adapter.FavoriteApdater;
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
    private RecyclerView recyclerView;
    private FavoriteApdater favoriteAdapter;
    private List<Product> favoriteProducts = new ArrayList<>();
    private ApiService apiService;
    String productId = "6612134450fdf064b2e1182f"; // Thay "123" bằng ID của sản phẩm bạn muốn lấy danh sách yêu thích từ API

    private  HttpRequest httpRequest;
    public Favorite() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

//    @Override
//    public void onViewCreated(View view, Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//        // Khởi tạo RecyclerView và cấu hình adapter
//        recyclerView = view.findViewById(R.id.rcyfavorite);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        favoriteAdapter = new FavoriteApdater(getContext(), (ArrayList<Product>) favoriteProducts);
//        recyclerView.setAdapter(favoriteAdapter);
//
//        // Khởi tạo đối tượng ApiService
//        apiService = httpRequest.CallApi();
//
//        // Lấy danh sách sản phẩm yêu thích từ API và cập nhật RecyclerView
//        getFavoriteProductsFromAPI(productId);
//    }
//
//    private void getFavoriteProductsFromAPI( String productId) {
//        // Gọi API để lấy danh sách sản phẩm yêu thích
//        ApiService apiService = httpRequest.CallApi();
//        Call<Response<ArrayList<Product>>> call = apiService.getFavoriteProducts(productId);
//
//        call.enqueue(new Callback<Response<ArrayList<Product>>>() {
//            @Override
//            public void onResponse(Call<Response<ArrayList<Product>>> call, retrofit2.Response<Response<ArrayList<Product>>> response) {
//
//            }
//
//            @Override
//            public void onFailure(Call<Response<ArrayList<Product>>> call, Throwable t) {
//
//            }
//
//            @Override
//            public void onResponse(Call<Response<List<Product>>> call, retrofit2.Response<Response<List<Product>>> response) {
//                if (response.isSuccessful()) {
//                    Response<List<Product>> responseBody = response.body();
//                    if (responseBody != null && responseBody.getStatus() == 200) {
//                        List<Product> userFavoriteProducts = responseBody.getData();
//                        // Duyệt qua danh sách sản phẩm yêu thích
//                        for (Product product : userFavoriteProducts) {
//                            // Kiểm tra trạng thái isFavorite và thêm vào danh sách sản phẩm yêu thích nếu đúng
//                            if (product.isFavorite()) {
//                                favoriteProducts.add(product);
//                            }
//                        }
//                        // Cập nhật dữ liệu cho adapter
//                        favoriteAdapter.notifyDataSetChanged();
//                    } else {
//                        Toast.makeText(getActivity(), "Lấy danh sách sản phẩm yêu thích thất bại", Toast.LENGTH_SHORT).show();
//                    }
//                } else {
//                    Toast.makeText(getActivity(), "Lỗi kết nối", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Response<List<Product>>> call, Throwable t) {
//                Toast.makeText(getActivity(), "Lỗi kết nối", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
}
