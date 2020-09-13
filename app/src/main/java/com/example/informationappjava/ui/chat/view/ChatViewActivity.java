package com.example.informationappjava.ui.chat.view;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.informationappjava.R;
import com.example.informationappjava.ui.chat.view.adapter.ChatMessageAdapter;
import com.example.informationappjava.ui.chat.view.keyboard.KeyboardUtil;
import com.example.informationappjava.ui.chat.view.keyboard.KeyboardUtil.KeyboardVisibilityListener;
import com.example.informationappjava.ui.chat.view.model.ChatMessage;
import com.example.informationappjava.ui.chat.view.model.ChatMessage.Type;
import com.example.informationappjava.ui.chat.view.model.ChatMessagesModel;

public class ChatViewActivity extends AppCompatActivity implements
    ChatMessageAdapter.OnInformRecyclerViewToScrollDownListener, KeyboardVisibilityListener {

  private RecyclerView chatMessageRecyclerView;
  private EditText textSendEditText;
  private ImageButton sendMessageButton;
  ChatMessageAdapter adapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_chat_view);

    getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    chatMessageRecyclerView = findViewById(R.id.chatMessagesRecycler);
    chatMessageRecyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));

    adapter = new ChatMessageAdapter(getApplicationContext(),
        "user@example.com");
    adapter.setOnInformRecyclerViewToScrollDownListener(this);
    chatMessageRecyclerView.setAdapter(adapter);

    textSendEditText = findViewById(R.id.textInput);
    sendMessageButton = findViewById(R.id.textSendButton);
    sendMessageButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {

        ChatMessagesModel.get(getApplicationContext()).addMessage(
            new ChatMessage(textSendEditText.getText().toString(), System.currentTimeMillis(),
                Type.SENT,
                "user@server.com"));

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

    if (item.getItemId() == R.id.received_message) {
      ChatMessagesModel.get(getApplicationContext()).addMessage(
          new ChatMessage("This is a message you received!", System.currentTimeMillis(),
              Type.RECEIVED,
              "user@server.com"));
    }

    return super.onOptionsItemSelected(item);
  }

  @Override
  public void onInformRecyclerViewToScrollDown(int size) {
    chatMessageRecyclerView.scrollToPosition(size - 1);
  }

  @Override
  public void onKeyboardVisibilityChanged(boolean keyboardVisible) {
    adapter.informRecyclerViewToScrollDown();
  }
}