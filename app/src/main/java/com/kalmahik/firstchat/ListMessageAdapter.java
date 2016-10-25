package com.kalmahik.firstchat;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


public class ListMessageAdapter extends RecyclerView.Adapter<ListMessageAdapter.ViewHolder> {
    private ArrayList<Message> messages;
    private OnListItemClickListener clickListener;
    private final int sender = 0;
    private final int receiver = 1;


    public ListMessageAdapter(ArrayList<Message> messages, OnListItemClickListener clickListener) {
        this.messages = messages;
        this.clickListener = clickListener;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        switch (viewType) {
            case sender:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_message_sender, parent, false);
                return new ViewHolder(v);
            case receiver:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_message_receiver, parent, false);
                return new ViewHolder(v);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(messages.get(position));
    }

    @Override
    public int getItemViewType(int position) {
        if (messages != null) {
            Message message = messages.get(position);
            Character choice = message.getSender().charAt(0);
            String str = choice + "";
            int i = Integer.valueOf(str);
            return i;
        }
        return 0;
    }


    @Override
    public int getItemCount() {
        return messages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView avatar;
        TextView sender;
        TextView created;
        TextView body;

        public ViewHolder(View itemView) {
            super(itemView);
            //sender = (TextView) itemView.findViewById(R.id.sender);
            body = (TextView) itemView.findViewById(R.id.body);
            created = (TextView) itemView.findViewById(R.id.created);
        }

        public void bind(Message message) {
            //Character nameLogo = message.getTitle().charAt(0);
            //avatar.setText(nameLogo.toString());
            //sender.setText(message.getSender());
            body.setText(message.getBody());
            Long l = message.getCreated();
            String str = Long.toString(l);
            created.setText(str);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListener.onClick(v, getAdapterPosition());
        }

    }

}
