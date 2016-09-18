package com.example.gautelokal.chat;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.List;

public class ChatActivity extends AppCompatActivity {


    public static final String CHAT_ID = "chatId";
    private ListView listOfMessages;
    private List<Message> messages;
    private Button sendButton;
    private String nameOfContact;
    private int chatId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        //Using intent to get contact name, and use it for chat-title.
        Intent i = getIntent();
        nameOfContact = i.getStringExtra("contactName");
        setTitle(nameOfContact);


       // chatId is automatically set too 0.

        chatId = i.getIntExtra(CHAT_ID, -1);
        DomainSingleton service = DomainSingleton.getSingleton(this);
        if (chatId != -1) {
            messages = service.getConversation(chatId);


        } else {
            messages = service.createConversation();
            chatId = service.getData().size() - 1; // OBS not threadsafe
        }

        this.sendButton = (Button) findViewById(R.id.sendButton);
        // Find the list view
        this.listOfMessages = (ListView) findViewById(R.id.messageListView);


        showMessages();

    }

    // Show messages
    public void showMessages() {
        MessageListAdapter mAdapter = new MessageListAdapter(this, messages);
        listOfMessages.setAdapter(mAdapter);

        sendButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                createMessage();

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    // hit the back-button
    public void onBackPressed() {

        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        //int id = item.getItemId();

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();

                return true;

            case R.id.action_settings:
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    // Create message
    private void createMessage() {

        EditText messageText = (EditText) findViewById(R.id.messageText);

        String newMessageText = messageText.getText().toString();

        if (!newMessageText.equals("")) {
            Message newMessage = new Message(newMessageText, nameOfContact, chatId);
            messages.add(newMessage);
            messageText.setText("");
        }


    }

}
