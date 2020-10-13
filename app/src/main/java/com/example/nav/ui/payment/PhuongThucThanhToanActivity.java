package com.example.nav.ui.payment;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nav.MainActivity;
import com.example.nav.R;
import com.example.nav.ui.Adapter.PhuongThucThanhToanAdapter;
import com.example.nav.ui.Model.NganHang;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PhuongThucThanhToanActivity extends AppCompatActivity {
    Toolbar toolbar;
    ExpandableListView expandableListView;
    ArrayList<NganHang> listHeader;
    HashMap<NganHang,List<NganHang>> listdataChild;
    PhuongThucThanhToanAdapter adapter;
    TextView txtcachthanhtoan;
    Button btndongy;
    int check = 0;
    String urlsotien = MainActivity.urlviairpay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phuong_thuc_thanh_toan);
        anhxa();
        actionBar();
        PichWayofPayment();
        EventButton();
    }

    private void EventButton() {
        btndongy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(check == 0){
                    Intent intent = new Intent();
                    intent.putExtra("cachthanhtoan", txtcachthanhtoan.getText().toString());
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }

    private void PichWayofPayment() {
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                int index = parent.getFlatListPosition(ExpandableListView.getPackedPositionForChild(groupPosition, childPosition));
                parent.setItemChecked(index, true);
                return false;
            }
        });
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                int index = parent.getFlatListPosition(ExpandableListView.getPackedPositionForGroup(groupPosition));
                parent.setItemChecked(index, true);
                NganHang nganHang = listHeader.get(groupPosition);

                if(nganHang.getTennganhang().equals("Ví Shopee")){
                    if(MainActivity.kiemtralogin == 1){
                        if(MainActivity.totalpayment > MainActivity.airpaymoney){
                            Toast.makeText(getApplicationContext(), "Số tiền trong ví không đủ!", Toast.LENGTH_SHORT).show();
                            check = 1;
                        }
                    }
                }
                txtcachthanhtoan.setText(nganHang.getTennganhang());
                MainActivity.waypayment = nganHang.getTennganhang();
                return false;
            }
        });
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
        toolbar = (Toolbar) findViewById(R.id.toolbarphuongthucthanhtoan);
        listHeader = new ArrayList<>();
        expandableListView = (ExpandableListView) findViewById(R.id.listviewphuongthucthanhtoan);
        listdataChild = new HashMap<NganHang, List<NganHang>>();
        listHeader.add(new NganHang(0, "Ví Shopee", R.drawable.b1));
        listHeader.add(new NganHang(1, "Thẻ tín dụng", R.drawable.b3));
        listHeader.add(new NganHang(2, "Thanh toán khi nhận hàng", R.drawable.b2));
        List<NganHang> listAirpay = new ArrayList<>();
        List<NganHang> listCredit = new ArrayList<>();
        List<NganHang> listCod = new ArrayList<>();
        if(MainActivity.kiemtralogin == 1){
            int sotien = MainActivity.airpaymoney;
            listAirpay.add(new NganHang(0, "Số dư : " + sotien, R.drawable.b4));
        }
        listdataChild.put(listHeader.get(0), listAirpay);
        listdataChild.put(listHeader.get(1), listCredit);
        listdataChild.put(listHeader.get(2), listCod);
        adapter = new PhuongThucThanhToanAdapter(getApplicationContext(), listHeader, listdataChild);
        expandableListView.setAdapter(adapter);
        Display newDisplay = getWindowManager().getDefaultDisplay();
        int width = newDisplay.getWidth();
        expandableListView.setIndicatorBounds(width-70, width);
        txtcachthanhtoan = (TextView) findViewById(R.id.textviewcachthanhtoan);
        btndongy = (Button) findViewById(R.id.buttondongypttt);
    }
}
