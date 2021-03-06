package com.example.nav.ui.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.example.nav.ui.Adapter.DaHuyAdapter;
import com.example.nav.ui.Model.GioHangChoLayHang;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FragmentDaHuy extends Fragment {
    ListView listView;
    ArrayList<GioHangChoLayHang> arrayList;
    DaHuyAdapter adapter;
    String urldahuy = MainActivity.urldahuy;
    Button btnmualai;
    String urlmualai = MainActivity.urlmualai;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dahuy, container, false);
        listView = (ListView) view.findViewById(R.id.listviewdahuynguoimua);
        arrayList = new ArrayList<>();
        adapter = new DaHuyAdapter(getActivity(), arrayList);
        listView.setAdapter(adapter);
        btnmualai = (Button) view.findViewById(R.id.buttonmualai);
        getData();
        return view;
    }


    private void getData() {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urldahuy,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
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
                        Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }){
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
