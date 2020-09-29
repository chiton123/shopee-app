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
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nav.R;
import com.example.nav.ui.Adapter.NapTienAdapter;

import java.util.ArrayList;

public class NapTienActivity extends AppCompatActivity {
    Toolbar toolbar;
    GridView gridView;
    NapTienAdapter adapter;
    ArrayList<Integer> arrayList;
    ImageView imageViewthanhtoan;
    TextView txtphuongthucthanhtoan, txtsotiennap;
    Button btnthanhtoan;
    int REQUEST_CODE = 123;
    int check = 0;
    int check1 = 0;
    int sotiennap = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nap_tien);
        anhxa();
        actionBar();
        EventButton();
        EventPutMoney();

    }

    private void EventPutMoney() {
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for(int i=0 ; i < parent.getChildCount(); i++){
                    if(i == position){
                        parent.getChildAt(i).setBackgroundColor(Color.RED);
                        txtsotiennap.setText(arrayList.get(position)+"");
                        sotiennap = arrayList.get(position);
                        check1 = 1;
                    }else {
                        parent.getChildAt(i).setBackgroundColor(Color.WHITE);
                    }
                }
            }
        });
    }

    private void EventButton() {
        imageViewthanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ThanhToanNapTienActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
        btnthanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(check == 1 && check1 == 1){
                //    Toast.makeText(getApplicationContext(), "ok", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    intent.putExtra("sotien", sotiennap);
                    setResult(RESULT_OK, intent);
                    finish();
                }else {
                    Toast.makeText(getApplicationContext(), "Vui lòng chọn phương thức thanh toán & số tiền !", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null){
            txtphuongthucthanhtoan.setText(data.getStringExtra("tennganhang"));
            check = 1;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void actionBar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Nạp tiền");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void anhxa() {
        toolbar = (Toolbar) findViewById(R.id.toolbarnaptien);
        gridView = (GridView) findViewById(R.id.gridviewnaptien);
        arrayList = new ArrayList<>();
        arrayList.add(40000);
        arrayList.add(200000);
        arrayList.add(400000);
        arrayList.add(1000000);
        arrayList.add(1200000);
        arrayList.add(10000000);
        arrayList.add(30000000);
        arrayList.add(50000000);
        arrayList.add(100000000);
        adapter = new NapTienAdapter(getApplicationContext(), arrayList);
        gridView.setAdapter(adapter);
        imageViewthanhtoan = (ImageView) findViewById(R.id.imageviewthanhtoannaptien);
        btnthanhtoan = (Button) findViewById(R.id.buttonthanhtoanngay);
        txtphuongthucthanhtoan = (TextView) findViewById(R.id.textviewphuongthucthanhtoannaptien);
        txtsotiennap = (TextView) findViewById(R.id.textviewsotiennap);
    }
}
