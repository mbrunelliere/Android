package com.gobelins.mbrunelliere.userlogin.Chat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mbrunelliere on 14/10/2015.
 */
public class Message {
    private String author;
    private String message;


    // Required default constructor for Firebase object mapping
    @SuppressWarnings("unused")
    private Message() {
    }

    public Message( String author, String message) {
        this.message = message;
        this.author = author;
    }

    public String getMessage() {
        return message;
    }

    public String getAuthor() {
        return author;
    }


    private static int lastMessageId = 0;

    public static List<Message> createMessagesList(int numContacts) {
        List<Message> messages = new ArrayList<Message>();

        for (int i = 1; i <= numContacts; i++) {
            messages.add(new Message("Person " + ++lastMessageId + " : ", "Message "));
        }

        return messages;
    }
}
