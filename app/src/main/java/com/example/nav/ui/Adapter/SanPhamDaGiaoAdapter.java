package com.example.nav.ui.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nav.R;
import com.example.nav.ui.UserActivity.DanhGiaActivity;
import com.example.nav.ui.Model.GioHangChoLayHang;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SanPhamDaGiaoAdapter extends BaseAdapter {
    Context context;
    ArrayList<GioHangChoLayHang> arrayList;

    public SanPhamDaGiaoAdapter(Context context, ArrayList<GioHangChoLayHang> arrayList) {
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
        public TextView txtten;
        public ImageView imageView;
        public Button btnDanhGia;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.dong_san_pham_da_giao, null);
            viewHolder.txtten = (TextView) convertView.findViewById(R.id.textviewtensanphamdagiao);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.imagviewdagiao);
            viewHolder.btnDanhGia = (Button) convertView.findViewById(R.id.buttondanhgia);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        GioHangChoLayHang sanPham = arrayList.get(position);
        viewHolder.txtten.setText(sanPham.getTensp());
        Picasso.get().load(sanPham.getHinhanhsp()).into(viewHolder.imageView);
        viewHolder.btnDanhGia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DanhGiaActivity.class);
                intent.putExtra("masanpham", arrayList.get(position).getIdsp());
                context.startActivity(intent);
            }
        });

        return convertView;
    }
}
