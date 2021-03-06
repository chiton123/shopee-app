package com.example.nav.ui.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.nav.ui.Adapter.ChoLayHangAdapter;
import com.example.nav.ui.Model.GioHangChoLayHang;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FragmentChoLayHangChuShop extends Fragment {
    ListView listView;

    ChoLayHangAdapter adapter;
//    String urlcholayhang = "http://192.168.137.83:8888/server/cholayhangsendo.php";
    String urlcholayhang = MainActivity.urlcholayhang;
    ArrayList<GioHangChoLayHang> arrayList = new ArrayList<>();
    int dem = 0;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cholayhangchushop, container, false);
        listView = (ListView) view.findViewById(R.id.listviewcholayhangchushop);
        adapter = new ChoLayHangAdapter(getActivity(), arrayList);
        listView.setAdapter(adapter);
        getData();

        return view;
    }

    private void getData() {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlcholayhang,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                       // Toast.makeText(getActivity(), response.toString(), Toast.LENGTH_LONG).show();
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for(int i=0; i < jsonArray.length(); i++){
                                JSONObject object = jsonArray.getJSONObject(i);
                                    arrayList.add(new GioHangChoLayHang(
                                            object.getInt("idproduct"),
                                            object.getString("productname"),
                                            object.getInt("price"),
                                            object.getString("image"),
                                            object.getInt("number"),
                                            object.getString("username"),
                                            object.getInt("status"),
                                            object.getInt("iddetail")
                                    ));

                             //   Toast.makeText(getActivity(), object.getString("tensanpham").toString(), Toast.LENGTH_LONG).show();
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
                Map<String,String> map = new HashMap<>();
                map.put("tendangnhap", MainActivity.username);

                return map;
            }
        };
        requestQueue.add(stringRequest);

    }
}
