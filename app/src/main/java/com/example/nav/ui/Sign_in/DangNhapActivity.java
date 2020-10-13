package com.example.nav.ui.Sign_in;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class DangNhapActivity extends AppCompatActivity {
    TextView txtDangKy;
    EditText editTenDangNhap;
    EditText editPass;
    String tenDangNhap = "", matkhau = "";
    String urldangnhap = MainActivity.urldangnhap;
    String urlfacebooklogin = MainActivity.urlfacebooklogin;
    String urlinfo = MainActivity.urluserinfo;
    Button btnDangNhap;
    Toolbar toolbar;
    LoginButton loginButton;
    CallbackManager callbackManager;
    String name, email;
    TextView abcd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);
        anhxa();
        callbackManager = CallbackManager.Factory.create();
        actionBar();
        EventRegister();
        EventLogin();
        loginButton.setReadPermissions(Arrays.asList("public_profile","email"));
        setlogin_facebook();


    }

    private void getUserInfo() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlinfo,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response != null){
                            MainActivity.iduser = Integer.parseInt(response.toString());
                            getShopeeWallet();
                            finish();
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
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
                map.put("username", MainActivity.username);
                return map;
            }
        };
        requestQueue.add(stringRequest);




    }

    private void setlogin_facebook() {
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                loginButton.setVisibility(View.INVISIBLE);
                Toast.makeText(getApplicationContext(), ""+ loginResult.getAccessToken().getUserId(), Toast.LENGTH_SHORT).show();
                result(loginResult.getAccessToken());

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void result(AccessToken accessToken) {
        GraphRequest graphRequest = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                Log.d("JSON", response.getJSONObject().toString());
                try {
//                    abcd.setText(object.getString("name") + object.getString("link"));
                    name = object.getString("name");
                    email = object.getString("email");
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, urlfacebooklogin,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();
                                    MainActivity.kiemtralogin = 1;
                                    MainActivity.username = email;
                                    MainActivity.tennguoidung = name;
                                    MainActivity.facebooklogin = 1;
                                    getUserInfo();
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                                }
                            }
                    ){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String,String> map = new HashMap<>();
                            map.put("email", email);
                            map.put("name", name);
                            return map;
                        }
                    };
                    requestQueue.add(stringRequest);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        //        parameters.putString("fields", "id,name,email,gender,birthday");
//        {
//            "id": "12345678",
//                "birthday": "1/1/1950",
//                "first_name": "Chris",
//                "gender": "male",
//                "last_name": "Colm",
//                "link": "http://www.facebook.com/12345678",
//                "location": {
//            "id": "110843418940484",
//                    "name": "Seattle, Washington"
//        },
//            "locale": "en_US",
//                "name": "Chris Colm",
//                "timezone": -8,
//                "updated_time": "2010-01-01T16:40:43+0000",
//                "verified": true
        //phones
//        }
        Bundle parameters = new Bundle();
        parameters.putString("fields", "name,email");

        graphRequest.setParameters(parameters);
        graphRequest.executeAsync();
    }



    public  void getShopeeWallet() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, MainActivity.urlviairpay,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                        if(!response.equals("0")){
                            MainActivity.airpaymoney = Integer.parseInt(response);
                            Toast.makeText(getApplicationContext(), MainActivity.airpaymoney + "", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("iduser", String.valueOf(MainActivity.iduser));
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }
    private void actionBar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Đăng nhập");
        actionBar.setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void EventLogin() {
        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tenDangNhap = editTenDangNhap.getText().toString();
                matkhau = editPass.getText().toString();
                //Toast.makeText(getApplicationContext(), tenDangNhap + matkhau, Toast.LENGTH_SHORT).show();
                if(tenDangNhap.isEmpty() || matkhau.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }else {
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, urldangnhap,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    if(!response.equals("fail")){
                                        Toast.makeText(getApplicationContext(), "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                                        MainActivity.kiemtralogin = 1;
                                        MainActivity.username = tenDangNhap;
                                        MainActivity.tennguoidung = tenDangNhap;
                                        getUserInfo();
                                    }
                                    else {
                                        Toast.makeText(getApplicationContext(), "Sai tên hoặc mật khẩu", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                }
                            }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String, String> map = new HashMap<>();
                            map.put("tendangnhap", tenDangNhap);
                            map.put("matkhau", matkhau);
                            return map;
                        }
                    };
                    requestQueue.add(stringRequest);
                }
            }
        });
    }

    private void EventRegister() {
        txtDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DangKyActivity.class);
                startActivity(intent);
            }
        });
    }

    private void anhxa() {
        toolbar = (Toolbar) findViewById(R.id.toolbardangnhap);
        txtDangKy = (TextView) findViewById(R.id.textviewdangki);
        editTenDangNhap = (EditText) findViewById(R.id.edittexttendangnhap);
        editPass = (EditText) findViewById(R.id.edittextmatkhau);
        btnDangNhap = (Button) findViewById(R.id.buttonDangNhap);
        loginButton = (LoginButton) findViewById(R.id.login_button);
        abcd = (TextView) findViewById(R.id.abcd);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onStart() {
        LoginManager.getInstance().logOut();
        super.onStart();
    }
}
