package com.example.informationappjava.ui.chat.contactlist;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.widget.EditText;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.informationappjava.ui.chat.contactlist.adapter.ContactListAdapter;
import com.example.informationappjava.ui.chat.view.ChatViewActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;

import com.example.informationappjava.R;

public class ContactListActivity extends AppCompatActivity implements ContactListAdapter.OnItemClickListener {

    private RecyclerView contactListRecyclerView;
    private static final String LOGTAG = "ContactListActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton newcontactbutton = findViewById(R.id.newcontactbutton);
        newcontactbutton.setBackgroundTintList(getResources().getColorStateList(R.color.colorPrimary));
        newcontactbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // A Dialog should pop up but it doesn't
                addContact();
            }
        });

        contactListRecyclerView = findViewById(R.id.contact_list_recycler_view);
        contactListRecyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));

        ContactListAdapter mAdapter = new ContactListAdapter(getApplicationContext());
        mAdapter.setmOnItemClickListener(this);
        contactListRecyclerView.setAdapter(mAdapter);
    }

    private void addContact() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ContactListActivity.this);
        builder.setTitle(R.string.add_contact_label_text);
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton(R.string.add_contact_confirm_text, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.d(LOGTAG, "User clicked on OK");
            }
        });
        builder.setNegativeButton(R.string.add_contact_cancel_text, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.d(LOGTAG, "User clicked on Cancel");
                dialogInterface.cancel();
            }
        });
    }

    @Override
    public void onItemClick(String contactJid) {
        Intent intent = new Intent(ContactListActivity.this, ChatViewActivity.class);
        startActivity(intent);
    }
}