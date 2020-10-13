package com.example.nav.ui.shop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
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
import com.example.nav.ui.Adapter.DanhGiaAdapter;
import com.example.nav.ui.Model.DanhGia;
import com.example.nav.ui.shop.ShopActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EvaluationShowActivity extends AppCompatActivity {
    ProgressBar progressBar;
    RatingBar ratingBar;
    TextView numberstar;
    ListView listView;
    ArrayList<DanhGia> arrayList;
    DanhGiaAdapter adapter;
    float sosao = 0;
    Toolbar toolbar;
    Button btntatca, btn1sao, btn2sao, btn3sao, btn4sao, btn5sao;
    String url = MainActivity.urlshopcomment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluation_show);
        anhxa();
        eventGetValue();
        getAccessment(0);
        eventButton();
    }

    private void eventButton() {
        btntatca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrayList.clear();
                getAccessment(0);
                adapter.notifyDataSetChanged();
            }
        });
        btn1sao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrayList.clear();
                getAccessment(1);
                adapter.notifyDataSetChanged();
            }
        });
        btn2sao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrayList.clear();
                getAccessment(2);
                adapter.notifyDataSetChanged();
            }
        });
        btn3sao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrayList.clear();
                getAccessment(3);
                adapter.notifyDataSetChanged();
            }
        });
        btn4sao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrayList.clear();
                getAccessment(4);
                adapter.notifyDataSetChanged();
            }
        });
        btn5sao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrayList.clear();
                getAccessment(5);
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void getAccessment(final int sosao) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for(int i=0; i < jsonArray.length(); i++){
                                JSONObject object = jsonArray.getJSONObject(i);
                                arrayList.add(new DanhGia(
                                        object.getInt("idproduct"),
                                        object.getString("username"),
                                        object.getString("remark"),
                                        (float) object.getDouble("star")
                                ));
                            }
                            adapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
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
                map.put("idcuahang", String.valueOf( ShopActivity.idcuahang));
                map.put("sosao", String.valueOf(sosao));
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void eventGetValue() {
        sosao =  getIntent().getFloatExtra("sosao",0);
        Toast.makeText(getApplicationContext(), sosao +"", Toast.LENGTH_SHORT).show();
        progressBar.setProgress((int) (sosao*20));
        numberstar.setText(sosao+"");
        ratingBar.setRating((float) sosao);
    }


    private void anhxa() {
        numberstar = (TextView) findViewById(R.id.numberstar);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        ratingBar = (RatingBar) findViewById(R.id.trungbinh);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btntatca = (Button) findViewById(R.id.buttontatca);
        btn1sao = (Button) findViewById(R.id.button1sao);
        btn2sao = (Button) findViewById(R.id.button2sao);
        btn3sao = (Button) findViewById(R.id.button3sao);
        btn4sao = (Button) findViewById(R.id.button4sao);
        btn5sao = (Button) findViewById(R.id.button5sao);
        listView = (ListView) findViewById(R.id.listview);
        arrayList = new ArrayList<>();
        adapter = new DanhGiaAdapter(getApplicationContext(), arrayList);
        listView.setAdapter(adapter);
    }
}
