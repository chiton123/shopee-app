package com.example.nav.ui.home;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
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
import com.example.nav.ui.home.ui.main.DiaChiNhanHangActivity;
import com.example.nav.ui.home.ui.main.PhuongThucThanhToanActivity;
import com.example.nav.ui.Adapter.ThanhToanAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ThanhToanActivity extends AppCompatActivity {
    Toolbar toolbarThanhToan;
    ListView listViewMathang;
    ImageButton imageButtonOption;
    TextView txtTongtien, txtTienship, txtTienThanhToan, txtoptionthanhtoan;
    public static TextView txtthanhtoan;
    ImageView imageViewluachon;
    Button btnDatHang;
    ThanhToanAdapter adapter;
    TextView txtdiachi;
    ImageView imageViewdiachi;
    String urldonhang = MainActivity.urldonhang;
    String urlchitietdonhang = MainActivity.urlchitietdonhang;
    String urlsoluongdaban = MainActivity.urlsoluongdaban;
    String urlthanhtoan = MainActivity.urlairpaypayment;
    int money  = 0;
    int REQUEST_CODE = 123;
    int airpay = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanh_toan);
        anhxa();
        actionBar();
        tinhTien();
        EventButton();
        optionPayment();
        AddressPayment();
    }

    private void AddressPayment() {
        imageViewdiachi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DiaChiNhanHangActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
    }


    private void optionPayment() {
        imageViewluachon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PhuongThucThanhToanActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
    }

    private void EventButton() {
        btnDatHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(airpay == 1){
                    RequestQueue requestQueue5 = Volley.newRequestQueue(getApplicationContext());
                    StringRequest stringRequest5 = new StringRequest(Request.Method.POST, urlthanhtoan,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                }
                            }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String,String> map  = new HashMap<>();
                            map.put("tongtien", String.valueOf(money));
                            map.put("username", MainActivity.username);
                            return map;
                        }
                    };
                    requestQueue5.add(stringRequest5);
                }


                RequestQueue requestQueue = Volley.newRequestQueue(ThanhToanActivity.this);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, urldonhang,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                              //  Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();
                                if(response.equals("success")){

                                    RequestQueue requestQueue1 = Volley.newRequestQueue(getApplicationContext());
                                    StringRequest stringRequest1 = new StringRequest(Request.Method.POST, urlchitietdonhang,
                                            new Response.Listener<String>() {
                                                @Override
                                                public void onResponse(String response) {
                                                  //  Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();
                                                        RequestQueue requestQueue2 = Volley.newRequestQueue(getApplicationContext());
                                                        StringRequest stringRequest2 = new StringRequest(Request.Method.POST, urlsoluongdaban,
                                                                new Response.Listener<String>() {
                                                                    @Override
                                                                    public void onResponse(String response) {

                                                                        Toast.makeText(getApplicationContext(), "ĐẶT HÀNG THÀNH CÔNG", Toast.LENGTH_SHORT).show();
                                                                        MainActivity.manggiohang.clear();
                                                                        finish();
                                                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                                        startActivity(intent);

                                                                    }
                                                                },
                                                                new Response.ErrorListener() {
                                                                    @Override
                                                                    public void onErrorResponse(VolleyError error) {
                                                                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                                                                    }
                                                                }){
                                                            @Override
                                                            protected Map<String, String> getParams() throws AuthFailureError {
                                                                JSONArray jsonArray1 = new JSONArray();
                                                                for(int i=0; i <  MainActivity.manggiohang.size(); i++){
                                                                    JSONObject object = new JSONObject();
                                                                    try {
                                                                    object.put("masanpham", MainActivity.manggiohang.get(i).getIdsp());
                                                                    object.put("soluong", MainActivity.manggiohang.get(i).getSoluongsp());
                                                           //             Toast.makeText(getApplicationContext(),MainActivity.manggiohang.get(i).getSoluongsp() +"" , Toast.LENGTH_LONG).show();
                                                                    } catch (JSONException e) {
                                                                        e.printStackTrace();
                                                                    }
                                                                    jsonArray1.put(object);
                                                                }
                                                                Map<String,String> map5 = new HashMap<>();
                                                                map5.put("json1", jsonArray1.toString());
                                                                return map5;
                                                            }
                                                        };
                                                        requestQueue2.add(stringRequest2);
                                                       // Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();

                                                    }

                                            },
                                            new Response.ErrorListener() {
                                                @Override
                                                public void onErrorResponse(VolleyError error) {
                                                    Toast.makeText(getApplicationContext(), "ĐẶT HÀNG THẤT BẠI", Toast.LENGTH_SHORT).show();
                                                }
                                            }) {
                                        @Override
                                        protected Map<String, String> getParams() throws AuthFailureError {
                                            JSONArray jsonArray = new JSONArray();
                                            for(int i=0; i < MainActivity.manggiohang.size(); i++){
                                                JSONObject object = new JSONObject();
                                                try {
                                                    object.put("masanpham", MainActivity.manggiohang.get(i).getIdsp());
                                                    object.put("tensanpham", MainActivity.manggiohang.get(i).getTensp());
                                                        object.put("giatridonhang", MainActivity.manggiohang.get(i).getGiasp());
                                                    object.put("soluongsanpham", MainActivity.manggiohang.get(i).getSoluongsp());
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                                jsonArray.put(object);

                                            }
                                            HashMap<String,String> map = new HashMap<String, String>();
                                            map.put("json", jsonArray.toString());
                                            map.put("tendangnhap", MainActivity.username);
                                            return map;
                                        }
                                    };
                                    requestQueue1.add(stringRequest1);
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getApplicationContext(), "don hang that bai", Toast.LENGTH_SHORT).show();
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> map = new HashMap<>();
                        map.put("tongtien", String.valueOf(money));
                        map.put("tendangnhap", MainActivity.username);
                        map.put("pttt", MainActivity.waypayment);
                        map.put("iduser", String.valueOf(MainActivity.iduser));
                        return map;
                    }
                };
                requestQueue.add(stringRequest);

            }
        });

    }


    private void tinhTien() {
        Intent intent = getIntent();
        int tongtien = intent.getIntExtra("tongtien", 0);
        money = tongtien;
        txtTongtien.setText(""+ money + "d");
        txtTienThanhToan.setText(money + "d");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null){
            txtthanhtoan.setText(data.getStringExtra("cachthanhtoan"));
            MainActivity.waypayment = data.getStringExtra("cachthanhtoan");
            if(MainActivity.waypayment.equals("Ví AirPay")){
                airpay = 1;
            }
        }
        if(requestCode == REQUEST_CODE && resultCode == 5 && data != null){
            txtdiachi.setText(data.getStringExtra("diachi"));
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void actionBar() {
        setSupportActionBar(toolbarThanhToan);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("THANH TOÁN");
        actionBar.setDisplayHomeAsUpEnabled(true);
        toolbarThanhToan.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void anhxa() {
        toolbarThanhToan = (Toolbar) findViewById(R.id.toolbarThanhToan);
        listViewMathang = (ListView) findViewById(R.id.listviewthanhtoan);
        txtTongtien = (TextView) findViewById(R.id.textviewtongtienhang);
        txtTienThanhToan = (TextView)findViewById(R.id.textviewtongthanhtoan);
        btnDatHang = (Button) findViewById(R.id.buttondathang1);
        adapter = new ThanhToanAdapter(getApplicationContext(), MainActivity.manggiohang);
        listViewMathang.setAdapter(adapter);
        imageViewluachon = (ImageView) findViewById(R.id.imageviewluachonthanhtoan);
        txtthanhtoan = (TextView) findViewById(R.id.textviewthanhtoan);
        imageViewdiachi = (ImageView) findViewById(R.id.imageviewdiachithanhtoan);
        txtdiachi = (TextView) findViewById(R.id.textviewdiachithanhtoan);
    }
}
