package com.kalmahik.firstchat;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class ListContactAdapter extends RecyclerView.Adapter<ListContactAdapter.ViewHolder> {
    private ArrayList<User> users;
    private OnListItemClickListener clickListener;

    public ListContactAdapter(ArrayList<User> users, OnListItemClickListener clickListener) {
        this.users = users;
        this.clickListener = clickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_contact, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(users.get(position));
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView avatar;
        TextView name;
        TextView status;

        public ViewHolder(View itemView) {
            super(itemView);
            avatar = (TextView) itemView.findViewById(R.id.avatar);
            name = (TextView) itemView.findViewById(R.id.name);
            status = (TextView) itemView.findViewById(R.id.status);
        }

        public void bind(User user) {
            Character nameLogo = user.getName().charAt(0);
            avatar.setText(nameLogo.toString());
            name.setText(user.getName());
            status.setText(user.getStatus());
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            clickListener.onClick(v, getAdapterPosition());
        }

    }



}
