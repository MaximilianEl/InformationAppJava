package com.example.informationappjava.ui.chat.view.model;

import android.text.format.DateFormat;
import java.util.concurrent.TimeUnit;

public class ChatMessage {
  private String message;
  private long timestamp;
  private Type type;
  private String contactJid;

  public static final String TABLE_NAME = "chatMessages";

  public static final class Cols {
    public static final String CHAT_MESSAGE_UNIQUE_ID = "chatMessageUniqueId";
    public static final String MESSAGE = "message";
    public static final String TIMESTAMP = "timestamp";
    public static final String MESSAGE_TYPE = "messageType";
    public static final String CONTACT_JID = "contactjid";
  }

  public ChatMessage(String message, long timestamp, Type type, String contactJid) {
    this.message = message;
    timestamp = timestamp;
    this.type = type;
    this.contactJid = contactJid;
  }

  public String getMessage() {
    return message;
  }

  public long getTimestamp() {
    return timestamp;
  }

  public Type getType() {
    return type;
  }

  public String getContactJid() {
    return contactJid;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public void setTimestamp(long timestamp) {
    timestamp = timestamp;
  }

  public void setType(Type type) {
    this.type = type;
  }

  public void setContactJid(String contactJid) {
    this.contactJid = contactJid;
  }

  public String getFormattedTime() {
    long oneDayInMillis = TimeUnit.DAYS.toMillis(1); //24 * 60 * 60 * 1000
    long timeDifference = System.currentTimeMillis() - timestamp;

    return timeDifference < oneDayInMillis
        ? DateFormat.format("hh:mm a", timestamp).toString()
        : DateFormat.format("dd MMM - hh:mm a", timestamp).toString();
  }

  public enum Type {
    SENT, RECEIVED
  }
}
