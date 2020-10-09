package com.example.informationappjava.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import com.example.informationappjava.R;

/**
 *
 */
public class HomeFragment extends Fragment {

  private HomeViewModel newsViewModel;
  private WebView youVideoView;
  // private VideoView videoView;

  /**
   * @param inflater
   * @param container
   * @param savedInstanceState
   * @return
   */
  public View onCreateView(@NonNull LayoutInflater inflater,
      ViewGroup container, Bundle savedInstanceState) {
    newsViewModel =
        ViewModelProviders.of(this).get(HomeViewModel.class);
    View view = inflater.inflate(R.layout.fragment_home, container, false);

    youVideoView = view.findViewById(R.id.videoView);
    youVideoView.setWebViewClient(new WebViewClient() {
      @Override
      public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        return false;
      }
    });
    WebSettings websettings = youVideoView.getSettings();
    websettings.setJavaScriptEnabled(true);
    youVideoView.loadData(
        "<html><iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/RhnYEDhsczs\" frameborder=\"0\" allow=\"accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture\" allowfullscreen></iframe></html>",
        "text/html", "utf-8");

//    final TextView textView = view.findViewById(R.id.text_home);
//    newsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//      @Override
//      public void onChanged(@Nullable String s) {
//        textView.setText(s);
//      }
//    });
    return view;
  }
}
