package com.example.nav.ui.ShopeeWallet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.nav.R;

import in.aabhasjindal.otptextview.OTPListener;
import in.aabhasjindal.otptextview.OtpTextView;

public class VishopeeActivity extends AppCompatActivity {
    Toolbar toolbar;
    OtpTextView otpTextView;
    Button btnhoanthanh;
    int code = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vishopee);
        anhxa();
        actionBar();
        otpTextView.setOtpListener(new OTPListener() {
            @Override
            public void onInteractionListener() {

            }

            @Override
            public void onOTPComplete(String otp) {
                code = Integer.parseInt(otpTextView.getOTP());
                btnhoanthanh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), ShopeeBankActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("code", code );
                        startActivity(intent);
                    }
                });
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
        otpTextView = (OtpTextView) findViewById(R.id.otp_view);
        btnhoanthanh = (Button) findViewById(R.id.buttonhoanthanh);


    }
}
