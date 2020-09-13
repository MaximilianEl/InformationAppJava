package com.example.informationappjava.ui.chat;

import android.app.ActionBar;
import android.content.Intent;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.informationappjava.NavDrawer;
import com.example.informationappjava.R;

public class ChatLoginActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_chat_login);

    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
  }
}