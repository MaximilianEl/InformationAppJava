package com.example.informationappjava.ui.options;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CompoundButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import com.example.informationappjava.R;

public class SettingsActivity extends AppCompatActivity {

    private static final String LOGTAG = "SWITCH";
    private int soEineNummer;

    private SwitchCompat myswitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (AppCompatDelegate.getDefaultNightMode()== AppCompatDelegate.MODE_NIGHT_YES){
            setTheme(R.style.darktheme);
        }else setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);

        if (AppCompatDelegate.getDefaultNightMode()== AppCompatDelegate.MODE_NIGHT_YES){
            myswitch.setChecked(true);
        }

        myswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    restartApp();
                }else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    restartApp();
                }
            }
        });
    }

    public  void restartApp() {
        Intent i = new Intent(getApplicationContext(), SettingsActivity.class);
        startActivity(i);
        finish();
    }


}