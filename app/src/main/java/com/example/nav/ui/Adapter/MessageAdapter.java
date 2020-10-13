package com.example.nav.ui.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nav.MainActivity;
import com.example.nav.R;
import com.example.nav.ui.Model.Chat;

import java.util.ArrayList;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ItemHoler> {
    public static final int MSG_TYPE_LEFT = 0;
    public static final int MSG_TYPE_RIGHT = 1;
    private Context context;
    private ArrayList<Chat> mchat;

    public MessageAdapter(Context context, ArrayList<Chat> mchat) {
        this.context = context;
        this.mchat = mchat;
    }

    @NonNull
    @Override
    public ItemHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == MSG_TYPE_RIGHT){
            View v = LayoutInflater.from(context).inflate(R.layout.chat_item_right, parent, false);
            ItemHoler itemHoler = new ItemHoler(v);
            return itemHoler;
        }else {
            View v = LayoutInflater.from(context).inflate(R.layout.chikd_item_left, parent, false);
            ItemHoler itemHoler = new ItemHoler(v);
            return itemHoler;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHoler holder, int position) {
        Chat chat = mchat.get(position);
        holder.txt_send.setText(chat.getMessage());

    }

    @Override
    public int getItemCount() {
        return mchat.size();
    }


    public class ItemHoler extends RecyclerView.ViewHolder{
        public TextView txt_send;
        public ItemHoler(@NonNull View itemView) {
            super(itemView);
            txt_send = (TextView) itemView.findViewById(R.id.show_message);

        }
    }

    @Override
    public int getItemViewType(int position) {
        if(mchat.get(position).getSender() == MainActivity.iduser){
            return MSG_TYPE_RIGHT;
        }else {
            return MSG_TYPE_LEFT;
        }
    }
}
