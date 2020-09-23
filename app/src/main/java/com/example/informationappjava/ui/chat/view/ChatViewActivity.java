package com.example.informationappjava.ui.chat.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.informationappjava.R;
import com.example.informationappjava.ui.chat.chatlist.model.Contact;
import com.example.informationappjava.ui.chat.chatlist.model.Contact.SubscriptionType;
import com.example.informationappjava.ui.chat.chatlist.model.ContactModel;
import com.example.informationappjava.ui.chat.contactdetails.ContactDetailsActivity;
import com.example.informationappjava.ui.chat.login.Constants;
import com.example.informationappjava.ui.chat.login.Constants.BroadCastMessages;
import com.example.informationappjava.ui.chat.login.model.Chat;
import com.example.informationappjava.ui.chat.view.adapter.ChatMessageAdapter;
import com.example.informationappjava.ui.chat.view.keyboard.KeyboardUtil;
import com.example.informationappjava.ui.chat.view.keyboard.KeyboardUtil.KeyboardVisibilityListener;
import com.example.informationappjava.xmpp.RoosterConnectionService;

public class ChatViewActivity extends AppCompatActivity implements
        ChatMessageAdapter.OnInformRecyclerViewToScrollDownListener, KeyboardVisibilityListener,
        ChatMessageAdapter.OnItemLongClickListener {

    private static final String LOGTAG = "ChatViewActivity";
    private RecyclerView chatMessageRecyclerView;
    private EditText textSendEditText;
    private ImageButton sendMessageButton;
    ChatMessageAdapter adapter;
    private String counterpartJid;
    private BroadcastReceiver receiver;
    private Context context;

    private View snackBar;
    private View snackBarStranger;
    private TextView snackBarActionAccept;
    private TextView snackBarActionDeny;
    private TextView snackBarStrangerAddContact;
    private TextView snackBarStrangerBlock;

    private Chat.ContactType chatType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_view);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Get the counterpart Jid
        Intent intent = getIntent();
        counterpartJid = intent.getStringExtra("contact_jid");
        chatType = (Chat.ContactType) intent.getSerializableExtra("chat_type");
        setTitle(counterpartJid);

        chatMessageRecyclerView = findViewById(R.id.chatMessagesRecycler);
        chatMessageRecyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));

        adapter = new ChatMessageAdapter(getApplicationContext(), counterpartJid);
        adapter.setOnInformRecyclerViewToScrollDownListener(this);
        adapter.setOnItemLongClickListener(this);
        chatMessageRecyclerView.setAdapter(adapter);

        textSendEditText = findViewById(R.id.textInput);
        sendMessageButton = findViewById(R.id.textSendButton);
        sendMessageButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                RoosterConnectionService.getConnection()
                        .sendMessage(textSendEditText.getText().toString(), counterpartJid);
                adapter.onMessageAdd();
                textSendEditText.getText().clear();
            }
        });

        Contact contactCheck = ContactModel.get(getApplicationContext())
                .getContactsByJidString(counterpartJid);

        if (contactCheck.isOnlineStatus()) {

            Log.d(LOGTAG, counterpartJid + " is ONLINE");
            sendMessageButton.setImageDrawable(
                    ContextCompat.getDrawable(ChatViewActivity.this, R.drawable.ic_send_text_online));
        } else {

            sendMessageButton.setImageDrawable(
                    ContextCompat.getDrawable(ChatViewActivity.this, R.drawable.ic_send_text_offline));
            Log.d(LOGTAG, counterpartJid + " is OFFLINE");
        }

        snackBar = findViewById(R.id.snackbar);
        snackBarStranger = findViewById(R.id.snackbar_stranger);

        if (!ContactModel.get(context).isContactStranger(counterpartJid)) {
            snackBarStranger.setVisibility(View.GONE);
            Log.d(LOGTAG, counterpartJid + " is not a stranger");
            Contact contact = ContactModel.get(this).getContactsByJidString(counterpartJid);
            Log.d(LOGTAG, "We got a contact with JID : " + contact.getJid());

            if (contact.isPendingFrom()) {
                Log.d(LOGTAG, " Your subscription to " + contact.getJid() + " is in the FROM direction is");
                int paddingBottom = getResources()
                        .getDimensionPixelOffset(R.dimen.chatview_recycler_view_padding_normal);
                chatMessageRecyclerView.setPadding(0, 0, 0, paddingBottom);
                snackBar.setVisibility(View.VISIBLE);
            } else {
                int paddingBottom = getResources()
                        .getDimensionPixelOffset(R.dimen.chatview_recycler_view_padding_huge);
                chatMessageRecyclerView.setPadding(0, 0, 0, paddingBottom);
                snackBar.setVisibility(View.GONE);
            }
        } else {
            if (chatType == Chat.ContactType.STRANGER) {
                int paddingBottom = getResources()
                        .getDimensionPixelOffset(R.dimen.chatview_recycler_view_padding_normal);
                chatMessageRecyclerView.setPadding(0, 0, 0, paddingBottom);
                snackBar.setVisibility(View.VISIBLE);
                snackBarStranger.setVisibility(View.GONE);
            } else {
                Log.d(LOGTAG, counterpartJid + " is a stranger. Hiding snackbar");
                int paddingBottom = getResources()
                        .getDimensionPixelOffset(R.dimen.chatview_recycler_view_padding_huge);
                chatMessageRecyclerView.setPadding(0, 0, 0, paddingBottom);
                snackBarStranger.setVisibility(View.VISIBLE);
                snackBar.setVisibility(View.GONE);
            }
        }

        snackBarActionAccept = findViewById(R.id.snackbar_action_accept);
        snackBarActionAccept.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContactModel.get(context).isContactStranger(counterpartJid)) {
                    if (ContactModel.get(context)
                            .addContact(new Contact(counterpartJid, SubscriptionType.FROM))) {
                        Log.d(LOGTAG,
                                "Previously stranger contact " + counterpartJid + " now successfully added to");
                    }
                }
                Log.d(LOGTAG, " Accept presence subscription from :" + counterpartJid);
                if (RoosterConnectionService.getConnection().subscribed(counterpartJid)) {
                    ContactModel.get(getApplicationContext())
                            .updateContactSubscriptionOnSendSubscribed(counterpartJid);
                    Toast.makeText(ChatViewActivity.this, "Subscription from " + counterpartJid + "accepted",
                            Toast.LENGTH_LONG).show();
                }
                snackBar.setVisibility(View.GONE);
            }
        });

        snackBarActionDeny = findViewById(R.id.snackbar_action_accept);
        snackBarActionDeny.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                //User denies presence subscription
                Log.d(LOGTAG, "Deny presence subscription from: " + counterpartJid);
                if (RoosterConnectionService.getConnection().unsubsribe(counterpartJid)) {

                    ContactModel.get(getApplicationContext())
                            .updateContactSubscriptionOnSendSubscribed(counterpartJid);

                    //No action required in the Contact Model regarding subscriptions
                    Toast.makeText(getApplicationContext(), "Subscription Rejected", Toast.LENGTH_LONG);
                }
                snackBar.setVisibility(View.GONE);
            }
        });

        snackBarStrangerAddContact = findViewById(R.id.snackbar_action_accept);
        snackBarStrangerAddContact.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ContactModel.get(getApplicationContext())
                        .addContact(new Contact(counterpartJid, SubscriptionType.TO))) {

                    if (RoosterConnectionService.getConnection().addContactToRoster(counterpartJid)) {

                        Log.d(LOGTAG, counterpartJid + " successfully added to remote roster");
                        snackBarStranger.setVisibility(View.GONE);
                    }
                }
            }
        });

        snackBarStrangerBlock = findViewById(R.id.snackbar_action_accept);
        snackBarStrangerBlock.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(ChatViewActivity.this, "Feature not implemented yet", Toast.LENGTH_LONG);
            }
        });

        KeyboardUtil.setKeyboardVisibilityListener(this, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_chat_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.contact_details_chat_view) {

            Intent intent = new Intent(ChatViewActivity.this, ContactDetailsActivity.class);
            intent.putExtra("contact_jid", counterpartJid);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }

    @Override
    protected void onResume() {
        super.onResume();

        adapter.informRecyclerViewToScrollDown();

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                switch (action) {
                    case BroadCastMessages.UI_NEW_MESSAGE_FLAG:
                        adapter.onMessageAdd();
                        return;

                    case Constants.BroadCastMessages.UI_ONLINE_STATUS_CHANGE:
                        String contactJid = intent.getStringExtra(Constants.ONLINE_STATUS_CHANGE_CONTACT);
                        Log.d(LOGTAG,
                                "Online status change for " + contactJid + " received in ChatViewActivity");
                        if (counterpartJid.equals(contactJid)) {

                            Contact mContact = ContactModel.get(getApplicationContext())
                                    .getContactsByJidString(contactJid);
                            if (mContact.isOnlineStatus()) {

                                Log.d(LOGTAG, "From Chat View, user " + contactJid + " has come ONLINE");
                                sendMessageButton.setImageDrawable(ContextCompat
                                        .getDrawable(ChatViewActivity.this, R.drawable.ic_send_text_online));
                            } else {

                                Log.d(LOGTAG, "From Chat View, user " + contactJid + " has gone OFFLINE");
                                sendMessageButton.setImageDrawable(ContextCompat
                                        .getDrawable(ChatViewActivity.this, R.drawable.ic_send_text_offline));
                            }
                        }
                }
            }
        };

        IntentFilter filter = new IntentFilter(Constants.BroadCastMessages.UI_NEW_MESSAGE_FLAG);
        filter.addAction(Constants.BroadCastMessages.UI_ONLINE_STATUS_CHANGE);
        registerReceiver(receiver, filter);
    }

    @Override
    public void onInformRecyclerViewToScrollDown(int size) {
        chatMessageRecyclerView.scrollToPosition(size - 1);
    }

    @Override
    public void onKeyboardVisibilityChanged(boolean keyboardVisible) {
        adapter.informRecyclerViewToScrollDown();
    }

    @Override
    public void onItemLongClick(int uniqueId, View anchor) {

        PopupMenu popup = new PopupMenu(ChatViewActivity.this, anchor, Gravity.CENTER);

        popup.getMenuInflater().inflate(R.menu.chat_view_popup_menu, popup.getMenu());

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.delete_message:
                        if (ContactModel.get(getApplicationContext()).deleteContact(uniqueId)) {
                            adapter.onMessageAdd();
                            Toast.makeText(ChatViewActivity.this,
                                    "Message deleted successfully ",
                                    Toast.LENGTH_SHORT).show();
                        }
                        break;
                }
                return true;
            }
        });
        popup.show();
    }
}