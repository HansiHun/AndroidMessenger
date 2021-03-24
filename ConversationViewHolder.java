// Bryant
package com.example.messengerapp;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class ConversationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView convoName;
    public TextView convoPreview;
    private OnClickListener o;

    public ConversationViewHolder(View view, OnClickListener o) {
        super(view);
        convoName = (TextView) view.findViewById(R.id.convoName);
        convoPreview = (TextView) view.findViewById(R.id.convoPreview);
        this.o = o;
        view.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        o.onClick(getAdapterPosition());
    }
}
