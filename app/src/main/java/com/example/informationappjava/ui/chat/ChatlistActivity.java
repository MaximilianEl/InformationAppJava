package com.example.informationappjava.ui.chat;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.informationappjava.R;
import com.example.informationappjava.ui.chat.adapters.ChatListAdapter;

public class ChatlistActivity extends AppCompatActivity {

    private RecyclerView chatRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatlist);

        chatRecycler = findViewById(R.id.chat_recyclerview);
        chatRecycler.setLayoutManager(new LinearLayoutManager(getBaseContext()));

        ChatListAdapter mAdapter = new ChatListAdapter(getApplicationContext());
        chatRecycler.setAdapter(mAdapter);
    }
}