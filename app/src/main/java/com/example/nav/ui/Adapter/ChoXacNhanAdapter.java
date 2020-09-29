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

public class ChoXacNhanAdapter extends BaseAdapter {
    Context context;
    ArrayList<GioHangChoLayHang> arrayList;

    public ChoXacNhanAdapter(Context context, ArrayList<GioHangChoLayHang> arrayList) {
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
        public ImageView imageViewHinhAnh;
        public TextView txtTensp, txtSoluongsp, txtgiasanpham, txttongthanhtoan;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.dong_choxacnhan, null);
            viewHolder.imageViewHinhAnh = (ImageView) convertView.findViewById(R.id.imageviewchoxacnhan);
            viewHolder.txtTensp = (TextView) convertView.findViewById(R.id.textviewtensanphamchoxacnhan);
            viewHolder.txtgiasanpham = (TextView) convertView.findViewById(R.id.textviewgiasanphamChoXacnhan);
            viewHolder.txtSoluongsp = (TextView) convertView.findViewById(R.id.textviewsoluongchoxacnhan);
            viewHolder.txttongthanhtoan = (TextView) convertView.findViewById(R.id.textviewtongthanhtoanChoxacnhan);
            viewHolder.imageViewHinhAnh.setFocusable(false);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        GioHangChoLayHang gioHang = arrayList.get(position);
        viewHolder.txtTensp.setText(gioHang.getTensp());
        viewHolder.txtSoluongsp.setText("X"+gioHang.getSoluongsp() + "");
        viewHolder.txtgiasanpham.setText("Giá : "+gioHang.getGiasp()/gioHang.getSoluongsp() + "");
        viewHolder.txttongthanhtoan.setText("Tổng thanh toán: "+ gioHang.getGiasp());
        Picasso.get().load(gioHang.getHinhanhsp()).into(viewHolder.imageViewHinhAnh);
        return convertView;
    }
}
