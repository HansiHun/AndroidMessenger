// Bryant
package com.example.messengerapp;

import android.os.Parcelable;

public class Message {
    private String text;
    private String user;
    private int id;

    public Message() {

    }

    public Message(String t, String user, int i) {
        this.text = t;
        this.user = user;
        this.id = i;
    }

    public String getText() {
        return this.text;
    }

    public String getUser() {
        return this.user;
    }

    public int getId() {
        return this.id;
    }
}
