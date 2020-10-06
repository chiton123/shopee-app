package com.example.nav.ui.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.nav.MainActivity;
import com.example.nav.R;
import com.example.nav.ui.Adapter.LoaiSanPhamAdapter;
import com.example.nav.ui.Adapter.SanPhamAdapter;
import com.example.nav.ui.Model.LoaiSanPham;
import com.example.nav.ui.Model.SanPham;
import com.example.nav.ui.home.DangNhapActivity;
import com.example.nav.ui.home.GioHangActivity;
import com.example.nav.ui.home.HomeViewModel;
import com.example.nav.ui.home.ui.main.ChatActivity;
import com.example.nav.ui.home.ui.main.LoaiSanPhamActivity;
import com.example.nav.ui.home.ui.main.MessageActivity;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    public static int kiemtradangnhap = 0;
    int id = 0;
    String tenloai = "";
    String hinhanh = "";
    TextView txt_soluong;
    int cartnumber = 0;
    int soluongdaban = 0 ;
    int soluong;
    int phantramkhuyenmai = 0;
    LoaiSanPhamAdapter adapter;
    ArrayList<LoaiSanPham> loaiSanPhamArrayList;
    List<SanPham> sanPhamArrayList;
//    String urlLoaiSP = "http://192.168.137.83:8888/server/getloaiSendo.php";
//    String urlSanPhamMoiNhat = "http://192.168.137.83:8888/server/getsanphamSendo.php";
    String urlLoaiSP = MainActivity.urlloaisanpham;
    String urlSanPhamMoiNhat = MainActivity.urlsanphammoinhat;
    SanPhamAdapter sanPhamAdapter;
    RecyclerView recyclerView;
    Toolbar toolbar;
    public static GridView gridView;
    SearchView searchView;
    Toolbar toolbarMain;
    ViewFlipper viewFlipper;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_home, container, false);
        setHasOptionsMenu(true);
        gridView = (GridView) root.findViewById(R.id.gridviewhome);
        recyclerView = (RecyclerView) root.findViewById(R.id.recycleview);
        recyclerView.setHasFixedSize(true);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        searchView = (SearchView) root.findViewById(R.id.searchview);
        setHasOptionsMenu(true);
        toolbarMain = (Toolbar) root.findViewById(R.id.toolbarMain);
        toolbarMain.inflateMenu(R.menu.menu_giohang);
        viewFlipper = (ViewFlipper) root.findViewById(R.id.viewfliper);
        MainActivity activity = (MainActivity) getActivity();
        activity.setSupportActionBar(toolbarMain);
        anhxa();
        setHasOptionsMenu(true);
        getDataLoaisp();
        getSanPhamMoiNhat();
        test();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                sanPhamAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                sanPhamAdapter.getFilter().filter(newText);
                return false;
            }
        });
        ArrayList<String> mangQuangCao = new ArrayList<>();
        mangQuangCao.add("https://taodoituong.com/wp-content/uploads/2018/07/facebook-ads-13-720x375.jpg");
        mangQuangCao.add("https://i.ytimg.com/vi/PAuMs3WCd68/maxresdefault.jpg");
        mangQuangCao.add("https://www.chotot.com/kinhnghiem/wp-content/uploads/2018/05/dien-thoai-samsung-cho-tot.jpg");
        mangQuangCao.add("https://ggstorage.oxii.vn/images/oxii-2019-5-6/728x436/4-mau-dien-thoai-tam-trung-cua-samsung-dang-mua-nhat-hien-naythumb.jpg");
        for(int i=0; i < mangQuangCao.size(); i++){
            ImageView imageView = new ImageView(getActivity());
            Picasso.get().load(mangQuangCao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(5000);
        viewFlipper.setAutoStart(true);
        Animation animation_in = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_in_right);
        Animation animation_out = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_out_right);
        viewFlipper.setInAnimation(animation_in);
        viewFlipper.setOutAnimation(animation_out);

        return root;
    }



    private void test() {
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), LoaiSanPhamActivity.class);
                intent.putExtra("idloaisanpham", loaiSanPhamArrayList.get(position).getIdloaisanpham());
                intent.putExtra("tenloai", loaiSanPhamArrayList.get(position).getTenloaisanpham());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }

    private void getSanPhamMoiNhat() {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, urlSanPhamMoiNhat, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                       // Toast.makeText(getActivity(), response.toString(), Toast.LENGTH_SHORT).show();
                        int idsp = 0;
                        String tensp = "";
                        String hinhanhsp = "";
                        int gia = 0;
                        int idcuahang = 0;
                        int idloaisp = 0;
                        if(response != null){
                            for(int i=0; i < response.length(); i++){
                                try {
                                    JSONObject object = response.getJSONObject(i);
                                    sanPhamArrayList.add(new SanPham(
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
                                    sanPhamAdapter.notifyDataSetChanged();

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(jsonArrayRequest);

    }
    private void anhxa() {
        loaiSanPhamArrayList = new ArrayList<>();
        adapter = new LoaiSanPhamAdapter(getActivity(), loaiSanPhamArrayList);
        gridView.setAdapter(adapter);
        sanPhamArrayList = new ArrayList<>();
        sanPhamAdapter = new SanPhamAdapter(recyclerView,getActivity(), sanPhamArrayList);
        recyclerView.setAdapter(sanPhamAdapter);
    }


    public void getDataLoaisp(){
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, urlLoaiSP, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if(response != null){

                            //  Toast.makeText(MainActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                            for(int i=0 ; i<response.length(); i++){
                                try {
                                    JSONObject object = response.getJSONObject(i);
                                    id = object.getInt("id");
                                    tenloai = object.getString("tenloaisanpham");
                                    hinhanh = object.getString("hinhanhloaisanpham");
                                    //Toast.makeText(MainActivity.this, tenloai, Toast.LENGTH_SHORT).show();
                                    loaiSanPhamArrayList.add(new LoaiSanPham(
                                            id, tenloai, hinhanh
                                    ));

                                    adapter.notifyDataSetChanged();

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
                        Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });

        requestQueue.add(jsonArrayRequest);

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
        getActivity().getMenuInflater().inflate(R.menu.menu_giohang, menu);
        final MenuItem menuItem = menu.findItem(R.id.menugiohang);
        View actionview = (View)menu.findItem(R.id.menugiohang).getActionView();
        txt_soluong = (TextView) actionview.findViewById(R.id.cart_badge);
        setUpBadge();
        actionview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(menuItem);
            }
        });
        super.onCreateOptionsMenu(menu, inflater);

    }

    private void setUpBadge() {
        cartnumber = MainActivity.manggiohang.size();
        if(cartnumber == 0){
            txt_soluong.setVisibility(View.GONE);
        }else {
            txt_soluong.setText(cartnumber+"");
            txt_soluong.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menugiohang:
                if(MainActivity.kiemtralogin == 0){
                    Intent intent = new Intent(getActivity(), DangNhapActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
                else {
                    Intent intent = new Intent(getActivity(), GioHangActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
                break;
            case R.id.chat:
                if(MainActivity.kiemtralogin == 0){
                    Intent intent = new Intent(getActivity(), DangNhapActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
                else {
                    Intent intent = new Intent(getActivity(), MessageActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
        }


        return super.onOptionsItemSelected(item);
    }
}
