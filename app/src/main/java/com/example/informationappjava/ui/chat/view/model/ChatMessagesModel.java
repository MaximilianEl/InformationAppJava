package com.example.informationappjava.ui.chat.view.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.example.informationappjava.persistence.ChatMessageCursorWrapper;
import com.example.informationappjava.persistence.DatabaseBackend;
import com.example.informationappjava.ui.chat.chatlist.model.ChatModel;

import java.util.ArrayList;
import java.util.List;

/**
 * The ChatMessageModel class adds Messages to the Chatview and also lets the User delete and add Messages.
 */
public class ChatMessagesModel {

  private static final String LOGTAG = "ChatMessagesModel";
  private static ChatMessagesModel chatMessagesModel;
  private final Context context;
  List<ChatMessage> messages;
  private final SQLiteDatabase mDatabase;

  public List<ChatMessage> getMessages(String counterpartJid) {

    List<ChatMessage> messages = new ArrayList<>();

    ChatMessageCursorWrapper cursor = queryMessages("contactJid= ?", new String[]{counterpartJid});

    try {
      cursor.moveToFirst();
      while (!cursor.isAfterLast()) {
        messages.add(cursor.getChatMessage());
        cursor.moveToNext();
      }
    } finally {
      cursor.close();
    }

    return messages;
  }

  /**
   * This function returns the ChatsModel if it exists.
   * If it doesn't exist it creates a new one.
   *
   * @param context
   * @return chatMessagesModel
   */
  public static ChatMessagesModel get(Context context) {
    if (chatMessagesModel == null) {
      chatMessagesModel = new ChatMessagesModel(context);
    }

    return chatMessagesModel;
  }

  /**
   * This is a Constructor to call upon the ChatMessagesModel.
   *
   * @param context
   */
  private ChatMessagesModel(Context context) {
    this.context = context;
    mDatabase = DatabaseBackend.getInstance(context).getWritableDatabase();
  }


  /**
   * @param message
   * @return
   */
  public boolean addMessage(ChatMessage message) {
    ContentValues values = message.getContentValues();
    if ((mDatabase.insert(ChatMessage.TABLE_NAME, null, values) == -1)) {
      return false;
    } else {
      ChatModel.get(context).updateLastMessageDetails(message);
      return true;
    }
  }

  /**
   * This function deletes a ChatMessage using the persistID.
   *
   * @param message
   * @return deleteMessage(message.getPersistID())
   */
  public boolean deleteMessage(ChatMessage message) {
    return deleteMessage(message.getPersistID());
  }

  /**
   * This function deletes a ChatMessage using the uniqueID.
   *
   * @param uniqueId
   * @return true, false
   */
  public boolean deleteMessage(int uniqueId) {
    int value = mDatabase
        .delete(ChatMessage.TABLE_NAME, ChatMessage.Cols.CHAT_MESSAGE_UNIQUE_ID + "=?",
            new String[]{String.valueOf(uniqueId)});

    if (value == 1) {
      Log.d(LOGTAG, "Successfully deleted a record");
      return true;
    } else {
      Log.d(LOGTAG, "Could not delete record");
      return false;
    }
  }

  /**
   *  selects the ChatMessage in a Database query.
   *
   * @param whereClause
   * @param whereArgs
   * @return new ChatMessageCursorWrapper(cursor)
   */
  private ChatMessageCursorWrapper queryMessages(String whereClause, String[] whereArgs) {
    Cursor cursor = mDatabase.query(
        ChatMessage.TABLE_NAME,
        null, //Columns - null selects all columns
        whereClause,
        whereArgs,
        null,
        null,
        null);
    return new ChatMessageCursorWrapper(cursor);
  }
}