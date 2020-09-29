package com.example.nav.ui.home.ui.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import in.aabhasjindal.otptextview.OtpTextView;

public class RutTienActivity extends AppCompatActivity {
    EditText editTextsotien;
    TextView txt_nganhang;
    Button btnxacnhan;
    Toolbar toolbar;
    int maxmoney = 0;
    String bankname = "";
    int sotien = 0;
    String url = MainActivity.urlgetmoney;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rut_tien);
        anhxa();
        actionBar();
        getValue();
        eventButton();

    }

    private void getValue() {
        maxmoney = getIntent().getIntExtra("surplus", 0);
        bankname = getIntent().getStringExtra("bankname");
        txt_nganhang.setText(bankname);

    }

    private void eventButton() {
        btnxacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sotien = Integer.parseInt(editTextsotien.getText().toString());
                Toast.makeText(getApplicationContext(), maxmoney +"", Toast.LENGTH_SHORT).show();
                if(sotien > maxmoney){
                    Toast.makeText(getApplicationContext(), "Số tiền lớn hơn số tiền hiện có trong ví!", Toast.LENGTH_SHORT).show();
                }else {
                    showDialog();
                }
            }
        });

    }

    private void showDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_ruttien);
        final OtpTextView otpTextView = (OtpTextView) dialog.findViewById(R.id.otp_view);
        Button btndongy = (Button) dialog.findViewById(R.id.buttondongy);
        Button btnthoat = (Button) dialog.findViewById(R.id.buttonthoat);
        btndongy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pincode = Integer.parseInt(otpTextView.getOTP());
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if(response.equals("success")){
                                    Toast.makeText(getApplicationContext(), "Rút tiền thành công", Toast.LENGTH_SHORT).show();
                                    dialog.cancel();
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
                        map.put("money", String.valueOf(sotien));
                        return map;
                    }
                };
                requestQueue.add(stringRequest);
            }
        });
        btnthoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        dialog.show();
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

    private void anhxa() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        txt_nganhang = (TextView) findViewById(R.id.tennganhang);
        btnxacnhan = (Button) findViewById(R.id.buttonxacnhan);
        editTextsotien = (EditText) findViewById(R.id.sotien);
    }
}
