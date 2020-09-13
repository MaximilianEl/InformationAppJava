package com.example.informationappjava.ui.chat.chatlist.model;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class ContactModel {

    private static final String LOGTAG = "ContactModel";
    private static ContactModel sContactModel;
    private Context mContext;
    private List<Contact> mContactList;

    public static ContactModel get(Context context) {
        if (sContactModel == null) {
            sContactModel = new ContactModel(context);
        }
        return sContactModel;
    }

    private ContactModel(Context context) {
        mContext = context;
        mContactList = new ArrayList<>();

        Contact contact1 = new Contact("Max", Contact.SubscriptionType.NONE_NONE);
        mContactList.add(contact1);
        Contact contact2 = new Contact("Sebastian", Contact.SubscriptionType.NONE_NONE);
        mContactList.add(contact2);
        Contact contact3 = new Contact("nochmalSebastian?", Contact.SubscriptionType.NONE_NONE);
        mContactList.add(contact3);
        mContactList.add(contact3);
        mContactList.add(contact3);
        mContactList.add(contact3);
        mContactList.add(contact3);
        mContactList.add(contact3);
        mContactList.add(contact3);
        mContactList.add(contact3);
        mContactList.add(contact3);
        mContactList.add(contact3);
        mContactList.add(contact3);
        mContactList.add(contact3);

    }

    public List<Contact> getContacts() {
        return mContactList;
    }

    public void addContact(Contact c) {
        mContactList.add(c);
    }
}
