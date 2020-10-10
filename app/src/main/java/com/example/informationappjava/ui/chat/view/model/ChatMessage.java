package com.example.informationappjava.ui.chat.view.model;

import android.content.ContentValues;

/**
 * The class ChatMessage contains all the getter and setter aswell as the Columns for all the ChatMessage Data.
 */
public class ChatMessage {

  private String message;
  private final long timestamp;
  private Type type;
  private String contactJid;
  private int persistID;

  public static final String TABLE_NAME = "chatMessages";

  /**
   * This class contains the Columns for the Database Table of the ChatMessage.
   */
  public static final class Cols {
    public static final String CHAT_MESSAGE_UNIQUE_ID = "chatMessageUniqueId";
    public static final String MESSAGE = "message";
    public static final String TIMESTAMP = "timestamp";
    public static final String MESSAGE_TYPE = "messageType";
    public static final String CONTACT_JID = "contactjid";
  }

  /**
   * This is a Constructor to create a ChatMessage.
   *
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
   * This is a getter for the Message.
   *
   * @return message
   */
  public String getMessage() {
    return message;
  }

  /**
   * This is a getter for the timestamp.
   *
   * @return timestamp
   */
  public long getTimestamp() {
    return timestamp;
  }

  /**
   * This is a getter for the type.
   *
   * @return type
   */
  public Type getType() {
    return type;
  }

  /**
   * This is a getter for the contactJid.
   *
   * @return contactJid
   */
  public String getContactJid() {
    return contactJid;
  }

  /**
   * This is a getter for the persistID.
   *
   * @return
   */
  public int getPersistID() {
    return persistID;
  }

  /**
   * This is a setter for the message.
   *
   * @param message
   */
  public void setMessage(String message) {
    this.message = message;
  }

  /**
   * This is a setter for the timestamp.
   *
   * @param timestamp
   */
  public void setTimestamp(long timestamp) {
    timestamp = timestamp;
  }

  /**
   * This is a setter for the type.
   *
   * @param type
   */
  public void setType(Type type) {
    this.type = type;
  }

  /**
   * This is a setter for the ContactJid.
   *
   * @param contactJid
   */
  public void setContactJid(String contactJid) {
    this.contactJid = contactJid;
  }

  /**
   * This is a setter for the persistID.
   *
   * @param persistID
   */
  public void setPersistID(int persistID) {
    this.persistID = persistID;
  }

  /**
   * This function returns the type of a Message
   *
   * @param type
   * @return SENT, RECEIVED
   */
  public String getTypeStringValue(Type type) {
    if (type == Type.SENT) {
      return "SENT";
    } else {
      return "RECEIVED";
    }
  }

  /**
   * This function returns the Contentvalues of a Message.
   *
   * @return values
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
   * This is an enum for the MessageType.
   */
  public enum Type {SENT, RECEIVED}
}