package com.gobelins.mbrunelliere.userlogin;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;

import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.ui.FirebaseRecyclerViewAdapter;
import com.gobelins.mbrunelliere.userlogin.Chat.Message;
import com.gobelins.mbrunelliere.userlogin.Chat.MessageHolder;


public class ChatActivity extends AppCompatActivity {

    private static final String TAG = "ChatActivity";
    private FirebaseRecyclerViewAdapter<Message, MessageHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle("Messagerie");

        Firebase.setAndroidContext(this);
        final Firebase ref = new Firebase("https://workshopandroid.firebaseio.com").child("messages");
        final AuthData authData = ref.getAuth();

        final String name = authData.getProviderData().get("email").toString();
        final Button sendButton = (Button) findViewById(R.id.chatSendButton);
        final EditText messageEdit = (EditText) findViewById(R.id.chatMessageInput);
        final RecyclerView messages = (RecyclerView) findViewById(R.id.rvMessages);

        messages.setHasFixedSize(true);
        messages.setLayoutManager(new LinearLayoutManager(this));

        messageEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_NULL && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    /*sendMessage();*/
                }
                return true;
            }
        });
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message message = new Message(name, messageEdit.getText().toString());
                ref.push().setValue(message, new Firebase.CompletionListener() {
                    @Override
                    public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                        if (firebaseError != null) {
                            Log.e("FirebaseUI.chat", firebaseError.toString());
                        }
                    }
                });
                messageEdit.setText("");
            }
        });

        adapter = new FirebaseRecyclerViewAdapter<Message, MessageHolder>(Message.class, R.layout.item_chat, MessageHolder.class, ref) {
             @Override
             public void populateViewHolder(MessageHolder messageView, Message message) {
                 messageView.textView.setText(message.getMessage());
                 messageView.textView.setPadding(10, 0, 10, 0);
                 messageView.nameView.setText(message.getAuthor());
                 messageView.nameView.setPadding(10, 0, 10, 15);
                 if (message.getAuthor().equals(name)) {
                     messageView.textView.setGravity(Gravity.END);
                     messageView.nameView.setGravity(Gravity.END);
                     messageView.nameView.setTextColor(Color.parseColor("#8BC34A"));
                 } else {
                     messageView.nameView.setTextColor(Color.parseColor("#00BCD4"));
                 }
             }
         };

        messages.setAdapter(adapter);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        adapter.cleanup();
    }
}
