package com.example.nav.ui.Sign_in;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class DangKyActivity extends AppCompatActivity {
    EditText editTen, editSodt, editDiaChi, editEmail, editMatKhau;
    Button btnDangKy, btnReset;
    String ten = "", diachi = "", email = "", matkhau = "";
    String sodt = "";
//    String url = "http://192.168.137.83:8888/server/dangkysendo.php";
    String url = MainActivity.urldangky;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);
        anhxa();
        actionBar();

        EventDK();
        EventReset();

    }

    private void actionBar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Đăng ký thành viên");
        actionBar.setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void EventReset() {
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTen.setText("");
                editSodt.setText("");
                editMatKhau.setText("");
                editEmail.setText("");
                editDiaChi.setText("");
            }
        });
    }

    private void EventDK() {
        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getdulieu();

                if(ten.isEmpty() || diachi.isEmpty() || email.isEmpty() || matkhau.isEmpty() || sodt.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Vui lòng điền đủ thông tin!", Toast.LENGTH_SHORT).show();
                }
                else {
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Toast.makeText(getApplicationContext(), "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), DangNhapActivity.class);
                                    startActivity(intent);
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String, String> map = new HashMap<>();
                            map.put("ten",ten);
                            map.put("diachi", diachi);
                            map.put("email", email);
                            map.put("matkhau", matkhau);
                            map.put("sodienthoai", sodt);

                            return map;
                        }
                    };
                    requestQueue.add(stringRequest);
                }
            }
        });
    }

    private void getdulieu() {
        ten = editTen.getText().toString();
        diachi = editDiaChi.getText().toString();
        email = editEmail.getText().toString();
        matkhau = editMatKhau.getText().toString();
        sodt = editSodt.getText().toString();
    }

    private void anhxa() {
        toolbar = (Toolbar) findViewById(R.id.toolbardangky);
        editTen = (EditText) findViewById(R.id.edittextTenDK);
        editDiaChi = (EditText) findViewById(R.id.edittextdiachiDK);
        editSodt = (EditText) findViewById(R.id.edittextSodtDK);
        editEmail = (EditText) findViewById(R.id.edittextEmailDK);
        editMatKhau = (EditText) findViewById(R.id.edittextmatkhauDK);
        btnDangKy = (Button) findViewById(R.id.buttondangky);
        btnReset = (Button) findViewById(R.id.buttonReset);

    }
}
