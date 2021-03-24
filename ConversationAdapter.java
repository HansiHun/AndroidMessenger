// Ryan
package com.example.messengerapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ConversationAdapter extends RecyclerView.Adapter {
    private ArrayList<Conversation> conversations;
    private OnClickListener o;

    public ConversationAdapter(Context context) {
        conversations = new ArrayList<Conversation>();
        o = (OnClickListener) context;
    }

    public void update(ArrayList<Conversation> convos) {
        conversations.clear();
        conversations.addAll(convos);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.conversation_layout, parent, false);
        ConversationViewHolder viewHolder = new ConversationViewHolder(v, o);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ConversationViewHolder viewHolder = (ConversationViewHolder) holder;
        Conversation c = conversations.get(position);
        viewHolder.convoName.setText(c.getName());
        Message msg = c.getNthMessage(c.getNumMessages() - 1);
        String msgText = msg.getText();
        if (msgText.length() >= 25) {
            msgText = msgText.substring(0, 25) + "...";
        }
        viewHolder.convoPreview.setText(msg.getUser() + ": " + msgText);
    }

    @Override
    public int getItemCount() {
        return conversations.size();
    }
}
