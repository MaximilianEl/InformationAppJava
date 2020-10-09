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

public class ContactModel {

  private static final String LOGTAG = "ContactModel";
  private static ContactModel sContactModel;
  private final Context mContext;
  private final SQLiteDatabase mDatabase;

  public static ContactModel get(Context context) {
    if (sContactModel == null) {
      sContactModel = new ContactModel(context);
    }
    return sContactModel;
  }

  private ContactModel(Context context) {
    mContext = context;
    mDatabase = DatabaseBackend.getInstance(mContext).getWritableDatabase();
  }

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

  public List<String> getContactJidStrings() {

    List<Contact> contacts = getContacts();
    List<String> stringJids = new ArrayList<>();
    for (Contact contact : contacts) {

      Log.d(LOGTAG, "Contact Jid: " + contact.getJid());
      stringJids.add(contact.getJid());

    }
    return stringJids;
  }

  public boolean isContactStranger(String contactJid) {

    List<String> contacts = getContactJidStrings();
    return !contacts.contains(contactJid);
  }

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

  public boolean addContact(Contact c) {
    ContentValues values = c.getContentValues();
    return mDatabase.insert(Contact.TABLE_NAME, null, values) != -1;
  }

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

  public void updateContactSubscriptionOnSendSubscribed(String contact) {

    //When we send a subscribed, the pending_from changes to from
    Contact mContact = getContactsByJidString(contact);
    mContact.setPendingFrom(false);
    updateContactSubscription(mContact);
  }

  public boolean deleteContact(Contact c) {
    int uniqueId = c.getPersistID();
    return deleteContact(uniqueId);
  }

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
