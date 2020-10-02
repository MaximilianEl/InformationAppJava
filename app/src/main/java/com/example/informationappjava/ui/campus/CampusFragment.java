package com.example.informationappjava.ui.campus;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;
import com.example.informationappjava.R;
import com.getbase.floatingactionbutton.FloatingActionButton;

public class CampusFragment extends Fragment {

  private CampusViewModel mViewModel;
  FloatingActionButton fButton;
  ViewPager viewPager;
  ImageAdapter imageAdapter;
  Button campMapButton;

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {

    View view = inflater.inflate(R.layout.fragment_campus, container, false);

    Fragment fragment = new ImgSlideFragment();
    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//    fragmentTransaction.replace(R.id.campus_fragment, fragment);
    fragmentTransaction.addToBackStack(null);
    fragmentTransaction.commit();

    campMapButton = view.findViewById(R.id.campus_map_button);
    campMapButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        Fragment fragment = new CampusMapFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.replace(R.id.campus_fragment, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
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