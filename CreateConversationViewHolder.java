// Ryan
package com.example.messengerapp;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class CreateConversationViewHolder extends RecyclerView.ViewHolder {
    public TextView userId;

    public CreateConversationViewHolder(View view) {
        super(view);
        userId = view.findViewById(R.id.convoUserID);
    }
}
