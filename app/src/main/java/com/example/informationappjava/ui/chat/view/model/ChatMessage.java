package com.example.informationappjava.ui.chat.view.model;

import android.content.ContentValues;

/**
 *
 */
public class ChatMessage {

  private String message;
  private final long timestamp;
  private Type type;
  private String contactJid;
  private int persistID;

  public static final String TABLE_NAME = "chatMessages";

  public static final class Cols {

    public static final String CHAT_MESSAGE_UNIQUE_ID = "chatMessageUniqueId";
    public static final String MESSAGE = "message";
    public static final String TIMESTAMP = "timestamp";
    public static final String MESSAGE_TYPE = "messageType";
    public static final String CONTACT_JID = "contactjid";
  }

  /**
   * @param message
   * @param timestamp
   * @param type
   * @param contactJid
   */
  public ChatMessage(String message, long timestamp, Type type, String contactJid) {
    this.message = message;
    this.timestamp = timestamp;
    this.type = type;
    this.contactJid = contactJid;
  }

  /**
   * @return
   */
  public String getMessage() {
    return message;
  }

  /**
   * @return
   */
  public long getTimestamp() {
    return timestamp;
  }

  /**
   * @return
   */
  public Type getType() {
    return type;
  }

  /**
   * @return
   */
  public String getContactJid() {
    return contactJid;
  }

  /**
   * @param message
   */
  public void setMessage(String message) {
    this.message = message;
  }

  /**
   * @param timestamp
   */
  public void setTimestamp(long timestamp) {
    timestamp = timestamp;
  }

  /**
   * @param type
   */
  public void setType(Type type) {
    this.type = type;
  }

  /**
   * @param contactJid
   */
  public void setContactJid(String contactJid) {
    this.contactJid = contactJid;
  }

  /**
   * @return
   */
  public int getPersistID() {
    return persistID;
  }

  /**
   * @param persistID
   */
  public void setPersistID(int persistID) {
    this.persistID = persistID;
  }

  /**
   * @param type
   * @return
   */
  public String getTypeStringValue(Type type) {
    if (type == Type.SENT) {
      return "SENT";
    } else {
      return "RECEIVED";
    }
  }

  /**
   * @return
   */
  public ContentValues getContentValues() {

    ContentValues values = new ContentValues();
    values.put(Cols.MESSAGE, message);
    values.put(Cols.TIMESTAMP, timestamp);
    values.put(Cols.MESSAGE_TYPE, getTypeStringValue(type));
    values.put(Cols.CONTACT_JID, contactJid);

    return values;
  }

  /**
   *
   */
  public enum Type {
    SENT, RECEIVED
  }
}
