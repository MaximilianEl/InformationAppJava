package com.example.informationappjava.ui.imprint;


import android.view.View.OnClickListener;
import android.widget.TextView;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.informationappjava.R;

/**
 *
 */
public class ImprintFragment extends Fragment {

  private ImprintViewModel mViewModel;
  private ImageView insta;
  private ImageView facebook;
  private ImageView twitter;
  private ImageView youtube;
  private TextView dataPriv;
  private TextView disclaimer;
  private TextView copyright;
  private TextView openSourceLibraries;
  private String dataPrivString = "dataPriv";
  private String disclaimerString = "disclaimer";
  private String copyrightString = "copyright";
  private String openSourceLibrariesString = "openSourceLibraries";


  public static ImprintFragment newInstance() {
    return new ImprintFragment();
  }

  /**
   *
   * @param inflater
   * @param container
   * @param savedInstanceState
   * @return
   */
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {

    View view = inflater.inflate(R.layout.fragment_imprint, container, false);

    insta = view.findViewById(R.id.impinsta);
    insta.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        String url = "https://www.instagram.com/hs_osnabrueck/?hl=de";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
      }
    });

    facebook = view.findViewById(R.id.impfacebook);
    facebook.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        String url = "https://www.facebook.com/hs.osnabrueck";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
      }
    });

    twitter = view.findViewById(R.id.imptwitter);
    twitter.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        String url = "https://twitter.com/HS_Osnabrueck?ref_src=twsrc%5Egoogle%7Ctwcamp%5Eserp%7Ctwgr%5Eauthor";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
      }
    });

    youtube = view.findViewById(R.id.impyoutube);
    youtube.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        String url = "https://www.youtube.com/user/HochschuleOS";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
      }
    });

    dataPriv = view.findViewById(R.id.datapriv);
    dataPriv.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent(getActivity(), ImprintContextActivity.class);
        intent.putExtra("value", dataPrivString);
        startActivity(intent);
      }
    });

    disclaimer = view.findViewById(R.id.disclaimer);
    disclaimer.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent(getActivity(), ImprintContextActivity.class);
        intent.putExtra("value", disclaimerString);
        startActivity(intent);
      }
    });

    copyright = view.findViewById(R.id.copyright);
    copyright.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent(getActivity(), ImprintContextActivity.class);
        intent.putExtra("value", copyrightString);
        startActivity(intent);
      }
    });

    openSourceLibraries = view.findViewById(R.id.osl);
    openSourceLibraries.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent(getActivity(), ImprintContextActivity.class);
        intent.putExtra("value", openSourceLibrariesString);
        startActivity(intent);
      }
    });


    return view;

  }

  /**
   *
   * @param savedInstanceState
   */
  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    mViewModel = ViewModelProviders.of(this).get(ImprintViewModel.class);
  }
}