package com.example.informationappjava.ui.settings;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import com.example.informationappjava.R;

import java.util.Locale;

/**
 *The SettingsActivity class displays the settings overview of the app.
 */
public class SettingsActivity extends AppCompatActivity {

  private SwitchCompat myswitch;

  private SharedPref sharedPref;

  /**
   *The onCreate() function is used to check the chosen theme and save the state of the chosen theme.
   * @param savedInstanceState
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {

    sharedPref = new SharedPref(this);

    if (sharedPref.loadNightModeState()) {
      setTheme(R.style.SettingsDark);
    } else {
      setTheme(R.style.SettingsLight);
    }

    super.onCreate(savedInstanceState);
    loadLocale();
    setContentView(R.layout.activity_settings);

    ActionBar actionBar = getSupportActionBar();
    actionBar.setTitle(getResources().getString(R.string.menu_settings));

    Button changeLang = findViewById(R.id.language);
    changeLang.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {

        showChangeLangugageDialog();
      }
    });

    myswitch = findViewById(R.id.myswitch);

    if (sharedPref.loadNightModeState()) {
      myswitch.setChecked(true);
    }

    myswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
          sharedPref.setNightModeState(true);
          restartApp();
        } else {
          sharedPref.setNightModeState(false);
          restartApp();
        }
      }
    });
  }

  /**
   *Restarts the app to apply changes.
   */
  public void restartApp() {
    Intent i = new Intent(getApplicationContext(), SettingsActivity.class);
    startActivity(i);
    finish();
  }

  /**
   *The showChangeLanguageDialog() method it used to display items which can be chosen due to the
   *onCLick() method.
   */
  private void showChangeLangugageDialog() {
    final String[] listItems = {"Deutsch/German", "Englisch/English"};
    AlertDialog.Builder mBuilder = new AlertDialog.Builder(SettingsActivity.this);
    mBuilder.setTitle("Choose a language/Wähle deine Sprache...");
    mBuilder.setSingleChoiceItems(listItems, -1, new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialogInterface, int i) {
        if (i == 0) {
          setLocale("de");
          recreate();

        } else if (i == 1) {
          setLocale("en");
          recreate();
        }

        dialogInterface.dismiss();
      }
    });

    AlertDialog mDialog = mBuilder.create();
    mDialog.show();

  }

  /**
   *The setLocale() function is used to set the right locale for the chosen language.
   * @param lang
   */
  private void setLocale(String lang) {
    Locale locale = new Locale(lang);
    Locale.setDefault(locale);
    Configuration config = new Configuration();
    config.locale = locale;
    getBaseContext().getResources()
        .updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());

    SharedPreferences.Editor editor = getSharedPreferences("Settings", MODE_PRIVATE).edit();
    editor.putString("My_Lang", lang);
    editor.apply();
  }

  /**
   *The laodLocale() function is used to load the right locale for the chosen language.
   */
  public void loadLocale() {
    SharedPreferences prefs = getSharedPreferences("Settings", Activity.MODE_PRIVATE);
    String language = prefs.getString("My_Lang", "");
    setLocale(language);
  }
}