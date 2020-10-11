package com.example.informationappjava.xmpp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.preference.PreferenceManager;
import android.util.Log;
import com.example.informationappjava.ui.chat.Utils.Utilities;
import com.example.informationappjava.ui.chat.contacts.model.Contact;
import com.example.informationappjava.ui.chat.contacts.model.Contact.SubscriptionType;
import com.example.informationappjava.ui.chat.contacts.model.ContactModel;
import com.example.informationappjava.ui.chat.login.Constants;
import com.example.informationappjava.ui.chat.login.Constants.BroadCastMessages;
import com.example.informationappjava.ui.chat.chatlist.model.Chat.ContactType;
import com.example.informationappjava.ui.chat.chatlist.model.ChatModel;
import com.example.informationappjava.ui.chat.view.model.ChatMessage;
import com.example.informationappjava.ui.chat.view.model.ChatMessage.Type;
import com.example.informationappjava.ui.chat.view.model.ChatMessagesModel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import java.net.InetAddress;
import java.util.Collection;
import java.util.List;

import org.jivesoftware.smack.ConnectionConfiguration.SecurityMode;
import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.SmackConfiguration;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.SmackException.NoResponseException;
import org.jivesoftware.smack.SmackException.NotConnectedException;
import org.jivesoftware.smack.SmackException.NotLoggedInException;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.XMPPException.XMPPErrorException;
import org.jivesoftware.smack.chat2.Chat;
import org.jivesoftware.smack.chat2.ChatManager;
import org.jivesoftware.smack.chat2.IncomingChatMessageListener;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.roster.Roster.SubscriptionMode;
import org.jivesoftware.smack.roster.RosterEntry;
import org.jivesoftware.smack.roster.RosterListener;
import org.jivesoftware.smack.roster.SubscribeListener;
import org.jivesoftware.smack.roster.packet.RosterPacket;
import org.jivesoftware.smack.roster.packet.RosterPacket.ItemType;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jivesoftware.smackx.ping.PingManager;
import org.jivesoftware.smackx.ping.android.ServerPingWithAlarmManager;
import org.jivesoftware.smackx.vcardtemp.VCardManager;
import org.jivesoftware.smackx.vcardtemp.packet.VCard;
import org.jxmpp.jid.EntityBareJid;
import org.jxmpp.jid.Jid;
import org.jxmpp.jid.impl.JidCreate;
import org.jxmpp.stringprep.XmppStringprepException;

/**
 *
 */
public class ChatConnection implements ConnectionListener, SubscribeListener, RosterListener {

  private static final String LOGTAG = "RoosterConnection";

  private final Context context;
  private String username;
  private String password;
  private String serviceName;
  private XMPPTCPConnection connection;
  private ConnectionState mConnectionState;
  private PingManager pingManager;
  private ChatManager chatManager;
  private Roster roster;
  private VCardManager vCardManager;

  public enum ConnectionState {
    OFFLINE, CONNECTING, ONLINE
  }

  /**
   * @param mConnectionState
   */
  private void updateActivitiesOfConnectionStateChange(ConnectionState mConnectionState) {
    ConnectionState connectionState = mConnectionState;
    String status;
    switch (mConnectionState) {
      case OFFLINE:
        status = "Offline";
        break;
      case ONLINE:
        status = "Online";
        break;
      case CONNECTING:
        status = "Connecting...";
        break;
      default:
        status = "Offline";
        break;
    }
    Intent i = new Intent(BroadCastMessages.UI_CONNECTION_STATUS_CHANGE_FLAG);
    i.putExtra(Constants.UI_CONNECTION_STATUS_CHANGE, status);
    i.setPackage(context.getPackageName());
    context.sendBroadcast(i);
  }

  /**
   * Constructor of ChatConnection.
   *
   * @param context
   */
  public ChatConnection(Context context) {
    Log.d(LOGTAG, "RoosterConnection Constructor called");
    this.context = context;
  }

  /**
   * @return mConnectionState
   */
  public ConnectionState getmConnectionState() {
    return mConnectionState;
  }

  /**
   * @param mConnectionState
   */
  public void setmConnectionState(ConnectionState mConnectionState) {
    this.mConnectionState = mConnectionState;
  }

  /**
   * @return Offline;Online;Connecting...;Offline;
   */
  public String getConnectionstateString() {
    switch (mConnectionState) {
      case OFFLINE:
        return "Offline";
      case ONLINE:
        return "Online";
      case CONNECTING:
        return "Connecting...";
      default:
        return "Offline";
    }
  }

  private void notifyUiForConnectionError() {
    Intent i = new Intent(BroadCastMessages.UI_CONNECTION_ERROR);
    i.setPackage(context.getPackageName());
    context.sendBroadcast(i);
    Log.d(LOGTAG, "Sent the broadcast for connection Error");
  }

  /**
   * In the function connect() the connection state is set to connecting. The login data is read in
   * the function gatherCredentials(). The XMPPTCPConnectionConfiguration is created, the connection
   * is established and on the connection different managers are instantiated which can be used to
   * send a ping or change the subscription mode.
   *
   * @throws IOException
   * @throws XMPPException
   * @throws SmackException
   */
  public void connect() throws IOException, XMPPException, SmackException {

    mConnectionState = ConnectionState.CONNECTING;
    updateActivitiesOfConnectionStateChange(ConnectionState.CONNECTING);
    gatherCredentials();

    XMPPTCPConnectionConfiguration connectionConfiguration = XMPPTCPConnectionConfiguration
        .builder()
        .setXmppDomain(serviceName)
        .setHostAddress(InetAddress.getByName("131.173.65.146"))
        .setResource("HsOsChat")

        .setKeystoreType(null)

        .setSendPresence(true)
        .setDebuggerEnabled(true)
        .setSecurityMode(SecurityMode.disabled)
        .setCompressionEnabled(true)
        .build();

    SmackConfiguration.DEBUG = true;
    XMPPTCPConnection.setUseStreamManagementDefault(true);

    connection = new XMPPTCPConnection(connectionConfiguration);
    connection.setUseStreamManagement(true);
    connection.setUseStreamManagementResumption(true);
    connection.setPreferredResumptionTime(5);
    connection.addConnectionListener(this);

    roster = Roster.getInstanceFor(connection);
    roster.setSubscriptionMode(SubscriptionMode.manual);
    roster.addSubscribeListener(this);

    vCardManager = VCardManager.getInstanceFor(connection);

    roster.addRosterListener(this);

    chatManager = ChatManager.getInstanceFor(connection);
    chatManager.addIncomingListener(new IncomingChatMessageListener() {
      /**
       * The newIncomingMessage() function retrieves the Jid from the message transmitted by
       * the server. It checks if the Jid contains a / and then adds the message to the
       * ChatMessageModel and checks if the contact is a stranger.
       * If so, it creates a new chat and a new contact.
       * @param from
       * @param message
       * @param chat
       */
      @Override
      public void newIncomingMessage(EntityBareJid from, Message message, Chat chat) {

        Log.d(LOGTAG, "message.getBody(): " + message.getBody());
        Log.d(LOGTAG, "message.getFrom(): " + message.getFrom());

        String messageSource = message.getFrom().toString();

        String contactJid = "";
        if (messageSource.contains("/")) {

          contactJid = messageSource.split("/")[0];
          Log.d(LOGTAG, "The real jid is: " + contactJid);
          Log.d(LOGTAG, "The message is from: " + from);
        } else {
          contactJid = messageSource;
        }

        //Add message to the model
        ChatMessagesModel.get(context).addMessage(
            new ChatMessage(message.getBody(), System.currentTimeMillis(), Type.RECEIVED,
                contactJid));

        if (ContactModel.get(context).isContactStranger(contactJid)) {

          List<com.example.informationappjava.ui.chat.chatlist.model.Chat> chats = ChatModel
              .get(context)
              .getChatsByJid(contactJid);
          if (chats.size() == 0) {

            Log.d(LOGTAG, contactJid + " is a new chat, adding them. With timestamp: " + Utilities
                .getFormattedTime(System.currentTimeMillis()));

            com.example.informationappjava.ui.chat.chatlist.model.Chat chatRoster = new com.example.informationappjava.ui.chat.chatlist.model.Chat(
                contactJid, message.getBody(), ContactType.ONE_ON_ONE, System.currentTimeMillis(),
                0);
            ChatModel.get(context).addChat(chatRoster);

            //Notify interested activities
            Intent intent = new Intent(Constants.BroadCastMessages.UI_NEW_CHAT_ITEM);
            intent.setPackage(context.getPackageName());
            context.sendBroadcast(intent);
          }
        }

        //If the view (ChatViewActivity) is visible, inform it so it can do necessary adjustments
        Intent intent = new Intent(BroadCastMessages.UI_NEW_MESSAGE_FLAG);
        intent.setPackage(context.getPackageName());
        context.sendBroadcast(intent);
      }
    });

    ServerPingWithAlarmManager.getInstanceFor(connection).setEnabled(true);
    pingManager = PingManager.getInstanceFor(connection);
    pingManager.setPingInterval(30);

    try {
      Log.d(LOGTAG, "Calling connection()");
      connection.connect();
      connection.login(username, password);
      Log.d(LOGTAG, "login() called");
      syncContactListWithRemoteRoster();

      //Cache the avatars for fast local use
      saveUserAvatarsLocaly();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  /**
   * Sets your own avatar.
   *
   * @param image
   * @return
   */
  public boolean setSelfAvatar(byte[] image) {
    VCard vCard = new VCard();
    vCard.setAvatar(image);

    if (vCard != null) {
      try {
        vCardManager.saveVCard(vCard);
      } catch (NoResponseException | XMPPErrorException | NotConnectedException | InterruptedException e) {
        e.printStackTrace();
        return false;
      }
      return true;
    } else {
      return false;
    }
  }

  /**
   * Fetches the avatar from the server and saves them in a local directory on external storage for
   * use in App without needing to user network all the time.
   */
  public void saveUserAvatarsLocaly() {

    Log.d(LOGTAG, "SAVING THE USER AVATARS TO DISK...");
    File rootPath = new File(context.getExternalFilesDir("DIRECTORY_PICTURES"), "profile_pictures");

    //Create the root Dir if it is not there
    if (!rootPath.exists()) {

      if (rootPath.mkdirs()) {

        Log.d(LOGTAG,
            " profile_pictures directory created successfully: " + rootPath.getAbsolutePath());
      } else {

        Log.d(LOGTAG,
            " Could not create profile_pictures directory: " + rootPath.getAbsolutePath());
      }
    }

    //Save self profile image to disk if available
    String selfJid = androidx.preference.PreferenceManager.getDefaultSharedPreferences(context)
        .getString("xmpp_jid", null);

    if (selfJid != null) {

      Log.d(LOGTAG, "Got a valid self Jid: " + selfJid);
      VCard vCard = null;

      try {
        vCard = vCardManager.loadVCard();
      } catch (NoResponseException | XMPPErrorException | NotConnectedException | InterruptedException e) {
        e.printStackTrace();
      }

      if (vCard != null) {
        saveAvatarToDisk(vCard, rootPath, selfJid);
      }

    } else {
      Log.d(LOGTAG, "Self jid is NULL");
    }

    //Save other contacts profile images to disk
    List<String> contacts = ContactModel.get(context).getContactJidStrings();
    for (String contact : contacts) {

      VCard vCard = getUserVCard(contact);
      if (vCard != null) {

        saveAvatarToDisk(vCard, rootPath, contact);
      }
    }
  }

  /**
   * The saveAvatarToDisk() function saves the Image from contacts first it checks which datatype
   * the image is and decodes it to a bitmap.
   *
   * @param vCard
   * @param rootPath
   * @param contact
   */
  private void saveAvatarToDisk(VCard vCard, File rootPath, String contact) {

    String imageMimeType = null;
    String imageExtension = null;
    Bitmap.CompressFormat format = null;

    if (vCard != null) {

      byte[] image_data = vCard.getAvatar();
      imageMimeType = vCard.getAvatarMimeType();
      if (image_data != null) {

        Log.d(LOGTAG, "Found an avatar for user " + contact);

        if (imageMimeType.equals("image/jpeg")) {
          Log.d(LOGTAG, "The image mime type is JPEG");
          imageExtension = "jpeg";
          format = CompressFormat.JPEG;
        } else if (imageMimeType.equals("image/jpg")) {
          Log.d(LOGTAG, "The image mime type is JPG");
          imageExtension = "jpg";
          format = CompressFormat.JPEG;
        } else if (imageMimeType.equals("image/png")) {
          Log.d(LOGTAG, "The image mime type is PNG");
          imageExtension = "png";
          format = CompressFormat.PNG;
        }

        Bitmap bitmap = BitmapFactory.decodeByteArray(image_data, 0, image_data.length);
        Bitmap bpResized = Bitmap.createScaledBitmap(bitmap, 70, 70, false);

        File file = new File(rootPath, contact + "." + imageExtension);
        if (file.exists()) {
          file.delete();

          try {
            FileOutputStream outputStream = new FileOutputStream(file);
            bpResized.compress(format, 90, outputStream);
            outputStream.flush();
            outputStream.close();
          } catch (IOException e) {
            e.printStackTrace();
          }

          Log.d(LOGTAG, "Image write operation successful.File: " + file.getAbsolutePath());
        } else {

          Log.d(LOGTAG, "Could not get avatar for user: " + contact);
        }
      }
    }
  }

  /**
   * @param username
   * @return
   */
  public VCard getUserVCard(String username) {

    EntityBareJid jid = null;

    try {
      jid = JidCreate.entityBareFrom(username);
    } catch (XmppStringprepException e) {
      e.printStackTrace();
    }

    VCard vCard = null;

    if (vCardManager != null) {

      try {
        vCard = vCardManager.loadVCard(jid);
      } catch (NoResponseException | XMPPErrorException | NotConnectedException | InterruptedException e) {
        e.printStackTrace();
      }
    }

    return vCard;
  }

  /**
   * Gets the absolute path from the profile images and checks which datatype the image is.
   *
   * @param jid
   * @return
   */
  public String getProfileImageAbsolutePath(String jid) {

    File rootPath = new File(context.getExternalFilesDir("DIRECTORY_PICTURES"), "profile_pictures");

    //Create the root Dir if it is not there
    if (!rootPath.exists()) {

      if (rootPath.mkdirs()) {

        Log.d(LOGTAG,
            "profile_pictures directory created successfully: " + rootPath.getAbsolutePath());
      } else {

        Log.d(LOGTAG, "Could not create profile_pictures directory: " + rootPath.getAbsolutePath());
      }
    }

    File file = new File(rootPath, jid + ".jpeg");
    if (!file.exists()) {

      file = new File(rootPath, jid + ".jpg");
      if (!file.exists()) {

        file = new File(rootPath, jid + ".png");
        if (!file.exists()) {

          return null;
        } else {

          return file.getAbsolutePath();
        }
      } else {

        return file.getAbsolutePath();
      }
    } else {

      return file.getAbsolutePath();
    }
  }

  /**
   * The syncContactListWithRemoteRoster() function synchronizes the roster entries with the entries
   * from the server. Also it updates already existing entries if necessary.
   */
  public void syncContactListWithRemoteRoster() {

    Log.d(LOGTAG, "Roster SYNCING...");
    //Get roster from server
    Collection<RosterEntry> entries = getRosterEntries();

    Log.d(LOGTAG,
        "Retrieving roster entries from server. " + entries.size() + " contacts in his roster");

    for (RosterEntry mEntry : entries) {

      RosterPacket.ItemType itemType = mEntry.getType();
      Log.d(LOGTAG,
          "Retrieving roster entries from server. " + entries.size() + " contacts in his roster");

      //Update data in the db
      //Get all the contacts
      List<String> contacts = ContactModel.get(context).getContactJidStrings();

      //Add new roster entries
      if ((!contacts.contains(mEntry.getJid().toString())) && (itemType != ItemType.none)) {

        if (ContactModel.get(context).addContact(new Contact(mEntry.getJid().toString(),
            rosterItemTypeToContactSubscriptionType(itemType)))) {

          Log.d(LOGTAG, "New Contact " + mEntry.getJid().toString() + " added successfully");
          //adapter.notifyForUiUpdate();
        } else {
          Log.d(LOGTAG, "Could not add Contact " + mEntry.getJid().toString());
        }
      }

      //Update already existing entries if necessary
      if ((contacts.contains(mEntry.getJid().toString()))) {

        Contact.SubscriptionType subscriptionType = rosterItemTypeToContactSubscriptionType(
            itemType);
        boolean isSubscriptionPending = mEntry.isSubscriptionPending();
        Contact contact = ContactModel.get(context)
            .getContactsByJidString(mEntry.getJid().toString());
        contact.setPendingTo(isSubscriptionPending);
        contact.setSubscriptionType(subscriptionType);
        ContactModel.get(context).updateContactSubscription(contact);
      }
    }
  }

  /**
   * Gets all entries from the roster.
   *
   * @return entries
   */
  public Collection<RosterEntry> getRosterEntries() {

    Collection<RosterEntry> entries = roster.getEntries();
    Log.d(LOGTAG, "The current user has " + entries.size() + " contacts in his roster");
    return entries;
  }

  /**
   * The disconnect() function sets the xmpp_logged_in to false so the User has to login again.
   */
  public void disconnect() {

    Log.d(LOGTAG, "Disconnecting from server " + serviceName);

    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
    preferences.edit().putBoolean("xmpp_logged_in", false).commit();

    if (connection != null) {
      connection.disconnect();
    }

  }

  /**
   * The sendMessage() function is used to send a message to the specific contact. Also it adds the
   * message to the ChatMessageModel.
   *
   * @param body
   * @param toJid
   */
  public void sendMessage(String body, String toJid) {
    Log.d(LOGTAG, "Sending message to: " + toJid);

    EntityBareJid jid = null;

    try {
      jid = JidCreate.entityBareFrom(toJid);
    } catch (XmppStringprepException e) {
      e.printStackTrace();
    }

    Chat chat = chatManager.chatWith(jid);
    try {

      Message message = new Message(jid, Message.Type.chat);
      message.setBody(body);
      chat.send(message);
      ChatMessagesModel.get(context)
          .addMessage(new ChatMessage(body, System.currentTimeMillis(), Type.SENT, toJid));

    } catch (NotConnectedException | InterruptedException e) {
      e.printStackTrace();
    }
  }

  /**
   * Adds contact to the remote roster. We maintain our own local contact list[Roster]
   *
   * @param contactJid
   * @return
   */
  public boolean addContactToRoster(String contactJid) {

    Jid jid;

    try {
      jid = JidCreate.from(contactJid);
    } catch (XmppStringprepException e) {
      e.printStackTrace();
      return false;
    }

    try {
      roster.createEntry(jid.asBareJid(), "", null);
    } catch (NotLoggedInException | NoResponseException | XMPPErrorException | NotConnectedException | InterruptedException e) {
      e.printStackTrace();
      return false;
    }
    return true;
  }

  /**
   * The subscribe() function is used to set the presence type of contact to subscribe.
   *
   * @param contact
   * @return
   */
  public boolean subscribe(String contact) {

    Jid jidTo = null;

    try {
      jidTo = JidCreate.from(contact);
    } catch (XmppStringprepException e) {
      e.printStackTrace();
      return false;
    }

    Presence subscribe = new Presence(jidTo, Presence.Type.subscribe);
    return sendPresence(subscribe);
  }

  /**
   * Here are the Contact.SubscriptionTypes are declared.
   *
   * @param itemType
   * @return
   */
  private Contact.SubscriptionType rosterItemTypeToContactSubscriptionType(
      RosterPacket.ItemType itemType) {

    if (itemType == ItemType.none) {

      return SubscriptionType.NONE;
    } else if (itemType == ItemType.from) {

      return SubscriptionType.FROM;
    } else if (itemType == ItemType.to) {

      return SubscriptionType.TO;
    } else if (itemType == ItemType.both) {

      return SubscriptionType.BOTH;
    } else {

      return SubscriptionType.NONE;
    }
  }

  /**
   * In this function we try to remove a contact from a roster.
   *
   * @param contactJid
   * @return
   */
  public boolean removeRosterEntry(String contactJid) {
    Jid jid;

    try {
      jid = JidCreate.from(contactJid);
    } catch (XmppStringprepException e) {
      e.printStackTrace();
      return false;
    }

    RosterEntry entry = roster.getEntry(jid.asBareJid());

    try {
      roster.removeEntry(entry);
    } catch (NotLoggedInException | NoResponseException | XMPPErrorException | NotConnectedException | InterruptedException e) {
      e.printStackTrace();
      return false;
    }
    return true;
  }

  /**
   * If the connection is not null we try to send the stanza presence of connection.
   *
   * @param presence
   * @return
   */
  public boolean sendPresence(Presence presence) {

    if (connection != null) {

      try {
        connection.sendStanza(presence);
      } catch (NotConnectedException | InterruptedException e) {
        e.printStackTrace();
        return false;
      }
      return true;
    }
    return false;
  }

  /**
   * The unsubsribe() function is used to set the presence type of contact to unsubscribe.
   *
   * @param contact
   * @return
   */
  public boolean unsubsribe(String contact) {

    Jid jidTo = null;

    try {
      jidTo = JidCreate.from(contact);
    } catch (XmppStringprepException e) {
      e.printStackTrace();
      return false;
    }

    Presence unsubscribe = new Presence(jidTo, Presence.Type.unsubscribe);
    return sendPresence(unsubscribe);
  }

  /**
   * The subscribed() function is used to set the presence type of contact to subscribed.
   *
   * @param contact
   * @return
   */
  public boolean subscribed(String contact) {

    Jid jidTo = null;

    try {
      jidTo = JidCreate.from(contact);
    } catch (XmppStringprepException e) {
      e.printStackTrace();
      return false;
    }

    Presence subscribe = new Presence(jidTo, Presence.Type.subscribe);
    sendPresence(subscribe);
    return true;
  }

  /**
   * The unsubscribed() function is used to set the presence type of contact to unsubscribed.
   *
   * @param contact
   * @return
   */
  public boolean unsubscribed(String contact) {

    Jid jidTo = null;

    try {
      jidTo = JidCreate.from(contact);
    } catch (XmppStringprepException e) {
      e.printStackTrace();
      return false;
    }

    Presence unsubscribed = new Presence(jidTo, Presence.Type.unsubscribed);
    return sendPresence(unsubscribed);
  }

  /**
   * The gatherCredentials() function is used to get the password and the Jid.
   */
  private void gatherCredentials() {
    String jid = PreferenceManager.getDefaultSharedPreferences(context).getString("xmpp_jid", null);

    password = PreferenceManager.getDefaultSharedPreferences(context)
        .getString("xmpp_password", null);

    if (jid != null) {
      username = jid.split("@")[0];
      serviceName = jid.split("@")[1];
    } else {
      username = "";
      serviceName = "";
    }
  }

  /**
   * The connected() function sets the connection state to connecting.
   *
   * @param connection
   */
  @Override
  public void connected(XMPPConnection connection) {
    Log.d(LOGTAG, " - Connected");
    mConnectionState = ConnectionState.CONNECTING;
    updateActivitiesOfConnectionStateChange(ConnectionState.CONNECTING);
  }

  /**
   * The authenticated() function sets the connection state to online and sets the xmpp_logged_in to
   * true.
   *
   * @param connection
   * @param resumed
   */
  @Override
  public void authenticated(XMPPConnection connection, boolean resumed) {
    mConnectionState = ConnectionState.ONLINE;
    updateActivitiesOfConnectionStateChange(ConnectionState.ONLINE);

    Log.d(LOGTAG, " - authenticated");
    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
    preferences.edit().putBoolean("xmpp_logged_in", true).commit();

    Intent intent = new Intent(Constants.BroadCastMessages.UI_AUTHENTICATED);
    intent.setPackage(context.getPackageName());
    context.sendBroadcast(intent);
    Log.d(LOGTAG, "Sent the broadcast that were authenticated");

  }

  /**
   *
   */
  @Override
  public void connectionClosed() {
    Log.d(LOGTAG, " - connectionClosed");
//        notifyUiForConnectionError();
    mConnectionState = ConnectionState.OFFLINE;
    updateActivitiesOfConnectionStateChange(ConnectionState.OFFLINE);
  }

  /**
   * The connectionClosedOnError() function is called if an error occurs and the connection is
   * closed.
   *
   * @param e
   */
  @Override
  public void connectionClosedOnError(Exception e) {
    Log.d(LOGTAG, " - connectionClosedOnError");
    mConnectionState = ConnectionState.OFFLINE;
    updateActivitiesOfConnectionStateChange(ConnectionState.OFFLINE);
  }

  /**
   * The reconnectionSuccessful() function is called when the reconnection is successful.
   */
  @Override
  public void reconnectionSuccessful() {
    Log.d(LOGTAG, " - reconnectionSuccessful");
    mConnectionState = ConnectionState.ONLINE;
    updateActivitiesOfConnectionStateChange(ConnectionState.ONLINE);
  }

  /**
   * The reconnectingIn() function is called when the client is connecting.
   *
   * @param seconds
   */
  @Override
  public void reconnectingIn(int seconds) {
    Log.d(LOGTAG, " - Reconnecting in " + seconds + " seconds");
    mConnectionState = ConnectionState.CONNECTING;
    updateActivitiesOfConnectionStateChange(ConnectionState.CONNECTING);
  }

  /**
   * The reconnectionFailed() is called if the reconnection is failed.
   *
   * @param e
   */
  @Override
  public void reconnectionFailed(Exception e) {
    Log.d(LOGTAG, " - reconnectionFailed");
    mConnectionState = ConnectionState.OFFLINE;
    updateActivitiesOfConnectionStateChange(ConnectionState.OFFLINE);
  }

  /**
   * SubscribeListener Overrides
   *
   * @param from
   * @param subscribeRequest
   * @return
   */
  @Override
  public SubscribeAnswer processSubscribe(Jid from, Presence subscribeRequest) {

    Log.d(LOGTAG, "-------------------------processSubscribe Called------------------------.");
    Log.d(LOGTAG, "JID is: " + from.toString());
    Log.d(LOGTAG, "Presence type: " + subscribeRequest.getType().toString());

    /* If somebody is not in our contact list, we should not process their subscription requests
     * We should however process their messages. After we have exchanged a few messages, can we
     * then subscribe to each other's presence. */

    if (!ContactModel.get(context).isContactStranger(from.toString())) {

      Contact contact = ContactModel.get(context).getContactsByJidString(from.toString());
      contact.setPendingFrom(true);
      ContactModel.get(context).updateContactSubscription(contact);

    } else {
      //Create a Chat with type STRANGER
      List<com.example.informationappjava.ui.chat.chatlist.model.Chat> chats = ChatModel
          .get(context)
          .getChatsByJid(from.toString());
      if (chats.size() == 0) {
        //Only add the chat when it is not already available
        if (ChatModel.get(context).addChat(
            new com.example.informationappjava.ui.chat.chatlist.model.Chat(from.toString(),
                "Subscription Request", ContactType.STRANGER,
                System.currentTimeMillis(), 1))) {

          Log.d(LOGTAG,
              "Chat item for stranger " + from.toString() + " successfully added to chat model");

        }
      }
    }
    //We do not provide an answer right away, we let the user actively accept or deny this subscription.
    return null;
  }

  /**
   * The entriesAdded() function adds Contacts to the roster and the Database. Also it updated the
   * existing entries if necessary.
   *
   * @param addresses
   */
  @Override
  public void entriesAdded(Collection<Jid> addresses) {

    for (Jid jid : addresses) {

      RosterEntry entry = roster.getEntry(jid.asBareJid());
      RosterPacket.ItemType itemType = entry.getType();
      boolean isSubscriptionPending = entry.isSubscriptionPending();

      //Get all the contacts
      List<String> contacts = ContactModel.get(context).getContactJidStrings();

      //Add new roster entries
      if ((!contacts.contains(entry.getJid().toString())) && (itemType != ItemType.none)) {

        /* We only add contacts that we don't have already and that don't have a subscription type of none.
         * none subscriptions add no needed information too our local contact list */

        //Add it to the db
        Contact contact = new Contact(entry.getJid().toString(),
            rosterItemTypeToContactSubscriptionType(itemType));
        contact.setPendingTo(isSubscriptionPending);
        if (ContactModel.get(context).addContact(contact)) {
          Log.d(LOGTAG, "New Contact " + entry.getJid().toString() + "Added successfully");
          //adapter.notifyForUiUpdate();
        } else {
          Log.d(LOGTAG, "Could not added Contact " + entry.getJid().toString());
        }
      }

      //Update already existing entries id necessary
      if ((contacts.contains(entry.getJid().toString()))) {

        Contact.SubscriptionType subscriptionType = rosterItemTypeToContactSubscriptionType(
            itemType);
        Contact contact = ContactModel.get(context)
            .getContactsByJidString(entry.getJid().toString());

        contact.setPendingTo(isSubscriptionPending);
        contact.setSubscriptionType(subscriptionType);
        ContactModel.get(context).updateContactSubscription(contact);
      }
    }
  }

  /**
   * The entriesUpdated() function updates all Contacts.
   *
   * @param addresses
   */
  @Override
  public void entriesUpdated(Collection<Jid> addresses) {

    for (Jid jid : addresses) {

      RosterEntry entry = roster.getEntry(jid.asBareJid());
      RosterPacket.ItemType itemType = entry.getType();
      boolean isSubscriptionPending = entry.isSubscriptionPending();

      List<String> contacts = ContactModel.get(context).getContactJidStrings();

      //Update already existing entries id necessary
      if ((contacts.contains(entry.getJid().toString()))) {

        Contact.SubscriptionType subscriptionType = rosterItemTypeToContactSubscriptionType(
            itemType);
        Contact contact = ContactModel.get(context)
            .getContactsByJidString(entry.getJid().toString());
        contact.setPendingTo(isSubscriptionPending);
        contact.setSubscriptionType(subscriptionType);
        ContactModel.get(context).updateContactSubscription(contact);
      }
    }
  }

  /**
   * The entriesDeleted() function is used to delete Contacts.
   *
   * @param addresses
   */
  @Override
  public void entriesDeleted(Collection<Jid> addresses) {

    for (Jid jid : addresses) {

      if (!ContactModel.get(context).isContactStranger(jid.toString())) {

        Contact contact = ContactModel.get(context).getContactsByJidString(jid.toString());
        if (ContactModel.get(context).deleteContact(contact)) {
          Log.d(LOGTAG, "Contact " + jid.toString() + " successfully deleted from the database");
        }
      }
    }
  }

  /**
   * The presenceChanged() function contains the Code which is used to change the Online Status from
   * a specific Contact.
   *
   * @param presence
   */
  @Override
  public void presenceChanged(Presence presence) {

    Log.d(LOGTAG, "PresenceChage calles. Presence is: " + presence.toString());

    Presence mPresence = roster.getPresence(presence.getFrom().asBareJid());
    Log.d(LOGTAG, "Best Presence is: " + mPresence.toString());
    Log.d(LOGTAG, "Type is: " + mPresence.getType());
    Contact mContact = ContactModel.get(context)
        .getContactsByJidString(presence.getFrom().asBareJid().toString());

    mContact.setOnlineStatus(mPresence.isAvailable() && (!mPresence.isAway()));

    ContactModel.get(context).updateContactSubscription(mContact);

    Intent intent = new Intent(Constants.BroadCastMessages.UI_ONLINE_STATUS_CHANGE);
    intent.putExtra(Constants.ONLINE_STATUS_CHANGE_CONTACT,
        presence.getFrom().asBareJid().toString());
    intent.setPackage(context.getPackageName());
    context.sendBroadcast(intent);
  }
}