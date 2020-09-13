package com.example.informationappjava.xmpp;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
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
    } catch (IOException e) {
      e.printStackTrace();
    } catch (XMPPException e) {
      e.printStackTrace();
    } catch (SmackException e) {
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
    return Service.START_STICKY;
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
  }
}
