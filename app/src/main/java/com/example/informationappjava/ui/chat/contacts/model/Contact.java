package com.example.informationappjava.ui.chat.contacts.model;

import android.content.ContentValues;

/**
 *
 */
public class Contact {

  private String jid;
  private SubscriptionType subscriptionType;
  private String profileImagePath;
  private int persistID;
  boolean pendingTo;
  boolean pendingFrom;
  boolean onlineStatus;

  public static final String TABLE_NAME = "contacts";

  /**
   * This class contains the Columns for the Database Table of the Contact.
   */
  public static final class Cols {
    public static final String CONTACT_UNIQUE_ID = "contactUniqueId";
    public static final String CONTACT_JID = "jid";
    public static final String SUBSCRIPTION_TYPE = "subscriptionType";
    public static final String PROFILE_IMAGE_PATH = "profileImagePath";
    public static final String PENDING_STATUS_TO = "pendingTo";
    public static final String PENDING_STATUS_FROM = "pendingFrom";
    public static final String ONLINE_STATUS = "onlineStatus";
  }

  /**
   * This function returns the status of the Subscriptiontype.
   *
   * @return values
   */
  public ContentValues getContentValues() {
    ContentValues values = new ContentValues();
    //CONTACT_UNIQUE_ID is autofilled
    //SQLite doesn't have a boolean data type. We turn boolean into integers before db write
    int pendingFromInt = (pendingFrom) ? 1 : 0;
    int pendingToInt = (pendingTo) ? 1 : 0;
    int onlineStatusInt = (onlineStatus) ? 1 : 0;

    values.put(Cols.CONTACT_JID, jid);
    values.put(Cols.SUBSCRIPTION_TYPE, getTypeStringValue(subscriptionType));
    values.put(Cols.PROFILE_IMAGE_PATH, profileImagePath);

    values.put(Cols.PENDING_STATUS_FROM, pendingFromInt);
    values.put(Cols.PENDING_STATUS_TO, pendingToInt);
    values.put(Cols.ONLINE_STATUS, onlineStatusInt);

    return values;
  }

  /**
   * This function returns the Subscriptiontype as a string.
   *
   * @param type
   * @return FROM, TO, BOTH, NONE, INDETERMINATE
   */
  public String getTypeStringValue(SubscriptionType type) {

    if (type == SubscriptionType.FROM) {
      return "FROM";
    } else if (type == SubscriptionType.TO) {
      return "TO";
    } else if (type == SubscriptionType.BOTH) {
      return "BOTH";
    } else if (type == SubscriptionType.NONE) {
      return "NONE";
    } else {
      return "INDETERMINATE";
    }
  }

  /**
   * This is an enum for the Subscriptiontype.
   */
  public enum SubscriptionType {
    NONE, FROM, TO, BOTH
  }

  /**
   * This is a Constructor to create a Contact.
   *
   * @param jid
   * @param subscriptionType
   */
  public Contact(String jid, SubscriptionType subscriptionType) {
    this.jid = jid;
    this.subscriptionType = subscriptionType;
    this.profileImagePath = "NONE";

    this.pendingFrom = false;
    this.pendingTo = false;
    this.onlineStatus = false;
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
   * This is a getter for the SubscriptionType.
   *
   * @return subscriptionType
   */
  public SubscriptionType getSubscriptionType() {
    return subscriptionType;
  }

  /**
   * This is a setter for the SubscriptionType.
   *
   * @param subscriptionType
   */
  public void setSubscriptionType(SubscriptionType subscriptionType) {
    this.subscriptionType = subscriptionType;
  }

  /**
   * This is a getter for the profileImagePath.
   *
   * @return profileImagePath
   */
  public String getProfileImagePath() {
    return profileImagePath;
  }

  /**
   * This is a setter for the profileImagePath.
   *
   * @param profileImagePath
   */
  public void setProfileImagePath(String profileImagePath) {
    this.profileImagePath = profileImagePath;
  }

  /**
   * This is a getter for the persistID.
   *
   * @return persistID
   */
  public int getPersistID() {
    return persistID;
  }

  /**
   * This is a getter for the persistID.
   *
   * @param persistID
   */
  public void setPersistID(int persistID) {
    this.persistID = persistID;
  }

  /**
   * This is a boolean to get the state of pendingTo.
   *
   * @return pendigTo
   */
  public boolean isPendingTo() {
    return pendingTo;
  }

  /**
   * This is a setter for the boolean pendingTo.
   *
   * @param pendingTo
   */
  public void setPendingTo(boolean pendingTo) {
    this.pendingTo = pendingTo;
  }

  /**
   * This is a boolean to get the state of pendingFrom.
   *
   * @return pendingFrom
   */
  public boolean isPendingFrom() {
    return pendingFrom;
  }

  /**
   * This is a setter for the boolean pendingFrom.
   *
   * @param pendingFrom
   */
  public void setPendingFrom(boolean pendingFrom) {
    this.pendingFrom = pendingFrom;
  }

  /**
   * This is a boolean to get the state the onlineStatus.
   *
   * @return
   */
  public boolean isOnlineStatus() {
    return onlineStatus;
  }

  /**
   * This is a setter for the onlineStatus.
   *
   * @param onlineStatus
   */
  public void setOnlineStatus(boolean onlineStatus) {
    this.onlineStatus = onlineStatus;
  }
}