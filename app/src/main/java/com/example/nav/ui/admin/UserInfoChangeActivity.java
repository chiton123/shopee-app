package com.example.nav.ui.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.nav.R;
import com.example.nav.ui.Model.Admin_User;

public class UserInfoChangeActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView txtname, txtphone, txtadd, txtemail;
    Button btnsua, btnxoa;
    Admin_User user ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info_change);
        anhxa();
        actionBar();
        getInfo();
        eventButton();


    }

    private void eventButton() {

        btnsua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EditUserActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("user", user);
                startActivity(intent);
            }
        });
    }

    private void getInfo() {


        txtadd.setText(user.getAddress());
        txtemail.setText(user.getEmail());
        txtname.setText(user.getName());
        txtphone.setText(user.getPhonenumber() + "");

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
        txtphone = (TextView) findViewById(R.id.phone);
        txtname = (TextView) findViewById(R.id.name);
        txtemail = (TextView) findViewById(R.id.email);
        txtadd = (TextView) findViewById(R.id.address);
        btnsua = (Button) findViewById(R.id.sua);
        btnxoa = (Button) findViewById(R.id.xoa);
        user = (Admin_User) getIntent().getSerializableExtra("user");
    }
}
