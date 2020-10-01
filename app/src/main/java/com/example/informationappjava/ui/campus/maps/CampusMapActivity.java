package com.example.informationappjava.ui.campus.maps;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.informationappjava.R;
import com.google.android.material.snackbar.Snackbar;

public class CampusMapActivity extends AppCompatActivity {

  private LinearLayout kdLayout;
  private LinearLayout kdPlusLayout;
  private LinearLayout keLayout;
  private LinearLayout kcLayout;
  private LinearLayout kfLayout;
  private LinearLayout kbLayout;
  private LinearLayout kgLayout;
  private LinearLayout kaLayout;
  private LinearLayout kaPlusLayout;
  private LinearLayout khLayout;
  private LinearLayout mapLayout;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_campus_map);

    kdLayout = findViewById(R.id.kd);
    kdPlusLayout = findViewById(R.id.kdplus);
    keLayout = findViewById(R.id.ke);
    kcLayout = findViewById(R.id.kc);
    kfLayout = findViewById(R.id.kf);
    kbLayout = findViewById(R.id.kb);
    kgLayout = findViewById(R.id.kg);
    kaLayout = findViewById(R.id.ka);
    kaPlusLayout = findViewById(R.id.kaplus);
    khLayout = findViewById(R.id.kh);
    mapLayout = findViewById(R.id.map_layout);

    kdLayout.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        Snackbar.make(mapLayout, "KD", Snackbar.LENGTH_LONG).show();
      }
    });

    kdPlusLayout.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        Snackbar.make(mapLayout, "KDPlus", Snackbar.LENGTH_LONG).show();
      }
    });

    keLayout.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        Snackbar.make(mapLayout, "KE", Snackbar.LENGTH_LONG).show();
      }
    });

    kcLayout.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        Snackbar.make(mapLayout, "KC", Snackbar.LENGTH_LONG).show();
      }
    });

    kfLayout.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        Snackbar.make(mapLayout, "KF", Snackbar.LENGTH_LONG).show();
      }
    });

    kbLayout.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        Snackbar.make(mapLayout, "KB", Snackbar.LENGTH_LONG).show();
      }
    });

    kgLayout.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        Snackbar.make(mapLayout, "KG", Snackbar.LENGTH_LONG).show();
      }
    });

    kaLayout.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        Snackbar.make(mapLayout, "KA", Snackbar.LENGTH_LONG).show();
      }
    });

    kaPlusLayout.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        Snackbar.make(mapLayout, "KAPlus", Snackbar.LENGTH_LONG).show();
      }
    });

    khLayout.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        Snackbar.make(mapLayout, "KH", Snackbar.LENGTH_LONG).show();
      }
    });
  }
}