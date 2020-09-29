package com.example.nav.ui.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.nav.R;
import com.example.nav.ui.Model.SanPham;
import com.example.nav.ui.Model.ThuongHieu;

import java.util.ArrayList;
import java.util.List;

public class ThuongHieuAdapter extends BaseAdapter implements Filterable {
    Context context;
    List<ThuongHieu> nameList;
    List<ThuongHieu> filterNameList;
    Activity activity;

    public ThuongHieuAdapter(Context context, List<ThuongHieu> nameList, Activity activity) {
        this.context = context;
        this.nameList = nameList;
        this.filterNameList = nameList;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return filterNameList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    public class ViewHoler{
        public TextView brandname;
        public RelativeLayout layout;
    }
    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        ViewHoler viewHoler = null;
        if(convertView == null){
            viewHoler = new ViewHoler();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.dong_thuong_hieu, null);
            viewHoler.brandname = (TextView) convertView.findViewById(R.id.tenthuonghieu);
            viewHoler.layout = (RelativeLayout) convertView.findViewById(R.id.layout);
            convertView.setTag(viewHoler);

        }else {
            viewHoler = (ViewHoler) convertView.getTag();
        }
        ThuongHieu thuongHieu = filterNameList.get(position);
        viewHoler.brandname.setText(thuongHieu.getName());
        viewHoler.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("name", filterNameList.get(position).getName());
                intent.putExtra("id", filterNameList.get(position).getId());
                activity.setResult(234, intent);
                activity.finish();
            }
        });

        return convertView;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charSequenceString = constraint.toString();
                if(charSequenceString.isEmpty()){
                    filterNameList = nameList;
                }else {
                    List<ThuongHieu> filterList = new ArrayList<>();
                    for(ThuongHieu name : nameList){
                        if(name.getName().toLowerCase().contains(charSequenceString.toLowerCase())){
                            filterList.add(name);
                        }
                        filterNameList = filterList;
                    }

                }
                FilterResults results = new FilterResults();
                results.values = filterNameList;
                return  results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filterNameList = (List<ThuongHieu>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
