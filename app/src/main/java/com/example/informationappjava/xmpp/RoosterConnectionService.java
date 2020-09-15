package com.example.informationappjava.xmpp;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import androidx.annotation.Nullable;
import java.io.IOException;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;

public class RoosterConnectionService extends Service {

  private static final String LOGTAG = "RoosterConnectionService";

  //Stores whether or not the thread is active
  private boolean active;
  private Thread thread;
  //We use this handler to post messages to the background thread.
  private Handler handler;
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

      Log.d(LOGTAG, "Something went wrong while connecting, make sure the credentials are right and try again");
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
    stop();
  }

  public void start() {
    Log.d(LOGTAG, "Service Start() function calles. active: " + active);
    if (!active) {

      active = true;
      if (thread == null || thread.isAlive()){
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
        if (connection != null){
          connection.disconnect();
        }
      }
    });
  }
}
