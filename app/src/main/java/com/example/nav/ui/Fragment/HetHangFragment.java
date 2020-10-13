package com.example.nav.ui.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
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
import com.example.nav.ui.Adapter.SuaSanPhamAdapter;
import com.example.nav.ui.Model.SanPhamSua;
import com.example.nav.ui.ProductAdjustion.SuaSanPhamActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class HetHangFragment extends Fragment {
    ListView listView;
    SuaSanPhamAdapter adapter;

    String urlchangeproduct = MainActivity.urlchangeproduct;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gan_day, container, false);
        listView = (ListView) view.findViewById(R.id.listview);

        adapter = new SuaSanPhamAdapter(getActivity(), SuaSanPhamActivity.arrayList3, getActivity());
        listView.setAdapter(adapter);
        getData();

        return view;
    }
    private void getData() {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlchangeproduct,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for(int i=0; i < jsonArray.length(); i++){
                                JSONObject object = jsonArray.getJSONObject(i);
                                SuaSanPhamActivity.arrayList3.add(new SanPhamSua(
                                        object.getInt("id"),
                                        object.getString("name"),
                                        object.getString("image"),
                                        object.getInt("price"),
                                        object.getInt("salenumber"),
                                        object.getInt("number"),
                                        object.getInt("like")
                                ));

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        adapter.notifyDataSetChanged();

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
                map.put("idstore", String.valueOf(MainActivity.idstore));
                map.put("x", String.valueOf(3));
                return map;
            }
        };
        requestQueue.add(stringRequest);


    }
}
