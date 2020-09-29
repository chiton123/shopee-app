package com.example.nav.ui.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.nav.MainActivity;
import com.example.nav.R;
import com.example.nav.ui.home.ChiTietSanPhamActivity;
import com.example.nav.ui.Model.SanPham;
import com.example.nav.ui.home.DangNhapActivity;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SanPhamAdapter extends RecyclerView.Adapter<SanPhamAdapter.ItemHolder> implements Filterable {
    Context context;
    List<SanPham> nameList;
    List<SanPham> filteredNameList;

    public SanPhamAdapter(Context context, List<SanPham> nameList) {
        super();
        this.context = context;
        this.nameList = nameList;
        this.filteredNameList = nameList;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dong_san_pham, null);
        ItemHolder itemHolder = new ItemHolder(v);
        return itemHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        SanPham sanPham = filteredNameList.get(position);
        holder.txttensanpham.setText(sanPham.getTensanpham());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txtgiasanpham.setText("Giá : " + decimalFormat.format(sanPham.getGiasanpham()) + " đồng");
        Picasso.get().load(sanPham.getHinhanhsanpham()).into(holder.imghinhanhsanpham);
        holder.txtphantramgiamgia.setText(sanPham.getPhantramkhuyenmai() + "%");
        holder.txtsoluongdaban.setText(sanPham.getSoluongdaban() + "");

    }

    @Override
    public int getItemCount() {
        return filteredNameList.size();
    }


    public class ItemHolder extends RecyclerView.ViewHolder {
        public ImageView imghinhanhsanpham;
        public TextView txttensanpham, txtgiasanpham, txtsoluongdaban, txttensoluong, txtphantramgiamgia, txttengiamgia;
        public LikeButton likeButton;
        public ItemHolder(@NonNull final View itemView) {
            super(itemView);
            txttensanpham = (TextView) itemView.findViewById(R.id.textviewtensanpham);
            txtgiasanpham = (TextView) itemView.findViewById(R.id.textviewgiasanpham);
            imghinhanhsanpham = (ImageView) itemView.findViewById(R.id.imageviewsanpham);
            txttensoluong = (TextView) itemView.findViewById(R.id.textviewtensoluong);
            txtsoluongdaban = (TextView) itemView.findViewById(R.id.textviewsoluongdaban);
            txtphantramgiamgia = (TextView) itemView.findViewById(R.id.textviewsophantramgiamgia);
            txttengiamgia = (TextView) itemView.findViewById(R.id.textviewtengiamgia);
            likeButton = (LikeButton) itemView.findViewById(R.id.like);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ChiTietSanPhamActivity.class);
                    intent.putExtra("thongtinsanpham", filteredNameList.get(getAdapterPosition()));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });
            likeButton.setOnLikeListener(new OnLikeListener() {
                @Override
                public void liked(LikeButton likeButton) {
                    if(MainActivity.kiemtralogin == 0){
                        Intent intent = new Intent(context, DangNhapActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }else {
                        RequestQueue requestQueue = Volley.newRequestQueue(context);
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, MainActivity.urlproductlike,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        Toast.makeText(context, "Đã thich", Toast.LENGTH_SHORT).show();
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
                                map.put("iduser", String.valueOf(MainActivity.iduser));
                                map.put("idproduct", String.valueOf(nameList.get(getAdapterPosition()).getIdsanpham()));
                                return map;
                            }
                        };
                        requestQueue.add(stringRequest);
                    }

                }

                @Override
                public void unLiked(LikeButton likeButton) {
                    RequestQueue requestQueue = Volley.newRequestQueue(context);
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, MainActivity.urlproductunlike,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
//                                    Toast.makeText(context, "Bỏ thich", Toast.LENGTH_SHORT).show();
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
//                                    Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
                                }
                            }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String,String> map = new HashMap<>();
                            map.put("iduser", String.valueOf(MainActivity.iduser));
                            map.put("idproduct", String.valueOf(nameList.get(getAdapterPosition()).getIdsanpham()));
                            return map;
                        }
                    };
                    requestQueue.add(stringRequest);
                }
            });

        }
    }
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charSequenceString = constraint.toString();
                if(charSequenceString.isEmpty()){
                    filteredNameList = nameList;
                }else {
                    List<SanPham> filteredList = new ArrayList<>();
                    for(SanPham name : nameList){
                        if(name.getTensanpham().toLowerCase().contains(charSequenceString.toLowerCase())){
                            filteredList.add(name);
                        }
                        filteredNameList = filteredList;
                    }
                }
                FilterResults results = new FilterResults();
                results.values = filteredNameList;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredNameList = (List<SanPham>) results.values;
                notifyDataSetChanged();
             }
        };
    }


    }

