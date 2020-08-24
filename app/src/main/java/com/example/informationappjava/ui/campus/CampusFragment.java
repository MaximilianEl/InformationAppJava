package com.example.informationappjava.ui.campus;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;
import com.example.informationappjava.R;
import com.example.informationappjava.ui.imprint.ImprintViewModel;

public class CampusFragment extends Fragment {

  private CampusViewModel mViewModel;
  ViewPager viewPager;
  ImageAdapter imageAdapter;

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {

    View view = inflater.inflate(R.layout.fragment_campus, container, false);
    viewPager = (ViewPager) view.findViewById(R.id.camppager);
    imageAdapter = new ImageAdapter(this.getActivity());
    viewPager.setAdapter(imageAdapter);
    return view;
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    mViewModel = ViewModelProviders.of(this).get(CampusViewModel.class);
  }

}