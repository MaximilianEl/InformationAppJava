package com.example.informationappjava.persistence;

import android.database.Cursor;
import android.database.CursorWrapper;
import com.example.informationappjava.ui.chat.view.model.ChatMessage;

/**
 *
 */
public class ChatMessageCursorWrapper extends CursorWrapper {

  /**
   * @param cursor
   */
  public ChatMessageCursorWrapper(Cursor cursor) {
    super(cursor);
  }

  /**
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
