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

public class DaHuyAdapter extends BaseAdapter {
    Context context;
    ArrayList<GioHangChoLayHang> arrayList;

    public DaHuyAdapter(Context context, ArrayList<GioHangChoLayHang> arrayList) {
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
        public TextView txttensp, txtsoluong, txtgia, txttongthanhtoan;
        public Button btnmualai;
        public ImageView imageView;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.dong_da_huy, null);
            viewHolder.txtgia = (TextView) convertView.findViewById(R.id.textviewgiasanphamdahuy);
            viewHolder.txtsoluong = (TextView) convertView.findViewById(R.id.textviewsoluongdahuy);
            viewHolder.txttensp = (TextView) convertView.findViewById(R.id.textviewtensanphamdahuy);
            viewHolder.txttongthanhtoan = (TextView) convertView.findViewById(R.id.textviewtongthanhtoandahuy);
            viewHolder.btnmualai = (Button) convertView.findViewById(R.id.buttonmualai);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.imageviewdahuy);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        GioHangChoLayHang giohang = arrayList.get(position);
        viewHolder.txttensp.setText(giohang.getTensp());
        viewHolder.txtsoluong.setText("X"+giohang.getSoluongsp());
        viewHolder.txtgia.setText("Giá: "  + giohang.getGiasp()/giohang.getSoluongsp() );
        viewHolder.txttongthanhtoan.setText("Tổng thanh toán: "+ giohang.getGiasp());
        Picasso.get().load(giohang.getHinhanhsp()).into(viewHolder.imageView);
        viewHolder.btnmualai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                alert.setMessage("Bạn có muốn mua lại sản phẩm không ?");
                alert.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        RequestQueue requestQueue = Volley.newRequestQueue(context);
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, MainActivity.urlmualai,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        if(response.equals("success")){
                                            Toast.makeText(context, "Mua lại thành công", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
                                    }
                                }){
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String,String> map = new HashMap<>();
                                map.put("idchitietdonhang",String.valueOf(arrayList.get(position).getIdchitietdonhang()));
                                return map;
                            }
                        };
                        requestQueue.add(stringRequest);
                    }
                });
                alert.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alert.show();
            }
        });
        return convertView;
    }
}
