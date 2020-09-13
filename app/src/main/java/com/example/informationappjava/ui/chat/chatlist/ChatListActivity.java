package com.example.informationappjava.ui.chat.chatlist;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.informationappjava.R;
import com.example.informationappjava.ui.chat.chatlist.adapters.ChatListAdapter;
import com.example.informationappjava.ui.chat.view.ChatViewActivity;

public class ChatListActivity extends AppCompatActivity implements ChatListAdapter.OnItemClickListener{

    private RecyclerView chatRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatlist);

        chatRecycler = findViewById(R.id.chat_recyclerview);
        chatRecycler.setLayoutManager(new LinearLayoutManager(getBaseContext()));

        ChatListAdapter mAdapter = new ChatListAdapter(getApplicationContext());
        mAdapter.setOnItemClickListener(this);
        chatRecycler.setAdapter(mAdapter);
    }

    @Override
    public void onItemClick(String contactJid) {
        Intent intent = new Intent(ChatListActivity.this, ChatViewActivity.class);
        startActivity(intent);
    }
}