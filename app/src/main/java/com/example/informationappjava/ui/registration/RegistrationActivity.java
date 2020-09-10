package com.example.informationappjava.ui.registration;

import android.app.Activity;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.informationappjava.R;

public class RegistrationActivity extends AppCompatActivity {

  private WebView webView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    webView = new WebView(this);
    webView.getSettings().setJavaScriptEnabled(true);
    webView.setWebViewClient(new WebViewClient());
    webView.loadUrl("http://sep-01.lin.hs-osnabrueck.de:9090/plugins/registration/sign-up.jsp");
    setContentView(webView);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();

    if (webView != null) {
      webView.destroy();
    }
  }
}