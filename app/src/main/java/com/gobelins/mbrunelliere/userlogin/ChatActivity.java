package com.gobelins.mbrunelliere.userlogin;

import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.ValueEventListener;
import com.gobelins.mbrunelliere.userlogin.Chat.ChatListAdapter;
import com.gobelins.mbrunelliere.userlogin.Chat.Message;



public class ChatActivity extends AppCompatActivity {

    private static final String FIREBASE_URL = "https://workshopandroid.firebaseio.com";

    private String mUsername;
    private Firebase mFirebaseRef;
    private ValueEventListener mConnectedListener;
    private ChatListAdapter adapter;
    private Firebase myFirebaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle("Messagerie");

        // Make sure we have a mUsername
        setupUsername();

        // Setup our Firebase mFirebaseRef
        mFirebaseRef = new Firebase(FIREBASE_URL).child("chat");

        // Setup our input methods. Enter key on the keyboard or pushing the send button
        EditText inputText = (EditText) findViewById(R.id.chatMessageInput);
        inputText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_NULL && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    sendMessage();
                }
                return true;
            }
        });

        findViewById(R.id.chatSendButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
            }
        });

        // Lookup the recyclerview in activity layout
        RecyclerView rvMessages = (RecyclerView) findViewById(R.id.rvMessages);
        // Create adapter passing in the sample user data
        adapter = new ChatListAdapter(mFirebaseRef.limit(50), this, R.layout.item_chat, mUsername);
        // Attach the adapter to the recyclerview to populate items
        rvMessages.setAdapter(adapter);
        // Set layout manager to position the items
        rvMessages.setLayoutManager(new LinearLayoutManager(this));
        // That's all!
    }

    @Override
    public void onStop() {
        super.onStop();
        mFirebaseRef.getRoot().child(".info/connected").removeEventListener(mConnectedListener);
        adapter.cleanup();
    }

    private void setupUsername() {
        myFirebaseRef = new Firebase("https://workshopandroid.firebaseio.com");
        AuthData authData = myFirebaseRef.getAuth();
        mUsername = authData.getProviderData().get("email").toString();
    }

    private void sendMessage() {
        EditText inputText = (EditText) findViewById(R.id.chatMessageInput);
        String input = inputText.getText().toString();
        if (!input.equals("")) {
            // Create our 'model', a Chat object
            Message userMessage = new Message(mUsername, input);
            // Create a new, auto-generated child of that chat location, and save our chat data there
            mFirebaseRef.push().setValue(userMessage);
            inputText.setText("");
        }
    }
}
