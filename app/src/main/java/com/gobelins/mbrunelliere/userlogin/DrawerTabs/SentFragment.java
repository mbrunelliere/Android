package com.gobelins.mbrunelliere.userlogin.DrawerTabs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.ui.FirebaseRecyclerViewAdapter;
import com.gobelins.mbrunelliere.userlogin.Chat.Message;
import com.gobelins.mbrunelliere.userlogin.Chat.MessageHolder;
import com.gobelins.mbrunelliere.userlogin.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Ratan on 7/29/2015.
 */
public class SentFragment extends Fragment {

    private FirebaseRecyclerViewAdapter<Message, MessageHolder> adapter;
    private LinearLayoutManager linearLayoutManager;
    private Firebase ref;
    private String name;
    private AuthData authData;

    public SentFragment() {
        // Required empty public constructor
    }

    @Bind(R.id.rvOldMessages)
    RecyclerView messages;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            View view = inflater.inflate(R.layout.fragment_old_messages, container, false);
            ButterKnife.bind(this, view);

            //Initialize Firebase
            ref = new Firebase("https://workshopandroid.firebaseio.com").child("messages");
            authData = ref.getAuth();
            name = authData.getProviderData().get("email").toString();

            //Initialize RecyclerView
            messages.setHasFixedSize(true);
            linearLayoutManager = new LinearLayoutManager(getActivity());
            messages.setLayoutManager(linearLayoutManager);

            adapter = new FirebaseRecyclerViewAdapter<Message, MessageHolder>(Message.class, R.layout.item_chat, MessageHolder.class, ref) {
                @Override
                public void populateViewHolder(MessageHolder messageView, Message message) {

                    if (message.getReceiver().equals(name) || message.getReceiver().equals("all")) {
                    messageView.textView.setText(message.getMessage());
                    messageView.textView.setPadding(10, 0, 10, 0);
                    messageView.nameView.setText(message.getAuthor() + " : ");
                    messageView.nameView.setPadding(10, 0, 10, 15);
                    messageView.textView.setGravity(Gravity.END);
                    messageView.nameView.setGravity(Gravity.END);
                    }

                }

            };

            messages.setAdapter(adapter);

            return view;
    }


}
