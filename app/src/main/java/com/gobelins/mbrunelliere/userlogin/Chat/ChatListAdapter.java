package com.gobelins.mbrunelliere.userlogin.Chat;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.client.Query;
import com.gobelins.mbrunelliere.userlogin.R;

import java.util.List;

/**
 * Created by mbrunelliere on 14/10/2015.
 */

public class ChatListAdapter extends FirebaseListAdapter<Message> {


    // The mUsername for this client. We use this to indicate which messages originated from this user
    private String mUsername;

    public ChatListAdapter(Query ref, Activity activity, int layout, String mUsername) {
        super(ref, Message.class, layout, activity);
        this.mUsername = mUsername;
    }


    /**
     * Bind an instance of the <code>Chat</code> class to our view. This method is called by <code>FirebaseListAdapter</code>
     * when there is a data change, and we are given an instance of a View that corresponds to the layout that we passed
     * to the constructor, as well as a single <code>Chat</code> instance that represents the current data to bind.
     *
     * @param view A view instance corresponding to the layout we passed to the constructor.
     * @param message An instance representing the current state of a chat message
     */

    protected void populateView(View view, Message message) {
        // Map a Chat object to an entry in our listview
        String author = message.getAuthor();
        TextView authorText = (TextView) view.findViewById(R.id.author);
        authorText.setText(author + ": ");
        ((TextView) view.findViewById(R.id.message)).setText(message.getMessage());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    // Store a member variable for the messages
    private List<Message> mMessages;

    // Pass in the message array into the constructor
    public ChatListAdapter(List<Message> messages) {
        super();
        mMessages = messages;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        
    }

    @Override
    public int getItemCount() {
        return 0;
    }


}

