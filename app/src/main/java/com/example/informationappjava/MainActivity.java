package com.example.informationappjava;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

/**
 * MainActivity InfoApp
 */
public class MainActivity extends AppCompatActivity {

    Timer timer;

    /**
     * Shows a short Logo and then goes straight to the NavDrawer (Navigation Drawer).
     *
     * @param savedInstanceState
     */
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