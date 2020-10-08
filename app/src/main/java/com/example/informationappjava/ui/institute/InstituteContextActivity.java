package com.example.informationappjava.ui.institute;

import android.content.Intent;
import android.widget.GridLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.informationappjava.R;

public class InstituteContextActivity extends AppCompatActivity {

  private String course = "course";
  private String freshmanHelp = "freshmanHelp";
  private String event = "event";
  private String person = "person";
  private GridLayout gridLayout;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_institute_context);

    Intent intent = getIntent();
    String s1 = intent.getStringExtra("value");

    gridLayout = findViewById(R.id.ins_grid);
  }
}