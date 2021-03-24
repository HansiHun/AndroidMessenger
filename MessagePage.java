// Ryan and Bryant
package com.example.messengerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MessagePage extends AppCompatActivity {

    private String userID;
    private Conversation convo;
    private TextView convoName;
    private RecyclerView rView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private FirebaseDatabase database;
    private DatabaseReference conversation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_page);

        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        String convoID = b.getString("convoID");
        userID = b.getString("userID");

        convoName = (TextView) findViewById(R.id.convoName);
        rView = (RecyclerView)findViewById(R.id.recyclerViewMessages);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        rView.setLayoutManager(layoutManager);
        adapter = new MessageAdapter(this, userID);
        rView.setAdapter(adapter);

        database = FirebaseDatabase.getInstance();
        DatabaseReference conversations = database.getReference("Conversations");
        conversation = conversations.child(convoID);
        conversation.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                convo = dataSnapshot.getValue(Conversation.class);
                ((MessageAdapter) adapter).update(convo);
                convoName.setText(convo.getName());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("Error retrieving Conversation.");
                Log.e("onCancelled", "Cancelled");
            }
        });
    }

    public String getUserID()
    {
        return userID;
    }

    public void setConvo(Conversation c)
    {
        convo = c;
    }

    public void sendMessage(View view)
    {
        EditText msg = (EditText)findViewById(R.id.editTextMessage);
        String s = msg.getText().toString();
        if (!s.equals("")) {
            convo.addMessage(userID, s);
            conversation.setValue(convo);
        }
        msg.setText("");
        rView.scrollToPosition(convo.getNumMessages() - 1);
    }

    public void exitChat(View view) {
        Intent intent = new Intent(this, ConversationPage.class);
        Bundle b = new Bundle();
        b.putString("User", userID);
        intent.putExtras(b);
        startActivity(intent);
    }

    public void leaveConvo(View view) {
        convo.addMessage(userID, "I have left the chat.");
        convo.removeUser(userID);

        DatabaseReference userConvos = database.getReference("UserConvos").child(userID);
        userConvos.child(convo.getKey()).removeValue();
        conversation.setValue(convo);

        exitChat(view);
    }


}
