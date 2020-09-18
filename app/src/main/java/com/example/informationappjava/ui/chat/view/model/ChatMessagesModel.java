package com.example.informationappjava.ui.chat.view.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.example.informationappjava.persistence.ChatMessageCursorWrapper;
import com.example.informationappjava.persistence.DatabaseBackend;
import com.example.informationappjava.ui.chat.login.model.Chat;
import com.example.informationappjava.ui.chat.login.model.ChatModel;

import java.util.ArrayList;
import java.util.List;

public class ChatMessagesModel {

    private static final String LOGTAG = "ChatMessagesModel";
    private static ChatMessagesModel chatMessagesModel;
    private Context context;
    List<ChatMessage> messages;
    private SQLiteDatabase mDatabase;

    public static ChatMessagesModel get(Context context) {
        if (chatMessagesModel == null) {
            chatMessagesModel = new ChatMessagesModel(context);
        }

        return chatMessagesModel;
    }

    private ChatMessagesModel(Context context) {
        this.context = context;

        mDatabase = DatabaseBackend.getInstance(context).getWritableDatabase();

    }

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

    public boolean addMessage(ChatMessage message) {
        ContentValues values = message.getContentValues();
        if ((mDatabase.insert(ChatMessage.TABLE_NAME, null, values) == -1)) {
            return false;
        } else {
            ChatModel.get(context).updateLastMessageDetails(message);
            return true;
        }
    }

    public boolean deleteMessage(ChatMessage message) {
        return deleteMessage(message.getPersistID());
    }

    public boolean deleteMessage(int uniqueId) {
        int value = mDatabase.delete(ChatMessage.TABLE_NAME, ChatMessage.Cols.CHAT_MESSAGE_UNIQUE_ID + )

        if (value == 1) {
            Log.d(LOGTAG, "Successfully deleted a record");
            return true;
        } else {
            Log.d(LOGTAG, "Could not delete record");
            return false;
        }
    }

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
