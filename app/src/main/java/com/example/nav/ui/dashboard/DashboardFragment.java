package com.example.nav.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.nav.MainActivity;
import com.example.nav.R;
import com.example.nav.ui.admin.AdminActivity;

import java.util.HashMap;
import java.util.Map;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    EditText editusername, editpass;
    Button btnlogin;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        editusername = (EditText) root.findViewById(R.id.username);
        editpass = (EditText) root.findViewById(R.id.password);
        btnlogin = (Button) root.findViewById(R.id.login);
        eventLogin();



        return root;
    }

    private void eventLogin() {
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = editusername.getText().toString();
                final String password = editpass.getText().toString();
                if(username.equals("") || password.equals("")){
                    Toast.makeText(getActivity(), "Vui lòng nhập đủ thông tin!", Toast.LENGTH_SHORT).show();
                }else {
                    RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, MainActivity.urldangnhapadmin,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    if(response.equals("success")){
                                        Toast.makeText(getActivity(), "thanh cong", Toast.LENGTH_SHORT).show();
                                        MainActivity.admin = 1;
                                        Intent intent = new Intent(getActivity(), AdminActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                    }else {
                                        Toast.makeText(getActivity(), "sai ten hoac mat khau", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
                                }
                            }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String,String> map = new HashMap<>();
                            map.put("username", username);
                            map.put("password", password);
                            return map;
                        }
                    };
                    requestQueue.add(stringRequest);
                }


            }
        });

    }
}
