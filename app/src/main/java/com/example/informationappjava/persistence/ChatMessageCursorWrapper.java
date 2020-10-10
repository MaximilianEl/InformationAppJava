package com.example.informationappjava.persistence;

import android.database.Cursor;
import android.database.CursorWrapper;
import com.example.informationappjava.ui.chat.view.model.ChatMessage;

/**
 * The ChatMessageCursorWrapper class is used to access the Chatmessage information if the Cursor accesses it.
 */
public class ChatMessageCursorWrapper extends CursorWrapper {

    /**
     * This is a constructor to call upon the ChatMessageCursorWrapper class.
     *
     * @param cursor
     */
    public ChatMessageCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    /**
     * This function is used to get the Chatmessage Informations for a specific Chatmessage like the messageType.
     *
     * @return
     */
    public ChatMessage getChatMessage() {
        String message = getString(getColumnIndex(ChatMessage.Cols.MESSAGE));
        long timestamp = getLong(getColumnIndex(ChatMessage.Cols.TIMESTAMP));
        String messagetype = getString(getColumnIndex(ChatMessage.Cols.MESSAGE_TYPE));
        String counterpartJid = getString(getColumnIndex(ChatMessage.Cols.CONTACT_JID));
        int uniqueId = getInt(getColumnIndex(ChatMessage.Cols.CHAT_MESSAGE_UNIQUE_ID));

        ChatMessage.Type chatMessageType = null;

        if (messagetype.equals("SENT")) {
            chatMessageType = ChatMessage.Type.SENT;
        } else if (messagetype.equals("RECEIVED")) {
            chatMessageType = ChatMessage.Type.RECEIVED;
        }
        ChatMessage chatMessage = new ChatMessage(message, timestamp, chatMessageType, counterpartJid);
        chatMessage.setPersistID(uniqueId);

        return chatMessage;
    }
}