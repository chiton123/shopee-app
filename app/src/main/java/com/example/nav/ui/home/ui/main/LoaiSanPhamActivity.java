package com.example.nav.ui.home.ui.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.example.nav.ui.Model.SanPham;
import com.example.nav.ui.Adapter.SanPhamAdapter;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import co.ceryle.radiorealbutton.library.RadioRealButton;
import co.ceryle.radiorealbutton.library.RadioRealButtonGroup;

import static android.graphics.Color.YELLOW;

public class LoaiSanPhamActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    SearchView searchView;
    RecyclerView recyclerView;
    SanPhamAdapter adapter;
    ArrayList<SanPham> arrayList;
    ArrayList<SanPham> arrayListplace;
    String urlhienthi = MainActivity.urlkindofproduct;
    String urlStoreLocation = MainActivity.urlfilterstore;
    int idcuahang = 0;
    int dem = 0;
    int a = 0;
    private Menu menu;
    EditText editpricemin, editpricemax;
    Button btnprice100k, btnprice200k, btnpriceover200k, btnreset, btnapply;
    RadioRealButtonGroup realButtonGroup;
    RadioRealButton btnmoinhat, btnbanchay, btngia;
    Button btncantho, btntphcm, btnvinhlong, btnhanoi;
    int idloaisp = 0;
    int x = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loai_san_pham);
        anhxa();
        actionBar();
        EventDefaultButtonColor();
        getData(0, ++a);
        EventFilter();
        EventRealButton();

    }
    private void EventDefaultButtonColor() {
        btnvinhlong.setBackgroundResource(android.R.drawable.btn_default);
        btncantho.setBackgroundResource(android.R.drawable.btn_default);
        btnhanoi.setBackgroundResource(android.R.drawable.btn_default);
        btntphcm.setBackgroundResource(android.R.drawable.btn_default);
        btnprice100k.setBackgroundResource(android.R.drawable.btn_default);
        btnpriceover200k.setBackgroundResource(android.R.drawable.btn_default);
        btnprice200k.setBackgroundResource(android.R.drawable.btn_default);
        editpricemin.setText("");
        editpricemax.setText("");

    }
    private void ButtonApply(){
        btnapply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editpricemin.getText().toString().length() != 0 && editpricemax.getText().toString().length() != 0){
                    Integer x = Integer.parseInt(editpricemin.getText().toString());
                    Integer y = Integer.parseInt(editpricemax.getText().toString());
                    if(String.valueOf(x).length() != 0 || String.valueOf(y).length() != 0){
                        for(int i=0; i < arrayList.size(); i++){
                            if(arrayList.get(i).getGiasanpham() > y || arrayList.get(i).getGiasanpham() < x){
                                arrayList.remove(i);
                                i--;
                            }
                        }
                    }
                }
                if(x > 0){
                    ChechTheSame();
                }

                adapter.notifyDataSetChanged();
                EventDefaultButtonColor();
                if(drawerLayout.isDrawerOpen(GravityCompat.END)){
                    drawerLayout.closeDrawer(GravityCompat.END);
                }

            }
        });
    }
    private void EventRealButton() {
        realButtonGroup.setOnClickedButtonPosition(new RadioRealButtonGroup.OnClickedButtonPosition() {
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
    public void RecoveryData(){
        arrayList.clear();
        getData(0, ++a);
    }
    private void EventFilter() {
        arrayListplace = new ArrayList<>();
        btnprice100k.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i=0; i < arrayList.size(); i++){
                    if(arrayList.get(i).getGiasanpham() > 100000){
                        arrayList.remove(i);
                        i--;
                    }
                }
                adapter.notifyDataSetChanged();
                btnprice100k.setBackgroundColor(YELLOW);
                btnpriceover200k.setBackgroundResource(android.R.drawable.btn_default);
                btnprice200k.setBackgroundResource(android.R.drawable.btn_default);
                ButtonApply();
            }
        });
        btnprice200k.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i=0; i < arrayList.size(); i++){
                    if(arrayList.get(i).getGiasanpham() > 300000 || arrayList.get(i).getGiasanpham() < 100000){
                        arrayList.remove(i);
                        i--;
                    }
                }
                btnprice200k.setBackgroundColor(YELLOW);
                btnpriceover200k.setBackgroundResource(android.R.drawable.btn_default);
                btnprice100k.setBackgroundResource(android.R.drawable.btn_default);
                ButtonApply();
            }
        });
        btnpriceover200k.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i=0; i < arrayList.size(); i++){
                    if(arrayList.get(i).getGiasanpham() < 300000){
                        arrayList.remove(i);
                        i--;
                    }
                }
                btnpriceover200k.setBackgroundColor(YELLOW);
                btnprice100k.setBackgroundResource(android.R.drawable.btn_default);
                btnprice200k.setBackgroundResource(android.R.drawable.btn_default);
                ButtonApply();
            }
        });
        btnhanoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrayListplace.clear();
                FilterSalePlace("Hà Nội");
                x++;
                btnhanoi.setBackgroundColor(YELLOW);
                btncantho.setBackgroundResource(android.R.drawable.btn_default);
                btnvinhlong.setBackgroundResource(android.R.drawable.btn_default);
                btntphcm.setBackgroundResource(android.R.drawable.btn_default);
                ButtonApply();

            }
        });
        btntphcm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrayListplace.clear();
                FilterSalePlace("TPHCM");
                x++;
                btntphcm.setBackgroundColor(YELLOW);
                btncantho.setBackgroundResource(android.R.drawable.btn_default);
                btnvinhlong.setBackgroundResource(android.R.drawable.btn_default);
                btnhanoi.setBackgroundResource(android.R.drawable.btn_default);
                ButtonApply();
            }
        });
        btnvinhlong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrayListplace.clear();
                FilterSalePlace("Vĩnh Long");
                x++;
                btnvinhlong.setBackgroundColor(YELLOW);
                btncantho.setBackgroundResource(android.R.drawable.btn_default);
                btnhanoi.setBackgroundResource(android.R.drawable.btn_default);
                btntphcm.setBackgroundResource(android.R.drawable.btn_default);
                ButtonApply();
            }
        });
        btncantho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrayListplace.clear();
                FilterSalePlace("Cần Thơ");
                x++;
                btncantho.setBackgroundColor(YELLOW);
                btnhanoi.setBackgroundResource(android.R.drawable.btn_default);
                btnvinhlong.setBackgroundResource(android.R.drawable.btn_default);
                btntphcm.setBackgroundResource(android.R.drawable.btn_default);
                ButtonApply();
            }
        });
        btnreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventDefaultButtonColor();
                getData(0, ++a);
            }
        });

    }
    public void ChechTheSame(){
        for(int i=0; i < arrayList.size(); i++){
            int check = 0;
            for(int j=0; j < arrayListplace.size() ; j++){
                if(arrayList.get(i).getIdsanpham() == arrayListplace.get(j).getIdsanpham()){
                    check++;
                }
            }
            if(check == 0){
                arrayList.remove(i);
                i--;
                adapter.notifyDataSetChanged();
            }

        }
    }
    public void FilterSalePlace(final String noiban){
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlStoreLocation,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for(int i=0; i < jsonArray.length(); i++){
                                JSONObject object = jsonArray.getJSONObject(i);
                                arrayListplace.add(new SanPham(
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
                              //  adapter.notifyDataSetChanged();
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
                map.put("idloaisp", String.valueOf(idloaisp));
                map.put("noiban", noiban);
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_search, menu);
        MenuItem searchItem = menu.findItem(R.id.searchhienthithongtin);
        SearchManager searchManager = (SearchManager) this.getSystemService(Context.SEARCH_SERVICE);

        searchView = null;
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(this.getComponentName()));
        }

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.filterhienthi){
            if(dem > 0){
                arrayList.clear();
                getData(0, ++a);
            }
            drawerLayout.openDrawer(GravityCompat.END);
            dem++;
        }
        return super.onOptionsItemSelected(item);
    }
    private void getData(final int x, final int tanggiam) {
        idloaisp = getIntent().getIntExtra("idloaisanpham", 0);
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlhienthi,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for(int i =0; i < jsonArray.length(); i++){
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
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
//                map.put("idcuahang", String.valueOf(idcuahang) );
                map.put("x", String.valueOf(x));
                map.put("tanggiam", String.valueOf(tanggiam));
                map.put("idloaisanpham", String.valueOf(idloaisp));
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }
    private void actionBar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        String tenloai = getIntent().getStringExtra("tenloai");
        actionBar.setTitle(tenloai);
        actionBar.setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void anhxa() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerlayoutloaisanpham);
        navigationView = (NavigationView) findViewById(R.id.navigationloaisanpham);
        toolbar = (Toolbar) findViewById(R.id.toolbarloaisanpham);
        recyclerView = (RecyclerView) findViewById(R.id.recycleviewloaisanpham);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        arrayList = new ArrayList<>();
        adapter = new SanPhamAdapter(getApplicationContext(), arrayList);
        recyclerView.setAdapter(adapter);
        editpricemax = (EditText) findViewById(R.id.edittextpricemaxloaisanpham);
        editpricemin = (EditText) findViewById(R.id.edittextpriceminloaisanpham);
        btnprice100k = (Button) findViewById(R.id.buttonnhonhatloaisanpham);
        btnprice200k = (Button) findViewById(R.id.buttonnvualoaisanpham);
        btnpriceover200k = (Button) findViewById(R.id.buttonlonnhatloaisanpham);
        btnreset = (Button) findViewById(R.id.buttonresetloaisanpham);
        btnapply = (Button) findViewById(R.id.buttonappdungloaisanpham);
        realButtonGroup = (RadioRealButtonGroup)findViewById(R.id.buttongrouploaisanpham);
        btnmoinhat = (RadioRealButton) findViewById(R.id.radiobuttonmoinhatloaisanpham);
        btnbanchay = (RadioRealButton) findViewById(R.id.radiobuttonbanchayloaisanpham);
        btngia = (RadioRealButton) findViewById(R.id.radiobuttonsapxeptheogialoaisanpham);
        btncantho = (Button) findViewById(R.id.buttonCanTho);
        btnvinhlong = (Button) findViewById(R.id.buttonVinhLong);
        btntphcm = (Button) findViewById(R.id.buttonTPhcm);
        btnhanoi = (Button) findViewById(R.id.buttonHanoi);
    }
}
