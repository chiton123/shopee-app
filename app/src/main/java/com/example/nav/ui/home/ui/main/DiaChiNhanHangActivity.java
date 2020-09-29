package com.example.nav.ui.home.ui.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nav.R;

public class DiaChiNhanHangActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView txthoanthanh;
    EditText editten, editsodt, editthanhpho, editquan, editsonha;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dia_chi_nhan_hang);
        anhxa();
        actionBar();
        registerForContextMenu(editthanhpho);
        getBack();

    }

    private void getBack() {
        txthoanthanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editthanhpho.equals("") || editquan.equals("") || editsodt.equals("") || editten.equals("") || editsonha.equals("")){
                    Toast.makeText(getApplicationContext(),"Vui lòng nhập đủ thông tin!", Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent = new Intent();
                    String diachi = editsonha.getText().toString() +", "+ editquan.getText().toString() +", " + editthanhpho.getText().toString();
                    intent.putExtra("diachi", diachi );
                    setResult(5, intent);
                    finish();
                }
            }
        });
    }

    private void actionBar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("");
        actionBar.setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.menu_thanh_pho, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.vinhlong:
                editthanhpho.setText("Vĩnh Long");
                break;
            case R.id.cantho:
                editthanhpho.setText("Cần Thơ");
                break;
            case R.id.angiang:
                editthanhpho.setText("An Giang");
                break;
        }

        return super.onContextItemSelected(item);
    }

    private void anhxa() {
        toolbar = (Toolbar) findViewById(R.id.toolbardiachinhanhang);
        txthoanthanh = (TextView) findViewById(R.id.textviewhoanthanh);
        editten = (EditText)findViewById(R.id.edittexttenkh);
        editquan = (EditText) findViewById(R.id.edittextquankh);
        editsodt = (EditText) findViewById(R.id.edittextsodtkh);
        editsonha = (EditText) findViewById(R.id.edittextsonhakh);
        editthanhpho = (EditText) findViewById(R.id.edittextthanhphokh);
    }
}
