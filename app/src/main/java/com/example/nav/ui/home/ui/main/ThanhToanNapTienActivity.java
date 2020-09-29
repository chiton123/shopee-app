package com.example.nav.ui.home.ui.main;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.nav.R;
import com.example.nav.ui.Adapter.NganHangAdapter;
import com.example.nav.ui.Model.NganHang;

import java.util.ArrayList;

public class ThanhToanNapTienActivity<onActivityResult> extends AppCompatActivity {
    Toolbar toolbar;
    Button btnaddBank;
    NganHangAdapter adapter;
    ArrayList<NganHang> arrayList;
    ListView listView;
    int REQUEST_CODE_EDIT = 123;
    Button btnok;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanh_toan_nap_tien);
        anhxa();
        actionBar();
        EventButton();
        PickBank();


    }

    private void PickBank() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                for(int i=0; i < parent.getChildCount(); i++){
                    if(i == position){
                     //   parent.getChildAt(i).setBackgroundColor(Color.GREEN);
                        btnok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent();
                                intent.putExtra("tennganhang", arrayList.get(position).getTennganhang());
                                setResult(RESULT_OK, intent);
                                finish();
                            }
                        });
                    }else {
                        parent.getChildAt(i).setBackgroundColor(Color.WHITE);
                    }
                }
            }
        });
    }

    private void EventButton() {
        btnaddBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ThanhToanNapTienActivity.this, NganHangActivity.class);
                startActivityForResult(intent, REQUEST_CODE_EDIT);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_CODE_EDIT && resultCode == RESULT_OK && data != null ){
            NganHang nganHang = (NganHang) data.getSerializableExtra("nganhang");
            arrayList.add(nganHang);
            adapter.notifyDataSetChanged();
        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    private void actionBar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Phương thức thanh toán");
        actionBar.setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void anhxa() {
        toolbar = (Toolbar) findViewById(R.id.toolbarnaptienthanhtoan);
        btnaddBank = (Button) findViewById(R.id.buttonthemnganhang);
        listView = (ListView) findViewById(R.id.listviewnganhanglienket);
        arrayList = new ArrayList<>();
        adapter = new NganHangAdapter(getApplicationContext(), arrayList);
        listView.setAdapter(adapter);
        btnok = (Button) findViewById(R.id.buttondongy);
    }
}
