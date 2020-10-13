package com.example.nav.ui.ProductAdjustion;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nav.R;

public class LinkImageActivity extends AppCompatActivity {
    Toolbar toolbar;
    EditText edit_link;
    Button btnxacnhan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link_image);
        anhxa();
        actionBar();
        eventButton();


    }

    private void eventButton() {
        btnxacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = edit_link.getText().toString();
                if(url.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Vui lòng nhập đường link vào", Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent = new Intent();
                    intent.putExtra("url", url);
                    setResult(345, intent);
                    finish();
                }

            }
        });

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
        btnxacnhan = (Button) findViewById(R.id.buttonxacnhan);
        edit_link = (EditText) findViewById(R.id.linkanh);

    }
}
