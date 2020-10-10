package com.example.informationappjava.ui.settings;

import android.content.Context;
import android.content.SharedPreferences;

import android.preference.PreferenceActivity;

/**
 *The SharedPref class is needed for saving changed settings.
 */
public class SharedPref extends PreferenceActivity {

  private final SharedPreferences mySharedPref;

  public SharedPref(Context context) {
    mySharedPref = context.getSharedPreferences("filename", Context.MODE_PRIVATE);
  }

  /**
   *This function applies the night mode.
   * @param state
   */
  public void setNightModeState(Boolean state) {
    SharedPreferences.Editor editor = mySharedPref.edit();
    editor.putBoolean("NightMode", state);
    editor.apply();
  }

  /**
   *This funtion checks if the night mode is applied.
   * @return
   */
  public Boolean loadNightModeState() {
    return mySharedPref.getBoolean("NightMode", false);

  }
}
