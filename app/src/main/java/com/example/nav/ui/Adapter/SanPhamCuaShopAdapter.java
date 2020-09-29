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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nav.R;
import com.example.nav.ui.home.ChiTietSanPhamActivity;
import com.example.nav.ui.Model.SanPham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class SanPhamCuaShopAdapter extends RecyclerView.Adapter<SanPhamCuaShopAdapter.ItemHolder> implements Filterable {
    Context context;
    List<SanPham> nameList;
    List<SanPham> filteredNameList;

    public SanPhamCuaShopAdapter(Context context, List<SanPham> nameList) {
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
        holder.txtgiasanpham.setText("Gía : " + decimalFormat.format(sanPham.getGiasanpham()) + " đồng");
        Picasso.get().load(sanPham.getHinhanhsanpham()).into(holder.imghinhanhsanpham);

    }

    @Override
    public int getItemCount() {
        return filteredNameList.size();
    }



    public class ItemHolder extends RecyclerView.ViewHolder {
        public ImageView imghinhanhsanpham;
        public TextView txttensanpham, txtgiasanpham;

        public ItemHolder(@NonNull final View itemView) {
            super(itemView);
            txttensanpham = (TextView) itemView.findViewById(R.id.textviewtensanpham);
            txtgiasanpham = (TextView) itemView.findViewById(R.id.textviewgiasanpham);
            imghinhanhsanpham = (ImageView) itemView.findViewById(R.id.imageviewsanpham);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ChiTietSanPhamActivity.class);
                    intent.putExtra("thongtinsanpham", filteredNameList.get(getAdapterPosition()));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
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

