// Ryan and Bryant
package com.example.messengerapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MessageAdapter extends RecyclerView.Adapter {
    private Conversation convo;
    private Context context;
    private String user;

    public MessageAdapter(Context context, String u) {
        this.convo = new Conversation();
        this.context = context;
        user = u;
    }

    public void update(Conversation convo) {
        this.convo = new Conversation(convo);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_layout, parent, false);
        MessageViewHolder viewHolder = new MessageViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MessageViewHolder viewHolder = (MessageViewHolder) holder;
        Message m = convo.getNthMessage(position);

        if (m.getUser().equals(user)) {
            viewHolder.rightUserID.setText(m.getUser());
            viewHolder.rightMessageContent.setText(m.getText());
            viewHolder.leftUserID.setText("");
            viewHolder.leftMessageContent.setText("");
        }
        else {
            viewHolder.leftUserID.setText(m.getUser());
            viewHolder.leftMessageContent.setText(m.getText());
            viewHolder.rightUserID.setText("");
            viewHolder.rightMessageContent.setText("");
        }
    }

    @Override
    public int getItemCount() {
        return convo.getNumMessages();
    }
}
