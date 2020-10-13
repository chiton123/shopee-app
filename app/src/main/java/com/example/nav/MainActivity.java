package com.example.nav;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.nav.ui.Model.GioHang;
import com.example.nav.ui.Fragment.HomeFragment;
import com.example.nav.ui.Model.LoaiSanPham;
import com.facebook.FacebookSdk;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    public static ArrayList<GioHang> manggiohang;
    HomeFragment fragmentHome = (HomeFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_home);
    // nguoi mua: cho xac nhan 0,  dang giao 2, đa giao 3, da huy 4
    // nguoi ban: cho lay hang 1, dang giao 2, da giao 3 , da huy 4
    public static int kiemtralogin = 0;
    public static int facebooklogin = 0;
    public static String username = "";
    public static String tennguoidung = "";
    public static int airpaymoney = 0;
    public static int totalpayment = 0;
    public static int iduser = 0;
    public static int idstore = 0;
    public static int admin = 0;
    public static String waypayment = "Thanh toán khi nhận hàng";
    public static String host = "http://10.10.35.151:8888/server/";
    public static String urldonhang = host + "getdonhangSendo.php";
    public static String urldanggiaochushop = host + "danggiaochushop.php";
    public static String urlchitietdonhang = host + "chitietdonhangSendo.php";
    public static String urlsoluongdaban = host + "soluongdabansendo.php";
    public static String urldanhgia = host + "getdanhgiasendo.php";
    public static String urlcholayhang = host + "cholayhangsendo.php";
    public static String urldanhmuc = host + "getdanhmucshop.php";
    public static String urlsanpham = host + "getsanphamcuashop.php";
  //  public static String urlaosomi = host + "getcacsanphamsendo.php?page=";
    public static String urlshop = host + "getthongtinshop.php";
    public static String urlsanphamlienquan = host + "getsanphamlienquan.php";
    public static String urldanhgiasanpham = host + "danhgiasendo.php";
    public static String urldangky = host + "dangkysendo.php";
    public static String urldangnhap = host + "dangnhapsendo.php";
    public static String urlchoxacnhan = host + "choxacnhansendo.php";
    public static String urldagiao = host + "getsanphamdagiaosendo.php";
    public static String urlloaisanpham = host + "getloaiSendo.php";
    public static String urlsanphammoinhat = host + "getsanphamSendo.php";
    public static String urlshopcuatoi = host + "shopcuatoisendo.php";
    public static String urltrangthaidonhang = host + "trangthaidonhangsendo.php";
    public static String urldanggiao = host + "danggiaosendo.php";
    public static String urldagiaonguoimua = host + "dagiaonguoimuasendo.php";
    public static String urldagiaochushop = host + "dagiaochushop.php";
    public static String urlhuydonhang = host + "huydonhangsendo.php";
    public static String urldahuy = host + "getdahuy.php";
    public static String urltheodoi = host + "theodoishop.php";
    public static String urlstopfollow = host + "stopfollow.php";
    public static String urlnumberfollow = host + "getnumberfollow.php";
    public static String urlsuggestitem = host + "getsuggestitem.php";
    public static String urlhienthitimkiem = host + "hienthitimkiem.php";
    public static String urlkindofproduct = host + "kindofproduct.php?page=";
    public static String urlkindallproduct = host + "kindallproduct.php";
    public static String urlkindproductshop = host + "kindproductshop.php";
    public static String urlviairpay = host + "viairpay.php";
    public static String urlupdateairpay = host + "updateairpay.php";
    public static String urlfilterstore = host + "filterstore.php";
    public static String urlairpaypayment = host + "Airpaypayment.php";
    public static String urlmualai = host + "mualai.php";
    public static String urlmessage = host + "message.php";
    public static String urlchat = host + "chat.php";
    public static String urlmessagelist = host + "messagelist.php";
    public static String urlstorelocation = host + "storelocation.php";
    public static String urlshopcomment = host + "shopcomment.php";
    public static String urlnotify = host + "notify.php";
    public static String urlproductlike = host + "productlike.php";
    public static String urlproductunlike = host + "productunlike.php";
    public static String urluserlikeproduct = host + "getproductlike.php";
    public static String urllikenumber = host + "likenumber.php";
    public static String urlfacebooklogin = host + "facebooklogin.php";
    public static String urlshopeewallet = host + "vishopee.php";
    public static String urlcheckshopeewallet = host + "checkshopeewallet.php";
    public static String urlshopeesurplus = host + "shopeesurplus.php";
    public static String urlgetmoney = host + "getmoney.php";
    public static String urlnotifyshop = host + "notifyshop.php";
    public static String urlkindproduct = host + "getkindproduct.php";
    public static String urlbrand = host + "getbrand.php";
    public static String urlshoppingunit = host + "shoppingunit.php";
    public static String urlpostproduct = host  + "dangsanpham.php";
    public static String urlchangeproduct = host + "changeproduct.php";
    public static String urlupdateproduct = host  + "updateproduct.php";
    public static String urldeleteproduct = host + "deleteproduct.php";
    public static String urlfiltership = host + "filtership.php";
    public static String urluserinfo = host + "getuserinfo.php";
    public static String urldangnhapadmin = host + "dangnhapadmin.php";
    public static String urlusermange = host + "usermanagement.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
      //  fragmentHome.CatchOnGridView((MainActivity) getApplicationContext());
        if(manggiohang != null){

        }else {
            manggiohang = new ArrayList<>();
        }
//        try {
//            PackageInfo info = null;
//            try {
//                info = getPackageManager().getPackageInfo(
//                        "com.example.nav",                  //Insert your own package name.
//                        PackageManager.GET_SIGNATURES);
//            } catch (PackageManager.NameNotFoundException e) {
//                e.printStackTrace();
//            }
//            for (Signature signature : info.signatures) {
//                MessageDigest md = MessageDigest.getInstance("SHA");
//                md.update(signature.toByteArray());
//                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
//            }
//        } catch (NoSuchAlgorithmException e) {
//
//        }

    }
    public void getShopeeWallet() {
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, MainActivity.urlviairpay,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                        if(response != null){
                            MainActivity.airpaymoney = Integer.parseInt(response);
                          //  Toast.makeText(getApplicationContext(), MainActivity.airpaymoney + "", Toast.LENGTH_SHORT).show();
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


}
