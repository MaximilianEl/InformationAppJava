package com.example.informationappjava.xmpp;

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

public class RoosterConnectionService extends Service {

  private static final String LOGTAG = "RoosterConService";

  //Stores whether or not the thread is active
  private boolean active;
  private Thread thread;
  //Posts Messages to the Background Thread
  private Handler handler;

  public static RoosterConnection getConnection() {
    return connection;
  }

  private static RoosterConnection connection;

  public RoosterConnectionService() {
  }

  private void initConnection() {
    Log.d(LOGTAG, " initConnection()");

    if (connection == null) {
      connection = new RoosterConnection(this);
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

  @Nullable
  @Override
  public IBinder onBind(Intent intent) {
    return null;
  }

  @Override
  public void onCreate() {
    super.onCreate();
    ServerPingWithAlarmManager.onCreate(this);
  }

  @Override
  public int onStartCommand(Intent intent, int flags, int startId) {
    //Do your task here
    start();
    return Service.START_STICKY;
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    ServerPingWithAlarmManager.onDestroy();
    stop();
  }

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
