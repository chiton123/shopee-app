package com.example.nav.ui.shop;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
import com.example.nav.ui.ProductAdjustion.TroLyBanHangActivity;
import com.example.nav.ui.Order.DonHangActivity;
import com.example.nav.ui.ProductAdjustion.ThemSanPhamActivity;

import java.util.HashMap;
import java.util.Map;

public class Quanlyshopactivity extends AppCompatActivity {
    TextView txttenshop;
    ImageView imageViewdonhang;
    Toolbar toolbar;
    ImageView imgcholayhang, imgdahuy, imgtrahang, imgdangsp;
    ImageView imgtrolybanhang;
    TextView notify, notify1, notify2;
    String url = MainActivity.urlnotifyshop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quanlyshopactivity);
        anhxa();
        actionBar();
        getShopName();
        eventImage();
        getnotify(1);
        getnotify(4);
    }

    private void getnotify(final int x) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        int a = Integer.parseInt(response.toString());
                        if(a > 0){
                            if(x == 1){
                                notify.setText(response.toString());
                                notify.setVisibility(View.VISIBLE);
                            }
                            if(x == 4){
                                notify1.setText(response.toString());
                                notify1.setVisibility(View.VISIBLE);
                            }


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
                map.put("iduser", String.valueOf(MainActivity.iduser));
                map.put("x", String.valueOf(x));
                return map;
            }
        };
        requestQueue.add(stringRequest);

    }

    private void eventImage() {
        imgcholayhang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DonHangActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("index", 0);
                startActivity(intent);
            }
        });
        imgdahuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DonHangActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("index", 3);
                startActivity(intent);
            }
        });
        imgdangsp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ThemSanPhamActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        imgtrolybanhang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TroLyBanHangActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

    }

    private void actionBar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Shop của tôi");
        actionBar.setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void anhxa() {
        toolbar = (Toolbar) findViewById(R.id.toolbarownershop);
        txttenshop = (TextView) findViewById(R.id.textviewtenshopquanly);
        imageViewdonhang = (ImageView) findViewById(R.id.imageviewdonhang);
        imageViewdonhang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DonHangActivity.class);
                startActivity(intent);
            }
        });
        imgdahuy = (ImageView) findViewById(R.id.dahuy);
        imgcholayhang = (ImageView) findViewById(R.id.cholayhang);
        imgtrahang = (ImageView) findViewById(R.id.trahang);
        notify = (TextView) findViewById(R.id.number);
        notify1 = (TextView) findViewById(R.id.number1);
        notify2 = (TextView) findViewById(R.id.number2);
        imgdangsp = (ImageView) findViewById(R.id.imageviewdangsanpham1);
        imgtrolybanhang = (ImageView) findViewById(R.id.troly);

    }

    private void getShopName() {
        Intent intent = getIntent();
        txttenshop.setText(intent.getStringExtra("tenshop"));

    }
}
