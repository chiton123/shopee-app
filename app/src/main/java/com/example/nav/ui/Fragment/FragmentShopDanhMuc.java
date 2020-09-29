package com.example.nav.ui.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.nav.MainActivity;
import com.example.nav.R;
import com.example.nav.ui.Adapter.SanPhamShopAdapter;
import com.example.nav.ui.Model.SanPhamShop;
import com.example.nav.ui.home.ui.main.DanhMucShopActivity;
import com.example.nav.ui.home.ui.main.ShopActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FragmentShopDanhMuc extends Fragment {
    ListView listView;
    ArrayList<SanPhamShop> arrayList;
    SanPhamShopAdapter adapter;
//    String urldanhmuc = "http://192.168.137.83:8888/server/getdanhmucshop.php";
    String urldanhmuc = MainActivity.urldanhmuc;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_danhmuc, container, false);
        listView = (ListView) view.findViewById(R.id.listviewshopdanhmuc);
        arrayList = new ArrayList<>();
        adapter = new SanPhamShopAdapter(getActivity(), arrayList);
        listView.setAdapter(adapter);
        changeActivity();
        getData();
        return view;
    }

    private void changeActivity() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), DanhMucShopActivity.class);
                intent.putExtra("idloai", arrayList.get(position).getIdloaisanpham());
                intent.putExtra("idcuahang", ShopActivity.idcuahang);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }

    public void getData() {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urldanhmuc,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for(int i=0; i < jsonArray.length(); i++){
                                JSONObject object = jsonArray.getJSONObject(i);
                                arrayList.add(new SanPhamShop(
                                        object.getInt("idloaisanpham"),
                                        object.getString("tenloai"),
                                        object.getString("hinhanhloaisp"),
                                        object.getInt("soluong")
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
                        Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map = new HashMap<>();
                map.put("idcuahang", String.valueOf(ShopActivity.idcuahang));
                return map;
            }
        };
        requestQueue.add(stringRequest);

    }

}
