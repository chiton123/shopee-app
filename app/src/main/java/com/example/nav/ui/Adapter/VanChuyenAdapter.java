package com.example.nav.ui.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.SwitchCompat;

import com.example.nav.R;
import com.example.nav.ui.Model.VanChuyen;

import java.util.ArrayList;

public class VanChuyenAdapter extends BaseAdapter {
    Context context;
    ArrayList<VanChuyen> arrayList;
    Activity activity;

    public VanChuyenAdapter(Context context, ArrayList<VanChuyen> arrayList, Activity activity) {
        this.context = context;
        this.arrayList = arrayList;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    public class ViewHoler{
        public TextView ten;
        public TextView gia;
        public Switch switchCompat;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHoler viewHoler = null;
        if(convertView == null){
            viewHoler = new ViewHoler();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.dong_van_chuyen, null);
            viewHoler.ten = (TextView) convertView.findViewById(R.id.tenkenhvanchuyen);
            viewHoler.gia = (TextView) convertView.findViewById(R.id.giakenhvanchuyen);
            viewHoler.switchCompat = (Switch) convertView.findViewById(R.id.switch1);
            convertView.setTag(viewHoler);
        }else {
            viewHoler = (ViewHoler) convertView.getTag();
        }
        final VanChuyen vanChuyen = arrayList.get(position);
        viewHoler.ten.setText(vanChuyen.getTen());
        viewHoler.gia.setText(vanChuyen.getGia()+"");
        viewHoler.switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Toast.makeText(context, "Đã chọn", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    intent.putExtra("id", vanChuyen.getId());
                    intent.putExtra("name", vanChuyen.getTen());
                    activity.setResult(456, intent);
                    activity.finish();

                }else {
                    Toast.makeText(context, "Bỏ chọn", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return convertView;
    }
}
