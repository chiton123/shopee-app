package com.example.nav.ui.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import co.ceryle.radiorealbutton.library.RadioRealButton;
import co.ceryle.radiorealbutton.library.RadioRealButtonGroup;

public class FragmentShopSanPham extends Fragment {
    RecyclerView recyclerView;
    SanPhamAdapter adapter;
    ArrayList<SanPham> arrayList;
//    String urlsanpham = "http://192.168.137.83:8888/server/getsanphamcuashop.php";
    String urlsanpham = MainActivity.urlsanpham;
    int idcuahang = 0;
    RadioRealButton btnmoinhat, btnbanchay, btnsapxep;
    RadioRealButtonGroup btngroup;
    int a = 0;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sanpham, container, false);
        btnbanchay = (RadioRealButton) view.findViewById(R.id.radiobuttonbanchay);
        btnmoinhat = (RadioRealButton) view.findViewById(R.id.radiobuttonmoinhat);
        btnsapxep = (RadioRealButton) view.findViewById(R.id.radiobuttonsapxeptheogia);
        btngroup = (RadioRealButtonGroup) view.findViewById(R.id.buttongroup);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycleviewSanPhamBanChayShop);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        arrayList = new ArrayList<>();
        adapter = new SanPhamAdapter(getActivity(), arrayList);
        recyclerView.setAdapter(adapter);
        getData(0, ++a);
        checkradiobutton();


        return view;
    }

    private void checkradiobutton() {
        btngroup.setOnClickedButtonPosition(new RadioRealButtonGroup.OnClickedButtonPosition() {
            @Override
            public void onClickedButtonPosition(int position) {
                arrayList.clear();
                adapter.notifyDataSetChanged();
                if(position == 2){
                    getData(position, ++a);
                }else {
                    getData(position, 0);
                }
            }
        });


    }


    private void getData(final int x, final int tanggiam) {
        Intent intent = getActivity().getIntent();
        idcuahang = intent.getIntExtra("idcuahang", 0);
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlsanpham,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for(int i=0; i < jsonArray.length(); i++){
                                JSONObject object = jsonArray.getJSONObject(i);
                                arrayList.add(new SanPham(
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
                        Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("idcuahang", String.valueOf(idcuahang));
                map.put("x", String.valueOf(x));
                map.put("tanggiam", String.valueOf(tanggiam));
                return map;
            }
        };
        requestQueue.add(stringRequest);

    }
}
