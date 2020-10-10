package com.example.informationappjava.persistence;

import android.database.Cursor;
import android.database.CursorWrapper;
import android.util.Log;
import com.example.informationappjava.ui.chat.chatlist.model.Chat;
import com.example.informationappjava.ui.chat.chatlist.model.Chat.ContactType;

/**
 * The ChatCursorWrapper class is used to access the Chat information if the Cursor accesses it.
 */
public class ChatCursorWrapper extends CursorWrapper {

    private static final String LOGTAG = "ChatCursorWrapper";

    /**
     * This is a constructor to call upon the ChatCursorWrapper class.
     *
     * @param cursor
     */
    public ChatCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    /**
     * This function is used to get the Chat Informations for a specific Chat like the messageType.
     *
     * @return
     */
    public Chat getChat() {

        String jid = getString(getColumnIndex(Chat.Cols.CONTACT_JID));
        String contactType = getString(getColumnIndex(Chat.Cols.CONTACT_TYPE));
        String lastMessage = getString(getColumnIndex(Chat.Cols.LAST_MESSAGE));
        long unreadCount = getLong(getColumnIndex(Chat.Cols.UNREAD_COUNT));
        long lastMessageTimeStamp = getLong(getColumnIndex(Chat.Cols.LAST_MESSAGE_TIME_STAMP));
        int uniqueId = getInt(getColumnIndex(Chat.Cols.CHAT_UNIQUE_ID));

        Log.d(LOGTAG, "Got a chat from database the unique ID is: " + uniqueId);

        Chat.ContactType chatType = null;

        if (contactType.equals("GROUP")) {

            chatType = Chat.ContactType.GROUP;

        } else if (contactType.equals("ONE_ON_ONE")) {

            chatType = ContactType.ONE_ON_ONE;

        } else if (contactType.equals("STRANGER")) {

            chatType = ContactType.STRANGER;

        }
        Chat chat = new Chat(jid, lastMessage, chatType, lastMessageTimeStamp, unreadCount);
        chat.setPersistID(uniqueId);
        return chat;
    }
}
