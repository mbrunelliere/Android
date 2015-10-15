package com.gobelins.mbrunelliere.userlogin.Chat;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.gobelins.mbrunelliere.userlogin.R;

/**
 * Created by mbrunelliere on 15/10/2015.
 */

public class MessageHolder extends RecyclerView.ViewHolder {
    public TextView nameView;
    public TextView textView;

    public MessageHolder(View itemView) {
        super(itemView);
        nameView = (TextView) itemView.findViewById(R.id.author);
        textView = (TextView) itemView.findViewById(R.id.message);
    }
}
