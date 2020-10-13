package com.example.nav.ui.Sale_purchase;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
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
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
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
import com.example.nav.ui.Interface.ILoadMore;
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
    // tat cac cac gia tri full pages
    ArrayList<SanPham> arrayListTongHop;
    ArrayList<SanPham> arraylistPrice;
    ArrayList<SanPham> arrayListplace;
    ArrayList<SanPham> arrayListShip;
    String urlhienthi = MainActivity.urlkindofproduct;
    String urlStoreLocation = MainActivity.urlfilterstore;
    String urlship = MainActivity.urlfiltership;
    String urlallkindproduct = MainActivity.urlkindallproduct;
    int idcuahang = 0;
    int dem = 0;
    int tang = 0, giam = 1;
    // so luong san pham trong loai do
    int max = 0;
    private Menu menu;
    EditText editpricemin, editpricemax;
    Button btnprice100k, btnprice1_300k, btnpriceover300k, btnreset, btnapply;
    Button btnviettelpost, btngiaohangnhanh, btngiaotietkiem, btnjtexpress;
    RadioRealButtonGroup realButtonGroup;
    RadioRealButton btnmoinhat, btnbanchay, btngiatang, btngiagiam;
    Button btncantho, btntphcm, btnvinhlong, btnhanoi;
    int idloaisp = 0;
    int page = 1;
    // postion cua realbutton
    int pos = 0;
    // loc theo gia
    int filter_gia = 0;
    // loc theo noi ban
    int filter_noiban = 0;
    // loc theo don vi van chuyen
    int filter_vanchuyen = 0;
    // lan dau tien
    int first = 0;
    // check co đủ minprice, maxprice hay l
    int pricecheck = 0;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loai_san_pham);
        anhxa();
        actionBar();
        EventDefaultButtonColor();


        if(first == 0){
            getData(0, 1);
            loadMore();
            first = 1;
        }
        getDataAll();
        loadMore();
        EventRealButton();
        eventFilterPrice();
        eventFilterPlace();
        eventFilterShip();
        ButtonApply();
        buttonReset();
        editminmaxEvent();


    }

    private void editminmaxEvent() {
        editpricemax.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filter_gia = 0;
                btnprice1_300k.setSelected(false);
                btnprice100k.setSelected(false);
                btnpriceover300k.setSelected(false);
            }
        });
        editpricemin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filter_gia = 0;
                btnprice1_300k.setSelected(false);
                btnprice100k.setSelected(false);
                btnpriceover300k.setSelected(false);
            }
        });

    }

    private void buttonReset() {
        btnreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventDefaultButtonColor();
            }
        });

    }

    private void eventFilterShip() {
        btnviettelpost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnviettelpost.isSelected()){
                    filter_vanchuyen = 0;
                    btnviettelpost.setSelected(false);
                    arrayListShip.clear();
                }else {
                    filter_vanchuyen = 1;
                    filterShip(1);
                    btnviettelpost.setSelected(true);
                    btngiaohangnhanh.setSelected(false);
                    btngiaotietkiem.setSelected(false);
                    btnjtexpress.setSelected(false);
                }
            }
        });
        btnjtexpress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnjtexpress.isSelected()){
                    filter_vanchuyen = 0;
                    btnjtexpress.setSelected(false);
                    arrayListShip.clear();
                }else {
                    filter_vanchuyen = 1;
                    filterShip(4);
                    btnviettelpost.setSelected(false);
                    btngiaohangnhanh.setSelected(false);
                    btngiaotietkiem.setSelected(false);
                    btnjtexpress.setSelected(true);
                }
            }
        });
        btngiaotietkiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btngiaotietkiem.isSelected()){
                    filter_vanchuyen = 0;
                    btngiaotietkiem.setSelected(false);
                    arrayListShip.clear();
                }else {
                    filter_vanchuyen = 1;
                    filterShip(3);
                    btnviettelpost.setSelected(false);
                    btngiaohangnhanh.setSelected(false);
                    btngiaotietkiem.setSelected(true);
                    btnjtexpress.setSelected(false);
                }
            }
        });
        btngiaohangnhanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnviettelpost.isSelected()){
                    filter_vanchuyen = 0;
                    btngiaohangnhanh.setSelected(false);
                    arrayListShip.clear();
                }else {
                    filter_vanchuyen = 1;
                    filterShip(2);
                    btnviettelpost.setSelected(false);
                    btngiaohangnhanh.setSelected(true);
                    btngiaotietkiem.setSelected(false);
                    btnjtexpress.setSelected(false);
                }
            }
        });
    }

    private void eventFilterPlace() {
        btnhanoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnhanoi.isSelected()){
                    filter_noiban = 0;
                    btnhanoi.setSelected(false);
                    arrayListplace.clear();
                }else{
                    filter_noiban = 1;
                    FilterSalePlace("Hà Nội");
                    btnhanoi.setSelected(true);
                    btnvinhlong.setSelected(false);
                    btncantho.setSelected(false);
                    btntphcm.setSelected(false);
                }

            }
        });
        btntphcm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btntphcm.isSelected()){
                    filter_noiban = 0;
                    btntphcm.setSelected(false);
                    arrayListplace.clear();
                }else{
                    filter_noiban = 1;
                    FilterSalePlace("TPHCM");
                    btnhanoi.setSelected(false);
                    btnvinhlong.setSelected(false);
                    btncantho.setSelected(false);
                    btntphcm.setSelected(true);
                }
            }
        });
        btnvinhlong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(btnvinhlong.isSelected()){
                    filter_noiban = 0;
                    btnvinhlong.setSelected(false);
                    arrayListplace.clear();
                }else{
                    filter_noiban = 1;
                    FilterSalePlace("Vĩnh Long");
                    btnhanoi.setSelected(false);
                    btnvinhlong.setSelected(true);
                    btncantho.setSelected(false);
                    btntphcm.setSelected(false);
                }

            }
        });
        btncantho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(btncantho.isSelected()){
                    filter_noiban = 0;
                    btncantho.setSelected(false);
                    arrayListplace.clear();
                }else{
                    filter_noiban = 1;
                    FilterSalePlace("Cần Thơ");
                    btnhanoi.setSelected(false);
                    btnvinhlong.setSelected(false);
                    btncantho.setSelected(true);
                    btntphcm.setSelected(false);
                }

            }
        });

    }

    private void eventFilterPrice() {
        // kiem tra edittext min va max
        btnprice100k.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editpricemax.setText("");
                editpricemin.setText("");
                if(btnprice100k.isSelected()){
                    btnprice100k.setSelected(false);
                    filter_gia = 0;
                }else {
                    btnprice100k.setSelected(true);
                    btnpriceover300k.setSelected(false);
                    btnprice1_300k.setSelected(false);
                    filter_gia = 1;
                    filterPrice(0, 100000);
                }

            }
        });
        btnprice1_300k.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editpricemax.setText("");
                editpricemin.setText("");
                if(btnprice1_300k.isSelected()){
                    btnprice1_300k.setSelected(false);
                    filter_gia = 0;
                }else {
                    btnprice1_300k.setSelected(true);
                    btnprice100k.setSelected(false);
                    btnpriceover300k.setSelected(false);
                    filter_gia = 1;
                    filterPrice(100000, 300000);
                }

            }
        });
        btnpriceover300k.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editpricemax.setText("");
                editpricemin.setText("");
                if(btnpriceover300k.isSelected()){
                    btnpriceover300k.setSelected(false);
                    filter_gia = 0;
                }else {
                    btnpriceover300k.setSelected(true);
                    btnprice100k.setSelected(false);
                    btnprice1_300k.setSelected(false);
                    filter_gia = 1;
                    filterPrice(300000, 10000000);
                }

            }
        });
    }

    private void filterPrice(int min, int max) {
        arraylistPrice.clear();
        if(!editpricemin.getText().toString().equals("") && !editpricemax.getText().toString().equals("")){
            int x = Integer.parseInt(editpricemin.getText().toString());
            int y = Integer.parseInt(editpricemax.getText().toString());
            for(int i=0; i < arrayList.size(); i++){
                if(arrayList.get(i).getGiasanpham() >= x && arrayList.get(i).getGiasanpham() <=y){
                    arraylistPrice.add(arrayList.get(i));
                }
            }
            filter_gia = 1;
        }else if(!editpricemin.getText().toString().equals("") && editpricemax.getText().toString().equals("")){
            int x = Integer.parseInt(editpricemin.getText().toString());
            for(int i=0; i < arrayList.size(); i++){
                if(arrayList.get(i).getGiasanpham() >= x ){
                    arraylistPrice.add(arrayList.get(i));
                }
            }
            filter_gia = 1;
        }else if(editpricemin.getText().toString().equals("") && !editpricemax.getText().toString().equals("")){
            int y = Integer.parseInt(editpricemax.getText().toString());
            for(int i=0; i < arrayList.size(); i++){
                if(arrayList.get(i).getGiasanpham() <=y){
                    arraylistPrice.add(arrayList.get(i));
                }
            }
            filter_gia = 1;
        }


    }
    private void filterShip(final int xyz){
        arrayListShip.clear();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlship,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response != null){
                           // Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();
                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                for(int i=0; i < jsonArray.length(); i++){
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    arrayListShip.add(new SanPham(
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

                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
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
                map.put("idship", String.valueOf(xyz));
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void FilterSalePlace(final String noiban){
        arrayListplace.clear();
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

    private void ButtonApply(){
        btnapply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(filter_gia == 0){
                    filterPrice(-1, -1);
                }
                ChechTheSame();
                adapter.notifyDataSetChanged();

                EventDefaultButtonColor();
                if(drawerLayout.isDrawerOpen(GravityCompat.END)){
                    drawerLayout.closeDrawer(GravityCompat.END);
                }
       //         stopLoad();




            }
        });
    }

    public void ChechTheSame(){
        if(filter_noiban > 0){
            if(arrayListplace.size() == 0){
                arrayList.clear();
            }
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
        if(filter_vanchuyen > 0){
            if(arrayListShip.size() == 0){
                arrayList.clear();
            }
            for(int i=0; i < arrayList.size(); i++){
                int check = 0;
                for(int j=0; j < arrayListShip.size() ; j++){
                    if(arrayList.get(i).getIdsanpham() == arrayListShip.get(j).getIdsanpham()){
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
        if(filter_gia > 0){
            if(arraylistPrice.size() == 0){
                arrayList.clear();
            }
            for(int i=0; i < arrayList.size(); i++){
                int check = 0;
                for(int j=0; j < arraylistPrice.size() ; j++){
                    if(arrayList.get(i).getIdsanpham() == arraylistPrice.get(j).getIdsanpham()){
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

    }
    private void EventRealButton() {
        realButtonGroup.setOnClickedButtonPosition(new RadioRealButtonGroup.OnClickedButtonPosition() {
            @Override
            public void onClickedButtonPosition(int position) {
              //  Toast.makeText(getApplicationContext(), position + "", Toast.LENGTH_SHORT).show();
                page = 1;
                pos = position;
                arrayList.clear();
                adapter.notifyDataSetChanged();
                getData(pos, page);
                loadMore();
            }

        });
    }

    private void stopLoad(){
        adapter.setLoadmore(new ILoadMore() {
            @Override
            public void onLoadMore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        adapter.setIsloaded(true);
                    }
                }, 1000);
            }
        });
    }
    private void loadMore() {
        adapter.setLoadmore(new ILoadMore() {
            @Override
            public void onLoadMore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getData(pos, ++page);
                        adapter.notifyDataSetChanged();
                        adapter.setIsloaded(false);
                    }
                },3000);
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.filterhienthi){
            arrayList.clear();
            arrayList.addAll(arrayListTongHop);
            adapter.notifyDataSetChanged();

            drawerLayout.openDrawer(GravityCompat.END);
        }
        return super.onOptionsItemSelected(item);
    }

    private void EventDefaultButtonColor() {
        btnvinhlong.setSelected(false);
        btncantho.setSelected(false);
        btnhanoi.setSelected(false);
        btntphcm.setSelected(false);
        btnpriceover300k.setSelected(false);
        btnprice1_300k.setSelected(false);
        btnprice100k.setSelected(false);
        editpricemin.setText("");
        editpricemax.setText("");
        btnviettelpost.setSelected(false);
        btngiaohangnhanh.setSelected(false);
        btngiaotietkiem.setSelected(false);
        btnjtexpress.setSelected(false);
        filter_gia = 0;
        filter_noiban = 0;
        filter_vanchuyen = 0;
        btnapply.setClickable(true);

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

    private void getDataAll(){
        idloaisp = getIntent().getIntExtra("idloaisanpham", 0);
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlallkindproduct,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for(int i =0; i < jsonArray.length(); i++){
                                JSONObject object = jsonArray.getJSONObject(i);
                                arrayListTongHop.add(new SanPham(
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
                map.put("idloaisanpham", String.valueOf(idloaisp));
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void getData(final int xy, int page) {
        idloaisp = getIntent().getIntExtra("idloaisanpham", 0);
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String url = urlhienthi + String.valueOf(page);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                       // Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();
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
                                max = object.getInt("max");
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
                map.put("x", String.valueOf(xy));
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
                first = 0;
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
        adapter = new SanPhamAdapter(recyclerView, this, arrayList);
        recyclerView.setAdapter(adapter);
        editpricemax = (EditText) findViewById(R.id.edittextpricemaxloaisanpham);
        editpricemin = (EditText) findViewById(R.id.edittextpriceminloaisanpham);
        btnprice100k = (Button) findViewById(R.id.buttonnhonhatloaisanpham);
        btnprice1_300k = (Button) findViewById(R.id.buttonnvualoaisanpham);
        btnpriceover300k = (Button) findViewById(R.id.buttonlonnhatloaisanpham);
        btnreset = (Button) findViewById(R.id.buttonresetloaisanpham);
        btnapply = (Button) findViewById(R.id.buttonappdungloaisanpham);
        realButtonGroup = (RadioRealButtonGroup)findViewById(R.id.buttongrouploaisanpham);
        btnmoinhat = (RadioRealButton) findViewById(R.id.radiobuttonmoinhatloaisanpham);
        btnbanchay = (RadioRealButton) findViewById(R.id.radiobuttonbanchayloaisanpham);
        btngiatang = (RadioRealButton) findViewById(R.id.radiobuttonsapxeptheogialoaisanphamtang);
        btngiagiam = (RadioRealButton) findViewById(R.id.radiobuttonsapxeptheogialoaisanphamgiam);
        btncantho = (Button) findViewById(R.id.buttonCanTho);
        btnvinhlong = (Button) findViewById(R.id.buttonVinhLong);
        btntphcm = (Button) findViewById(R.id.buttonTPhcm);
        btnhanoi = (Button) findViewById(R.id.buttonHanoi);
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        btnviettelpost = (Button) findViewById(R.id.viettelpost);
        btngiaohangnhanh = (Button) findViewById(R.id.giaohangnhanh);
        btngiaotietkiem = (Button) findViewById(R.id.giaohangtietkiem);
        btnjtexpress = (Button) findViewById(R.id.jtexpress);
        arrayListShip = new ArrayList<>();
        arraylistPrice = new ArrayList<>();
        arrayListTongHop = new ArrayList<>();
        arrayListplace = new ArrayList<>();
        
    }
}
