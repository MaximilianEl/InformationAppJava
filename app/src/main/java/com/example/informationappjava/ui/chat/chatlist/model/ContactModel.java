package com.example.informationappjava.ui.chat.chatlist.model;

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
    private Context mContext;
    private SQLiteDatabase mDatabase;

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
        if ((mDatabase.insert(Contact.TABLE_NAME, null, values) == -1)) {
            return false;
        } else {
            return true;
        }
    }

    public boolean deleteContact(Contact c){
        int uniqueId = c.getPersistID();
        return deleteContact(uniqueId);
    }

    public boolean deleteContact(int uniqueId) {
        int value = mDatabase.delete(Contact.TABLE_NAME, Contact.Cols.CONTACT_UNIQUE_ID + "=?", new String[]{String.valueOf(uniqueId)});

        if (value == 1) {
            Log.d(LOGTAG, "Successfully delted a record");
            return true;
        } else {
            Log.d(LOGTAG, "Could not delete record");
            return false;
        }
    }
}
