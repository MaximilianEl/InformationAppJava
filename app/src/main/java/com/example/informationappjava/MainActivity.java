package com.example.informationappjava;

import android.Manifest.permission;
import android.content.Intent;
import android.content.pm.PackageManager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import java.util.Timer;
import java.util.TimerTask;

public class   MainActivity extends AppCompatActivity {

  Timer timer;


  @Override
  protected void onCreate(Bundle savedInstanceState) {



    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    timer = new Timer();
    timer.schedule(new TimerTask() {
      @Override
      public void run() {
        Intent intent = new Intent(MainActivity.this, NavDrawer.class);
        startActivity(intent);
        finish();
      }
    }, 3000);
  }


}