package com.example.informationappjava.ui.chat.contacts.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.example.informationappjava.persistence.ContactCursorWrapper;
import com.example.informationappjava.persistence.DatabaseBackend;

import java.util.ArrayList;
import java.util.List;

/**
 * The ContactModel class adds Contacts to the Contactlist and also lets the User delete and add Chats to the
 * Contactlist, aswell as in the DB.
 */
public class ContactModel {

    private static final String LOGTAG = "ContactModel";
    private static ContactModel sContactModel;
    private final Context mContext;
    private final SQLiteDatabase mDatabase;

    public List<Contact> getContacts() {
        List<Contact> contacts = new ArrayList<>();

        ContactCursorWrapper cursor = queryContacts(null, null);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                contacts.add(cursor.getContact());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return contacts;
    }

    public List<String> getContactJidStrings() {

        List<Contact> contacts = getContacts();
        List<String> stringJids = new ArrayList<>();
        for (Contact contact : contacts) {

            Log.d(LOGTAG, "Contact Jid: " + contact.getJid());
            stringJids.add(contact.getJid());

        }
        return stringJids;
    }

    /**
     * This function returns the ContactModel if it exists.
     * If it doesn't exist it creates a new one.
     *
     * @param context
     * @return sContactModel
     */
    public static ContactModel get(Context context) {
        if (sContactModel == null) {
            sContactModel = new ContactModel(context);
        }
        return sContactModel;
    }

    /**
     * This is a Constructor to call upon the ContactModel.
     *
     * @param context
     */
    private ContactModel(Context context) {
        mContext = context;
        mDatabase = DatabaseBackend.getInstance(mContext).getWritableDatabase();
    }


    /**
     * This function returns the Contacts by their Jid.
     *
     * @param jidString
     * @return mContact
     */
    public Contact getContactsByJidString(String jidString) {

        List<Contact> contacts = getContacts();
        List<String> stringJid = new ArrayList<>();

        Contact mContact = null;

        Log.d(LOGTAG, "Looping around contacts==========");

        for (Contact contact : contacts) {

            Log.d(LOGTAG, "Contact Jid: " + contact.getJid());
            Log.d(LOGTAG,
                    "Subscription type: " + contact.getTypeStringValue(contact.getSubscriptionType()));
            if (contact.getJid().equals(jidString)) {

                mContact = contact;
            }
        }
        return mContact;
    }


    /**
     * This function examines if the Contact is already added to the Contactlist.
     *
     * @param contactJid
     * @return !contacts.contains(contactJid)
     */
    public boolean isContactStranger(String contactJid) {
        List<String> contacts = getContactJidStrings();
        return !contacts.contains(contactJid);
    }

    /**
     * selects the Contact in a Database query.
     *
     * @param whereClause
     * @param whereArgs
     * @return new ContactCursorWrapper(cursor)
     */
    private ContactCursorWrapper queryContacts(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                Contact.TABLE_NAME,
                null, //Colums - null selects all Columns
                whereClause,
                whereArgs,
                null, //groupBy
                null, //having
                null //orderby
        );
        return new ContactCursorWrapper(cursor);
    }

    /**
     * This function adds a new Contact to the Contactlist View.
     *
     * @param c
     * @return mDatabase.insert
     */
    public boolean addContact(Contact c) {
        ContentValues values = c.getContentValues();
        return mDatabase.insert(Contact.TABLE_NAME, null, values) != -1;
    }

    /**
     * This function alters the ContactSubscription in the DB.
     *
     * @param contact
     * @return true, false
     */
    public boolean updateContactSubscription(Contact contact) {

        Contact mContact = contact;
        String jidString = contact.getJid();

        //Get new content values to add to db
        ContentValues values = contact.getContentValues();
        //db.update returns the number of affected rows in the db, if this return value is not zero, we succeeded

        int rows = mDatabase.update(Contact.TABLE_NAME, values, "jid = ? ", new String[]{jidString});
        Log.d(LOGTAG, rows + " rows affected in db");

        if (rows != 0) {

            Log.d(LOGTAG, "DB record update successful");
            return true;
        }
        return false;
    }

    /**
     * This function updates the subscription type of a contact.
     *
     * @param contact
     */
    public void updateContactSubscriptionOnSendSubscribed(String contact) {
        //When we send a subscribed, the pending_from changes to from
        Contact mContact = getContactsByJidString(contact);
        mContact.setPendingFrom(false);
        updateContactSubscription(mContact);
    }

    /**
     * This function deletes a Contact from the Contactlist.
     *
     * @param c
     * @return deleteContact(uniqueId)
     */
    public boolean deleteContact(Contact c) {
        int uniqueId = c.getPersistID();
        return deleteContact(uniqueId);
    }

    /**
     * This function deletes a Contact from the DB.
     *
     * @param uniqueId
     * @return true, false
     */
    public boolean deleteContact(int uniqueId) {
        int value = mDatabase.delete(Contact.TABLE_NAME, Contact.Cols.CONTACT_UNIQUE_ID + "=?",
                new String[]{String.valueOf(uniqueId)});

        if (value == 1) {
            Log.d(LOGTAG, "Successfully delted a record");
            return true;
        } else {
            Log.d(LOGTAG, "Could not delete record");
            return false;
        }
    }
}