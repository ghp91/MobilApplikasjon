package com.example.gautelokal.chat;

import android.content.Context;
import java.util.ArrayList;
import java.util.List;



public class DomainSingleton {
    public static DomainSingleton SINGLETON;

    private List<List<Message>> data = new ArrayList<>();

    private DomainSingleton() {
    }


    public static synchronized DomainSingleton getSingleton(Context context) {
        if (SINGLETON == null) {
            SINGLETON = new DomainSingleton();
        }

        return SINGLETON;
    }

    public synchronized List<List<Message>> getData() {
        return data;
    }

    public synchronized List<Message> getConversation(int conversationId) {
        return getData().get(conversationId);
    }

    public synchronized List<Message> createConversation() {
        List<Message> result = new ArrayList<>();
        getData().add(result);
        return result;
    }



    //returns a list with conversations
    public synchronized List<String> getAllConversationNames() {
        List<String> allnames = new ArrayList<String>();
        for (int i = 0; getData().size() > i; i++) {
            String name = getFirstMessageInConversation(i).getContact();
            if (!allnames.contains(name)) {
                allnames.add(name);
            }
        }
        return allnames;
    }

    //Returns the firstmessage
    public synchronized Message getFirstMessageInConversation(int dataIndex) {
        Message firstMessage = getData().get(dataIndex).get(0);
        return firstMessage;
    }

    public synchronized int getConversationIdByContactName(String ContactName)
    {
        int conversationId=0;
        for (int i = 0; getData().size() > i; i++) {
            Message tempMessage = getFirstMessageInConversation(i);
            if(ContactName.equals(tempMessage.getContact()))
            {
                conversationId = tempMessage.getConversationId();
            }

        }
        return conversationId;
    }
}