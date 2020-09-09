package com.example.informationappjava.ui.registration;

import android.webkit.WebView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.informationappjava.R;

public class RegistrationActivity extends AppCompatActivity {

  private WebView webView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    webView = new WebView(this);
    webView.loadUrl("http://sep-01.lin.hs-osnabrueck.de:9090/plugins/registration/sign-up.jsp");
    setContentView(webView);
  }
}