package com.example.nav.ui.home.ui.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ShopeeBankActivity extends AppCompatActivity {
    Toolbar toolbar;
    Spinner spinner;
    EditText sotk;
    Button btnxacnhan, btnreset;
    String tk = "", bankname = "HD Bank";
    String url = MainActivity.urlshopeewallet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopee_bank);
        anhxa();
        spinerEvent();
        eventButton();
//        Toast.makeText(getApplicationContext(), "" + getIntent().getIntExtra("code", 0), Toast.LENGTH_SHORT).show();
    }

    private void eventButton() {
        btnxacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tk = sotk.getText().toString();
                if(tk.equals("")){
                    Toast.makeText(getApplicationContext(), "Vui lòng nhập số tài khoản", Toast.LENGTH_SHORT).show();
                }else {
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    if(response.equals("success")){
                                        Toast.makeText(getApplicationContext(), "Tạo ví thành công", Toast.LENGTH_SHORT).show();
                                        finish();
                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);

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
                            map.put("bank", bankname);
                            map.put("account", tk);
                            map.put("pin", String.valueOf(getIntent().getIntExtra("code", 0)));
                            return map;
                        }
                    };
                    requestQueue.add(stringRequest);

                }
            }
        });
        btnreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sotk.setText("");
            }
        });
    }

    private void spinerEvent() {
        String[] data = {"HD Bank", "Sacombank", "Viettinbank", "Vietcombank", "BIDV", "Dong A Bank"};

        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.selected_item, data);
        adapter.setDropDownViewResource(R.layout.dropdown_text_color);

        Spinner spinner = findViewById(R.id.spinner);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                bankname = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void anhxa() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        spinner = (Spinner) findViewById(R.id.spinner);
        sotk = (EditText) findViewById(R.id.editsotk);
        btnreset = (Button) findViewById(R.id.buttonreset);
        btnxacnhan = (Button) findViewById(R.id.buttonxacnhan);

    }
}
