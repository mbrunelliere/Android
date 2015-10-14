package com.gobelins.mbrunelliere.userlogin.Chat;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.gobelins.mbrunelliere.userlogin.R;

import java.util.List;

/**
 * Created by mbrunelliere on 14/10/2015.
 */

public class ChatListAdapter extends
        RecyclerView.Adapter<ChatListAdapter.ViewHolder> {


    private ViewHolder viewHolder;

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView authorTextView;
        public TextView messageTextView;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            authorTextView = (TextView) itemView.findViewById(R.id.author);
            messageTextView = (TextView) itemView.findViewById(R.id.message);
        }
    }

    // Store a member variable for the contacts
    private List<Message> mMessages;

    // Pass in the contact array into the constructor
    public ChatListAdapter(List<Message> messages) {
        mMessages = messages;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_chat, parent, false);

        // Return a new holder instance
        viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // Get the data model based on position
        Message message = mMessages.get(position);

        // Set item views based on the data model

        TextView authorView = viewHolder.authorTextView;
        authorView.setText(message.getAuthor());

        // Set item views based on the data model
        TextView messageView = viewHolder.messageTextView;
        messageView.setText(message.getMessage());
    }

    @Override
    public int getItemCount() {
        return mMessages.size();
    }

}

