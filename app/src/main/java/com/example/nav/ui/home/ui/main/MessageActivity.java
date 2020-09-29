package com.example.nav.ui.home.ui.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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
import com.example.nav.ui.Adapter.UserAdapter;
import com.example.nav.ui.Model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class MessageActivity extends AppCompatActivity {
    Toolbar toolbar;
    ListView listView;
    UserAdapter adapter;
    ArrayList<User> arrayList;
    String url = MainActivity.urlmessagelist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        anhxa();
        actionBar();
        getData();
        EventChat();
    }

    private void EventChat() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MessageActivity.this, ChatActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("iduser", arrayList.get(position).getIduser());
                intent.putExtra("ownername", arrayList.get(position).getName());
                startActivity(intent);
            }
        });
    }

    private void getData() {
        final ArrayList<Integer> numberlist = new ArrayList<>();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for(int i=0; i < jsonArray.length(); i++){
                                JSONObject object = jsonArray.getJSONObject(i);
                                int iduser = object.getInt("iduser");
                                String username = object.getString("name");

                                if(i >= 1){
                                    if(!numberlist.contains(iduser)){
                                        arrayList.add(new User(
                                                object.getInt("iduser"),
                                                object.getString("name")
                                        ));
                                    }
                                }else {
                                    arrayList.add(new User(
                                            object.getInt("iduser"),
                                            object.getString("name")
                                    ));
                                }
                                numberlist.add(object.getInt("iduser"));



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
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("iduser", String.valueOf(MainActivity.iduser));
                return map;
            }
        };
        requestQueue.add(stringRequest);

        adapter.notifyDataSetChanged();

    }

    private void actionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Chat");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void anhxa() {
        toolbar = (Toolbar) findViewById(R.id.toolbarabc);
        listView = (ListView) findViewById(R.id.listviewuserlist);
        arrayList = new ArrayList<>();
        adapter = new UserAdapter(getApplicationContext(), arrayList);
        listView.setAdapter(adapter);

    }
}
