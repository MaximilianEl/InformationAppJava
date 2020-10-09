package com.example.informationappjava.ui.chat.Utils;

import android.app.ActivityManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.format.DateFormat;
import java.util.concurrent.TimeUnit;

/**
 *
 */
public class Utilities {

  /**
   * @param serviceClas
   * @param context
   * @return
   */
  public static boolean isServiceRunning(Class<?> serviceClas, Context context) {
    ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
    for (ActivityManager.RunningServiceInfo service : manager
        .getRunningServices(Integer.MAX_VALUE)) {
      if (serviceClas.getName().equals(service.service.getClassName())) {
        return true;
      }
    }
    return false;
  }

  /**
   * @param context
   * @return
   */
  public static boolean isNetworkAvailable(Context context) {
    ConnectivityManager connectivityManager = (ConnectivityManager) context
        .getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
  }

  /**
   * @param timestamp
   * @return
   */
  public static String getFormattedTime(long timestamp) {

    long oneDayInMillis = TimeUnit.DAYS.toMillis(1); //24 * 60 * 60 * 1000
    long timeDifference = System.currentTimeMillis() - timestamp;

    return timeDifference < oneDayInMillis
        ? DateFormat.format("hh:mm a", timestamp).toString()
        : DateFormat.format("dd MMM - hh:mm a", timestamp).toString();
  }
}