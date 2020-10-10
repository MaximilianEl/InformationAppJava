package com.example.informationappjava.ui.campus;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import com.example.informationappjava.R;

/**
 *The campus fragment is needed to display an overview of the campus site.
 */
public class CampusFragment extends Fragment {

  private CampusViewModel mViewModel;
  CardView campMapButton;

  /**
   * The onCreateView() method is used for creating an image slide fragment inside the campus fragment.
   * The onCreateView() method contains a onClick() method, which is used to start the campus map
   * activity.
   * @param inflater
   * @param container
   * @param savedInstanceState
   * @return view
   */
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {

    View view = inflater.inflate(R.layout.fragment_campus, container, false);

    Fragment fragment = new ImgSlideFragment();
    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
    fragmentTransaction.replace(R.id.campus_fragment_slideshow, fragment);
    fragmentTransaction.addToBackStack(null);
    fragmentTransaction.commit();

    campMapButton = view.findViewById(R.id.campus_map_button);
    campMapButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent(getActivity(), CampusMapActivity.class);
        startActivity(intent);
      }
    });

    return view;
  }

  /**
   * The onActivityCreated() method calls upon the ViewModel for the campus fragment.
   * @param savedInstanceState
   */
  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    mViewModel = ViewModelProviders.of(this).get(CampusViewModel.class);
  }

}