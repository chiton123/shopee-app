package com.example.nav.ui.notifications;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.nav.MainActivity;
import com.example.nav.R;
import com.example.nav.ui.Sign_in.DangKyActivity;
import com.example.nav.ui.Sign_in.DangNhapActivity;
import com.example.nav.ui.Order.DonMuaActivity;
import com.example.nav.ui.Sale_purchase.GioHangActivity;
import com.example.nav.ui.UserActivity.LuotThichActivity;
import com.example.nav.ui.Chat.MessageActivity;
import com.example.nav.ui.shop.Quanlyshopactivity;
import com.example.nav.ui.ShopeeWallet.ShopeewalletAActivity;
import com.example.nav.ui.ShopeeWallet.VishopeeActivity;
import com.facebook.login.LoginManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class NotificationsFragment extends Fragment {

//    private NotificationsViewModel notificationsViewModel;
    TextView txtTenUser;
    Button btnDangNhap, btnDangky, btnDangXuat;
    TextView txtlichsumuahang;
    String urlshop = MainActivity.urlshopcuatoi;
    String urlnotification = MainActivity.urlnotify;
    String urllike = MainActivity.urllikenumber;
    LinearLayout linearLayoutShopcuatoi;
    TextView txtshopcuatoi;
    ImageView imageViewlichsu;
    ImageView imageViewChoxacnhan, imageViewChoLayhang, imageViewDangGiao, imageViewDanhgia;
    String tenuser = "";
    TextView notify;
    TextView notify1;
    TextView notify2;
    Toolbar toolbar;
    TextView txt_soluong;
    ImageView likemore;
    int cartnumber = 0;
    int shopornot = 0;
    int checkshopeewallet = 0;
    TextView likenumber;
    ImageView vishopee;
    String urlcheckshopeewallet = MainActivity.urlcheckshopeewallet;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        notificationsViewModel =
//                ViewModelProviders.of(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        txtTenUser = (TextView) root.findViewById(R.id.textviewTenuser);
        btnDangky = (Button) root.findViewById(R.id.buttonDangKyUser);
        btnDangNhap = (Button) root.findViewById(R.id.buttonDangNhapUser);
        btnDangXuat = (Button) root.findViewById(R.id.buttonDangXuat);
        txtlichsumuahang = (TextView) root.findViewById(R.id.textviewlichsu);
        linearLayoutShopcuatoi = (LinearLayout) root.findViewById(R.id.linearshopcuatoi);
        txtshopcuatoi = (TextView) root.findViewById(R.id.textviewshopcuatoi);
        imageViewlichsu = (ImageView) root.findViewById(R.id.imageviewlichsumuahang);
        imageViewChoxacnhan = (ImageView) root.findViewById(R.id.imageviewuserchoxacnhan);
        imageViewDangGiao = (ImageView) root.findViewById(R.id.imageviewuserdanggiao);
        imageViewDanhgia = (ImageView) root.findViewById(R.id.imageviewuserdanhgia);
        notify = (TextView) root.findViewById(R.id.number);
        notify1 = (TextView) root.findViewById(R.id.number1);
        notify2 = (TextView) root.findViewById(R.id.number2);
        toolbar = (Toolbar) root.findViewById(R.id.toolbar);
        likemore = (ImageView) root.findViewById(R.id.likemore);
        likenumber = (TextView) root.findViewById(R.id.likenumber);
        vishopee = (ImageView) root.findViewById(R.id.vishopee);
        setHasOptionsMenu(true);
        actionBar();
        imageViewlichsu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DonMuaActivity.class);
                startActivity(intent);
            }
        });
        likemore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LuotThichActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        checkShopeeWallet();
        vishopee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkshopeewallet == 1){
                    Intent intent = new Intent(getActivity(), ShopeewalletAActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(getActivity(), VishopeeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }

            }
        });
        btnDangXuat.setVisibility(View.INVISIBLE);
        txtTenUser.setVisibility(View.INVISIBLE);
        linearLayoutShopcuatoi.setVisibility(View.INVISIBLE);
        EventResgister();
        EventLogin();
        ShowUpUserName();
        EventShop();
        EventImageview();
        notification(0);
        notification(2);
        notification(3);
        getNumberLikes();
        return root;
    }

    private void checkShopeeWallet() {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlcheckshopeewallet,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("exits")){
                            checkshopeewallet = 1;
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
                map.put("iduser", String.valueOf(MainActivity.iduser));
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void getNumberLikes() {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urllike,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        int numberlike = Integer.parseInt(response.toString());
                        likenumber.setText(numberlike + " likes");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

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
    }

    private void actionBar() {
        toolbar.inflateMenu(R.menu.menu_user);
        MainActivity activity = (MainActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setTitle("");
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.menu_user, menu);
        final MenuItem menuItem = menu.findItem(R.id.giohang);
        if(MainActivity.kiemtralogin == 1){
            if(shopornot == 0){
                MenuItem menuItem1 = menu.findItem(R.id.shopcuatoi);
                menuItem1.setVisible(false);
            }
        }
        View actionview = (View)menu.findItem(R.id.giohang).getActionView();
        txt_soluong = (TextView) actionview.findViewById(R.id.cart_badge);
        setUpBadge();
        actionview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(menuItem);
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }
    private void setUpBadge() {
        cartnumber = MainActivity.manggiohang.size();
        if(cartnumber == 0){
            txt_soluong.setVisibility(View.GONE);
        }else {
            txt_soluong.setText(cartnumber+"");
            txt_soluong.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menugiohang:
                if(MainActivity.kiemtralogin == 0){
                    Intent intent = new Intent(getActivity(), DangNhapActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
                else {
                    Intent intent = new Intent(getActivity(), GioHangActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
                break;
            case R.id.chat:
                if(MainActivity.kiemtralogin == 0){
                    Intent intent = new Intent(getActivity(), DangNhapActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
                else {
                    Intent intent = new Intent(getActivity(), MessageActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
                break;
            case R.id.shopcuatoi:
                if(MainActivity.kiemtralogin == 0){
                    Intent intent = new Intent(getActivity(), DangNhapActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
                else {
                    if(shopornot == 1){
                        Intent intent = new Intent(getActivity(), Quanlyshopactivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                }
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    private void notification(final int x) {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlnotification,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(getActivity(), response.toString(), Toast.LENGTH_SHORT).show();
                        int a = Integer.parseInt(response.toString());
                        if(a > 0){
                            if(x == 0){
                                notify.setText(response.toString());
                                notify.setVisibility(View.VISIBLE);
                            }
                            if(x == 2){
                                notify1.setText(response.toString());
                                notify1.setVisibility(View.VISIBLE);
                            }
                            if(x == 3){
                                notify2.setText(response.toString());
                                notify2.setVisibility(View.VISIBLE);
                            }

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
                map.put("iduser", String.valueOf(MainActivity.iduser));
                map.put("x",String.valueOf(x));
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void EventImageview() {
        imageViewChoxacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DonMuaActivity.class);
                intent.putExtra("index", 0);
                startActivity(intent);
            }
        });

        imageViewDangGiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DonMuaActivity.class);
                intent.putExtra("index", 1);
                startActivity(intent);
            }
        });
        imageViewDanhgia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DonMuaActivity.class);
                intent.putExtra("index", 2);
                startActivity(intent);
            }
        });
    }

    private void EventShop() {
        txtshopcuatoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Quanlyshopactivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("tenshop", txtshopcuatoi.getText().toString());
                startActivity(intent);
            }
        });
    }

    public void ShowUpUserName() {
        if(MainActivity.kiemtralogin == 1){
                txtTenUser.setVisibility(View.VISIBLE);
                txtTenUser.setText("Xin chào: " + MainActivity.tennguoidung);
                btnDangNhap.setVisibility(View.INVISIBLE);
                btnDangky.setVisibility(View.INVISIBLE);
                btnDangXuat.setVisibility(View.VISIBLE);
            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            StringRequest stringRequest = new StringRequest(Request.Method.POST, urlshop,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                JSONObject object = jsonArray.getJSONObject(0);
                                if(object.getString("name").equals("")){
                                    linearLayoutShopcuatoi.setVisibility(View.GONE);
                                    getActivity().invalidateOptionsMenu();
                                }else {
                                    linearLayoutShopcuatoi.setVisibility(View.VISIBLE);
                                    txtshopcuatoi.setText(object.getString("name"));
                                    getActivity().invalidateOptionsMenu();
                                    shopornot = 1;
                                    MainActivity.idstore = object.getInt("id");
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
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
                    map.put("tendangnhap", MainActivity.username);
                    return map;
                }
            };
            requestQueue.add(stringRequest);
        }


        btnDangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MainActivity.facebooklogin == 1){
                    LoginManager.getInstance().logOut();
                    MainActivity.facebooklogin = 0;
                    MainActivity.kiemtralogin = 0;
                    MainActivity.username = "";
                    MainActivity.waypayment = "";
                    MainActivity.totalpayment = 0;
                    MainActivity.airpaymoney = 0;
                    MainActivity.manggiohang.clear();
                    txtTenUser.setVisibility(View.INVISIBLE);
                    btnDangNhap.setVisibility(View.VISIBLE);
                    btnDangky.setVisibility(View.VISIBLE);
                    btnDangXuat.setVisibility(View.INVISIBLE);
                }else {
                    MainActivity.kiemtralogin = 0;
                    MainActivity.username = "";
                    MainActivity.waypayment = "";
                    MainActivity.totalpayment = 0;
                    MainActivity.airpaymoney = 0;
                    MainActivity.manggiohang.clear();
                    txtTenUser.setVisibility(View.INVISIBLE);
                    btnDangNhap.setVisibility(View.VISIBLE);
                    btnDangky.setVisibility(View.VISIBLE);
                    btnDangXuat.setVisibility(View.INVISIBLE);
                }
            }
        });

    }

    private void EventLogin() {
        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DangNhapActivity.class);
          //      intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivityForResult(intent, 1);


            }
        });
    }

    private void EventResgister() {
        btnDangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DangKyActivity.class);
                startActivity(intent);
            }
        });
    }

}
