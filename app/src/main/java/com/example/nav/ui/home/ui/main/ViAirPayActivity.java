package com.example.nav.ui.home.ui.main;

import androidx.annotation.Nullable;
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

import java.util.HashMap;
import java.util.Map;

public class ViAirPayActivity extends AppCompatActivity {
    Toolbar toolbar;
    ImageView imageViewnaptien, imageViewruttien, imageViewlichsugiaodich;
    TextView txtsotien;
    int REQUEST_CODE = 123;
    String urlsotien = MainActivity.urlviairpay;
    String urlupdatemoney = MainActivity.urlupdateairpay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vi_air_pay);
        anhxa();
        actionBar();
        getAirPay();
        PutMoney();


    }

    private void getAirPay() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlsotien,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response != null){
                            txtsotien.setText(response.toString());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("username", MainActivity.username);
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null){
            int sotien = data.getIntExtra("sotien", 0);
            int sotienhientai = Integer.parseInt(txtsotien.getText().toString());
            final int tongcong = sotien + sotienhientai;
            txtsotien.setText(tongcong + "");
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            StringRequest stringRequest = new StringRequest(Request.Method.POST, urlupdatemoney,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if(response.equals("1")){
                                Toast.makeText(getApplicationContext(), "Nạp tiền thành công", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(getApplicationContext(), "Nạp tiền thất bại", Toast.LENGTH_SHORT).show();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> map = new HashMap<>();
                    map.put("username", MainActivity.username);
                    map.put("sotien", String.valueOf(tongcong));
                    return map;
                }
            };
            requestQueue.add(stringRequest);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void PutMoney() {
        imageViewnaptien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NapTienActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
    }

    private void actionBar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Ví AirPay");
        actionBar.setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void anhxa() {
        toolbar = (Toolbar) findViewById(R.id.toolbarviairpay);
        imageViewnaptien = (ImageView) findViewById(R.id.imageviewnaptien);
        imageViewruttien = (ImageView) findViewById(R.id.imageviewruttien);
        imageViewlichsugiaodich = (ImageView) findViewById(R.id.imageviewlichsugiaodich);
        txtsotien = (TextView) findViewById(R.id.textviewsotienhienco);

    }
}
