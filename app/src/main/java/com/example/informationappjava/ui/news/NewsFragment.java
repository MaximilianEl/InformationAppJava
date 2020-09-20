
package com.example.informationappjava.ui.news;

import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.informationappjava.R;

public class NewsFragment extends Fragment {

    private NewsViewModel newsViewModel;
    private WebView youVideoView;
    // private VideoView videoView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        

        newsViewModel =
                ViewModelProviders.of(this).get(NewsViewModel.class);
        View view = inflater.inflate(R.layout.fragment_news, container, false);


        //videoView = (VideoView) view.findViewById(R.id.videoView);

        // Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.video_campus);
        // videoView.setVideoURI(uri);

        // @Override
        // protected void onResume(){
        //     super.onResume();
        //    videoView.start();
        //  }

        // @Override
        //  protected void onPause(){
        //     super.onPause();
        //     videoView.suspend();
        // }

        youVideoView = (WebView) view.findViewById(R.id.videoView);
        youVideoView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return false;
            }
        });
        WebSettings websettings = youVideoView.getSettings();
        websettings.setJavaScriptEnabled(true);
        youVideoView.loadData("<html><iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/RhnYEDhsczs\" frameborder=\"0\" allow=\"accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture\" allowfullscreen></iframe></html>", "text/html", "utf-8");

        final TextView textView = view.findViewById(R.id.text_home);
        newsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return view;
    }
}
