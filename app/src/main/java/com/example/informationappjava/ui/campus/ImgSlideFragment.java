package com.example.informationappjava.ui.campus;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.informationappjava.R;
import org.osmdroid.config.Configuration;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;
import androidx.viewpager.widget.ViewPager;

public class ImgSlideFragment extends Fragment {
    private  ViewPager viewPager;
    private ImageAdapter imageAdapter;
    public ImgSlideFragment() {}

    public static ImgSlideFragment newInstance() {
        return new ImgSlideFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_img_slide, container, false);
        viewPager = view.findViewById(R.id.camppager);
        imageAdapter = new ImageAdapter(this.getActivity());
        viewPager.setAdapter(imageAdapter);
        return view;
    }
}