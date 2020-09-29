package com.example.nav.ui.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.example.nav.ui.Model.SanPhamSua;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SuaSanPhamAdapter extends BaseAdapter {
    Context context;
    ArrayList<SanPhamSua> arrayList;
    Activity activity;
    public SuaSanPhamAdapter(Context context, ArrayList<SanPhamSua> arrayList, Activity activity) {
        this.context = context;
        this.arrayList = arrayList;
        this.activity = activity;
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
        public TextView tensp, giasp, khohang, daban, luotthich;
        public Button btnsua, btnxoa;
        public ImageView imghinh;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewholer = null;
        if(convertView == null){
            viewholer = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.dong_sua_san_pham, null);
            viewholer.btnsua = (Button) convertView.findViewById(R.id.buttonsua);
            viewholer.btnxoa = (Button) convertView.findViewById(R.id.buttonxoa);
            viewholer.daban = (TextView) convertView.findViewById(R.id.daban);
            viewholer.giasp = (TextView) convertView.findViewById(R.id.textviewgiasp);
            viewholer.imghinh = (ImageView) convertView.findViewById(R.id.imageviewhinhsp);
            viewholer.khohang = (TextView) convertView.findViewById(R.id.khohang);
            viewholer.luotthich = (TextView) convertView.findViewById(R.id.luotthich);
            viewholer.tensp = (TextView) convertView.findViewById(R.id.textviewtensp);
            convertView.setTag(viewholer);

        }else {
            viewholer = (ViewHolder) convertView.getTag();
        }
        SanPhamSua sanPhamSua = arrayList.get(position);
        viewholer.tensp.setText(sanPhamSua.getTensanpham());
        viewholer.luotthich.setText(sanPhamSua.getLuotthich() + "");
        viewholer.khohang.setText(sanPhamSua.getSoluong()+"");
        viewholer.daban.setText(sanPhamSua.getSoluongdaban() + "");
        viewholer.giasp.setText(sanPhamSua.getGiasanpham()+"");
        viewholer.btnxoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "xoa", Toast.LENGTH_SHORT).show();
            }
        });
        viewholer.btnsua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "sua", Toast.LENGTH_SHORT).show();
            }
        });
        Picasso.get().load(sanPhamSua.getHinhanhsanpham()).into(viewholer.imghinh);
        viewholer.btnsua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SuaActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("idproduct", arrayList.get(position).getIdsanpham());
                intent.putExtra("price", arrayList.get(position).getGiasanpham());
                intent.putExtra("number", arrayList.get(position).getSoluong());
                context.startActivity(intent);
            }
        });
        viewholer.btnxoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder alert = new AlertDialog.Builder(context);
                alert.setMessage("Bạn có muốn xóa sản phẩm này không ? ");
                alert.setTitle("");
                alert.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        RequestQueue requestQueue = Volley.newRequestQueue(context);
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, MainActivity.urldeleteproduct,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        if(response.equals("success")){
                                            Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                                            arrayList.remove(position);
                                            notifyDataSetChanged();
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
                                map.put("idproduct", String.valueOf(arrayList.get(position).getIdsanpham()));
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
