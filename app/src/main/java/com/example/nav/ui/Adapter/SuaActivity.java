package com.example.nav.ui.Adapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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

public class SuaActivity extends AppCompatActivity {
    Toolbar toolbar;
    EditText editkhohang, editgia;
    String url = MainActivity.urlupdateproduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua);
        anhxa();
        actionBar();
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
        getMenuInflater().inflate(R.menu.menu_suasanpham, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.suasp:{
                final String price = editgia.getText().toString();
                final String number = editkhohang.getText().toString();
                if(price.equals("") || number.equals("")){
                    Toast.makeText(getApplicationContext(), "Điền thông tin vào", Toast.LENGTH_SHORT).show();
                }else {
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    if(response.equals("success")){
                                        Toast.makeText(getApplicationContext(), "Đã sửa thành công" , Toast.LENGTH_SHORT).show();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(getApplicationContext(), error.toString() , Toast.LENGTH_SHORT).show();
                                }
                            }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String,String> map = new HashMap<>();
                            map.put("idproduct", String.valueOf(getIntent().getIntExtra("idproduct", 0)) );
                            map.put("price", String.valueOf(price) );
                            map.put("number", String.valueOf(number));
                            return map;
                        }
                    };
                    requestQueue.add(stringRequest);

                }

            }
        }

        return super.onOptionsItemSelected(item);
    }

    private void anhxa() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        editgia = (EditText) findViewById(R.id.gia);
        editkhohang = (EditText) findViewById(R.id.soluong);
    }
}
