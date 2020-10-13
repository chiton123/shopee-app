package com.example.nav.ui.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.nav.R;
import com.example.nav.ui.Model.Admin_User;

public class EditUserActivity extends AppCompatActivity {
    Toolbar toolbar;
    EditText editname, editphone, editadd, editemail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);
        anhxa();
        actionBar();
        getInfo();


    }

    private void getInfo() {
        Intent intent = getIntent();
        Admin_User user = (Admin_User) intent.getSerializableExtra("user");
        editadd.setText(user.getAddress());
        editemail.setText(user.getEmail());
        editname.setText(user.getName());
        editphone.setText(user.getPhonenumber()+"");

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
        editphone = (EditText) findViewById(R.id.editphone);
        editname = (EditText) findViewById(R.id.editname);
        editemail = (EditText) findViewById(R.id.editemail);
        editadd = (EditText) findViewById(R.id.editadd);

    }
}
