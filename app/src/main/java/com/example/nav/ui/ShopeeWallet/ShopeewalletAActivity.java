package com.example.nav.ui.ShopeeWallet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.nav.MainActivity;
import com.example.nav.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ShopeewalletAActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView txt_sodu;
    ImageView imageViewruttien;
    String url = MainActivity.urlshopeesurplus;
    int surplus = 0;
    String bankname = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopeewallet_a);
        anhxa();
        actionBar();
        getSurplus();
        getMoney();

    }

    private void getMoney() {
//        imageViewruttien.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), RutTienActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                intent.putExtra("bankname", bankname);
//                intent.putExtra("surplus", surplus);
//                startActivity(intent);
//            }
//        });
    }

    private void getSurplus() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for(int i=0; i < jsonArray.length(); i++){
                                JSONObject object = jsonArray.getJSONObject(i);
                                surplus = object.getInt("surplus");
                                bankname = object.getString("bankname");
                                txt_sodu.setText(surplus + "đ");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
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
                map.put("iduser", String.valueOf(MainActivity.iduser));
                return map;
            }
        };
        requestQueue.add(stringRequest);

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
        txt_sodu = (TextView) findViewById(R.id.sodu);
    //    imageViewruttien = (ImageView) findViewById(R.id.imageviewruttien);


    }
}
