package com.example.nav.ui.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.nav.R;
import com.example.nav.ui.Model.DanhGia;

import java.util.ArrayList;

public class DanhGiaAdapter extends BaseAdapter {
    Context context;
    ArrayList<DanhGia> arrayList;

    public DanhGiaAdapter(Context context, ArrayList<DanhGia> arrayList) {
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
    public class ViewHolder{
        public TextView txtTennguoimua, txtdanhgia;
        public RatingBar ratingBar;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.dong_danh_gia, null);
            viewHolder.txtTennguoimua = (TextView) convertView.findViewById(R.id.textviewTennguoimua);
            viewHolder.txtdanhgia = (TextView) convertView.findViewById(R.id.textviewdanhgianguoimua);
            viewHolder.ratingBar = (RatingBar) convertView.findViewById(R.id.ratingbarnguoimua);
            convertView.setTag(viewHolder);

        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        DanhGia danhGia = arrayList.get(position);
        viewHolder.txtTennguoimua.setText(danhGia.getTennguoimua());
        viewHolder.txtdanhgia.setText(danhGia.getNhanxet());
        viewHolder.ratingBar.setRating((float) danhGia.getSosao());

        return convertView;
    }
}
