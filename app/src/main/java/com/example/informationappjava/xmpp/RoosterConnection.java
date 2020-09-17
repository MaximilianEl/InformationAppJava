package com.example.informationappjava.xmpp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import com.example.informationappjava.ui.chat.login.Constants;
import com.example.informationappjava.ui.chat.login.Constants.BroadCastMessages;

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
import org.jivesoftware.smackx.ping.PingManager;
import org.jivesoftware.smackx.ping.android.ServerPingWithAlarmManager;

public class RoosterConnection implements ConnectionListener {

    private static final String LOGTAG = "RoosterConnection";

    private final Context context;
    private String username;
    private String password;
    private String serviceName;
    private XMPPTCPConnection connection;
    private ConnectionState mConnectionState;
    private PingManager pingManager;

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
        preferences.edit().putBoolean("xmpp_logged_in", false).commit();

        Intent intent = new Intent(BroadCastMessages.UI_AUTHENTICATED);
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
}
