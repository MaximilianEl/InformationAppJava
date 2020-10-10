package com.example.informationappjava.ui.chat.contacts;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.informationappjava.R;
import com.example.informationappjava.ui.chat.contacts.model.Contact;
import com.example.informationappjava.ui.chat.contacts.model.Contact.SubscriptionType;
import com.example.informationappjava.ui.chat.contacts.model.ContactModel;
import com.example.informationappjava.xmpp.ChatConnection;
import com.example.informationappjava.xmpp.ChatConnectionService;

/**
 *
 */
public class ContactDetailsActivity extends AppCompatActivity {

  private static final String LOGTAG = "ContactDetailsActivity";
  private String contactJid;
  private ImageView profileImage;
  private CheckBox fromCheckBox;
  private CheckBox toCheckBox;
  private Context context;
  private TextView pendingFrom;
  private TextView pendingTo;

  /**
   * @param savedInstanceState
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_contact_details);

    context = getApplicationContext();

    //Get the contact Jid
    Intent intent = getIntent();
    contactJid = intent.getStringExtra("contact_jid");
    setTitle(contactJid);

    profileImage = findViewById(R.id.contact_details_user_profile);

    ChatConnection rc = ChatConnectionService.getConnection();

    profileImage.setImageResource(R.drawable.ic_baseline_person_24);
    if (rc != null) {
      String imageAbsPath = rc.getProfileImageAbsolutePath(contactJid);
      if (imageAbsPath != null) {
        Drawable d = Drawable.createFromPath(imageAbsPath);
        profileImage.setImageDrawable(d);
      }
    }

    pendingFrom = findViewById(R.id.pending_from);
    pendingTo = findViewById(R.id.pending_to);

    fromCheckBox = findViewById(R.id.them_to_me);
    fromCheckBox.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {

        if (fromCheckBox.isChecked()) {
          //There is nothing to do here
          Log.d(LOGTAG, "The FROM checkbox is checked");
        } else {

          //Send unsubscribe to cancel subscription
          Log.d(LOGTAG, "The FROM checkbox is UNchecked");
          if (ChatConnectionService.getConnection().unsubscribed(contactJid)) {
            Toast
                .makeText(context, "Successfully stopped sending presence updates to " + contactJid,
                    Toast.LENGTH_SHORT);
          }
        }
      }
    });

    toCheckBox = findViewById(R.id.me_to_them);
    toCheckBox.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {

        if (toCheckBox.isChecked()) {

          //Send subscription request
          Log.d(LOGTAG, "The TO checkbox is checked");
          if (ChatConnectionService.getConnection().subscribe(contactJid)) {
            Toast.makeText(context, "Subscription request sent to " + contactJid,
                Toast.LENGTH_SHORT);
          } else {

            //Send them an unsubscribe
            Log.d(LOGTAG, "The TO checkbox is UNchecked");
            if (ChatConnectionService.getConnection().unsubsribe(contactJid)) {
              Toast.makeText(context,
                  "You successfully stopped getting precense updates from " + contactJid,
                  Toast.LENGTH_LONG);
            }
          }
        }
      }
    });

    if (!ContactModel.get(getApplication()).isContactStranger(contactJid)) {

      Contact contact = ContactModel.get(getApplicationContext())
          .getContactsByJidString(contactJid);
      Contact.SubscriptionType subscriptionType = contact.getSubscriptionType();

      if (subscriptionType == SubscriptionType.NONE) {

        fromCheckBox.setEnabled(false);
        fromCheckBox.setChecked(false);
        toCheckBox.setChecked(false);
      } else if (subscriptionType == SubscriptionType.FROM) {

        fromCheckBox.setEnabled(true);
        fromCheckBox.setChecked(true);
        toCheckBox.setChecked(false);
      } else if (subscriptionType == SubscriptionType.TO) {

        fromCheckBox.setEnabled(false);
        fromCheckBox.setChecked(false);
        toCheckBox.setChecked(true);
      } else if (subscriptionType == SubscriptionType.BOTH) {

        fromCheckBox.setEnabled(true);
        fromCheckBox.setChecked(true);
        toCheckBox.setChecked(true);
      }

      if (contact.isPendingFrom()) {

        pendingFrom.setVisibility(View.VISIBLE);
      } else {

        pendingFrom.setVisibility(View.GONE);
      }

      if (contact.isPendingTo()) {

        pendingTo.setVisibility(View.VISIBLE);
      } else {

        pendingTo.setVisibility(View.GONE);
      }
    } else {

      fromCheckBox.setEnabled(false);
      fromCheckBox.setChecked(false);
      toCheckBox.setChecked(false);
      toCheckBox.setEnabled(true);
    }
  }
}