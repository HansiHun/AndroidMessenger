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

public class LoginActivity extends AppCompatActivity {

    private Button backButton, loginButton;
    private EditText mloginUN, mloginPW;
    private TextView mloginError;
    private DatabaseReference db,accounts,user;
    private Bundle bun;
    private Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginButton=(Button)findViewById(R.id.loginConfirm);
        backButton=(Button)findViewById(R.id.backButton);
        mloginUN=(EditText)findViewById(R.id.loginUN);
        mloginPW=(EditText)findViewById(R.id.loginPW);
        mloginError=(TextView)findViewById(R.id.loginError);
        db=FirebaseDatabase.getInstance().getReference();
        accounts=db.child("Accounts");

        mloginError.setVisibility(View.INVISIBLE);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    i=new Intent(LoginActivity.this, ConversationPage.class);
                    if (mloginUN.getText().toString().matches("")) {
                        mloginError.setVisibility(View.VISIBLE);
                        return;
                    }
                    user=accounts.child(mloginUN.getText().toString());
                    user.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists())
                            {
                                String value = dataSnapshot.getValue(String.class);
                                if (mloginPW.getText().toString().equals(value)) {
                                    i.putExtra("User", mloginUN.getText().toString());
                                    startActivity(i);
                                }
                                else
                                {
                                    mloginError.setVisibility(View.VISIBLE);
                                }
                            }
                            else
                            {
                                mloginError.setVisibility(View.VISIBLE);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }
        });
    }
}
