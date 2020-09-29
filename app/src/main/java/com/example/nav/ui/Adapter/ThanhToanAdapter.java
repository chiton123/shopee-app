package com.example.nav.ui.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nav.R;
import com.example.nav.ui.Model.GioHang;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ThanhToanAdapter extends BaseAdapter {
    Context context;
    ArrayList<GioHang> arrayList;

    public ThanhToanAdapter(Context context, ArrayList<GioHang> arrayList) {
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
        TextView txttensp, txtsoluong, txtgiatridonhang;
        ImageView imageViewhinhanh;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.dong_thanh_toan, null);
            viewHolder.txttensp = (TextView) convertView.findViewById(R.id.textviewtensanphamthanhtoan);
            viewHolder.txtsoluong = (TextView) convertView.findViewById(R.id.textviewsoluongthanhtoan);
            viewHolder.txtgiatridonhang = (TextView) convertView.findViewById(R.id.textviewgiadonhangthanhtoan);
            viewHolder.imageViewhinhanh = (ImageView) convertView.findViewById(R.id.imageviewhinhanhthanhtoan);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        GioHang gioHang = arrayList.get(position);
        viewHolder.txttensp.setText(gioHang.getTensp());
        viewHolder.txtsoluong.setText("X"+gioHang.getSoluongsp());
        viewHolder.txtgiatridonhang.setText("Thanh toán :" + gioHang.getGiasp());
        Picasso.get().load(gioHang.getHinhanhsp()).into(viewHolder.imageViewhinhanh);


        return convertView;
    }
}
