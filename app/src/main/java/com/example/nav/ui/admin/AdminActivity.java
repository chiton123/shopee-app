package com.example.nav.ui.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.nav.R;
import com.example.nav.ui.Adapter.AdminAdapter;
import com.example.nav.ui.Model.Admin;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class AdminActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    ArrayList<Admin> arrayList;
    GridView gridView;
    AdminAdapter adminAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        anhxa();
        actionBar();
        eventGrid();


    }

    private void eventGrid() {
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        Intent intent = new Intent(getApplicationContext(), UserManagmentActivity.class );
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                }
            }
        });

    }

    private void actionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }
    public void addItem(){
        arrayList.add(new Admin(0, "Khách hàng", R.drawable.admin_customer));
        arrayList.add(new Admin(1, "Sản phẩm", R.drawable.admin_product));
        arrayList.add(new Admin(2, "Loại sản phẩm", R.drawable.admin_categary));
        arrayList.add(new Admin(3, "Cửa hàng", R.drawable.admin_shop));
        arrayList.add(new Admin(4, "Sale", R.drawable.admin_sale));
        arrayList.add(new Admin(5, "Đánh giá", R.drawable.admin_chat));
        arrayList.add(new Admin(6, "Đơn hàng", R.drawable.admin_order));


    }
    private void anhxa() {
        gridView = (GridView) findViewById(R.id.gridview);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        navigationView = (NavigationView) findViewById(R.id.navi);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        arrayList = new ArrayList<>();
        addItem();
        adminAdapter = new AdminAdapter(getApplicationContext(), arrayList);
        try{
            gridView.setAdapter(adminAdapter);
        }catch (NullPointerException e){
            Toast.makeText(getApplicationContext(), "NullPointerException" ,Toast.LENGTH_SHORT).show();
        }



    }
}
