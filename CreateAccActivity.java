// Hansi
package com.example.messengerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CreateAccActivity extends AppCompatActivity {

    private Button backButton,createAccButton;
    private EditText mcreateAccUN, mcreateAccPW;
    private TextView errorMsg;
    private DatabaseReference db;
    private Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_acc);

        backButton=(Button)findViewById(R.id.backButton);
        createAccButton=(Button)findViewById(R.id.createAccConfirm);
        mcreateAccUN=(EditText)findViewById(R.id.createAccUN);
        mcreateAccPW=(EditText)findViewById(R.id.createAccPW);
        errorMsg = (TextView)findViewById(R.id.errorMessage);
        errorMsg.setVisibility(View.GONE);
        db= FirebaseDatabase.getInstance().getReference();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CreateAccActivity.this, MainActivity.class));
            }
        });

        createAccButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = mcreateAccUN.getText().toString();
                final DatabaseReference acc = db.child("Accounts");
                acc.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (!dataSnapshot.hasChild(username)) {
                            acc.child(username).setValue(mcreateAccPW.getText().toString());
                            logoutScreen();
                        }
                        else {
                            errorMsg.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {}
                });
            }
        });
    }

    private void logoutScreen() {
        i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}
