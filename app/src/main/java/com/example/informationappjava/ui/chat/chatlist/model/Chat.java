package com.example.informationappjava.ui.chat.chatlist.model;

import android.content.ContentValues;

/**
 * The class Chat contains all getter and setter aswell as the Columns for all the Chat Data.
 */
public class Chat {
  private String jid;
  private String lastMessage;
  private long lastMessageTimeStamp;
  private ContactType contactType;
  private int persistID;
  private long unreadCount;

  public static final String TABLE_NAME = "chats";

  /**
   * This class contains the Columns for the Database Table of the Chat.
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
   * This is a Constructor to create a Chat.
   *
   * @param jid
   * @param lastMessage
   * @param contactType
   * @param timeStamp
   * @param unreadCount
   */
  public Chat(String jid, String lastMessage, ContactType contactType, long timeStamp, long unreadCount) {
    this.jid = jid;
    this.lastMessage = lastMessage;
    this.lastMessageTimeStamp = timeStamp;
    this.contactType = contactType;
    this.unreadCount = unreadCount;
  }

  /**
   * This is a getter for the jid.
   *
   * @return jid
   */
  public String getJid() {
    return jid;
  }

  /**
   * This is a setter for the jid.
   *
   * @param jid
   */
  public void setJid(String jid) {
    this.jid = jid;
  }

  /**
   * This is a getter for the last message.
   *
   * @return lastMessage
   */
  public String getLastMessage() {
    return lastMessage;
  }

  /**
   * This is a setter for the lastMessage.
   *
   * @param lastMessage
   */
  public void setLastMessage(String lastMessage) {
    this.lastMessage = lastMessage;
  }

  /**
   * This is a getter for the contact type.
   *
   * @return contactType
   */
  public ContactType getContactType() {
    return contactType;
  }

  /**
   * This is a setter for the contact type.
   *
   * @param contactType
   */
  public void setContactType(ContactType contactType) {
    this.contactType = contactType;
  }

  /**
   * This is a getter for the PersistID.
   *
   * @return persistID
   */
  public int getPersistID() {
    return persistID;
  }

  /**
   * This is a setter for the PersistID.
   *
   * @param persistID
   */
  public void setPersistID(int persistID) {
    this.persistID = persistID;
  }

  /**
   * This is a getter for the UnreadCount.
   *
   * @return
   */
  public long getUnreadCount() {
    return unreadCount;
  }

  /**
   * This is a setter for the UnreadCount.
   *
   * @param unreadCount
   */
  public void setUnreadCount(long unreadCount) {
    this.unreadCount = unreadCount;
  }

  /**
   * This is a getter for the lastMessageTimeStamp.
   *
   * @return lastMessageTimeStamp
   */
  public long getLastMessageTimeStamp() {
    return lastMessageTimeStamp;
  }

  /**
   * This is a setter for the lastMessageTimeStamp.
   *
   * @param lastMessageTimeStamp
   */
  public void setLastMessageTimeStamp(long lastMessageTimeStamp) {
    this.lastMessageTimeStamp = lastMessageTimeStamp;
  }

  /**
   * This is a getter for the Contentvalues.
   *
   * @return values
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
   * This function returns the Contacttype as a String.
   *
   * @param type
   * @return null, type
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
   * This is an enum for the ContactType.
   */
  public enum ContactType {ONE_ON_ONE, GROUP, STRANGER}
}
