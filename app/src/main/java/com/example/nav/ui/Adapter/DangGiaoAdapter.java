package com.example.nav.ui.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nav.R;
import com.example.nav.ui.Model.GioHangChoLayHang;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DangGiaoAdapter extends BaseAdapter {
    Context context;
    ArrayList<GioHangChoLayHang> arrayList;

    public DangGiaoAdapter(Context context, ArrayList<GioHangChoLayHang> arrayList) {
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
        public ImageView imageViewhinhanh;
        public TextView txttensp, txtsoluongsp, txtGiasanpham, txtTongthanhtoan;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.dong_dang_giao, null);
            viewHolder.txttensp = (TextView) convertView.findViewById(R.id.textviewtensanphamdanggiaochushop);
            viewHolder.txtGiasanpham = (TextView) convertView.findViewById(R.id.textviewgiasanphamdanggiaochushop);
            viewHolder.txtsoluongsp = (TextView) convertView.findViewById(R.id.textviewsoluongdanggiaochushop);
            viewHolder.txtTongthanhtoan = (TextView) convertView.findViewById(R.id.textviewtongthanhtoandanggiaochushop);
            viewHolder.imageViewhinhanh = (ImageView) convertView.findViewById(R.id.imageviewdanggiaochushop);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        GioHangChoLayHang giohang = arrayList.get(position);
        viewHolder.txttensp.setText(giohang.getTensp());
        viewHolder.txtsoluongsp.setText("X"+giohang.getSoluongsp() +"");
        viewHolder.txtGiasanpham.setText("Giá : " +giohang.getGiasp()/giohang.getSoluongsp() + "");
        viewHolder.txtTongthanhtoan.setText("Tổng thanh toán " + giohang.getGiasp() + "");
        Picasso.get().load(giohang.getHinhanhsp()).into(viewHolder.imageViewhinhanh);

        return convertView;
    }
}
