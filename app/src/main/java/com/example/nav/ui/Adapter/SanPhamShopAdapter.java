package com.example.nav.ui.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nav.R;
import com.example.nav.ui.Model.SanPhamShop;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SanPhamShopAdapter extends BaseAdapter {
    Context context;
    ArrayList<SanPhamShop> arrayList;

    public SanPhamShopAdapter(Context context, ArrayList<SanPhamShop> arrayList) {
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
        public TextView txttenloai, txtsoluong;
        public ImageView imageAnhLoai;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.dong_danh_muc_shop, null);
            viewHolder.imageAnhLoai = (ImageView) convertView.findViewById(R.id.imagviewshopdanhmuc);
            viewHolder.txtsoluong = (TextView) convertView.findViewById(R.id.textviewsoluongshop);
            viewHolder.txttenloai = (TextView) convertView.findViewById(R.id.textviewtenloaishop);
            convertView.setTag(viewHolder);

        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        SanPhamShop sp = (SanPhamShop)arrayList.get(position);
        viewHolder.txttenloai.setText(sp.getTenloai());
        viewHolder.txtsoluong.setText(sp.getSoluong()+ " Sản phẩm");
        Picasso.get().load(sp.getHinhanhloai()).into(viewHolder.imageAnhLoai);

        return convertView;
    }
}
