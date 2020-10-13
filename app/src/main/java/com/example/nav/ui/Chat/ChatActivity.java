package com.example.nav.ui.Chat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
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
import com.example.nav.ui.Adapter.MessageAdapter;
import com.example.nav.ui.Model.Chat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;



public class ChatActivity extends AppCompatActivity {
    EditText txt_send;
    ImageButton btnsend;
    RecyclerView recyclerView;
    Toolbar toolbar;
    String owner_name = "";
    public static int iduser_other = 0;
    ArrayList<Chat> mchat;
    String urlchat = MainActivity.urlchat;
    MessageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        anhxa();
        actionBar();
        readMessage(MainActivity.iduser, iduser_other);
        eventButton(MainActivity.iduser);

    }

    private void actionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(owner_name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void readMessage(final int idmain, int userid) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, MainActivity.urlmessage,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for(int i=0; i < jsonArray.length(); i++){
                                JSONObject object = jsonArray.getJSONObject(i);
                                mchat.add(new Chat(
                                        object.getInt("sender"),
                                        object.getInt("reciever"),
                                        object.getString("message")
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
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("idmain", String.valueOf(idmain));
                map.put("iduser", String.valueOf(iduser_other));
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }
    private void eventButton(final int idmain) {
        btnsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String message = txt_send.getText().toString();
                if(message.equals("")){
                    Toast.makeText(getApplicationContext(), "Không thể gửi tin rỗng", Toast.LENGTH_SHORT).show();
                }else {
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, urlchat,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    if(response.equals("success")){
                                        mchat.add(new Chat(idmain, iduser_other, message));
                                        adapter.notifyDataSetChanged();
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
                            map.put("idmain", String.valueOf(idmain));
                            map.put("iduser", String.valueOf(iduser_other));
                            map.put("message", message);
                            return map;
                        }
                    };
                    requestQueue.add(stringRequest);
                    txt_send.setText("");
                }

            }
        });

    }

    private void anhxa() {
        iduser_other = getIntent().getIntExtra("iduser",0);
        owner_name = getIntent().getStringExtra("ownername");
        toolbar = (Toolbar) findViewById(R.id.toolbarchat);
        btnsend = (ImageButton) findViewById(R.id.buttonsend);
        txt_send = (EditText) findViewById(R.id.edittextmessage);
        recyclerView = (RecyclerView) findViewById(R.id.recycleview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mchat = new ArrayList<>();
        adapter = new MessageAdapter(ChatActivity.this, mchat);
        recyclerView.setAdapter(adapter);


    }
}
