package com.example.nav.ui.UserActivity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
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

import java.util.HashMap;
import java.util.Map;

public class DanhGiaActivity extends AppCompatActivity {
    RatingBar ratingBar;
    EditText editTextNhanxet;
    Button btndanhgia, btnhuy;
    float sosao = 0;
    String nhanxet = "";
    int masanpham = 0;
//    String urldanhgia = "http://192.168.137.83:8888/server/getdanhgiasendo.php";
    String urldanhgia = MainActivity.urldanhgia;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_gia);
        anhxa();
        actionBar();
        getMaSP();
        EventDanhGia();


    }
    private void actionBar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Đánh giá sản phẩm");
        actionBar.setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void EventDanhGia() {
        btndanhgia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sosao = ratingBar.getRating();
                nhanxet = editTextNhanxet.getText().toString();
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                StringRequest stringRequest = new StringRequest(Request.Method.POST, urldanhgia,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if(response.equals("success")){
                                    Toast.makeText(getApplicationContext(), "Đánh giá thành công", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
//                                Toast.makeText(getApplicationContext(), "Đánh giá thành công", Toast.LENGTH_SHORT).show();
//                                finish();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getApplicationContext(), "Đánh giá thất bại", Toast.LENGTH_SHORT).show();
                            }
                        }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> map = new HashMap<>();
                        map.put("tendangnhap", MainActivity.username);
                        map.put("sosao", String.valueOf(sosao));
                        map.put("nhanxet", nhanxet);
                        map.put("masanpham", String.valueOf(masanpham));

                        return map;
                    }
                };
                requestQueue.add(stringRequest);

            }
        });
        btnhuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getMaSP() {
        Intent intent = getIntent();
        masanpham = intent.getIntExtra("masanpham", 0);
    }

    private void anhxa() {
        toolbar = (Toolbar) findViewById(R.id.toolbardanhgia);
        ratingBar = (RatingBar) findViewById(R.id.ratingbardanhgia);
        editTextNhanxet = (EditText) findViewById(R.id.edittextdanhgia);
        btndanhgia = (Button) findViewById(R.id.buttondanhgiaDanhGia);
        btnhuy = (Button) findViewById(R.id.buttonHuyDanhGia);

    }
}
