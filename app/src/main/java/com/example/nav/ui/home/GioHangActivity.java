package com.example.nav.ui.home;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nav.MainActivity;
import com.example.nav.R;
import com.example.nav.ui.Adapter.GioHangAdapter;

public class GioHangActivity extends AppCompatActivity {
    private static TextView txtTongTien;
    ListView listView;
    TextView txtThongBao;
    Button btnThanhToan;
    Button btnTiepTucMuaHang;
    GioHangAdapter adapter;
    Toolbar toolbar;
    TextView txttoolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);
        anhxa();
        actionBar();
        DeleteItem();
        ChechData();
        EventUntil();
        EventButton();


    }
    private void actionBar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("");
        actionBar.setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void DeleteItem() {
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder alert = new AlertDialog.Builder(GioHangActivity.this);
                alert.setTitle("Xác nhận xóa");
                alert.setMessage("Bạn có muốn xóa món hàng này không ?");

                alert.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MainActivity.manggiohang.remove(position);
                        adapter.notifyDataSetChanged();
                        EventUntil();
                    }
                });
                alert.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                alert.show();



                return false;
            }
        });
    }


    private void EventButton() {
        btnTiepTucMuaHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
          //      intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        btnThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MainActivity.manggiohang.size() > 0){
                    Intent intent = new Intent(getApplicationContext(), ThanhToanActivity.class);
                    intent.putExtra("tongtien", Integer.valueOf(txtTongTien.getText().toString()));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }else {
                    Toast.makeText(getApplicationContext(), "Bạn chưa có mặt hàng nào !", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public static void EventUntil() {
        long tongtien=0;
        for(int i = 0; i < MainActivity.manggiohang.size(); i++){
            tongtien += MainActivity.manggiohang.get(i).getGiasp();
        }
        txtTongTien.setText(tongtien + "");
        MainActivity.totalpayment = (int) tongtien;
    }

    private void ChechData() {
        adapter.notifyDataSetChanged();
        if(MainActivity.manggiohang.size() > 0){
            listView.setVisibility(View.VISIBLE);
    //        txtThongBao.setVisibility(View.INVISIBLE);

        }else {
            adapter.notifyDataSetChanged();
            listView.setVisibility(View.INVISIBLE);
   //         txtThongBao.setVisibility(View.VISIBLE);

        }
    }

    private void anhxa() {
        toolbar = (Toolbar) findViewById(R.id.toolbargiohang);
        txttoolbar = (TextView) toolbar.findViewById(R.id.toolbartitle);
        listView = (ListView) findViewById(R.id.listviewgiohang);
//        txtThongBao = (TextView) findViewById(R.id.textviewGioHangTrong);
        txtTongTien = (TextView) findViewById(R.id.textviewtongtien);
        btnThanhToan = (Button) findViewById(R.id.buttonThanhToan);
        btnTiepTucMuaHang = (Button) findViewById(R.id.buttonTieptucmuahang);
        adapter = new GioHangAdapter(getApplicationContext(), MainActivity.manggiohang);
        listView.setAdapter(adapter);
    }
}
