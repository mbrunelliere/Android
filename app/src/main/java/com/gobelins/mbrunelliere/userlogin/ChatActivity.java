package com.gobelins.mbrunelliere.userlogin;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
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
import com.gobelins.mbrunelliere.userlogin.User.LoginFragment;
import com.gobelins.mbrunelliere.userlogin.User.RegisterFragment;


public class ChatActivity extends AppCompatActivity {

    private static final String TAG = "ChatActivity";
    private FirebaseRecyclerViewAdapter<Message, MessageHolder> adapter;

    private String passwordSession;
    private String emailSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle("Messagerie");

        passwordSession = getIntent().getStringExtra("passwordSession");
        emailSession = getIntent().getStringExtra("emailSession");

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
                 messageView.nameView.setText(message.getAuthor() + " : ");
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
        //messages.scrollToPosition(adapter.getItemCount() - 1);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        adapter.cleanup();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_settings) {
            //login clicked
            return true;
        }else if (item.getItemId() == R.id.actions_Profile) {
            //profile clicked
            Intent userActivity = new Intent(ChatActivity.this, UserActivity.class);
            userActivity.putExtra("emailSession",emailSession);
            userActivity.putExtra("passwordSession",passwordSession);
            startActivity(userActivity);
            return true;
        }else if (item.getItemId() == R.id.actions_Logout) {
            //logout clicked

            Firebase FirebaseRef = new Firebase("https://workshopandroid.firebaseio.com");
            FirebaseRef.unauth();
            Intent i = new Intent(ChatActivity.this, MainActivity.class);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_chat, menu);
        return true;
    }
}
