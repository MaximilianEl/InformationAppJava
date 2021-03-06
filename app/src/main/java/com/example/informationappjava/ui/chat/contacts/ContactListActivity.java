package com.example.informationappjava.ui.chat.contacts;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.*;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.informationappjava.ui.chat.MeActivity;
import com.example.informationappjava.ui.chat.Utils.Utilities;
import com.example.informationappjava.ui.chat.contacts.model.Contact;
import com.example.informationappjava.ui.chat.contacts.model.Contact.SubscriptionType;
import com.example.informationappjava.ui.chat.contacts.model.ContactModel;
import com.example.informationappjava.ui.chat.contacts.adapter.ContactListAdapter;
import com.example.informationappjava.ui.chat.chatlist.model.Chat;
import com.example.informationappjava.ui.chat.chatlist.model.Chat.ContactType;
import com.example.informationappjava.ui.chat.chatlist.model.ChatModel;
import com.example.informationappjava.ui.chat.view.ChatViewActivity;
import com.example.informationappjava.xmpp.ChatConnectionService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.example.informationappjava.R;

import java.util.List;

/**
 * The ContactListActivity class is used to get to the Contactlist and gives the Contacts different function and
 * onclickevents. It also gives acces to add a Contact and to the MeActivity.
 * If there aren't any Contacts added it also adds the preset Contacts to the Users Contactroster.
 */
public class ContactListActivity extends AppCompatActivity implements
        ContactListAdapter.OnItemClickListener, ContactListAdapter.OnItemLongClickListener {

    private RecyclerView contactListRecyclerView;
    ContactListAdapter mAdapter;
    private static final String LOGTAG = "ContactListActivity";

    /**
     * This function sets the Contentview to the activity_contact_list and sets an onClick event on the newcontactbutton
     * to access the addContact() function.
     * It also adds the preset Contacts to the Contactlist for a new User.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton newContactButton = findViewById(R.id.newcontactbutton);
        newContactButton.setBackgroundTintList(getResources().getColorStateList(R.color.colorPrimary));
        newContactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // A Dialog should pop up but it doesn't
                addContact();
            }
        });

        contactListRecyclerView = findViewById(R.id.contact_list_recycler_view);
        contactListRecyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));

        mAdapter = new ContactListAdapter(getApplicationContext());
        mAdapter.setmOnItemClickListener(this);
        mAdapter.setmOnItemLongClickListener(this);
        contactListRecyclerView.setAdapter(mAdapter);

// Adds the preset Contacts to the Contactroster if there aren't any Contacts on it already
        if (mAdapter.getItemCount() == 0) {
            ContactModel.get(getApplicationContext()).addContact(
                    new Contact("test@hsoschat.de", Contact.SubscriptionType.NONE));
            ContactModel.get(getApplicationContext()).addContact(
                    new Contact("test2@hsoschat.de", Contact.SubscriptionType.NONE));
            mAdapter.onContactCountChange();
        }
    }

    /**
     * This function Displays an AlertDialog with the possibility to add a Contact to the Contactlist.
     */
    private void addContact() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ContactListActivity.this);
        builder.setTitle(R.string.add_contact_label_text);
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton(R.string.add_contact_confirm_text,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.d(LOGTAG, "User clicked on OK");
                        if (ContactModel.get(getApplicationContext()).addContact(
                                new Contact(input.getText().toString(), SubscriptionType.NONE))) {
                            mAdapter.onContactCountChange();
                            Log.d(LOGTAG, "Contact added successfully");
                        } else {
                            Log.d(LOGTAG, "Could not add contact");
                        }
                    }
                });
        builder
                .setNegativeButton(R.string.add_contact_cancel_text, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.d(LOGTAG, "User clicked on Cancel");
                        dialogInterface.cancel();
                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    /**
     * This function inflates the activity_me_menu.
     *
     * @param menu
     * @return true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_me_menu, menu);
        return true;
    }

    /**
     * This function starts the MeActivity if it is clicked on the activity_me_menu.
     *
     * @param item
     * @return super.onOptionsItemSelected(item)
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.me) {
            Intent i = new Intent(ContactListActivity.this, MeActivity.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * This function creates a clickevent on the Contactlist and starts the ChatViewActivity to access the Chat or
     * opens a new Chat up with the specific Contact if there isn't already an existing one.
     *
     * @param contactJid
     */
    @Override
    public void onItemClick(String contactJid) {

        Log.d(LOGTAG, "Inside contactListActivity the clicked contact is: " + contactJid);
        List<Chat> chats = ChatModel.get(getApplicationContext()).getChatsByJid(contactJid);
        if (chats.size() == 0) {
            Log.d(LOGTAG, contactJid + " is a new chat, adding them. With timestamp: " + Utilities
                    .getFormattedTime(System.currentTimeMillis()));

            Chat chat = new Chat(contactJid, "", ContactType.ONE_ON_ONE, System.currentTimeMillis(), 0);
            ChatModel.get(getApplicationContext()).addChat(chat);

            //Inside here we start the chat activity
            Intent intent = new Intent(ContactListActivity.this, ChatViewActivity.class);
            intent.putExtra("contact_jid", contactJid);
            startActivity(intent);
            finish();
        } else {
            Log.d(LOGTAG, contactJid + "is ALREADY in chat db. Just opening conversation");
            Intent intent = new Intent(ContactListActivity.this, ChatViewActivity.class);
            intent.putExtra("contact_jid", contactJid);
            startActivity(intent);
            finish();
        }
    }

    /**
     * This function creates a longclickevent on the Contactlist that opens a PopupMenu to be able to delete a Contact or
     * open the contact details.
     *
     * @param uniqueId
     * @param contactJid
     * @param anchor
     */
    @Override
    public void onItemLongClick(final int uniqueId, final String contactJid, View anchor) {

        PopupMenu popup = new PopupMenu(ContactListActivity.this, anchor, Gravity.CENTER);

        popup.getMenuInflater().inflate(R.menu.contact_list_popup_menu, popup.getMenu());

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.delete_contact:
                        if (ContactModel.get(getApplicationContext()).deleteContact(uniqueId)) {
                            mAdapter.onContactCountChange();
                            if (ChatConnectionService.getConnection().removeRosterEntry(contactJid)) {

                                Log.d(LOGTAG, contactJid + " successfully deleted from Roster");
                                Toast.makeText(ContactListActivity.this,
                                        "Contact deleted successfully ",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                        break;

                    case R.id.contact_details:
                        Intent intent = new Intent(ContactListActivity.this, ContactDetailsActivity.class);
                        intent.putExtra("contact_jid", contactJid);
                        startActivity(intent);
                        return true;
                }
                return true;
            }
        });
        popup.show();
    }
}