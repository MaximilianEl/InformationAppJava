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
import com.example.informationappjava.xmpp.RoosterConnection;
import com.example.informationappjava.xmpp.RoosterConnectionService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ChatListActivity extends AppCompatActivity implements
        ChatListAdapter.OnItemClickListener, ChatListAdapter.OnItemLongClickListener {

    private static final String LOGTAG = "ChatListActivity";
    private RecyclerView chatRecycler;
    private FloatingActionButton newConversationButton;
    private ChatListAdapter mAdapter;
    private BroadcastReceiver receiver;

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

            if (!Utilities.isServiceRunning(RoosterConnection.class, getApplicationContext())) {

                Log.d(LOGTAG, "Service not running, starting it.. ");
                Intent intent = new Intent(this, RoosterConnectionService.class);
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

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }

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
                        return;
                }
            }
        };

        IntentFilter filter = new IntentFilter(Constants.BroadCastMessages.UI_NEW_CHAT_ITEM);
        registerReceiver(receiver, filter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_me_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.me) {

            Intent i = new Intent(ChatListActivity.this, MeActivity.class);
            startActivity(i);

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(String contactJid, Chat.ContactType chatType) {
        Intent intent = new Intent(ChatListActivity.this, ChatViewActivity.class);
        intent.putExtra("contact_jid", contactJid);
        intent.putExtra("chat_type", chatType);
        startActivity(intent);
    }

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