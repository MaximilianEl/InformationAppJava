package com.example.informationappjava.ui.chat.chatlist;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.informationappjava.R;
import com.example.informationappjava.ui.chat.MeActivity;
import com.example.informationappjava.ui.chat.Utils.Utilities;
import com.example.informationappjava.ui.chat.chatlist.adapters.ChatListAdapter;
import com.example.informationappjava.ui.chat.contacts.ContactListActivity;
import com.example.informationappjava.ui.chat.login.Constants;
import com.example.informationappjava.ui.chat.login.LoginActivity;
import com.example.informationappjava.ui.chat.chatlist.model.Chat;
import com.example.informationappjava.ui.chat.chatlist.model.ChatModel;
import com.example.informationappjava.ui.chat.view.ChatViewActivity;
import com.example.informationappjava.xmpp.ChatConnection;
import com.example.informationappjava.xmpp.ChatConnectionService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * The ChatlistActivity is used to get to the Chatlist and gives the Chats different functions and onclickevents.
 * It also gives access to the MeActivity.
 */
public class ChatListActivity extends AppCompatActivity implements
        ChatListAdapter.OnItemClickListener, ChatListAdapter.OnItemLongClickListener {

    private static final String LOGTAG = "ChatListActivity";
    private RecyclerView chatRecycler;
    private FloatingActionButton newConversationButton;
    private ChatListAdapter mAdapter;
    private BroadcastReceiver receiver;

    /**
     * The onCreate Button tests if the User is logged in with the logged_in_state boolean, if the User is not logged
     * in he gets sent to the LoginActivity.
     * If the User is already logged in it loads the Chats and provides an onclick event for the button to get to the
     * Contactlist.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatlist);

        boolean logged_in_state = PreferenceManager.getDefaultSharedPreferences(getApplicationContext())
                .getBoolean("xmpp_logged_in", false);
        if (!logged_in_state) {
            Log.d(LOGTAG, "Logged in state: " + logged_in_state);
            Intent intent = new Intent(ChatListActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();

        } else {
            if (!Utilities.isServiceRunning(ChatConnection.class, getApplicationContext())) {
                Log.d(LOGTAG, "Service not running, starting it.. ");
                Intent intent = new Intent(this, ChatConnectionService.class);
                startService(intent);

            } else {
                Log.d(LOGTAG, "The service is already running.");
            }
        }

        chatRecycler = findViewById(R.id.chatsRecyclerView);
        chatRecycler.setLayoutManager(new LinearLayoutManager(getBaseContext()));

        mAdapter = new ChatListAdapter(this);
        mAdapter.setOnItemClickListener(this);
        mAdapter.setOnItemLongClick(this);
        chatRecycler.setAdapter(mAdapter);

        newConversationButton = findViewById(R.id.new_conversation_floating_button);
        newConversationButton
                .setBackgroundTintList(getResources().getColorStateList(R.color.colorPrimary));
        newConversationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ChatListActivity.this, ContactListActivity.class);
                startActivity(i);
            }
        });
    }

    /**
     * Pauses the Receiver.
     */
    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }

    /**
     * Resumes the Receiver.
     */
    @Override
    protected void onResume() {
        super.onResume();
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                String action = intent.getAction();
                switch (action) {

                    case Constants.BroadCastMessages.UI_NEW_CHAT_ITEM:
                        mAdapter.onChatCountChange();
                }
            }
        };

        IntentFilter filter = new IntentFilter(Constants.BroadCastMessages.UI_NEW_CHAT_ITEM);
        registerReceiver(receiver, filter);
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
     * @return onOptionsItemSelected
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.me) {
            Intent i = new Intent(ChatListActivity.this, MeActivity.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * This function creates a clickevent on the Chatlist and starts the ChatViewActivity to access the Chat.
     *
     * @param contactJid
     * @param chatType
     */
    @Override
    public void onItemClick(String contactJid, Chat.ContactType chatType) {
        Intent intent = new Intent(ChatListActivity.this, ChatViewActivity.class);
        intent.putExtra("contact_jid", contactJid);
        intent.putExtra("chat_type", chatType);
        startActivity(intent);
    }

    /**
     * This function creates a longclickevent on the Chatlist that opens a PopupMenu to be able to delete the Chat.
     *
     * @param contactJid
     * @param chatUniqueId
     * @param anchor
     */
    @Override
    public void onItemLongClick(String contactJid, int chatUniqueId, View anchor) {
        PopupMenu popupMenu = new PopupMenu(ChatListActivity.this, anchor, Gravity.CENTER);

        //Inflating the Popup using .xml file
        popupMenu.getMenuInflater().inflate(R.menu.chat_list_popup_menu, popupMenu.getMenu());

        //registering Popup with OnMenuItemClickListener
        popupMenu.setOnMenuItemClickListener(new OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.delete_chat:
                        if (ChatModel.get(getApplicationContext()).deleteChat(chatUniqueId)) {
                            mAdapter.onChatCountChange();
                            Toast.makeText(
                                    ChatListActivity.this,
                                    "Chat deleted successfully!",
                                    Toast.LENGTH_SHORT
                            ).show();
                        }
                        break;
                }
                return true;
            }
        });
        popupMenu.show();
    }
}