package com.example.informationappjava.ui.chat.chatlist;

import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.informationappjava.R;
import com.example.informationappjava.ui.chat.MeActivity;
import com.example.informationappjava.ui.chat.Utilities;
import com.example.informationappjava.ui.chat.chatlist.adapters.ChatListAdapter;
import com.example.informationappjava.ui.chat.contactlist.ContactListActivity;
import com.example.informationappjava.ui.chat.login.LoginActivity;
import com.example.informationappjava.ui.chat.view.ChatViewActivity;
import com.example.informationappjava.xmpp.RoosterConnection;
import com.example.informationappjava.xmpp.RoosterConnectionService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ChatListActivity extends AppCompatActivity implements
    ChatListAdapter.OnItemClickListener {

  private static final String LOGTAG = "ChatListActivity";
  private RecyclerView chatRecycler;
  private FloatingActionButton newConversationButton;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_chatlist);

    boolean loggedInState = PreferenceManager.getDefaultSharedPreferences(getApplicationContext())
        .getBoolean("xmpp_logged_in", false);
    if (!loggedInState) {
      Log.d(LOGTAG, "Logged in state: " + loggedInState);
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

    ChatListAdapter mAdapter = new ChatListAdapter(getApplicationContext());
    mAdapter.setOnItemClickListener(this);
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
  public void onItemClick(String contactJid) {
    Intent intent = new Intent(ChatListActivity.this, ChatViewActivity.class);
    startActivity(intent);
  }
}