package com.example.nav.ui.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.nav.R;
import com.example.nav.ui.Model.LoaiSanPham;

import java.util.ArrayList;

public class LoaiAdapter extends BaseAdapter {
    Context context;
    ArrayList<LoaiSanPham> arrayList;

    public LoaiAdapter(Context context, ArrayList<LoaiSanPham> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
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
        public TextView txt_name;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHoler viewHoler = null;
        if(convertView == null){
            viewHoler = new ViewHoler();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.dong_loai, null);
            viewHoler.txt_name = (TextView) convertView.findViewById(R.id.textviewten);
            convertView.setTag(viewHoler);

        }else {
            viewHoler = (ViewHoler) convertView.getTag();
        }
        LoaiSanPham loaiSanPham = arrayList.get(position);
        viewHoler.txt_name.setText(loaiSanPham.getTenloaisanpham());

        return convertView;
    }
}
