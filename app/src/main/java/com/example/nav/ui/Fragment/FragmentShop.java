package com.example.nav.ui.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.nav.MainActivity;
import com.example.nav.R;
import com.example.nav.ui.Model.SanPham;
import com.example.nav.ui.Adapter.SanPhamAdapter;
import com.example.nav.ui.shop.EvaluationShowActivity;
import com.example.nav.ui.shop.MapsActivity;
import com.example.nav.ui.shop.ShopActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FragmentShop extends Fragment {
    SanPhamAdapter adapter;
    ArrayList<SanPham> arrayListgoiy;
    RecyclerView recyclerViewgoiy;
    TextView txtsosao, txtsosanpham;
    String urlsanphamgoiy = MainActivity.urlsuggestitem;
    ImageView imgdanhgia;
    int idsp = 0;
    ImageView location;
    double vido = 0;
    double kinhdo = 0;
    String url = MainActivity.urlstorelocation;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop, container, false);
        arrayListgoiy = new ArrayList<>();
        recyclerViewgoiy = (RecyclerView) view.findViewById(R.id.recycleviewshop);
        recyclerViewgoiy.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewgoiy.setLayoutManager(layoutManager);
        recyclerViewgoiy.setItemAnimator(new DefaultItemAnimator());
        adapter = new SanPhamAdapter(recyclerViewgoiy,getActivity(), arrayListgoiy);
        recyclerViewgoiy.setAdapter(adapter);
        location = (ImageView) view.findViewById(R.id.location);
        imgdanhgia = (ImageView) view.findViewById(R.id.imageviewdanhgiashop);
        txtsosao = (TextView) view.findViewById(R.id.textviewdanhgiashop);
        txtsosanpham = (TextView) view.findViewById(R.id.soluong);
        eventAddress();
        getDataSuggestItem();
        idsp = ShopActivity.idsanpham;
        getLocation();
        return view;
    }

    public void getNumberStar(float sosao) {
        txtsosao.setText(sosao+"/5");
    }
    public void getThongtinshop() {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, MainActivity.urlshop,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for(int i=0; i < jsonArray.length(); i++){
                                JSONObject object = jsonArray.getJSONObject(i);
                                txtsosanpham.setText(object.getInt("soluongsanpham") + "");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("idcuahang", String.valueOf(ShopActivity.idcuahang));
                return map;
            }
        };
        requestQueue.add(stringRequest);

    }
    public void evaluationEvent() {
        imgdanhgia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EvaluationShowActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("sosao", ShopActivity.sosaotb);
                startActivity(intent);
            }
        });

    }

    private void getLocation() {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for(int i=0; i < jsonArray.length(); i++){
                                JSONObject object = jsonArray.getJSONObject(i);
                                vido = object.getDouble("vido");
                                kinhdo = object.getDouble("kinhdo");
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
                map.put("idcuahang", String.valueOf(ShopActivity.idcuahang));
                return map;
            }
        };
        requestQueue.add(stringRequest);

    }
    private void eventAddress() {
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MapsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("idcuahang", ShopActivity.idcuahang);
                intent.putExtra("vido", vido);
                intent.putExtra("kinhdo", kinhdo);
                startActivity(intent);
            }
        });

    }

    private void getDataSuggestItem() {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlsanphamgoiy,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for(int i =0; i < jsonArray.length(); i++){
                                JSONObject object = jsonArray.getJSONObject(i);
                                arrayListgoiy.add(new SanPham(
                                        object.getInt("id"),
                                        object.getString("name"),
                                        object.getString("image"),
                                        object.getInt("price"),
                                        object.getInt("idstore"),
                                        object.getInt("idtypeproduct"),
                                        object.getInt("salenumber"),
                                        object.getInt("number"),
                                        object.getInt("discount")
                                ));
                                adapter.notifyDataSetChanged();
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
                map.put("idsanpham", String.valueOf(idsp));
                map.put("idcuahang", String.valueOf(ShopActivity.idcuahang));
                return map;
            }
        };
        requestQueue.add(stringRequest);

    }
}
