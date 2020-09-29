package com.example.nav.ui.home.ui.main;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
import com.example.nav.ui.Model.GioHangChoLayHang;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class ThongTinDonHang extends AppCompatActivity {
    TextView txttensp, txtsoluong, txtgia, txttongthanhtoan;
    ImageView imganhsanpham;
    Button btnhuydonhang;
    String urlhuydonhang = MainActivity.urlhuydonhang;
    Intent intent ;
    GioHangChoLayHang donhang;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_don_hang);
        anhxa();
        getInfo();
        EventButton();
        actionBar();


    }

    private void actionBar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Thông tin đơn hàng");
        actionBar.setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void EventButton() {
        btnhuydonhang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(ThongTinDonHang.this);
                alert.setTitle("Hủy đơn hàng");
                alert.setMessage("Bạn có muốn hủy đơn hàng này không ?");
                alert.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlhuydonhang,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        if(response.equals("success")){
                                            Toast.makeText(getApplicationContext(), "Đã hủy thành công", Toast.LENGTH_SHORT).show();
                                             finish();
                                        }
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                                    }
                                }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String,String> map = new HashMap<>();
                                map.put("idchitietdonhang", String.valueOf(donhang.getIdchitietdonhang()));
                                return map;
                            }
                        };
                        requestQueue.add(stringRequest);
                    }
                });
                alert.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alert.show();
            }
        });
    }

    private void getInfo() {
        toolbar = (Toolbar) findViewById(R.id.toolbarthongtindonhang);
        intent = getIntent();
        donhang = (GioHangChoLayHang) intent.getSerializableExtra("donhang");
        txttensp.setText(donhang.getTensp());
        txtsoluong.setText("X"+donhang.getSoluongsp() +"");
        txtgia.setText("Giá: "+ donhang.getGiasp()/donhang.getSoluongsp());
        txttongthanhtoan.setText("Tổng thanh toán: "+ donhang.getGiasp());
        Picasso.get().load(donhang.getHinhanhsp()).into(imganhsanpham);

    }

    private void anhxa() {
        txtgia = (TextView) findViewById(R.id.textviewgiathongtindonhang);
        txtsoluong = (TextView) findViewById(R.id.textviewsoluongthongtindonhang);
        txttensp = (TextView) findViewById(R.id.textviewtensanphamthongtindonhang);
        txttongthanhtoan = (TextView) findViewById(R.id.textviewtongthanhtoanthongtindonhang);
        imganhsanpham = (ImageView) findViewById(R.id.imageviewthongtindonhang);
        btnhuydonhang = (Button) findViewById(R.id.buttonhuydonhangthongtindonhang);
        imganhsanpham = (ImageView) findViewById(R.id.imageviewthongtindonhang);
    }
}
