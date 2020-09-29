package com.example.nav.ui.home.ui.main;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.nav.R;
import com.example.nav.ui.Adapter.NganHangAdapter;
import com.example.nav.ui.Model.NganHang;

import java.util.ArrayList;

public class NganHangActivity extends AppCompatActivity {
    Toolbar toolbar;
    ArrayList<NganHang> arrayList;
    NganHangAdapter adapter;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ngan_hang);
        anhxa();
        actionBar();
        EventPickBank();
    }

    private void EventPickBank() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               // Toast.makeText(getApplicationContext(), arrayList.get(position).getTennganhang(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.putExtra("nganhang", arrayList.get(position));
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    private void actionBar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Ngân hàng");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void anhxa() {
        toolbar = (Toolbar) findViewById(R.id.toolbarnganhang);
        listView = (ListView) findViewById(R.id.listviewnganhang);
        arrayList = new ArrayList<>();
        arrayList.add(new NganHang(0, "VietTinBank", R.drawable.viettin));
        arrayList.add(new NganHang(1, "Sacombank", R.drawable.sacom));
        arrayList.add(new NganHang(2, "HDBank", R.drawable.hdbank));
        arrayList.add(new NganHang(3, "MMBBank", R.drawable.mbb));
        arrayList.add(new NganHang(4, "BIDV", R.drawable.bidv));
        adapter = new NganHangAdapter(getApplicationContext(), arrayList);
        listView.setAdapter(adapter);

    }
}
