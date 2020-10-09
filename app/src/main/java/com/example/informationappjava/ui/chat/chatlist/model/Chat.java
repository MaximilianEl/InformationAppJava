package com.example.informationappjava.ui.chat.chatlist.model;

import android.content.ContentValues;

/**
 *
 */
public class Chat {

  private String jid;
  private String lastMessage;
  private long lastMessageTimeStamp;
  private ContactType contactType;
  private int presistID;
  private long unreadCount;

  public static final String TABLE_NAME = "chats";

  /**
   *
   */
  public static final class Cols {
    public static final String CHAT_UNIQUE_ID = "chatUniqueId";
    public static final String CONTACT_JID = "jid";
    public static final String CONTACT_TYPE = "contactType";
    public static final String LAST_MESSAGE = "lastMessage";
    public static final String UNREAD_COUNT = "unreadCount";
    public static final String LAST_MESSAGE_TIME_STAMP = "lastMessageTimeStamp";
  }

  /**
   * @param jid
   * @param lastMessage
   * @param contactType
   * @param timeStamp
   * @param unreadCount
   */
  public Chat(String jid, String lastMessage, ContactType contactType, long timeStamp,
      long unreadCount) {
    this.jid = jid;
    this.lastMessage = lastMessage;
    this.lastMessageTimeStamp = timeStamp;
    this.contactType = contactType;
    this.unreadCount = unreadCount;
  }

  /**
   * @return
   */
  public String getJid() {
    return jid;
  }

  /**
   * @return
   */
  public String getLastMessage() {
    return lastMessage;
  }

  /**
   * @param jid
   */
  public void setJid(String jid) {
    this.jid = jid;
  }

  /**
   * @return
   */
  public ContactType getContactType() {
    return contactType;
  }

  /**
   * @param contactType
   */
  public void setContactType(ContactType contactType) {
    this.contactType = contactType;
  }

  /**
   * @return
   */
  public int getPresistID() {
    return presistID;
  }

  /**
   * @param presistID
   */
  public void setPresistID(int presistID) {
    this.presistID = presistID;
  }

  /**
   * @return
   */
  public long getUnreadCount() {
    return unreadCount;
  }

  /**
   * @param unreadCount
   */
  public void setUnreadCount(long unreadCount) {
    this.unreadCount = unreadCount;
  }

  /**
   * @param lastMessage
   */
  public void setLastMessage(String lastMessage) {
    this.lastMessage = lastMessage;
  }

  /**
   * @return
   */
  public long getLastMessageTimeStamp() {
    return lastMessageTimeStamp;
  }

  /**
   * @param lastMessageTimeStamp
   */
  public void setLastMessageTimeStamp(long lastMessageTimeStamp) {
    this.lastMessageTimeStamp = lastMessageTimeStamp;
  }

  /**
   * @return
   */
  public ContentValues getContentValues() {
    ContentValues values = new ContentValues();
    values.put(Cols.CONTACT_JID, jid);
    values.put(Cols.CONTACT_TYPE, getTypeStringValue(contactType));
    values.put(Cols.LAST_MESSAGE, lastMessage);
    values.put(Cols.LAST_MESSAGE_TIME_STAMP, lastMessageTimeStamp);
    values.put(Cols.UNREAD_COUNT, unreadCount);

    return values;
  }

  /**
   * @param type
   * @return
   */
  public String getTypeStringValue(ContactType type) {
    if (type == ContactType.ONE_ON_ONE) {
      return "ONE_ON_ONE";
    } else if (type == ContactType.GROUP) {
      return "GROUP";
    } else if (type == ContactType.STRANGER) {
      return "STRANGER";
    } else {
      return null;
    }
  }

  /**
   *
   */
  public enum ContactType {
    ONE_ON_ONE, GROUP, STRANGER
  }
}
