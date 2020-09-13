package com.example.informationappjava.ui.chat.view;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.informationappjava.R;
import com.example.informationappjava.ui.chat.view.adapter.ChatMessageAdapter;

public class ChatViewActivity extends AppCompatActivity {

  private RecyclerView chatMessageRecyclerView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_chat_view);

    getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    chatMessageRecyclerView = findViewById(R.id.chatMessagesRecycler);
    chatMessageRecyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));

    ChatMessageAdapter adapter = new ChatMessageAdapter(getApplicationContext(), "user@example.com");
    chatMessageRecyclerView.setAdapter(adapter);
  }
}