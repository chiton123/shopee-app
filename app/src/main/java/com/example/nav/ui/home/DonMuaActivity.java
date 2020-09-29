package com.example.nav.ui.home;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;

import com.example.nav.R;
import com.example.nav.ui.Fragment.FragmentChoXacNhan;
import com.example.nav.ui.Fragment.FragmentDaGiao;
import com.example.nav.ui.Fragment.FragmentDaHuy;
import com.example.nav.ui.Fragment.FragmentDangGiao;
import com.example.nav.ui.Adapter.ViewPageAdapter;
import com.google.android.material.tabs.TabLayout;

public class DonMuaActivity extends AppCompatActivity {
    ViewPager viewPager;
    Toolbar toolbar;
    TabLayout tabLayout;
    int index = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_don_mua);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        toolbar = (Toolbar) findViewById(R.id.toolbarDonMua);
        tabLayout = (TabLayout)findViewById(R.id.tablayout);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("ĐƠN HÀNG");
        actionBar.setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        setUpViewPager();
        tabLayout.setupWithViewPager(viewPager);
        index = getIntent().getIntExtra("index", 0);
        TabLayout.Tab tab = tabLayout.getTabAt(index);
        tab.select();


    }

    private void setUpViewPager() {
        ViewPageAdapter adapter = new ViewPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new FragmentChoXacNhan(), "Chờ xác nhận");
        adapter.addFragment(new FragmentDangGiao(), "Đang giao");
        adapter.addFragment(new FragmentDaGiao(), "Đã giao");
        adapter.addFragment(new FragmentDaHuy(), "Đã hủy");
        viewPager.setAdapter(adapter);
    }
}
