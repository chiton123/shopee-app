package com.example.nav.ui.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nav.R;
import com.example.nav.ui.Model.NganHang;

import java.util.HashMap;
import java.util.List;

public class PhuongThucThanhToanAdapter extends BaseExpandableListAdapter {
    Context context;
    List<NganHang> listHeader;
    HashMap<NganHang, List<NganHang>> listdataChild;

    public PhuongThucThanhToanAdapter(Context context, List<NganHang> listHeader, HashMap<NganHang, List<NganHang>> listdataChild) {
        this.context = context;
        this.listHeader = listHeader;
        this.listdataChild = listdataChild;
    }

    @Override
    public int getGroupCount() {
        return listHeader.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return listdataChild.get(listHeader.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return listHeader.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return listdataChild.get(listHeader.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String ten = listHeader.get(groupPosition).getTennganhang();
        int hinh = listHeader.get(groupPosition).getHinhnganhang();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.group_view, null);
        TextView txtten = (TextView) convertView.findViewById(R.id.textviewgroupview);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.imageviewgroupview);
        txtten.setText(ten);
        imageView.setImageResource(hinh);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        String ten = listdataChild.get(listHeader.get(groupPosition)).get(childPosition).getTennganhang();
        int img = listdataChild.get(listHeader.get(groupPosition)).get(childPosition).getHinhnganhang();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.group_child, null);
        TextView txtten = (TextView) convertView.findViewById(R.id.textviewgroupchild);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.imageviewgroupchild);
        txtten.setText(ten);
        imageView.setImageResource(img);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
