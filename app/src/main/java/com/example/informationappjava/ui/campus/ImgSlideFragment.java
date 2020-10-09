package com.example.informationappjava.ui.campus;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.informationappjava.R;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

/**
 *
 */
public class ImgSlideFragment extends Fragment {

  private ViewPager viewPager;
  private ImageAdapter imageAdapter;

  public ImgSlideFragment() {
  }

  /**
   * @return
   */
  public static ImgSlideFragment newInstance() {
    return new ImgSlideFragment();
  }

  /**
   * @param inflater
   * @param container
   * @param savedInstanceState
   * @return
   */
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
      @Nullable Bundle savedInstanceState) {

    View view = inflater.inflate(R.layout.fragment_img_slide, container, false);
    viewPager = view.findViewById(R.id.camppager);
    imageAdapter = new ImageAdapter(this.getActivity());
    viewPager.setAdapter(imageAdapter);
    return view;
  }
}