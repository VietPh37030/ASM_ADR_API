package com.example.ass_adr_api.Fragment;

import static android.app.ProgressDialog.show;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.ass_adr_api.Adapter.CategoriesAdapter;
import com.example.ass_adr_api.Adapter.ProductsAdapter;
import com.example.ass_adr_api.Models.Categorie;
import com.example.ass_adr_api.Models.Product;
import com.example.ass_adr_api.Models.Response;
import com.example.ass_adr_api.R;
import com.example.ass_adr_api.services.HttpRequest;
import com.example.ass_adr_api.services.HttpRequest2;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;

public class Home extends Fragment  {
    private RecyclerView recloai;
    private RecyclerView rcyproduct;
    private HttpRequest httpRequest;
    private HttpRequest2 httpRequest2;
    private CategoriesAdapter adapter;
    private ProductsAdapter productAdapter;
    private ArrayList<Categorie> list;
    private ArrayList<Product> listprouct;
    private ViewPager viewPager;
    public Home() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        initLocation(rootView); // Pass the inflated view to the method
        recloai = rootView.findViewById(R.id.rcvLoai);  // Corrected
        rcyproduct = rootView.findViewById(R.id.rcyproduct);
        list= new ArrayList<>();

        httpRequest = new HttpRequest();
        httpRequest.CallApi()
                .getListCategories()//phương thi api cân thuc thi
                .enqueue(getListCategotiesApi);//xử lý bất đồng bộ
        listprouct= new ArrayList<>();
        httpRequest2 = new HttpRequest2();
        httpRequest2.CallApi()
                .getListProduct()//phương thi api cân thuc thi
                .enqueue(getListProductApi);//xử lý bất đồng bộ
        return rootView;
    }


    private void initLocation(View rootView) {
        String[] items = {"Hà Nội", "Hồ Chí Minh", "Đà Nẵng"};
        final Spinner locationSpinner = rootView.findViewById(R.id.spinner); // Use rootView.findViewById()
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
        productAdapter = new ProductsAdapter(getContext(),pr);
        rcyproduct.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rcyproduct.setHasFixedSize(true);
        rcyproduct.setAdapter(productAdapter);
    }
    Callback<Response<ArrayList<Categorie>>> getListCategotiesApi = new Callback<Response<ArrayList<Categorie>>>() {
        // phương thức được gọi khi có phản hồi từ yêu cầu API.
        // Nó nhận vào đối tượng Response chứa dữ liệu trả về từ server.
        @Override
        public void onResponse(Call<Response<ArrayList<Categorie>>> call, retrofit2.Response<Response<ArrayList<Categorie>>> response) {
            //khi call thành công
            if (response.isSuccessful()){
                if (response.body().getStatus() == 200){ //check status code
                    list= response.body().getData();// gán dữ liệu trả về từ phản hồi vào biến ds
                    getData(list);
                }
            }
        }

        @Override
        public void onFailure(Call<Response<ArrayList<Categorie>>> call, Throwable t) {
            Log.d(">>>> Distributor", "onFailure: " + t.getMessage());
        }
    };
    ///// product

    Callback<Response<ArrayList<Product>>> getListProductApi = new Callback<Response<ArrayList<Product>>>() {
        @Override
        public void onResponse(Call<Response<ArrayList<Product>>> call, retrofit2.Response<Response<ArrayList<Product>>> response) {
            if (response.isSuccessful()) {
                if (response.body() != null && response.body().getStatus() == 200) {
                    ArrayList<Product> products = response.body().getData();
                    for (Product product : products) {
                        Log.d("getListProductApi", "Product: " + product.toString());
                    }
                    listprouct = products;
                    getData2(listprouct);
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
        public void onFailure(Call<Response<ArrayList<Product>>> call, Throwable t) {
            Log.d("getListProductApi", "onFailure: " + t.getMessage());
            showAlert("Failed to retrieve products: " + t.getMessage());
        }
    };

    private void showAlert(String message) {
        // Hiển thị cảnh báo cho người dùng
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(message)
                .setTitle("Error")
                .setPositiveButton("OK", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    /// close product


}

