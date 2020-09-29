package com.example.nav.ui.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.nav.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class NapTienAdapter extends BaseAdapter {
    Context context;
    ArrayList<Integer> arrayList;

    public NapTienAdapter(Context context, ArrayList<Integer> arrayList) {
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
        public TextView txtsotien;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHoler viewHoler = null;
        if(convertView == null){
            viewHoler = new ViewHoler();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.dong_nap_tien, null);
            viewHoler.txtsotien = (TextView) convertView.findViewById(R.id.textviewnaptien);
            convertView.setTag(viewHoler);
        }else {
            viewHoler = (ViewHoler) convertView.getTag();
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHoler.txtsotien.setText(decimalFormat.format(arrayList.get(position)) + "");
        return convertView;
    }
}
