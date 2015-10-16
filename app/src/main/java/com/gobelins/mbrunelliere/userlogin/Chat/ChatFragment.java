package com.gobelins.mbrunelliere.userlogin.Chat;

import android.graphics.Color;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.ui.FirebaseRecyclerViewAdapter;

import com.gobelins.mbrunelliere.userlogin.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class ChatFragment extends Fragment {

    private FirebaseRecyclerViewAdapter<Message, MessageHolder> adapter;
    private LinearLayoutManager linearLayoutManager;
    private Firebase ref;
    private String name;
    private AuthData authData;

    public ChatFragment() {
        // Required empty public constructor
    }

    @Bind(R.id.chatSendButton)
    Button sendButton;
    @Bind(R.id.rvMessages)
    RecyclerView messages;
    @Bind(R.id.chatMessageInput)
    EditText messageEdit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        ButterKnife.bind(this, view);

        //Initialize Firebase
        ref = new Firebase("https://workshopandroid.firebaseio.com").child("messages");
        authData = ref.getAuth();
        name = authData.getProviderData().get("email").toString();

        //Initialize RecyclerView
        messages.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        messages.setLayoutManager(linearLayoutManager);

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
                        messages.smoothScrollToPosition(adapter.getItemCount() - 1);
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

        return view;
    }

    /*@Override
    public void onDestroy() {
        super.onDestroy();
        adapter.cleanup();
    }*/

}
