package com.kalmahik.firstchat;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ListUserAdapter extends RecyclerView.Adapter<ListUserAdapter.ViewHolder> {
    private List<User> users;
    private OnListItemClickListener clickListener;

    public ListUserAdapter(List<User> users, OnListItemClickListener clickListener) {
        this.users = users;
        this.clickListener = clickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_user, parent, false);
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
        TextView name;
        TextView description;
        TextView image;

        public ViewHolder(View itemView) {
            super(itemView);
            image = (TextView) itemView.findViewById(R.id.image);
            name = (TextView) itemView.findViewById(R.id.name);
            description = (TextView) itemView.findViewById(R.id.description);
        }

        public void bind(User user) {
            Character character = user.getName().charAt(0);
            image.setText(character.toString());
            name.setText(user.getName());
            description.setText(user.getDescription());
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListener.onClick(v, getAdapterPosition());
        }

    }



}
