// Hansi and Ryan
package com.example.messengerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class ConversationPage extends AppCompatActivity implements OnClickListener {

    private String userID;
    private ArrayList<Conversation> convos;
    private ArrayList<String> convoIDs;
    private RecyclerView rView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private FirebaseDatabase database;
    private DatabaseReference conversations;
    private DatabaseReference userConvos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation_page);

        Bundle extras = getIntent().getExtras();
        String value = extras.getString("User");
        userID = value;
        convos = new ArrayList<Conversation>();
        convoIDs = new ArrayList<String>();
        rView = (RecyclerView) findViewById(R.id.recyclerViewConversations);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        rView.setLayoutManager(layoutManager);
        adapter = new ConversationAdapter(this);
        rView.setAdapter(adapter);
        rView.addItemDecoration(new DividerItemDecoration(rView.getContext(), DividerItemDecoration.VERTICAL));
        TextView userIDBox = (TextView)findViewById(R.id.userName);
        userIDBox.setText(userID);

        database = FirebaseDatabase.getInstance();
        conversations = database.getReference("Conversations");
        userConvos = database.getReference("UserConvos").child(userID);
        userConvos.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<String> newConvoIDs = new ArrayList<String>();
                HashMap<String, String> m = (HashMap<String, String>) dataSnapshot.getValue();
                if (m != null) {
                    newConvoIDs.addAll(m.keySet());
                    updateConvoList(newConvoIDs);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("Error retrieving Conversation.");
                Log.e("onCancelled", "Cancelled");
            }
        });
    }

    public void onClick(int position) {
        Intent intent = new Intent(this, MessagePage.class);
        Conversation c = convos.get(position);
        Bundle b = new Bundle();
        b.putString("convoID", c.getKey());
        b.putString("userID", userID);
        intent.putExtras(b);
        startActivity(intent);
    }

    public void createConvo(View view) {
        Intent intent = new Intent(this, CreateConversation.class);
        Bundle b = new Bundle();
        b.putString("userID", userID);
        intent.putExtras(b);
        startActivity(intent);
    }

    private void updateConvoList(ArrayList<String> newConvoIDList) {
        newConvoIDList.removeAll(convoIDs);
        getConversations(newConvoIDList);
        convoIDs.addAll(newConvoIDList);
    }

    private void getConversations(final ArrayList<String> convoIDList) {
        conversations.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (String key : convoIDList) {
                    Conversation c = dataSnapshot.child(key).getValue(Conversation.class);
                    convos.add(dataSnapshot.child(key).getValue(Conversation.class));
                }
                ((ConversationAdapter) adapter).update(convos);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("Error retrieving Conversation.");
                Log.e("onCancelled", "Cancelled");
            }
        });
    }

    public void logout(View view) {
        startActivity(new Intent(this, MainActivity.class));
    }
}
