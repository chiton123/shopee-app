package com.example.nav.ui.home.ui.main;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.nav.MainActivity;
import com.example.nav.R;
import com.example.nav.ui.home.LinkImageActivity;
import com.example.nav.ui.notifications.NotificationsFragment;

import java.util.HashMap;
import java.util.Map;

public class ThemSanPhamActivity extends AppCompatActivity {
    Toolbar toolbar;
    ImageView imgdanhmuc, imgthuonghieu, imglink, imgvanchuyen;
    TextView txt_danhmuc, txt_thuonghieu, txt_anh, txt_vanchuyen;
    EditText editgia, editsoluong, editten;
    Button btndang;
    int REQUEST_CODE= 123;
    int REQUEST_CODE_THUONG_HIEU= 234;
    int REQUEST_CODE_IMAGE = 345;
    int REQUEST_CODE_VAN_CHUYEN = 456;
    int idloai = 0, idthuonghieu = 0, gia = 0, soluong = 0, idvanchuyen = 0;
    String urlanh = "";
    String url = MainActivity.urlpostproduct;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_san_pham);
        anhxa();
        actionBar();
        eventImageRight();
        eventButton();
    }

    private void eventButton() {
        btndang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String price = editgia.getText().toString();
                final String number = editsoluong.getText().toString();
                final String name = editten.getText().toString();
                if(idloai == 0 || idthuonghieu ==0 || idvanchuyen == 0 || urlanh.isEmpty() || price.isEmpty() || number.isEmpty() || name.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                }else {
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    if(response.equals("success")){
                                        Toast.makeText(getApplicationContext(), "Đăng sản phẩm thành công", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                                }
                            }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String,String> map = new HashMap<>();
                            map.put("name", name);
                            map.put("price", price);
                            map.put("number", number);
                            map.put("image", urlanh);
                            map.put("idstore", String.valueOf(MainActivity.idstore) );
                            map.put("idtype", String.valueOf(idloai));
                            map.put("idbrand", String.valueOf(idthuonghieu));
                            map.put("ship", String.valueOf(idvanchuyen));


                            return map;
                        }
                    };
                    requestQueue.add(stringRequest);
                }
            }
        });

    }

    private void eventImageRight() {
        imgdanhmuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DanhMucActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
        imgthuonghieu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ThuongHieuActivity.class);
                startActivityForResult(intent, REQUEST_CODE_THUONG_HIEU);
            }
        });
        imglink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LinkImageActivity.class);
                startActivityForResult(intent, REQUEST_CODE_IMAGE);
            }
        });
        imgvanchuyen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), KenhVanChuyenActivity.class);
                startActivityForResult(intent, REQUEST_CODE_VAN_CHUYEN);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null){
            txt_danhmuc.setText(data.getStringExtra("name"));
            idloai = data.getIntExtra("id", 0);
        }
        if(requestCode == REQUEST_CODE_THUONG_HIEU && resultCode == 234 && data != null){
            txt_thuonghieu.setText(data.getStringExtra("name"));
            idthuonghieu = data.getIntExtra("id", 0);
        }
        if(requestCode == REQUEST_CODE_IMAGE && resultCode == 345 && data != null){
            txt_anh.setText("Đã có");
            urlanh = data.getStringExtra("url");
        }
        if(requestCode == REQUEST_CODE_VAN_CHUYEN && resultCode == 456 && data != null){
            idvanchuyen = data.getIntExtra("id", 0);
            txt_vanchuyen.setText(data.getStringExtra("name"));
        }
        super.onActivityResult(requestCode, resultCode, data);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_them_san_pham, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuthemsp:
                Toast.makeText(getApplicationContext(), "ok", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void anhxa() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        imgdanhmuc = (ImageView) findViewById(R.id.imageviewright);
        txt_danhmuc = (TextView) findViewById(R.id.tendanhmuc);
        imgthuonghieu = (ImageView) findViewById(R.id.imageviewrightthuonghieu);
        txt_thuonghieu = (TextView) findViewById(R.id.tenthuonghieu);
        imglink = (ImageView) findViewById(R.id.imageviewrightanh);
        txt_anh = (TextView) findViewById(R.id.urlanh);
        editgia = (EditText) findViewById(R.id.gia);
        editsoluong = (EditText) findViewById(R.id.soluong);
        editten = (EditText) findViewById(R.id.edittexttensp);
        imgvanchuyen = (ImageView) findViewById(R.id.imageviewrightvanchuyen);
        txt_vanchuyen = (TextView) findViewById(R.id.kenhvanchuyen);
        btndang = (Button) findViewById(R.id.buttondang);


    }
}
