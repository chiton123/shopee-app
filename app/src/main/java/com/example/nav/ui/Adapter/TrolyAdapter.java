package com.example.nav.ui.Adapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nav.R;
import com.example.nav.ui.Model.TroLy;

import java.util.ArrayList;

public class TrolyAdapter extends BaseAdapter {
    Context context;
    ArrayList<TroLy> arrayList;
    int layout;

    public TrolyAdapter(Context context, ArrayList<TroLy> arrayList, int layout) {
        this.context = context;
        this.arrayList = arrayList;
        this.layout = layout;
    }
    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
        super.registerDataSetObserver(observer);
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
    public class ViewHoler{
        public TextView ten;
        public ImageView hinh;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHoler viewHoler = null;
        if(convertView == null){
            viewHoler = new ViewHoler();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);
            viewHoler.ten = (TextView) convertView.findViewById(R.id.textviewten);
            viewHoler.hinh = (ImageView) convertView.findViewById(R.id.imageviewtroly);
            convertView.setTag(viewHoler);

        }else {
            viewHoler = (ViewHoler) convertView.getTag();
        }
        TroLy troLy = arrayList.get(position);
        viewHoler.ten.setText(troLy.getTen());
        viewHoler.hinh.setImageResource(troLy.getHinh());

        return convertView;
    }
}
