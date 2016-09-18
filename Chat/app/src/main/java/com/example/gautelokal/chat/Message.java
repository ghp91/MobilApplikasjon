package com.example.gautelokal.chat;

import java.io.Serializable;

public class Message implements Serializable {
    String contact;
    String message;
    Integer conversationId;

    public Message(String message, String contact, int conversationId) {
        this.contact = contact;
        this.message = message;
        this.conversationId = conversationId;

    }

    public String getContact() {
        return contact;
    }

    public String getMessage() {
        return message;
    }

    public Integer getConversationId() {
        return conversationId;
    }


}