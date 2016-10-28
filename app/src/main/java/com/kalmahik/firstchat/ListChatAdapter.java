package com.kalmahik.firstchat;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


public class ListChatAdapter extends RecyclerView.Adapter<ListChatAdapter.ViewHolder> {
    private ArrayList<Chat> chats;
    private OnListItemClickListener clickListener;

    public ListChatAdapter(ArrayList<Chat> chats, OnListItemClickListener clickListener) {
        this.chats = chats;
        this.clickListener = clickListener;
    }

    @Override
    public ListChatAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_chat, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(chats.get(position));
    }

    @Override
    public int getItemCount() {
        return chats.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView image;
        TextView title;
        TextView lastMessage;
        TextView updated;

        public ViewHolder(View itemView) {
            super(itemView);
            image = (TextView) itemView.findViewById(R.id.image);
            title = (TextView) itemView.findViewById(R.id.title);
            lastMessage = (TextView) itemView.findViewById(R.id.last_message);
            updated = (TextView) itemView.findViewById(R.id.updated);
        }

        public void bind(Chat chat) {
            Character character = chat.getTitle().charAt(0);
            image.setText(character.toString());
            title.setText(chat.getTitle());
            lastMessage.setText(chat.getLastMessage());
            Long l = chat.getUpdated();
            String str = Long.toString(l);
            updated.setText(str);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListener.onClick(v, getAdapterPosition());
        }
    }
}
