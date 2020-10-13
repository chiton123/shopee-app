package com.example.nav.ui.shop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
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
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {
    Toolbar toolbar;
    private GoogleMap mMap;
    int idcuahang = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        anhxa();


    }

//    private void getLocation() {
//        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        try {
//                            JSONArray jsonArray = new JSONArray(response);
//                            for(int i=0; i < jsonArray.length(); i++){
//                                JSONObject object = jsonArray.getJSONObject(i);
//                                vido = object.getInt("vido");
//                                kinhdo = object.getInt("kinhdo");
//                            }
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//
//                    }
//                }){
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String,String> map = new HashMap<>();
//                map.put("idcuahang", String.valueOf(idcuahang));
//                return map;
//            }
//        };
//        requestQueue.add(stringRequest);
//
//    }

    private void anhxa() {
        idcuahang = getIntent().getIntExtra("idcuahang",0);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
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
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng store = new LatLng(getIntent().getDoubleExtra("vido",0), getIntent().getDoubleExtra("kinhdo",0));
        Toast.makeText(getApplicationContext(), getIntent().getIntExtra("vido", 0) +"" + getIntent().getIntExtra("kinhdo",0), Toast.LENGTH_SHORT).show();
        mMap.addMarker(new MarkerOptions().position(store).title("Store")).setIcon(BitmapDescriptorFactory.defaultMarker());
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        CameraPosition cameraPosition = new CameraPosition.Builder().target(store).zoom(200).build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }
}
