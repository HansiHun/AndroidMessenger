// Ryan and Bryant
package com.example.messengerapp;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Conversation {
    public ArrayList<String> users;
    public ArrayList<Message> messages;
    private int numMessages;
    private String name;
    private String key;

    public Conversation() {
        users = new ArrayList<String>();
        messages = new ArrayList<Message>();
        numMessages = 0;
        name = "";
        key = "";
    }

    public Conversation(String name) {
        users = new ArrayList<String>();
        messages = new ArrayList<Message>();
        numMessages = 0;
        this.name = name;
        key = "";
    }

    public Conversation(Conversation convo) {
        users = new ArrayList<String>();
        messages = new ArrayList<Message>();

        numMessages = convo.numMessages;
        name = convo.name;
        key = convo.key;

        users.addAll(convo.users);
        messages.addAll(convo.messages);
    }

    public void addUser(String user) {
        users.add(user);
    }

    public void addUsers(ArrayList<String> users) {
        for (String user : users) {
            this.users.add(user);
        }
    }

    public void removeUser(String user) {
        users.remove(user);
    }

    public ArrayList<String> getUsers() {
        ArrayList<String> users = (ArrayList<String>) this.users.clone();
        return users;
    }

    public void addMessage(String user, String text) {
        Message newMessage = new Message(text, user, numMessages);
        messages.add(newMessage);
        numMessages++;
    }

    public void setName(String s) {
        this.name = s;
    }

    public Message getNthMessage(int n)
    {
        return messages.get(n);
    }

    public int getNumMessages() {
        return numMessages;
    }

    public String getName() { return name; }

    public String getKey() { return key; }

    public void setKey(String k) {
        this.key = k;
    }
}
