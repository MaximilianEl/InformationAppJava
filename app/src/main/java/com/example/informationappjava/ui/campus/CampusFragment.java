package com.example.informationappjava.ui.campus;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;
import com.example.informationappjava.R;
import com.example.informationappjava.ui.campus.maps.CampusMapActivity;
import com.example.informationappjava.ui.campus.maps.CityMapActivity;
import com.getbase.floatingactionbutton.FloatingActionButton;

public class CampusFragment extends Fragment {

  private CampusViewModel mViewModel;
  FloatingActionButton fButton;
  ViewPager viewPager;
  ImageAdapter imageAdapter;
  Button campMapButton;
  Button cityMapButton;

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {

    View view = inflater.inflate(R.layout.fragment_campus, container, false);
    viewPager = (ViewPager) view.findViewById(R.id.camppager);
    imageAdapter = new ImageAdapter(this.getActivity());
    viewPager.setAdapter(imageAdapter);

    campMapButton = view.findViewById(R.id.campus_map_button);
    campMapButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent(getActivity(), CampusMapActivity.class);
        startActivity(intent);
      }
    });

    cityMapButton = view.findViewById(R.id.campus_des_button);
    cityMapButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent(getActivity(), CityMapActivity.class);
        startActivity(intent);
      }
    });

    return view;
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    mViewModel = ViewModelProviders.of(this).get(CampusViewModel.class);
  }

}