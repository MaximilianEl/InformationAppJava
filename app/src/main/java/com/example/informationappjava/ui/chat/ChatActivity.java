package com.example.informationappjava.ui.chat;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.informationappjava.R;

public class ChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
    }

    //Um den Username aus dem vorherigen Ding zu bekommen
    Bundle bundle = getIntent().getExtras();
}