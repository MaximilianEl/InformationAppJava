package com.example.informationappjava.xmpp;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import androidx.annotation.Nullable;
import androidx.preference.PreferenceManager;
import com.example.informationappjava.ui.chat.login.Constants.BroadCastMessages;

import java.io.IOException;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smackx.ping.android.ServerPingWithAlarmManager;

/**
 * The ChatConnectionService class is used to get and initialize a connection to the server.
 */
public class ChatConnectionService extends Service {

  private static final String LOGTAG = "RoosterConService";

  //Stores whether or not the thread is active
  private boolean active;
  private Thread thread;
  //Posts Messages to the Background Thread
  private Handler handler;

  /**
   * @return connection
   */
  public static ChatConnection getConnection() {
    return connection;
  }

  @SuppressLint("StaticFieldLeak")
  private static ChatConnection connection;

  /**
   * Constructor of ChatConnectionService.
   */
  public ChatConnectionService() {
  }

  /**
   * If there is no connection, a new connection will be established using the ChatConnection class
   * Then the try statement tries to establish a connection with connection.connect().If the connect
   * function fails, a message is displayed that an error has occurred. At the end it is checked if
   * the user is still shown as logged in or not. If the user is not logged in, the connection
   * service is stopped. In case the user is still logged in, the system will continue to try to
   * establish a connection.
   */
  private void initConnection() {
    Log.d(LOGTAG, " initConnection()");

    if (connection == null) {
      connection = new ChatConnection(this);
    }

    try {

      connection.connect();

    } catch (IOException | XMPPException | SmackException e) {
      Log.d(LOGTAG,
          "Something went wrong while connecting, make sure the credentials are right and try again");

      Intent intent = new Intent(BroadCastMessages.UI_CONNECTION_ERROR);
      intent.setPackage(getApplicationContext().getPackageName());
      getApplicationContext().sendBroadcast(intent);
      Log.d(LOGTAG, "Sent the broadcast for connection Error from service");

      //Stop the service all together if user is not logged in already.
      boolean logged_in_state = PreferenceManager
          .getDefaultSharedPreferences(getApplicationContext())
          .getBoolean("xmpp_logged_in", false);
      if (!logged_in_state) {

        Log.d(LOGTAG, "Logged in state: " + logged_in_state + " calling stopself()");
        stopSelf();

      } else {

        Log.d(LOGTAG, "Logged in state: " + logged_in_state);

      }

      e.printStackTrace();

    }
  }

  /**
   * @param intent
   * @return null
   */
  @Nullable
  @Override
  public IBinder onBind(Intent intent) {
    return null;
  }

  /**
   * Sends automatic pings to the Server.
   */
  @Override
  public void onCreate() {
    super.onCreate();
    ServerPingWithAlarmManager.onCreate(this);
  }

  /**
   * onStartCommand() function.
   *
   * @param intent
   * @param flags
   * @param startId
   * @return
   */
  @Override
  public int onStartCommand(Intent intent, int flags, int startId) {
    //Do your task here
    start();
    return Service.START_STICKY;
  }

  /**
   * Unregisters the alarm broadcast receiver and cancels the alarm. Then stops.
   */
  @Override
  public void onDestroy() {
    super.onDestroy();
    ServerPingWithAlarmManager.onDestroy();
    stop();
  }

  /**
   * Starts the initConnection() on a Fred function if it is not active.
   */
  public void start() {
    Log.d(LOGTAG, "Service Start() function calles. active: " + active);
    if (!active) {

      active = true;
      if (thread == null || !thread.isAlive()) {
        thread = new Thread(new Runnable() {
          @Override
          public void run() {

            Looper.prepare();
            handler = new Handler();
            initConnection();

            //The Code here runs in background thread
            Looper.loop();
          }
        });
        thread.start();
      }
    }
  }

  /**
   * Function to disconnect from the connection if connection is not null.
   */
  public void stop() {

    Log.d(LOGTAG, "stop()");
    active = false;
    handler.post(new Runnable() {
      @Override
      public void run() {
        if (connection != null) {
          connection.disconnect();
        }
      }
    });
  }
}
