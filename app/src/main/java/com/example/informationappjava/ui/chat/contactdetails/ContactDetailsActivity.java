package com.example.informationappjava.ui.chat.contactdetails;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.informationappjava.R;

public class ContactDetailsActivity extends AppCompatActivity {

    private static final String LOGTAG = "ContactDetailsActivity";
    private String contactJid;
    private ImageView profileImage;
    private CheckBox fromCheckBox;
    private CheckBox toCheckBox;
    private Context context;

    private TextView pendingFrom;
    private TextView pendingTo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_details);

        context = getApplicationContext();

        //Get the contact Jid
        Intent intent = getIntent();
        contactJid = intent.getStringExtra("contact_jid");
        setTitle(contactJid);

        pendingFrom = findViewById(R.id.pending_from);
        pendingTo = findViewById(R.id.pending_to);

        fromCheckBox = findViewById(R.id.them_to_me);
        fromCheckBox.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        toCheckBox = findViewById(R.id.me_to_them);
        toCheckBox.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                if ( toCheckBox.isChecked()) {

                    Log.d(LOGTAG, "The TO checkbox is checked");
                }
            }
        });
    }
}