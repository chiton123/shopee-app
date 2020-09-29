package com.example.nav.ui.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.nav.MainActivity;
import com.example.nav.R;
import com.example.nav.ui.Model.GioHangChoLayHang;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DangGiaoNguoiMuaAdapter extends BaseAdapter {
    Context context;
    ArrayList<GioHangChoLayHang> arrayList;

    public DangGiaoNguoiMuaAdapter(Context context, ArrayList<GioHangChoLayHang> arrayList) {
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
        public Button btnxacnhan, btnyeucautrahang;

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.dong_danggiaonguoimua, null);
            viewHolder.txttensp = (TextView) convertView.findViewById(R.id.textviewtensanphamdanggiaonguoimua);
            viewHolder.txtGiasanpham = (TextView) convertView.findViewById(R.id.textviewgiasanphamdanggiaonguoimua);
            viewHolder.txtsoluongsp = (TextView) convertView.findViewById(R.id.textviewsoluongdanggiaonguoimua);
            viewHolder.txtTongthanhtoan = (TextView) convertView.findViewById(R.id.textviewtongthanhtoandanggiaonguoimua);
            viewHolder.imageViewhinhanh = (ImageView) convertView.findViewById(R.id.imageviewdanggiaonguoimua);
            viewHolder.btnxacnhan = (Button) convertView.findViewById(R.id.buttondaduocnhanhangnguoimua);
            viewHolder.btnyeucautrahang = (Button) convertView.findViewById(R.id.buttonyeucautrahang);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final GioHangChoLayHang giohang = arrayList.get(position);
        viewHolder.txttensp.setText(giohang.getTensp());
        viewHolder.txtsoluongsp.setText("X"+giohang.getSoluongsp() +"");
        viewHolder.txtGiasanpham.setText("Giá : " +giohang.getGiasp()/giohang.getSoluongsp() + "");
        viewHolder.txtTongthanhtoan.setText("Tổng thanh toán " + giohang.getGiasp() + "");
        Picasso.get().load(giohang.getHinhanhsp()).into(viewHolder.imageViewhinhanh);
        viewHolder.btnxacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                alert.setMessage("Tôi xác nhận đã nhận được hàng, không có yêu cầu đổi trả");

                alert.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        RequestQueue requestQueue = Volley.newRequestQueue(context);
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, MainActivity.urldagiaonguoimua,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        if(response.equals("success")){
                                            Toast.makeText(context,"Đã giao thành công", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(context,error.toString(), Toast.LENGTH_LONG).show();
                                    }
                                }){
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String,String> map = new HashMap<>();
                                map.put("idchitietdonhang", String.valueOf(giohang.getIdchitietdonhang()));
                                return map;
                            }
                        };
                        requestQueue.add(stringRequest);
                    }
                });
                alert.setNegativeButton("Chưa nhận", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alert.show();
            }
        });
        viewHolder.btnyeucautrahang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return convertView;
    }

}
