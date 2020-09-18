package com.example.informationappjava.ui.chat.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.informationappjava.R;
import com.example.informationappjava.ui.chat.chatlist.model.ContactModel;
import com.example.informationappjava.ui.chat.contactdetails.ContactDetailsActivity;
import com.example.informationappjava.ui.chat.contactlist.ContactListActivity;
import com.example.informationappjava.ui.chat.login.Constants;
import com.example.informationappjava.ui.chat.login.Constants.BroadCastMessages;
import com.example.informationappjava.ui.chat.view.adapter.ChatMessageAdapter;
import com.example.informationappjava.ui.chat.view.keyboard.KeyboardUtil;
import com.example.informationappjava.ui.chat.view.keyboard.KeyboardUtil.KeyboardVisibilityListener;
import com.example.informationappjava.ui.chat.view.model.ChatMessage;
import com.example.informationappjava.ui.chat.view.model.ChatMessage.Type;
import com.example.informationappjava.ui.chat.view.model.ChatMessagesModel;
import com.example.informationappjava.xmpp.RoosterConnectionService;

public class ChatViewActivity extends AppCompatActivity implements
    ChatMessageAdapter.OnInformRecyclerViewToScrollDownListener, KeyboardVisibilityListener,
    ChatMessageAdapter.OnItemLongClickListener {

  private RecyclerView chatMessageRecyclerView;
  private EditText textSendEditText;
  private ImageButton sendMessageButton;
  ChatMessageAdapter adapter;
  private String counterpartJid;
  private BroadcastReceiver receiver;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_chat_view);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    //Get the counterpart Jid
    Intent intent = getIntent();
    counterpartJid = intent.getStringExtra("contact_jid");
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
        }
      }
    };

    IntentFilter filter = new IntentFilter(Constants.BroadCastMessages.UI_NEW_MESSAGE_FLAG);
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