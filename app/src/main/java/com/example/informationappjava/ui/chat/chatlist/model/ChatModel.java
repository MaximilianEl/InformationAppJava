package com.example.informationappjava.ui.chat.chatlist.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.example.informationappjava.persistence.ChatCursorWrapper;
import com.example.informationappjava.persistence.DatabaseBackend;
import com.example.informationappjava.ui.chat.chatlist.model.Chat.Cols;
import com.example.informationappjava.ui.chat.view.model.ChatMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * The ChatModel class adds Chats to the Chatlist and also lets the User delete and add Chats to the Chatlist.
 */
public class ChatModel {

    private static final String LOGTAG = "ChatModel";
    private static ChatModel sChatsModel;
    private final Context mContext;
    private final SQLiteDatabase mDatabase;

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

    /**
     * This function returns the ChatsModel if it exists.
     * If it doesn't exist it creates a new one.
     *
     * @param context
     * @return sChatModel
     */
    public static ChatModel get(Context context) {
        if (sChatsModel == null) {
            sChatsModel = new ChatModel(context);
        }
        return sChatsModel;
    }

    /**
     * This is a Constructor to call upon the ChatModel.
     *
     * @param context
     */
    private ChatModel(Context context) {
        mContext = context;
        mDatabase = DatabaseBackend.getInstance(context).getReadableDatabase();
    }

    /**
     * This function adds a new Chat to the Chatlist View.
     *
     * @param chat
     * @return Database
     */
    public boolean addChat(Chat chat) {
        ContentValues values = chat.getContentValues();
        return mDatabase.insert(Chat.TABLE_NAME, null, values) != -1;
    }

    /**
     * This function updates the Details of the last Messages on the Chatviews in the Chatlist.
     *
     * @param chatMessage
     * @return true, false
     */
    public boolean updateLastMessageDetails(ChatMessage chatMessage) {
        List<Chat> chats = getChatsByJid(chatMessage.getContactJid());

        if (!chats.isEmpty()) {
            Chat chat = chats.get(0);
            chat.setLastMessageTimeStamp(chatMessage.getTimestamp());
            chat.setLastMessage(chatMessage.getMessage());
            ContentValues values = chat.getContentValues();

            int ret = mDatabase.update(Chat.TABLE_NAME, values,
                    Cols.CHAT_UNIQUE_ID + "=?",
                    new String[]{String.valueOf(chat.getPersistID())});
            if (ret == 1) {
                Log.d(LOGTAG, "Chat Last Message updated successfully");
                return true;
            } else {
                Log.d(LOGTAG, "Could not update Chat Last Message info");
                return false;
            }
        }
        return false;
    }

    /**
     * This function deletes a Chat from the Chatlist.
     *
     * @param uniqueId
     * @return true, false
     */
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

    /**
     * selects the Chat in a Database query.
     *
     * @param whereClause
     * @param whereArgs
     * @return new ChatCursorWrapper
     */
    private ChatCursorWrapper queryChats(String whereClause, String[] whereArgs) {

        Cursor cursor = mDatabase.query(
                Chat.TABLE_NAME,
                null, //null selects all columns
                whereClause,
                whereArgs,
                null,
                null,
                null
        );
        return new ChatCursorWrapper(cursor);
    }
}
