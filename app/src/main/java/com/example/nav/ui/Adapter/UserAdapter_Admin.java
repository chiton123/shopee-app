package com.example.nav.ui.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.nav.R;
import com.example.nav.ui.Model.Admin_User;

import java.util.ArrayList;

public class UserAdapter_Admin extends BaseAdapter {
    Context context;
    ArrayList<Admin_User> arrayList;

    public UserAdapter_Admin(Context context, ArrayList<Admin_User> arrayList) {
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
        public TextView txtname, txtemail;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.user_item, null);
            viewHolder.txtemail = (TextView) convertView.findViewById(R.id.email);
            viewHolder.txtname = (TextView) convertView.findViewById(R.id.name);
            convertView.setTag(viewHolder);

        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Admin_User adminUser = arrayList.get(position);
        viewHolder.txtname.setText(adminUser.getName());
        viewHolder.txtemail.setText(adminUser.getEmail());

        return convertView;
    }
}
