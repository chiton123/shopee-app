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
import com.example.nav.ui.shop.ChuanBiHangActivity;
import com.example.nav.ui.Model.GioHangChoLayHang;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ChoLayHangAdapter extends BaseAdapter {
    Context context;
    ArrayList<GioHangChoLayHang> arrayList;

    public ChoLayHangAdapter(Context context, ArrayList<GioHangChoLayHang> arrayList) {
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
        public TextView txttensp, txtsoluongsp, txtGiasanpham, txtTongthanhtoan, txttennguoimua;
        public Button btnchuanbihang;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.dong_cholayhang_chushop, null);
            viewHolder.imageViewhinhanh = (ImageView) convertView.findViewById(R.id.imageviewcholayhangchushop);
            viewHolder.txttensp = (TextView) convertView.findViewById(R.id.textviewtensanphancholayhangchushop);
            viewHolder.txtGiasanpham = (TextView) convertView.findViewById(R.id.textviewgiatridonhangchushop);
            viewHolder.txtsoluongsp = (TextView) convertView.findViewById(R.id.textviewsoluongcholayhangchushop);
            viewHolder.txtTongthanhtoan = (TextView) convertView.findViewById(R.id.textviewtongthanhtoancholayhangchushop);
            viewHolder.txttennguoimua = (TextView)convertView.findViewById(R.id.textviewtennguoimuachushop);
            viewHolder.btnchuanbihang = (Button) convertView.findViewById(R.id.buttonchuanbihang);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        GioHangChoLayHang gioHang = arrayList.get(position);
        viewHolder.txttensp.setText(gioHang.getTensp());
        viewHolder.txtsoluongsp.setText("X"+gioHang.getSoluongsp() + "");
        viewHolder.txtGiasanpham.setText("Giá : "+gioHang.getGiasp()/gioHang.getSoluongsp() + "");
        viewHolder.txtTongthanhtoan.setText("Tổng thanh toán: "+ gioHang.getGiasp());
        viewHolder.txttennguoimua.setText(gioHang.getTennguoimua());
        Picasso.get().load(gioHang.getHinhanhsp()).into(viewHolder.imageViewhinhanh);
        viewHolder.btnchuanbihang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ChuanBiHangActivity.class);
                intent.putExtra("sanpham", arrayList.get(position));
                context.startActivity(intent);
            }
        });

        return convertView;
    }
}
