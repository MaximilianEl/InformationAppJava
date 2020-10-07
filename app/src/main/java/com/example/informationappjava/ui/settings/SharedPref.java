package com.example.informationappjava.ui.settings;

import android.content.Context;
import android.content.SharedPreferences;

import android.preference.PreferenceActivity;

public class SharedPref extends PreferenceActivity {

  private SharedPreferences mySharedPref;
  public SharedPref (Context context){
    mySharedPref = context.getSharedPreferences("filename",Context.MODE_PRIVATE);
  }

  public void setNightModeState (Boolean state){
    SharedPreferences.Editor editor = mySharedPref.edit();
    editor.putBoolean("NightMode",state);
    editor.apply();
  }

  public Boolean loadNightModeState(){
    return mySharedPref.getBoolean("NightMode",false);

  }
}
