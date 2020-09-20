package com.example.informationappjava.xmpp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import com.example.informationappjava.ui.chat.Utilities;
import com.example.informationappjava.ui.chat.chatlist.model.Contact;
import com.example.informationappjava.ui.chat.chatlist.model.ContactModel;
import com.example.informationappjava.ui.chat.login.Constants;
import com.example.informationappjava.ui.chat.login.Constants.BroadCastMessages;
import com.example.informationappjava.ui.chat.login.model.Chat.ContactType;
import com.example.informationappjava.ui.chat.login.model.ChatModel;
import com.example.informationappjava.ui.chat.view.model.ChatMessage;
import com.example.informationappjava.ui.chat.view.model.ChatMessage.Type;
import com.example.informationappjava.ui.chat.view.model.ChatMessagesModel;

import java.io.IOException;

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
import org.jivesoftware.smack.roster.SubscribeListener;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jivesoftware.smackx.ping.PingManager;
import org.jivesoftware.smackx.ping.android.ServerPingWithAlarmManager;
import org.jxmpp.jid.EntityBareJid;
import org.jxmpp.jid.Jid;
import org.jxmpp.jid.impl.JidCreate;
import org.jxmpp.stringprep.XmppStringprepException;

public class RoosterConnection implements ConnectionListener, SubscribeListener {

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

  public enum ConnectionState {
    OFFLINE, CONNECTING, ONLINE
  }

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

  public RoosterConnection(Context context) {
    Log.d(LOGTAG, "RoosterConnection Constructor called");
    this.context = context;
  }

  public ConnectionState getmConnectionState() {
    return mConnectionState;
  }

  public void setmConnectionState(ConnectionState mConnectionState) {
    this.mConnectionState = mConnectionState;
  }

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

  public void connect() throws IOException, XMPPException, SmackException {

    mConnectionState = ConnectionState.CONNECTING;
    updateActivitiesOfConnectionStateChange(ConnectionState.CONNECTING);
    gatherCredentials();

    XMPPTCPConnectionConfiguration connectionConfiguration = XMPPTCPConnectionConfiguration
        .builder()
        .setXmppDomain(serviceName)
        .setHost(serviceName)
        .setResource("Rooster+")

        .setKeystoreType(null)

        .setSendPresence(true)
        .setDebuggerEnabled(true)
        .setSecurityMode(SecurityMode.required)
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

    chatManager = ChatManager.getInstanceFor(connection);
    chatManager.addIncomingListener(new IncomingChatMessageListener() {
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

          List<com.example.informationappjava.ui.chat.login.model.Chat> chats = ChatModel
              .get(context)
              .getChatsByJid(contactJid);
          if (chats.size() == 0) {

            Log.d(LOGTAG, contactJid + " is a new chat, adding them. With timestamp: " + Utilities
                .getFormattedTime(System.currentTimeMillis()));

            com.example.informationappjava.ui.chat.login.model.Chat chatRoster = new com.example.informationappjava.ui.chat.login.model.Chat(
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
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public void disconnect() {

    Log.d(LOGTAG, "Disconnecting from server " + serviceName);

    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
    preferences.edit().putBoolean("xmpp_logged_in", false).commit();

    if (connection != null) {
      connection.disconnect();
    }

  }

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

  //Adds contact to the remote roster. We maintain our own local contact list[Roster]
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

  public boolean subscribe(String contact) {

    Jid jidTo = null;

    try {
      jidTo = JidCreate.from(contact);
    } catch (XmppStringprepException e) {
      e.printStackTrace();
      return false;
    }

    Presence subscribe = new Presence(jidTo, Presence.Type.subscribe);
    if (sendPresence(subscribe)) {
      return true;
    } else {
      return false;
    }
  }


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

  public boolean unsubsribe(String contact) {

    Jid jidTo = null;

    try {
      jidTo = JidCreate.from(contact);
    } catch (XmppStringprepException e) {
      e.printStackTrace();
      return false;
    }

    Presence unsubscribe = new Presence(jidTo, Presence.Type.unsubscribe);
    if (sendPresence(unsubscribe)) {
      return true;
    } else {
      return false;
    }
  }

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


  @Override
  public void connected(XMPPConnection connection) {
    Log.d(LOGTAG, " - Connected");
    mConnectionState = ConnectionState.CONNECTING;
    updateActivitiesOfConnectionStateChange(ConnectionState.CONNECTING);
  }

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

  @Override
  public void connectionClosed() {
    Log.d(LOGTAG, " - connectionClosed");
//        notifyUiForConnectionError();
    mConnectionState = ConnectionState.OFFLINE;
    updateActivitiesOfConnectionStateChange(ConnectionState.OFFLINE);
  }

  @Override
  public void connectionClosedOnError(Exception e) {
    Log.d(LOGTAG, " - connectionClosedOnError");
    mConnectionState = ConnectionState.OFFLINE;
    updateActivitiesOfConnectionStateChange(ConnectionState.OFFLINE);
  }

  @Override
  public void reconnectionSuccessful() {
    Log.d(LOGTAG, " - reconnectionSuccessful");
    mConnectionState = ConnectionState.ONLINE;
    updateActivitiesOfConnectionStateChange(ConnectionState.ONLINE);
  }

  @Override
  public void reconnectingIn(int seconds) {
    Log.d(LOGTAG, " - Reconnecting in " + seconds + " seconds");
    mConnectionState = ConnectionState.CONNECTING;
    updateActivitiesOfConnectionStateChange(ConnectionState.CONNECTING);
  }

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
      List<com.example.informationappjava.ui.chat.login.model.Chat> chats = ChatModel.get(context)
          .getChatsByJid(from.toString());
      if (chats.size() == 0) {
        //Only add the chat when it is not already available
        if (ChatModel.get(context).addChat(
            new com.example.informationappjava.ui.chat.login.model.Chat(from.toString(),
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
}
