package com.example.informationappjava.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import androidx.annotation.Nullable;
import com.example.informationappjava.ui.chat.contacts.model.Contact;
import com.example.informationappjava.ui.chat.contacts.model.Contact.Cols;
import com.example.informationappjava.ui.chat.chatlist.model.Chat;
import com.example.informationappjava.ui.chat.view.model.ChatMessage;


/**
 *
 */
public class DatabaseBackend extends SQLiteOpenHelper {

    private static final String LOGTAG = "DatabaseBackend";
    private static DatabaseBackend instance = null;
    private static final String DATABASE_NAME = "hsosChat";
    private static final int DATABASE_VERSION = 2;

    //Create Chat List Table
    private static String CREATE_CHAT_LIST_STATEMENT = "create table "
            + Chat.TABLE_NAME + "("
            + Chat.Cols.CHAT_UNIQUE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + Chat.Cols.CONTACT_JID + " TEXT,"
            + Chat.Cols.LAST_MESSAGE + " TEXT, "
            + Chat.Cols.CONTACT_TYPE + " TEXT, "
            + Chat.Cols.LAST_MESSAGE_TIME_STAMP + " NUMBER, "
            + Chat.Cols.UNREAD_COUNT + " NUMBER"
            + ");";

    //Create Contact List Table
    private static String CREATE_CONTACT_LIST_STATEMENT = "create table "
            + Contact.TABLE_NAME + "("
            + Contact.Cols.CONTACT_UNIQUE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + Contact.Cols.SUBSCRIPTION_TYPE + " TEXT, "
            + Contact.Cols.CONTACT_JID + " TEXT,"
            + Contact.Cols.PROFILE_IMAGE_PATH + " TEXT,"
            + Cols.PENDING_STATUS_FROM + " NUMBER DEFAULT 0,"
            + Cols.PENDING_STATUS_TO + " NUMBER DEFAULT 0,"
            + Cols.ONLINE_STATUS + " NUMBER DEFAULT 0"
            + ");";

    //Create Message List Table
    private static String CREATE_CHAT_MESSAGE_STATEMENT = "create table "
            + ChatMessage.TABLE_NAME + "("
            + ChatMessage.Cols.CHAT_MESSAGE_UNIQUE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + ChatMessage.Cols.MESSAGE + " TEXT, "
            + ChatMessage.Cols.MESSAGE_TYPE + " TEXT,"
            + ChatMessage.Cols.TIMESTAMP + " NUMBER, "
            + ChatMessage.Cols.CONTACT_JID + " TEXT"
            + ");";


    /**
     * @param context
     */
    private DatabaseBackend(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    /**
     * @param context
     * @return
     */
    public static synchronized DatabaseBackend getInstance(Context context) {
        Log.d(LOGTAG, "Getting db instance");
        if (instance == null) {
            instance = new DatabaseBackend(context);
        }
        return instance;
    }


    /**
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(LOGTAG, "Creating the tables");
        db.execSQL(CREATE_CONTACT_LIST_STATEMENT);
        db.execSQL(CREATE_CHAT_LIST_STATEMENT);
        db.execSQL(CREATE_CHAT_MESSAGE_STATEMENT);
    }


    /**
     * @param sqLiteDatabase
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

        if (oldVersion < 2 && newVersion >= 2) {
            Log.d(LOGTAG, "Upgrading db to version 2...");
            sqLiteDatabase.execSQL("ALTER TABLE " + Contact.TABLE_NAME + " ADD COLUMN "
                    + Cols.PENDING_STATUS_TO + " NUMBER DEFAULT 0");
            sqLiteDatabase.execSQL("ALTER TABLE " + Contact.TABLE_NAME + " ADD COLUMN "
                    + Cols.PENDING_STATUS_FROM + " NUMBER DEFAULT 0");
            sqLiteDatabase.execSQL("ALTER TABLE " + Contact.TABLE_NAME + " ADD COLUMN "
                    + Cols.ONLINE_STATUS + " NUMBER DEFAULT 0");
        }
    }
}