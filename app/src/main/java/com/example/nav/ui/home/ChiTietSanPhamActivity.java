package com.example.nav.ui.home;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Spinner;
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
import com.example.nav.ui.Adapter.SanPhamAdapter;
import com.example.nav.ui.Model.GioHang;
import com.example.nav.ui.Model.SanPham;
import com.example.nav.ui.Model.DanhGia;
import com.example.nav.ui.Adapter.DanhGiaAdapter;
import com.example.nav.ui.home.ui.main.ChatActivity;
import com.example.nav.ui.home.ui.main.ShopActivity;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChiTietSanPhamActivity extends AppCompatActivity {
    TextView txttenchitiet, txtgiachitiet;
    ImageView imgHinhAnhChiTiet;
    Button  btnMuaNgay;
    Button btnchat;
    Button btnThemVaoGio;
    Toolbar toolbar;
    Spinner spinner;
    int id = 0;
    String tenchitiet = "";
    long giachitiet = 0;
    String hinhanhchitiet = "";
    int soluong = 0;
    ArrayList<DanhGia> arrayList;
    ListView listView;
    DanhGiaAdapter adapter;
    RatingBar ratingBarTB;
    TextView txtaveragestar, txtviewtotalstar;
    float diemtb = 0;
    float sonhanxet = 0;
    TextView txttenshop, txtviewdiachishop;
    ImageView imganhshop;
    Button btnxemshop;
    int idcuahang = 0;
    int idowner = 0;
    String owner_name = "";
    //    String urlShop = "http://192.168.137.83:8888/server/getthongtinshop.php";
////    String urlSanphamcuashop = "http://192.168.137.83:8888/server/getsanphamlienquan.php";
////    String urlDanhgia = "http://192.168.137.83:8888/server/danhgiasendo.php";
    String urlShop = MainActivity.urlshop;
    String urlSanphamcuashop = MainActivity.urlsanphamlienquan;
    String urlDanhgia = MainActivity.urldanhgiasanpham;
    RecyclerView recyclerViewSPLienQuan;
    ArrayList<SanPham> sanphamlienquanarraylist;
    SanPhamAdapter sanPhamAdapter;
    TextView txtsoluongcuashop, txtdanhgiashop, txtphanhoichat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_san_pham);
        anhxa();
        actionBar();
        getThongTin();
        EventButton();
        spinnerEvent();
        getAccessment();
        getThongTinShop();
        getSanPhanLienQuanCuaShop();


    }

    private void getSanPhanLienQuanCuaShop() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlSanphamcuashop,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for(int i=0; i < jsonArray.length(); i++){
                                JSONObject object = jsonArray.getJSONObject(i);
                                sanphamlienquanarraylist.add(new SanPham(
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
                            }
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
                map.put("idcuahang", String.valueOf(idcuahang));
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void getThongTinShop() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlShop,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for(int i=0 ; i < jsonArray.length(); i++){
                                JSONObject object = jsonArray.getJSONObject(i);
                                txttenshop.setText(object.getString("tencuahang"));
                                txtviewdiachishop.setText(object.getString("diachi"));
                                Picasso.get().load(object.getString("hinhanhcuahang")).into(imganhshop);
                                txtsoluongcuashop.setText(object.getInt("soluongsanpham") + "\n  sản phẩm");
                                txtdanhgiashop.setText(object.getInt("danhgiatb") + "/5" + "\n  đánh giá");
                                txtphanhoichat.setText("98% "+"\n Trả lời chat");
                                idowner = object.getInt("iduser");
                                owner_name = object.getString("tenchu");
                            }
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
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("idcuahang", String.valueOf(idcuahang));
                return map;
            }
        };
        requestQueue.add(stringRequest);
        btnxemshop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ShopActivity.class);
                intent.putExtra("tenshop", txttenshop.getText().toString() );
                intent.putExtra("idcuahang", idcuahang);
                intent.putExtra("idsanpham", id);
                startActivity(intent);
            }
        });
    }


    private void getAccessment() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlDanhgia,
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
                                diemtb += (float) object.getDouble("star");
                                sonhanxet += 1;
                                adapter.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if(diemtb == 0){
                            txtviewtotalstar.setText("");
                            txtaveragestar.setText("Chưa có đánh giá");
                            listView.setVisibility(View.GONE);
                        }else {
                            listView.setVisibility(View.VISIBLE);
                            txtaveragestar.setText(diemtb/sonhanxet + "");
                            ratingBarTB.setRating(diemtb/sonhanxet);
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("masanpham", String.valueOf(id));
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void spinnerEvent() {
//        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.spinner_items, R.layout.color_spinner_layout);
//        adapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
//        spinner.setAdapter(adapter);
        Integer[] mang = new Integer[] {1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_dropdown_item, mang);
        spinner.setAdapter(arrayAdapter);


    }

    private void actionBar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Chi tiết sản phẩm");
        actionBar.setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    private void EventButton() {
        btnMuaNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MainActivity.kiemtralogin == 0){
                    Intent intent = new Intent(getApplicationContext(), DangNhapActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }else {

                    if(MainActivity.manggiohang.size() > 0){
                        int sl = Integer.parseInt(spinner.getSelectedItem().toString());
                        boolean exists = false;
                        for(int i=0 ; i < MainActivity.manggiohang.size(); i++){
                            if(MainActivity.manggiohang.get(i).getIdsp() == id) {
                                MainActivity.manggiohang.get(i).setSoluongsp(MainActivity.manggiohang.get(i).getSoluongsp() + sl);
                                if (MainActivity.manggiohang.get(i).getSoluongsp() > 10) {
                                    MainActivity.manggiohang.get(i).setSoluongsp(10);
                                }
                                MainActivity.manggiohang.get(i).setGiasp(MainActivity.manggiohang.get(i).getSoluongsp() * giachitiet);
                                exists = true;
                            }
                        }
                        if(exists == false){
                            int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
                            long giamoi = soluong * giachitiet;
                            MainActivity.manggiohang.add(new GioHang(id, tenchitiet, giamoi, hinhanhchitiet, soluong));
                        }

                    } else {
                        int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
                        long giamoi = soluong * giachitiet;
                        MainActivity.manggiohang.add(new GioHang(id, tenchitiet, giamoi, hinhanhchitiet, soluong));
                    }
                    Intent intent = new Intent(getApplicationContext(), GioHangActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }
        });
        btnThemVaoGio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MainActivity.kiemtralogin == 0){
                    Intent intent = new Intent(getApplicationContext(), DangNhapActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }else {
                    if(MainActivity.manggiohang.size() > 0){
                        int sl = Integer.parseInt(spinner.getSelectedItem().toString());
                        boolean exists = false;
                        for(int i=0 ; i < MainActivity.manggiohang.size(); i++){
                            if(MainActivity.manggiohang.get(i).getIdsp() == id) {
                                MainActivity.manggiohang.get(i).setSoluongsp(MainActivity.manggiohang.get(i).getSoluongsp() + sl);
                                if (MainActivity.manggiohang.get(i).getSoluongsp() > 10) {
                                    MainActivity.manggiohang.get(i).setSoluongsp(10);
                                }
                                MainActivity.manggiohang.get(i).setGiasp(MainActivity.manggiohang.get(i).getSoluongsp() * giachitiet);
                                exists = true;
                            }
                        }
                        if(exists == false){
                            int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
                            long giamoi = soluong * giachitiet;
                            MainActivity.manggiohang.add(new GioHang(id, tenchitiet, giamoi, hinhanhchitiet, soluong));
                        }

                    } else {
                        int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
                        long giamoi = soluong * giachitiet;
                        MainActivity.manggiohang.add(new GioHang(id, tenchitiet, giamoi, hinhanhchitiet, soluong));
                    }
                    Toast.makeText(getApplicationContext(), "Đã thêm", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);


                }
            }
        });
        btnchat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MainActivity.kiemtralogin == 0){
                    Intent intent = new Intent(getApplicationContext(), DangNhapActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(ChiTietSanPhamActivity.this, ChatActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("iduser", idowner);
                    intent.putExtra("ownername", owner_name);
                    startActivity(intent);
                }

            }
        });
    }

    private void getThongTin() {
        Intent intent = getIntent();
        SanPham sp = (SanPham) intent.getSerializableExtra("thongtinsanpham");
        txttenchitiet.setText(sp.getTensanpham());
        txtgiachitiet.setText(sp.getGiasanpham() + "");
        Picasso.get().load(sp.getHinhanhsanpham()).into(imgHinhAnhChiTiet);
        id = sp.getIdsanpham();
        tenchitiet = sp.getTensanpham();
        giachitiet = sp.getGiasanpham();
        hinhanhchitiet = sp.getHinhanhsanpham();
        idcuahang = sp.getIdcuahang();

    }


    private void anhxa() {
        imganhshop = (ImageView) findViewById(R.id.imageviewanhshop);
        txtgiachitiet = (TextView) findViewById(R.id.textviewgiachitiet);
        txttenchitiet = (TextView) findViewById(R.id.textviewtenchitietsp);
        imgHinhAnhChiTiet = (ImageView) findViewById(R.id.imageviewchitiet);
        btnThemVaoGio = (Button) findViewById(R.id.buttonthemvaogiohang);
        btnMuaNgay = (Button) findViewById(R.id.buttonmuangay);
        toolbar = (Toolbar) findViewById(R.id.toolbarchitietsp);
        spinner = (Spinner) findViewById(R.id.spinner);
        arrayList = new ArrayList<>();
        listView =(ListView) findViewById(R.id.listviewdanhgia123);
        adapter = new DanhGiaAdapter(getApplicationContext(), arrayList);
        listView.setAdapter(adapter);
        txtaveragestar = (TextView) findViewById(R.id.textviewaveragestar);
        ratingBarTB = (RatingBar) findViewById(R.id.ratingbarTB1);
        txttenshop = (TextView) findViewById(R.id.textviewtenshop);
        txtviewdiachishop = (TextView) findViewById(R.id.textviewdiachishop);
        btnxemshop = (Button) findViewById(R.id.buttonxemshop);
        txtviewtotalstar = (TextView) findViewById(R.id.textviewstar1);
        recyclerViewSPLienQuan = (RecyclerView) findViewById(R.id.recycleviewsanphamcuashop);
        sanphamlienquanarraylist = new ArrayList<>();
        recyclerViewSPLienQuan.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewSPLienQuan.setLayoutManager(layoutManager);
        sanPhamAdapter = new SanPhamAdapter(getApplicationContext(), sanphamlienquanarraylist);
        recyclerViewSPLienQuan.setItemAnimator(new DefaultItemAnimator());
        recyclerViewSPLienQuan.setAdapter(sanPhamAdapter);
        txtsoluongcuashop = (TextView) findViewById(R.id.textviewsosanphamchitietsp);
        txtdanhgiashop = (TextView) findViewById(R.id.textviewdanhgiachitietsp);
        txtphanhoichat = (TextView) findViewById(R.id.textviewphanhoichitietsp);
        btnchat = (Button) findViewById(R.id.buttonchat);



    }

}
