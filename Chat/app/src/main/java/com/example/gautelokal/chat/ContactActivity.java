package com.example.gautelokal.chat;


import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ContactActivity extends AppCompatActivity {
    public static final String CHAT_ID = "conversationid";
    public static final String NAME_OF_CONTACT = "contactName";

    // listview
    private ListView listOfContacts;

    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        setTitle("MyContacts");

        // Find the list view
        this.listOfContacts = (ListView) findViewById(R.id.listOfContacts);

        // Read and show the contacts
        showContacts();


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }


    /**
     * showing contacts in ListView.
     */
    private void showContacts() {
        // Check the SDK version and whether the permission is already granted or not.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISSIONS_REQUEST_READ_CONTACTS);
            //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
        } else {

            List contacts = getContactNames();
            final ContactAdapter seeAdapter = new ContactAdapter(this, contacts);
            listOfContacts.setAdapter(seeAdapter);
            listOfContacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position,
                                        long id) {

                    Intent i = new Intent(getApplicationContext(), ChatActivity.class);

                    //Finding contactID and sent it too chatActivity.
                    String contactName = seeAdapter.getItem(position).getName();
                    if (!(DomainSingleton.getSingleton(ContactActivity.this).getAllConversationNames().contains(contactName))) {
                        i.putExtra(NAME_OF_CONTACT, contactName);
                        startActivity(i);
                    } else {
                        int conversationId = DomainSingleton.getSingleton(ContactActivity.this).getConversationIdByContactName(contactName);
                        i.putExtra(CHAT_ID, conversationId);
                        i.putExtra(NAME_OF_CONTACT, contactName);
                        startActivity(i);
                    }
                }
            });
        }
    }

    /**
     * Read the name of all the contacts.
     *
     * @return a list of names.
     */
    private List getContactNames() {
        List<Contact> contacts = new ArrayList<>();
        // Get the ContentResolver
        ContentResolver cr = getContentResolver();
        // Get the Cursor of all the contacts
        Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        while (cursor.moveToNext()) {

            // phonenumber
            String hasNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.HAS_PHONE_NUMBER));
            String contactName = "";
            if (hasNumber.equals("1")) {
                contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));

                Contact contact = new Contact(contactName);
                contacts.add(contact);


            }
        }
        cursor.close();
        return contacts;
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

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();

                return true;

            case R.id.action_settings:
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}

