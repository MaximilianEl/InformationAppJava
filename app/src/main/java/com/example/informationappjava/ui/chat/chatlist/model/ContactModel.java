package com.example.informationappjava.ui.chat.chatlist.model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
        return mContactList;
    }

    private ContactCursorWrapper queryContacts(String whereClause, String [] whereArgs){
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

    public void addContact(Contact c) {
        mContactList.add(c);
    }
}
