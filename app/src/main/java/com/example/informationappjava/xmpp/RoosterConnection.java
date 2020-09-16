package com.example.informationappjava.xmpp;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import java.io.IOException;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.ConnectionConfiguration.SecurityMode;
import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.SmackConfiguration;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;

public class RoosterConnection implements ConnectionListener {

  private static final String LOGTAG = "RoosterConnection";

  private final Context context;
  private String username;
  private String password;
  private String serviceName;
  private XMPPTCPConnection connection;


  public RoosterConnection(Context context) {
    Log.d(LOGTAG, "RoosterConnection Constructor called");
    this.context = context;
  }

  public void connect() throws IOException, XMPPException, SmackException {

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
  }

  @Override
  public void authenticated(XMPPConnection connection, boolean resumed) {
    Log.d(LOGTAG, " - authenticated");
    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
    preferences.edit().putBoolean("xmpp_logged_in", false).commit();

  }

  @Override
  public void connectionClosed() {
    Log.d(LOGTAG, " - connectionClosed");
  }

  @Override
  public void connectionClosedOnError(Exception e) {
    Log.d(LOGTAG, " - connectionClosedOnError");
  }

  @Override
  public void reconnectionSuccessful() {
    Log.d(LOGTAG, " - reconnectionSuccessful");
  }

  @Override
  public void reconnectingIn(int seconds) {
    Log.d(LOGTAG, " - Reconnecting in " + seconds + " seconds");
  }

  @Override
  public void reconnectionFailed(Exception e) {
    Log.d(LOGTAG, " - reconnectionFailed");
  }
}
