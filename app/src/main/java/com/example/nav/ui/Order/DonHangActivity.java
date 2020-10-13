package com.example.nav.ui.Order;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;

import com.example.nav.R;
import com.example.nav.ui.Adapter.ViewPageAdapter;
import com.example.nav.ui.Fragment.FragmentChoLayHangChuShop;
import com.example.nav.ui.Fragment.FragmentDaGiaoChuShop;
import com.example.nav.ui.Fragment.FragmentDaHuyChuShop;
import com.example.nav.ui.Fragment.FragmentDangGiaoChuShop;
import com.example.nav.ui.Model.GioHangChoLayHang;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class DonHangActivity extends AppCompatActivity {
    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;
    int index = 0;
    public static ArrayList<GioHangChoLayHang> arrayListSanphamxuly;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_don_hang);
        anhxa();
        SetUpTabLayout();
        tabLayout.setupWithViewPager(viewPager);
        index = getIntent().getIntExtra("index",0);
        TabLayout.Tab tab = tabLayout.getTabAt(index);
        tab.select();
    }

    private void SetUpTabLayout() {
        ViewPageAdapter adapter = new ViewPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new FragmentChoLayHangChuShop(), "Chờ lấy hàng");
        adapter.addFragment(new FragmentDangGiaoChuShop(), "Đang giao");
        adapter.addFragment(new FragmentDaGiaoChuShop(), "Đã giao");
        adapter.addFragment(new FragmentDaHuyChuShop(), "Đã hủy");
        viewPager.setAdapter(adapter);
    }

    private void anhxa() {
        toolbar = (Toolbar) findViewById(R.id.toolbardonhang);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Đơn hàng");
        actionBar.setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tabLayout = (TabLayout) findViewById(R.id.tablayoutdonhang);
        viewPager = (ViewPager) findViewById(R.id.viewpagerdonhang);
        arrayListSanphamxuly = new ArrayList<>();

    }
}
