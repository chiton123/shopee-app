package com.example.nav.ui.ProductAdjustion;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;

import com.example.nav.R;
import com.example.nav.ui.Adapter.ViewPageAdapter;
import com.example.nav.ui.Fragment.GanDayFragment;
import com.example.nav.ui.Fragment.HetHangFragment;
import com.example.nav.ui.Fragment.KhoHangFragment;
import com.example.nav.ui.Model.SanPhamSua;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class SuaSanPhamActivity extends AppCompatActivity {
    Toolbar toolbar;
    ViewPageAdapter adapter;
    TabLayout tabLayout;
    ViewPager viewPager;
    public static ArrayList<SanPhamSua> arrayList1;
    public static ArrayList<SanPhamSua> arrayList2;
    public static ArrayList<SanPhamSua> arrayList3;
    // gan day: các sản phẩm mới đăng
    // kho hàng: tất cả các sản phẩm
    // hết hàng : số lượng = 0

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_san_pham);
        arrayList1 = new ArrayList<>();
        arrayList2 = new ArrayList<>();
        arrayList3 = new ArrayList<>();
        anhxa();
        actionBar();
        setUpFragment();
        tabLayout.setupWithViewPager(viewPager);

    }

    private void actionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setUpFragment() {
        adapter = new ViewPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new GanDayFragment(), "Gần đây");
        adapter.addFragment(new KhoHangFragment(), "Kho hàng");
        adapter.addFragment(new HetHangFragment(), "Hết hàng");
        viewPager.setAdapter(adapter);

    }

    private void anhxa() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        viewPager = (ViewPager) findViewById(R.id.viewpager);

    }
}
