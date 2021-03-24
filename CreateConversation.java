// Bryant
package com.example.messengerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CreateConversation extends AppCompatActivity {

    private String userID;
    private ArrayList<String> userList;

    EditText addUserID;

    private RecyclerView rView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private FirebaseDatabase database;
    private DatabaseReference conversations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_conversation);

        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        userID = b.getString("userID");
        userList = new ArrayList<String>();

        rView = findViewById(R.id.recyclerViewUsers);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        rView.setLayoutManager(layoutManager);
        adapter = new CreateConversationAdapter(this);
        rView.setAdapter(adapter);

        addUserID = findViewById(R.id.addUserID);

        database = FirebaseDatabase.getInstance();
        conversations = database.getReference("Conversations");
    }

    public void addUser(View view) {
        final String newUser = addUserID.getText().toString();
        if (!newUser.equals("") && !userList.contains(newUser) && newUser != userID) {
            final DatabaseReference acc = database.getReference("Accounts");
            acc.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.hasChild(newUser)) {
                        userList.add(newUser);
                        ((CreateConversationAdapter) adapter).update(userList);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {}
            });
        }

        addUserID.setText("");
    }

    public void cancel(View view) {
        Intent intent = new Intent(this, ConversationPage.class);
        Bundle b = new Bundle();
        b.putString("User", userID);
        intent.putExtras(b);
        startActivity(intent);
    }

    public void createConvo(View view) {
        String convoName = ((EditText) findViewById(R.id.convoName)).getText().toString();
        Conversation c = new Conversation(convoName);

        userList.add(userID); // Add yourself to the convo.
        c.addUsers(userList);

        String message = ((EditText) findViewById(R.id.convoMsg)).getText().toString();
        if (message.equals("")) {
            return; // Message cannot be empty.
        }
        c.addMessage(userID, message);

        String key = conversations.push().getKey();
        c.setKey(key);
        conversations.child(key).setValue(c);

        updateConvoKeys(key);
        cancel(view); // Return to list of conversations.
    }

    // Add the conversation key to the list of keys stored under each user.
    private void updateConvoKeys(String convoID) {
        DatabaseReference userConvos = database.getReference("UserConvos");

        for (String user : userList) {
            DatabaseReference convosIn = userConvos.child(user);
            convosIn.child(convoID).setValue(0);
        }
    }
}
