package com.example.informationappjava.persistence;

import android.database.Cursor;
import android.database.CursorWrapper;
import com.example.informationappjava.ui.chat.contacts.model.Contact;
import com.example.informationappjava.ui.chat.contacts.model.Contact.Cols;
import com.example.informationappjava.ui.chat.contacts.model.Contact.SubscriptionType;

/**
 * The ContactCursorWrapper class is used to access the Contact information if the Cursor accesses it.
 */
public class ContactCursorWrapper extends CursorWrapper {

    /**
     * This is a constructor to call upon the ContactCursorWrapper class.
     *
     * @param cursor
     */
    public ContactCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    /**
     * This function is used to get the Contact Informations for a specific Contact like his Subscription status.
     *
     * @return
     */
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

        contact.setPendingFrom(pendingFromInt != 0);
        contact.setPendingTo(pendingToInt != 0);
        contact.setOnlineStatus(onlineStatusInt != 0);
        return contact;
    }
}
