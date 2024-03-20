package com.example.ass_adr_api;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.ass_adr_api.Adapter.MenuAdapter;
import com.example.ass_adr_api.Fragment.Account;
import com.example.ass_adr_api.Fragment.CartProduct;
import com.example.ass_adr_api.Fragment.Favorite;
import com.example.ass_adr_api.Fragment.Home;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    MenuAdapter adapter;
    ViewPager2 pagerMain;
    ArrayList<Fragment> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }
    private void initView() {
        pagerMain = findViewById(R.id.pagerMain);
        bottomNavigationView = findViewById(R.id.menu_nav);
        list.add(new Home());
        list.add(new CartProduct());
        list.add(new Favorite());
        list.add(new Account());
        adapter = new MenuAdapter(this, list);
        pagerMain.setAdapter(adapter);
        pagerMain.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        bottomNavigationView.setSelectedItemId(R.id.nav_trangtru);
                        break;
                    case 1:
                        bottomNavigationView.setSelectedItemId(R.id.nav_sanpham);
                        break;
                    case 2:
                        bottomNavigationView.setSelectedItemId(R.id.nav_favorite);
                        break;
                    case 3:
                        bottomNavigationView.setSelectedItemId(R.id.nav_tai_khoan);
                        break;
                }
                super.onPageSelected(position);
            }
        });
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.nav_trangtru) {
                    pagerMain.setCurrentItem(0);
                }
                if (item.getItemId() == R.id.nav_sanpham) {
                    pagerMain.setCurrentItem(1);
                }
                if (item.getItemId() == R.id.nav_favorite) {
                    pagerMain.setCurrentItem(2);
                }
                if (item.getItemId() == R.id.nav_tai_khoan) {
                    pagerMain.setCurrentItem(3);
                }
                return true;
            }
        });
    }
}