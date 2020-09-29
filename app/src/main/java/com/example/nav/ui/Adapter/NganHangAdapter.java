package com.example.nav.ui.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.nav.R;
import com.example.nav.ui.Model.NganHang;

import java.util.ArrayList;

public class NganHangAdapter extends BaseAdapter {
    Context context;
    ArrayList<NganHang> arrayList;

    public NganHangAdapter(Context context, ArrayList<NganHang> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    public class ViewHoler{
        public TextView txttennganhang;
        public ImageView imageViewnganhang;
        public LinearLayout linearLayout;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHoler viewHoler = null;
        if(convertView == null){
            viewHoler = new ViewHoler();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.dong_ngan_hang, null);
            viewHoler.txttennganhang = (TextView) convertView.findViewById(R.id.textviewtengnganhang);
            viewHoler.imageViewnganhang = (ImageView) convertView.findViewById(R.id.imageviewhinhnganhang);
            viewHoler.linearLayout = (LinearLayout) convertView.findViewById(R.id.linearnganhang);
            convertView.setTag(viewHoler);
        }else {
            viewHoler = (ViewHoler) convertView.getTag();
        }
        NganHang nganHang = arrayList.get(position);
        viewHoler.txttennganhang.setText(nganHang.getTennganhang());
//        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), nganHang.getHinhnganhang());
//        Bitmap oldBitmap = ((BitmapDrawable)viewHoler.imageViewnganhang.getDrawable()).getBitmap();
//        viewHoler.imageViewnganhang.setImageBitmap(bitmap);
//        oldBitmap.recycle();
        viewHoler.imageViewnganhang.setImageResource(nganHang.getHinhnganhang());
        final ViewHoler finalViewHoler = viewHoler;


        return convertView;
    }

}
