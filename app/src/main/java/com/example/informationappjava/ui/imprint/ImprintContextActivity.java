package com.example.informationappjava.ui.imprint;

import android.content.Intent;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.informationappjava.R;

/**
 * ImprintContextActivity is used to set different Texts to the layout xml file.
 */
public class ImprintContextActivity extends AppCompatActivity {

  private String dataPrivString = "dataPriv";
  private String disclaimerString = "disclaimer";
  private String copyrightString = "copyright";
  private String openSourceLibrariesString = "openSourceLibraries";
  private TextView header;
  private TextView body;

  /**
   * The onCreate() function contains an if statement which one checks which extra it got from the
   * ImprintFragment.
   *
   * @param savedInstanceState
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_imprint_context);

    Intent intent = getIntent();
    String s1 = intent.getStringExtra("value");

    header = findViewById(R.id.imprint_header);
    body = findViewById(R.id.imprint_context);

    if (s1.equals(dataPrivString)) {
      header.setText(getResources().getString(R.string.imprint_data));
      body.setText("Hier steht was zum Datenschutz");
    } else if (s1.equals(disclaimerString)) {
      header.setText(getResources().getString(R.string.imprint_haft));
      body.setText("Hier steht was zur Haftung");
    } else if (s1.equals(copyrightString)) {
      header.setText(getResources().getString(R.string.imprint_uhr));
      body.setText("Hier steht was zum Uhrheberrecht");
    } else if (s1.equals(openSourceLibrariesString)) {
      header.setText(getResources().getString(R.string.imprint_osource));
      body.setText("Hier stehen die OpenSourceLibraries");
    }
  }
}