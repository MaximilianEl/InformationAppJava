package com.example.informationappjava.ui.imprint;

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

public class ImprintFragment extends Fragment {

  private ImprintViewModel mViewModel;
  private ImageView insta;
  private ImageView facebook;
  private ImageView twitter;
  private ImageView youtube;
  //SPast

  public static ImprintFragment newInstance() {
    return new ImprintFragment();
  }

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {

    View view = inflater.inflate(R.layout.fragment_imprint,container,false);

    /*insta = view.findViewById(R.id.instagram);
    insta.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        String url = "https://www.instagram.com/hs_osnabrueck/?hl=de";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
      }
    });

    facebook = view.findViewById(R.id.facebook);
    facebook.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        String url = "https://www.facebook.com/hs.osnabrueck";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
      }
    });

    twitter = view.findViewById(R.id.twitter);
    twitter.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        String url = "https://twitter.com/HS_Osnabrueck?ref_src=twsrc%5Egoogle%7Ctwcamp%5Eserp%7Ctwgr%5Eauthor";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
      }
    });

    youtube = view.findViewById(R.id.youtube);
    youtube.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        String url = "https://www.youtube.com/user/HochschuleOS";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
      }
    });*/

    return view;

  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);


    mViewModel = ViewModelProviders.of(this).get(ImprintViewModel.class);
    // TODO: Use the ViewModel

  }

}