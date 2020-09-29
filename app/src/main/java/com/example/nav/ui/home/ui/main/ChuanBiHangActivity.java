package com.example.nav.ui.home.ui.main;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
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
import com.example.nav.ui.Model.GioHangChoLayHang;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ChuanBiHangActivity extends AppCompatActivity {
    Toolbar toolbar;
    EditText editTextdiachi;
    TextView txtthoigian;
    Button btnxacnhan;
    ImageView imageViewthaydoithoigian;
    GioHangChoLayHang sp;
    int id = 0;
    String tensp = "";
    long gia = 0;
    String hinhanh = "";
    int soluong;
    String tennguoimua = "";
    int tinhtrang = 0;
    int thutu = 0;
    int idchitietdonhang = 0;
    String urltrangthaidonhang  = MainActivity.urltrangthaidonhang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chuan_bi_hang);
        anhxa();
        getthutusp();
        actionBar();
        EventButton();
    }
    private void getthutusp() {
        sp = (GioHangChoLayHang) getIntent().getSerializableExtra("sanpham");
        id = sp.getIdsp();
        gia = sp.getGiasp();
        tensp = sp.getTensp();
        soluong = sp.getSoluongsp();
        tennguoimua = sp.getTennguoimua();
        tinhtrang = sp.getTinhtrang();
        idchitietdonhang = sp.getIdchitietdonhang();
//        thutu = getIntent().getIntExtra("thutu", 0);

    }

    private void EventButton() {
        btnxacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Shopee sẽ đến lấy hàng đúng ngày hẹn!", Toast.LENGTH_SHORT).show();
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                StringRequest stringRequest = new StringRequest(Request.Method.POST, urltrangthaidonhang,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if(response.equals("success")){
                                    Toast.makeText(getApplicationContext(), "Xác nhận thành công", Toast.LENGTH_SHORT).show();
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
                        map.put("idchitietdonhang", String.valueOf(idchitietdonhang));
                        map.put("trangthai", String.valueOf(2));
                        return map;
                    }
                };
                requestQueue.add(stringRequest);

            }
        });
        imageViewthaydoithoigian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDate();
            }
        });
    }
    public void showDate(){
        final Calendar calendar = Calendar.getInstance();
        int ngay = calendar.get(Calendar.DATE);
        int thang = calendar.get(Calendar.MONTH);
        int nam = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year, month, dayOfMonth);
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                txtthoigian.setText(dateFormat.format(calendar.getTime()));
            }
        }, nam, thang, ngay);
        datePickerDialog.show();
    }

    private void actionBar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Chuẩn bị hàng");
        actionBar.setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void anhxa() {
        toolbar = (Toolbar) findViewById(R.id.toolbarchuanbihang);
        editTextdiachi = (EditText) findViewById(R.id.edittextdiachilayhang);
        txtthoigian = (TextView) findViewById(R.id.textviewngaylayhang);
        btnxacnhan = (Button) findViewById(R.id.buttonxacnhanchuanbihang);
        imageViewthaydoithoigian = (ImageView) findViewById(R.id.imageviewngaylayhang);

    }
}
