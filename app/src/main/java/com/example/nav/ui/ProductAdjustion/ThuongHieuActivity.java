package com.example.nav.ui.ProductAdjustion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.nav.MainActivity;
import com.example.nav.R;
import com.example.nav.ui.Adapter.ThuongHieuAdapter;
import com.example.nav.ui.Model.ThuongHieu;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ThuongHieuActivity extends AppCompatActivity {
    Toolbar toolbar;
    ListView listView;
    SearchView searchView ;
    ThuongHieuAdapter adapter;
    ArrayList<ThuongHieu> arrayList;
    String url = MainActivity.urlbrand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thuong_hieu);
        anhxa();
        actionBar();
        getData();
        eventBack();

    }

    private void eventBack() {
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent();
//                intent.putExtra("name", arrayList.get(position).getName());
//                intent.putExtra("id", arrayList.get(position).getId());
//                setResult(234, intent);
//                finish();
//            }
//        });
    }

    private void getData() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if(response != null){
                            for(int i = 0 ; i < response.length(); i++){
                                try {
                                    JSONObject object = response.getJSONObject(i);
                                    arrayList.add(new ThuongHieu(
                                            object.getInt("id"),
                                            object.getString("name")
                                    ));

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            adapter.notifyDataSetChanged();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
        requestQueue.add(jsonArrayRequest);

    }

    private void actionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_search_brand, menu);
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
        return super.onOptionsItemSelected(item);
    }

    private void anhxa() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        listView = (ListView) findViewById(R.id.listview);
        arrayList  = new ArrayList<>();
        adapter = new ThuongHieuAdapter(getApplicationContext(), arrayList, this);
        listView.setAdapter(adapter);
    }
}
