package com.example.informationappjava.persistence;

import android.database.Cursor;
import android.database.CursorWrapper;
import com.example.informationappjava.ui.chat.chatlist.model.Contact;
import com.example.informationappjava.ui.chat.chatlist.model.Contact.Cols;
import com.example.informationappjava.ui.chat.chatlist.model.Contact.SubscriptionType;

public class ContactCursorWrapper extends CursorWrapper {

  public ContactCursorWrapper(Cursor cursor) {
    super(cursor);
  }

  public Contact getContact() {
    String subscriptionTypeString = getString(getColumnIndex(Contact.Cols.SUBSCRIPTION_TYPE));
    String jid = getString(getColumnIndex(Contact.Cols.CONTACT_JID));
    int contactUniqueId = getInt(getColumnIndex(Contact.Cols.CONTACT_UNIQUE_ID));
    String profileImagePath = getString(getColumnIndex(Contact.Cols.PROFILE_IMAGE_PATH));

    int pendingFromInt = getInt(getColumnIndex(Cols.PENDING_STATUS_FROM));
    int pendingToInt = getInt(getColumnIndex(Cols.PENDING_STATUS_TO));
    int onlineStatusInt = getInt(getColumnIndex(Cols.ONLINE_STATUS));

    Contact.SubscriptionType subscriptionType = null;

    if (subscriptionTypeString.equals("NONE")) {
      subscriptionType = SubscriptionType.NONE;
    } else if (subscriptionTypeString.equals("FROM")) {
      subscriptionType = SubscriptionType.FROM;
    } else if (subscriptionTypeString.equals("TO")) {
      subscriptionType = SubscriptionType.TO;
    } else if (subscriptionTypeString.equals("BOTH")) {
      subscriptionType = SubscriptionType.BOTH;
    }

    Contact contact = new Contact(jid, subscriptionType);
    contact.setPersistID(contactUniqueId);
    contact.setProfileImagePath(profileImagePath);

    contact.setPendingFrom((pendingFromInt == 0) ? false : true);
    contact.setPendingTo((pendingToInt == 0) ? false : true);
    contact.setOnlineStatus((onlineStatusInt == 0) ? false : true);
    return contact;
  }
}
