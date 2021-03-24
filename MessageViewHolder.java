// Hansi
package com.example.messengerapp;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class MessageViewHolder extends RecyclerView.ViewHolder {
    public TextView leftUserID;
    public TextView leftMessageContent;
    public TextView rightUserID;
    public TextView rightMessageContent;

    public MessageViewHolder(View view) {
        super(view);

        leftUserID = (TextView) view.findViewById(R.id.messageUserID);
        leftMessageContent = (TextView) view.findViewById(R.id.messageContent);
        rightUserID = (TextView) view.findViewById(R.id.messageUserID2);
        rightMessageContent = (TextView) view.findViewById(R.id.messageContent2);
    }
}
