package com.example.nav.ui.shop;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.nav.R;
import com.example.nav.ui.Model.SanPham;
import com.example.nav.ui.Adapter.SanPhamAdapter;

import java.util.ArrayList;

import co.ceryle.radiorealbutton.library.RadioRealButton;
import co.ceryle.radiorealbutton.library.RadioRealButtonGroup;

public class SearchOnShop extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recyclerView;
    RadioRealButtonGroup realButtonGroup;
    RadioRealButton btnmoinhat, btnbanchay, btnsapxepgia;
    ArrayList<SanPham> arrayList;
    SanPhamAdapter adapter;
    String content = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_on_shop);
        anhxa();
        actionBar();
        content = getIntent().getStringExtra("content");


    }

    private void actionBar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Tìm kiếm");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void anhxa() {
        toolbar = (Toolbar) findViewById(R.id.toolbarsearchonshop);
        arrayList = new ArrayList<>();
        adapter = new SanPhamAdapter(recyclerView,this, arrayList);
        recyclerView = (RecyclerView) findViewById(R.id.recycleviewsearchonshop);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        recyclerView.setAdapter(adapter);
    }
}
