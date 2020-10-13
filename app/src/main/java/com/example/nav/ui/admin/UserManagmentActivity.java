package com.example.nav.ui.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
import com.example.nav.ui.Adapter.UserAdapter;
import com.example.nav.ui.Adapter.UserAdapter_Admin;
import com.example.nav.ui.Model.Admin_User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class UserManagmentActivity extends AppCompatActivity {
    Toolbar toolbar;
    UserAdapter_Admin adapter_admin;
    ListView listView;

    ArrayList<Admin_User> arrayList;
    String url = MainActivity.urlusermange;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_managment);
        anhxa();
        actionBar();
        getData();
        eventInfo();
        
    }

    private void eventInfo() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), UserInfoChangeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("user", arrayList.get(position));
                startActivity(intent);

            }
        });
    }

    private void getData() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if(response != null){
                            for(int i=0; i < response.length(); i++){
                                try {
                                    JSONObject object = response.getJSONObject(i);
                                    arrayList.add(new Admin_User(
                                        object.getInt("id"),
                                        object.getString("name"),
                                        object.getInt("phone"),
                                        object.getString("address"),
                                        object.getString("email")
                                    ));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                            adapter_admin.notifyDataSetChanged();
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

    private void anhxa() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        listView = (ListView) findViewById(R.id.listview);
        arrayList = new ArrayList<>();
        adapter_admin = new UserAdapter_Admin(getApplicationContext(), arrayList);
        listView.setAdapter(adapter_admin);
        
        
    }
}
