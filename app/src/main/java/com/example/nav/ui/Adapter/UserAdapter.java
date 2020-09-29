package com.example.nav.ui.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.nav.R;
import com.example.nav.ui.Model.User;

import java.util.ArrayList;

public class UserAdapter extends BaseAdapter {
    Context context;
    ArrayList<User> arrayList;

    public UserAdapter(Context context, ArrayList<User> arrayList) {
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
        public TextView name;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewholer = null;
        if(convertView == null){
            viewholer = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.dong_user, null);
            viewholer.name = (TextView) convertView.findViewById(R.id.textviewusername);
            convertView.setTag(viewholer);
        }else {
            viewholer = (ViewHolder) convertView.getTag();
        }
        User user = arrayList.get(position);
        viewholer.name.setText(user.getName());

        return convertView;
    }
}
