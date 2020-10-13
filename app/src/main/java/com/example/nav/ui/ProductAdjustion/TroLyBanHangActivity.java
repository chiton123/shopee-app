package com.example.nav.ui.ProductAdjustion;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.nav.R;
import com.example.nav.ui.Adapter.TrolyAdapter;
import com.example.nav.ui.Model.TroLy;

import java.util.ArrayList;

public class TroLyBanHangActivity extends AppCompatActivity {
    Toolbar toolbar;
    ArrayList<TroLy> arrayList;
    TrolyAdapter adapter;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tro_ly_ban_hang);
        anhxa();
        actionBar();
        eventChange();
    }

    private void eventChange() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    Intent intent = new Intent(getApplicationContext(), SuaSanPhamActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }
        });

    }

    private void actionBar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void anhxa() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        listView = (ListView) findViewById(R.id.listview);
        arrayList = new ArrayList<TroLy>();
        arrayList.add(new TroLy("Sản phẩm", R.drawable.product));
        arrayList.add(new TroLy("Khách hàng", R.drawable.customer));
        arrayList.add(new TroLy("Hồ sơ shop", R.drawable.document));
        arrayList.add(new TroLy("Thiết lập shop", R.drawable.set));
        adapter = new TrolyAdapter(getApplicationContext(), arrayList, R.layout.dong_tro_ly_ban_hang);
        listView.setAdapter(adapter);




    }
}
