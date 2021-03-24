// Ryan
package com.example.messengerapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CreateConversationAdapter extends RecyclerView.Adapter {
    private ArrayList<String> userList;

    public CreateConversationAdapter(Context context) {
        userList = new ArrayList<String>();
    }

    public void update(ArrayList<String> newUserList) {
        userList.clear();
        userList.addAll(newUserList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.convo_user_layout, parent, false);
        CreateConversationViewHolder viewHolder = new CreateConversationViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        CreateConversationViewHolder viewHolder = (CreateConversationViewHolder) holder;
        viewHolder.userId.setText(userList.get(position));
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }
}
