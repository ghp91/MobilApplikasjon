package com.example.gautelokal.chat;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    public static final String CHAT_ID = "chatId";
    public static final String NAME_OF_CONTACT = "NameOfContact";
  //  String[] items;
  //ArrayList<String> adapter;
  //  ListView listView2;
   // EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle("Chat app");
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), ContactActivity.class);
                startActivity(i);

            }
        });

        ListView listView = (ListView) findViewById(R.id.listView);


        int conversationSize = DomainSingleton.getSingleton(this).getData().size();
        if (conversationSize > 0) {


            final ArrayAdapter<String> namesAdapter = new ArrayAdapter<>(
                    this,
                    android.R.layout.simple_list_item_1,
                    DomainSingleton.getSingleton(this).getAllConversationNames());


            listView.setAdapter(namesAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    //getting the name of the contact
                    String NameOfContact = namesAdapter.getItem(position);

                    //Getting the chatID
                    int chatId = DomainSingleton.getSingleton(MainActivity.this).getConversationIdByContactName(NameOfContact);

                    Intent i = new Intent(getApplicationContext(), ChatActivity.class);
                    i.putExtra(NAME_OF_CONTACT, NameOfContact);
                    i.putExtra(CHAT_ID, chatId);
                    startActivity(i);

                }
            });

        }
        //listView2 = (ListView) findViewById(listView);
        //editText=

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
       // getMenuInflater().inflate(R.menu.options_menu, menu);





        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}