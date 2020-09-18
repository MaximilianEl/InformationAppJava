package com.example.informationappjava.ui.chat.login.model;

import android.content.ContentValues;
import android.content.Context;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.example.informationappjava.persistence.ChatCursorWrapper;
import com.example.informationappjava.persistence.DatabaseBackend;
import com.example.informationappjava.ui.chat.login.model.Chat.Cols;
import java.util.ArrayList;
import java.util.List;

public class ChatModel {

  private static final String LOGTAG = "ChatModel";
  private static ChatModel sChatsModel;
  private Context mContext;
  private SQLiteDatabase mDatabase;

  public static ChatModel get(Context context) {
    if (sChatsModel == null) {
      sChatsModel = new ChatModel(context);
    }
    return sChatsModel;
  }

  private ChatModel(Context context) {
    mContext = context;
    mDatabase = DatabaseBackend.getInstance(context).getReadableDatabase();
  }

  public List<Chat> getChats() {

    List<Chat> chats = new ArrayList<>();

    ChatCursorWrapper chatCursorWrapper = queryChats(null, null);

    try {

      chatCursorWrapper.moveToFirst();
      while (!chatCursorWrapper.isAfterLast()) {
        Log.d(LOGTAG, "Got a chat from db : Timestamp :" + chatCursorWrapper.getChat()
            .getLastMessageTimeStamp());
        chats.add(chatCursorWrapper.getChat());
        chatCursorWrapper.moveToNext();
      }
    } finally {
      chatCursorWrapper.close();
    }
    return chats;
  }

  public List<Chat> getChatsByJid(String jid) {

    List<Chat> chats = new ArrayList<>();
    ChatCursorWrapper cursor = queryChats(Cols.CONTACT_JID + "=?", new String[]{jid});

    try {

      cursor.moveToFirst();
      while (!cursor.isAfterLast()) {
        chats.add(cursor.getChat());
        cursor.moveToNext();
      }
    } finally {
      cursor.close();
    }
    return chats;

  }

  public boolean addChat(Chat chat) {
    ContentValues values = chat.getContentValues();
    if (mDatabase.insert(Chat.TABLE_NAME, null, values) == -1) {
      return false;
    } else {
      return true;
    }
  }

  public boolean deleteChat(Chat chat) {
    return deleteChat(chat.getPresistID());
  }

  public boolean deleteChat(int uniqueId) {

    int value = mDatabase.delete(Chat.TABLE_NAME, Cols.CHAT_UNIQUE_ID + "=?",
        new String[]{String.valueOf(uniqueId)});

    if (value == 1) {

      Log.d(LOGTAG, "Successfully deleted a record");
      return true;

    } else {

      Log.d(LOGTAG, "Could not delete record");
      return false;

    }
  }

  private ChatCursorWrapper queryChats(String whereClause, String[] whereArgs) {

    Cursor cursor = mDatabase.query(
        Chat.TABLE_NAME,
        null, //Columns - null selects all columns
        whereClause,
        whereArgs,
        null, //groupBy
        null, //having
        null //orderBy
    );
    return new ChatCursorWrapper(cursor);
  }
}
