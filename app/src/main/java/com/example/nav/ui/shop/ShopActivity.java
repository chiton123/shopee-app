package com.example.nav.ui.shop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
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
import com.example.nav.ui.Adapter.ViewPageAdapter;
import com.example.nav.ui.Fragment.FragmentShop;
import com.example.nav.ui.Fragment.FragmentShopDanhMuc;
import com.example.nav.ui.Fragment.FragmentShopSanPham;
import com.example.nav.ui.Sign_in.DangNhapActivity;
import com.example.nav.ui.Chat.ChatActivity;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ShopActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPageAdapter adapter;
    ViewPager viewPager;
    Toolbar toolbar;
    TextView txttenshop;
    public static int idcuahang = 0;
    public static int idsanpham = 0;
    public static int idowner = 0;
    public static String owner_name = "";
    public static float sosaotb = 0;
    SearchView searchView;
    TextView txtsoluongsp, txtdanhgia, txtphanhoichat, txtsonguoitheodoi;
    ImageView imageView;
    String url = MainActivity.urlshop;
    Button btntheodoi, btnchat;
    String urltheodoi = MainActivity.urltheodoi;
    String urlnumberfollow = MainActivity.urlnumberfollow;
    String urlstopfollow = MainActivity.urlstopfollow;
    int numberfollow = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        ActionBar();
        anhxa();
        EventTenShop();
        getThongtinshop();
        CheckFollow();
        setUpTabLayout();
        tabLayout.setupWithViewPager(viewPager);

        EventButton();

        SetNumberFollower();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_shop, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.searchviewmenu:
                Intent intent = new Intent(getApplicationContext(), HienThiTimKiemActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("idcuahang", idcuahang);
                startActivity(intent);
                break;
            case R.id.filtermenu:
                Intent intent1 = new Intent(getApplicationContext(), HienThiTimKiemActivity.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent1.putExtra("idcuahang", idcuahang);
                startActivity(intent1);
                break;
            case R.id.more:
                Toast.makeText(getApplicationContext(), "more", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void SetNumberFollower() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlnumberfollow,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        numberfollow = Integer.parseInt(response.toString());
                        txtsonguoitheodoi.setText(numberfollow+" người theo dõi");
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

    private void CheckFollow() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urltheodoi,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("datheodoi")){
                            btntheodoi.setText("Đã theo dõi");
                            Toast.makeText(getApplicationContext(), "da theo doi", Toast.LENGTH_SHORT).show();
//                            btntheodoi.setEnabled(false);
//                            btntheodoi.setClickable(false);
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
                map.put("iduser", String.valueOf(MainActivity.iduser));
                map.put("idcuahang", String.valueOf(idcuahang));
                return map;
            }
        };

        requestQueue.add(stringRequest);
    }

    private void EventButton() {
        btntheodoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MainActivity.kiemtralogin == 1){
                    if(btntheodoi.getText().equals("Đã theo dõi")){
                        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlstopfollow,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        if(response.equals("success")){
                                            btntheodoi.setText("Theo dõi");
                                            SetNumberFollower();
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
                                map.put("iduser", String.valueOf(MainActivity.iduser));
                                map.put("idcuahang", String.valueOf(idcuahang));
                                return map;
                            }
                        };
                        requestQueue.add(stringRequest);
                    }else {

                        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, urltheodoi,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        Toast.makeText(getApplicationContext(), "Đã theo dõi", Toast.LENGTH_SHORT).show();
                                        btntheodoi.setText("Đã theo dõi");
                                        SetNumberFollower();

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
                                map.put("iduser", String.valueOf(MainActivity.iduser));
                                map.put("idcuahang", String.valueOf(idcuahang));
                                return map;
                            }
                        };
                        requestQueue.add(stringRequest);

                    }


                }else {
                    Intent intent = new Intent(ShopActivity.this, DangNhapActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }

            }
        });
        btnchat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MainActivity.kiemtralogin == 0){
                    Intent intent = new Intent(ShopActivity.this, DangNhapActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(ShopActivity.this, ChatActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("iduser", idowner);
                    intent.putExtra("ownername", owner_name);
                    startActivity(intent);
                }

            }
        });

    }

    private void getThongtinshop() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for(int i=0; i < jsonArray.length(); i++){
                                JSONObject object = jsonArray.getJSONObject(i);
                                txttenshop.setText(object.getString("tencuahang"));
//                                txtsoluongsp.setText(object.getInt("soluongsanpham") + "\n sản phẩm");
//                                txtdanhgia.setText(object.getInt("danhgiatb") +"/5" +"\nđánh giá");
                                sosaotb = object.getInt("danhgiatb");
                                FragmentPagerAdapter fragmentPagerAdapter = (FragmentPagerAdapter) viewPager.getAdapter();
                                FragmentShop fragmentShop = (FragmentShop) fragmentPagerAdapter.getItem(0);
                                fragmentShop.getNumberStar(sosaotb);
                                fragmentShop.evaluationEvent();
                                fragmentShop.getThongtinshop();

                           //     txtphanhoichat.setText("98%" + "\n phản hồi chat");
                                idowner = object.getInt("iduser");
                                Picasso.get().load(object.getString("hinhanhcuahang")).into(imageView);
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
                Map<String,String> map = new HashMap<>();
                map.put("idcuahang", String.valueOf(idcuahang));
                return map;
            }
        };
        requestQueue.add(stringRequest);

    }

    private void EventTenShop() {
        Intent intent = getIntent();
        txttenshop.setText(intent.getStringExtra("tenshop"));
        idcuahang = intent.getIntExtra("idcuahang", 0);
        idsanpham = intent.getIntExtra("idsanpham", 0);
    }

    private void anhxa() {
        txtsonguoitheodoi = (TextView) findViewById(R.id.textviewsonguoitheodoishop);
        btntheodoi = (Button) findViewById(R.id.buttonTheodoishop);
        btnchat = (Button) findViewById(R.id.buttonchatshop);
        imageView = (ImageView) findViewById(R.id.imageviewanhshopcuahang);
        viewPager = (ViewPager) findViewById(R.id.viewpagerShop);
        tabLayout = (TabLayout) findViewById(R.id.tablayoutShop);
        txttenshop = (TextView) findViewById(R.id.textviewtenshop1);
    }

    private void setUpTabLayout() {
        adapter = new ViewPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new FragmentShop(), "SHOP");
        adapter.addFragment(new FragmentShopSanPham(), "SẢN PHẨM");
        adapter.addFragment(new FragmentShopDanhMuc(), "DANH MỤC");
        viewPager.setAdapter(adapter);
    }

    private void ActionBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbarshop);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("SHOP");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
