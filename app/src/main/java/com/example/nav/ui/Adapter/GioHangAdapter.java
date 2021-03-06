package com.example.nav.ui.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nav.MainActivity;
import com.example.nav.R;
import com.example.nav.ui.Model.GioHang;
import com.example.nav.ui.Sale_purchase.GioHangActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class GioHangAdapter extends BaseAdapter {
    Context context;
    ArrayList<GioHang> arrayList;

    public GioHangAdapter(Context context, ArrayList<GioHang> arrayList) {
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
        ImageView imggiohang;
        TextView tengiohang, giagiohang, soluongconlai;
        Button btnminus, btnvalue, btnplus;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.dong_gio_hang, null);
            viewHolder.tengiohang = (TextView) convertView.findViewById(R.id.textviewtenGioHang);
            viewHolder.giagiohang = (TextView) convertView.findViewById(R.id.textviewgiaGioHang);
            viewHolder.imggiohang = (ImageView) convertView.findViewById(R.id.imageviewgiohang);
            viewHolder.btnminus = (Button) convertView.findViewById(R.id.buttonMinus);
            viewHolder.btnplus = (Button) convertView.findViewById(R.id.buttonPlus);
            viewHolder.btnvalue = (Button) convertView.findViewById(R.id.buttonValue);
            viewHolder.soluongconlai = (TextView) convertView.findViewById(R.id.soluong);
            viewHolder.btnminus.setFocusable(false);
            viewHolder.btnvalue.setFocusable(false);
            viewHolder.btnplus.setFocusable(false);
            viewHolder.imggiohang.setFocusable(false);
            viewHolder.giagiohang.setFocusable(false);
            convertView.setTag(viewHolder);

        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        GioHang gioHang = arrayList.get(position);
        viewHolder.tengiohang.setText(gioHang.getTensp());
        viewHolder.giagiohang.setText(gioHang.getGiasp() + "");
        viewHolder.btnvalue.setText(gioHang.getSoluongsp()+"");
        viewHolder.soluongconlai.setText("Số lượng còn lại: " + arrayList.get(position).getSoluongtoida() );
        Picasso.get().load(gioHang.getHinhanhsp()).into(viewHolder.imggiohang);
     //   int sl = Integer.parseInt(viewHolder.btnvalue.getText().toString());
        final ViewHolder finalViewHolder = viewHolder;
        final ViewHolder finalViewHolder1 = viewHolder;
        viewHolder.btnplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int soluonghientai = MainActivity.manggiohang.get(position).getSoluongsp();
                if(soluonghientai == arrayList.get(position).getSoluongtoida()){
                    Toast.makeText(context, "Shop không có đủ hàng", Toast.LENGTH_SHORT).show();
                }else {
                    int soluongmoinhat = Integer.parseInt(finalViewHolder.btnvalue.getText().toString()) + 1;
                    long giahientai = MainActivity.manggiohang.get(position).getGiasp();
                    MainActivity.manggiohang.get(position).setSoluongsp(soluongmoinhat);
                    long giamoinhat = (giahientai * soluongmoinhat) / soluonghientai;
                    MainActivity.manggiohang.get(position).setGiasp(giamoinhat);
                    GioHangActivity.EventUntil();
                    finalViewHolder.btnvalue.setText(soluongmoinhat + "");
                    finalViewHolder.giagiohang.setText(giamoinhat + "");
                }

            }
        });
        final ViewHolder finalViewHolder2 = viewHolder;
        viewHolder.btnminus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int soluonghientai = MainActivity.manggiohang.get(position).getSoluongsp();
                if(soluonghientai == 1){
                    Toast.makeText(context, "Khổng thể giảm được nữa!", Toast.LENGTH_SHORT).show();
                }else {
                    int soluongmoinhat = Integer.parseInt(finalViewHolder.btnvalue.getText().toString()) - 1;
                    long giahientai = MainActivity.manggiohang.get(position).getGiasp();
                    MainActivity.manggiohang.get(position).setSoluongsp(soluongmoinhat);
                    long giamoinhat = (giahientai * soluongmoinhat) / soluonghientai;
                    MainActivity.manggiohang.get(position).setGiasp(giamoinhat);
                    GioHangActivity.EventUntil();
                    finalViewHolder2.btnvalue.setText(soluongmoinhat + "");
                    finalViewHolder2.giagiohang.setText(giamoinhat + "");
                }


            }
        });

        return convertView;
    }
}
