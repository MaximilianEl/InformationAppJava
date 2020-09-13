package com.example.informationappjava.ui.chat.chatlist;

import android.content.Intent;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.informationappjava.R;
import com.example.informationappjava.ui.chat.chatlist.adapters.ChatListAdapter;
import com.example.informationappjava.ui.chat.contactlist.ContactListActivity;
import com.example.informationappjava.ui.chat.view.ChatViewActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ChatListActivity extends AppCompatActivity implements ChatListAdapter.OnItemClickListener{

    private RecyclerView chatRecycler;
    private FloatingActionButton  newConversationButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatlist);

        chatRecycler = findViewById(R.id.chatsRecyclerView);
        chatRecycler.setLayoutManager(new LinearLayoutManager(getBaseContext()));

        ChatListAdapter mAdapter = new ChatListAdapter(getApplicationContext());
        mAdapter.setOnItemClickListener(this);
        chatRecycler.setAdapter(mAdapter);

        newConversationButton = findViewById(R.id.new_conversation_floating_button);
        newConversationButton.setBackgroundTintList(getResources().getColorStateList(R.color.colorPrimary));
        newConversationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ChatListActivity.this, ContactListActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    public void onItemClick(String contactJid) {
        Intent intent = new Intent(ChatListActivity.this, ChatViewActivity.class);
        startActivity(intent);
    }
}