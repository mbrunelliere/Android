package com.gobelins.mbrunelliere.userlogin.Chat;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gobelins.mbrunelliere.userlogin.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class ChatActivityFragment extends Fragment {

    public ChatActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.item_chat, container, false);
    }
}