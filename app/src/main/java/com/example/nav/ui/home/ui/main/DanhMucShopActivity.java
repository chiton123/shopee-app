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

public class DanhMucShopActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    SearchView searchView;
    RecyclerView recyclerView;
    SanPhamAdapter adapter;
    ArrayList<SanPham> arrayList;
    String urlhienthi = MainActivity.urlkindproductshop;
    int idcuahang = 0;
    int dem = 0;
    int a = 0;
    int idloai = 0;
    private Menu menu;
    EditText editpricemin, editpricemax;
    Button btnprice100k, btnprice200k, btnpriceover200k, btnreset, btnapply;
    RadioRealButtonGroup realButtonGroup;
    RadioRealButton btnmoinhat, btnbanchay, btngia;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_muc_shop);
        anhxa();
        actionBar();
        getData(0, ++a);
        EventFilter();
        EventRealButton();

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

    private void EventFilter() {
        btnapply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer x = new Integer(editpricemin.getText().toString());
                Integer y = new Integer(editpricemax.getText().toString());
                for(int i=0; i < arrayList.size(); i++){
                    if(arrayList.get(i).getGiasanpham() > y || arrayList.get(i).getGiasanpham() < x){
                        arrayList.remove(i);
                        i--;
                    }
                }
                adapter.notifyDataSetChanged();
                editpricemin.setText("");
                editpricemax.setText("");
                if(drawerLayout.isDrawerOpen(GravityCompat.END)){
                    drawerLayout.closeDrawer(GravityCompat.END);
                }

            }
        });
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
                editpricemin.setText("");
                editpricemax.setText("");
                if(drawerLayout.isDrawerOpen(GravityCompat.END)){
                    drawerLayout.closeDrawer(GravityCompat.END);
                }
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
                adapter.notifyDataSetChanged();
                editpricemin.setText("");
                editpricemax.setText("");
                if(drawerLayout.isDrawerOpen(GravityCompat.END)){
                    drawerLayout.closeDrawer(GravityCompat.END);
                }
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
                adapter.notifyDataSetChanged();
                editpricemin.setText("");
                editpricemax.setText("");
                if(drawerLayout.isDrawerOpen(GravityCompat.END)){
                    drawerLayout.closeDrawer(GravityCompat.END);
                }
            }
        });

    }
    private void getData(final int x, final int tanggiam) {
        idloai = getIntent().getIntExtra("idloai", 0);
        idcuahang = getIntent().getIntExtra("idcuahang", 0);
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlhienthi,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                      //  Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();
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
                map.put("idcuahang", String.valueOf(idcuahang) );
                map.put("x", String.valueOf(x));
                map.put("tanggiam", String.valueOf(tanggiam));
                map.put("idloai", String.valueOf(idloai) );
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void actionBar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Tìm kiếm");
        actionBar.setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void anhxa() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerlayoutdanhmucshop);
        navigationView = (NavigationView) findViewById(R.id.navigationdanhmucshop);
        toolbar = (Toolbar) findViewById(R.id.toolbardanhmucshop);
        recyclerView = (RecyclerView) findViewById(R.id.recycleviewdanhmucshop);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        arrayList = new ArrayList<>();
        adapter = new SanPhamAdapter(recyclerView,this, arrayList);
        recyclerView.setAdapter(adapter);
        editpricemax = (EditText) findViewById(R.id.edittextpricemaxdanhmucshop);
        editpricemin = (EditText) findViewById(R.id.edittextpricemindanhmucshop);
        btnprice100k = (Button) findViewById(R.id.buttonnhonhatdanhmucshop);
        btnprice200k = (Button) findViewById(R.id.buttonnvuadanhmucshop);
        btnpriceover200k = (Button) findViewById(R.id.buttonlonnhatdanhmucshop);
        btnreset = (Button) findViewById(R.id.buttonresetdanhmucshop);
        btnapply = (Button) findViewById(R.id.buttonappdungdanhmucshop);
        realButtonGroup = (RadioRealButtonGroup)findViewById(R.id.buttongroupdanhmucshop);
        btnmoinhat = (RadioRealButton) findViewById(R.id.radiobuttonmoinhatdanhmucshop);
        btnbanchay = (RadioRealButton) findViewById(R.id.radiobuttonbanchaydanhmucshop);
        btngia = (RadioRealButton) findViewById(R.id.radiobuttonsapxeptheogiadanhmucshop);

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
//                if(x == 0){
//                    adapter.getFilter().filter(content);
//                    x++;
//                }else {
//                    adapter.getFilter().filter(query);
//                }
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
}
