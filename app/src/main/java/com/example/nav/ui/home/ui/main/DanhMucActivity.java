package com.example.nav.ui.home.ui.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.nav.MainActivity;
import com.example.nav.R;
import com.example.nav.ui.Adapter.LoaiAdapter;
import com.example.nav.ui.Model.LoaiSanPham;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DanhMucActivity extends AppCompatActivity {
    Toolbar toolbar;
    ArrayList<LoaiSanPham> arrayList;
    ListView listView;
    LoaiAdapter adapter;
    String url = MainActivity.urlkindproduct;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_muc);
        anhxa();
        actionBar();
        getData();
        eventName();
    }

    private void eventName() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), arrayList.get(position).getTenloaisanpham(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.putExtra("id", arrayList.get(position).getIdloaisanpham());
                intent.putExtra("name", arrayList.get(position).getTenloaisanpham());
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    private void getData() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for(int i=0; i < response.length(); i++){
                            try {
                                JSONObject object = response.getJSONObject(i);
                                arrayList.add(new LoaiSanPham(
                                        object.getInt("id"),
                                        object.getString("name"),
                                        object.getString("image")
                                ));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                        adapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
        requestQueue.add(jsonArrayRequest);
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
        listView = (ListView) findViewById(R.id.listview);
        arrayList = new ArrayList<>();
        adapter = new LoaiAdapter(getApplicationContext(), arrayList);
        listView.setAdapter(adapter);
    }
}
