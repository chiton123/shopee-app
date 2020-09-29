package com.example.nav.ui.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nav.R;
import com.example.nav.ui.Model.LoaiSanPham;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class LoaiSanPhamAdapter extends BaseAdapter {
    Context context;
    ArrayList<LoaiSanPham> loaiSanPhamArrayList;

    public LoaiSanPhamAdapter(Context context, ArrayList<LoaiSanPham> loaiSanPhamArrayList) {
        this.context = context;
        this.loaiSanPhamArrayList = loaiSanPhamArrayList;
    }

    @Override
    public int getCount() {
        return loaiSanPhamArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return loaiSanPhamArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public class ViewHolder{
        public TextView txtTenSanPham;
        public ImageView imageViewHinhAnh;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.dong_loai_sanpham, null);
            viewHolder.txtTenSanPham = (TextView) convertView.findViewById(R.id.textviewtenloaisanpham);
            viewHolder.imageViewHinhAnh = (ImageView) convertView.findViewById(R.id.imageviewhinhanhloaisanpham);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        LoaiSanPham loaiSanPham = (LoaiSanPham) getItem(position);
        viewHolder.txtTenSanPham.setText(loaiSanPham.getTenloaisanpham());
     //   Picasso.with(context).load(loaiSanPham.getHinhanhloaisanpham()).placeholder(R.drawable.noimage).error(R.drawable.error).into(viewHolder.imageViewHinhAnh);
        Picasso.get().load(loaiSanPham.getHinhanhloaisanpham()).into(viewHolder.imageViewHinhAnh);

        return convertView;
    }
}
