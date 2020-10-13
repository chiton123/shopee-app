package com.example.nav.ui.ProductAdjustion;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.nav.MainActivity;
import com.example.nav.R;
import com.example.nav.ui.Adapter.VanChuyenAdapter;
import com.example.nav.ui.Model.VanChuyen;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class KenhVanChuyenActivity extends AppCompatActivity {
    Toolbar toolbar;
    ListView listView;
    ArrayList<VanChuyen> arrayList;
    VanChuyenAdapter adapter;
    String url = MainActivity.urlshoppingunit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kenh_van_chuyen);
        anhxa();
        actionBar();
        getData();

    }



    private void getData() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, MainActivity.urlshoppingunit, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                       // Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();
                        if(response != null){
                            for(int i=0; i < response.length(); i++){
                                try {
                                    JSONObject object = response.getJSONObject(i);
//                                    Toast.makeText(getApplicationContext(), object.getInt("id") + "", Toast.LENGTH_SHORT).show();
//                                    Toast.makeText(getApplicationContext(), object.getString("name"), Toast.LENGTH_SHORT).show();
//                                    Toast.makeText(getApplicationContext(), object.getInt("cost") + "", Toast.LENGTH_SHORT).show();
                                    arrayList.add(new VanChuyen(
                                            object.getInt("id"),
                                            object.getString("name"),
                                            object.getInt("cost")
                                    ));
                                   // adapter.notifyDataSetChanged();

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }

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
        arrayList = new ArrayList<VanChuyen>();
        adapter = new VanChuyenAdapter(getApplicationContext(), arrayList, this);
        listView.setAdapter(adapter);

    }
}
