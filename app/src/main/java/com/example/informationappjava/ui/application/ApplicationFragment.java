package com.example.informationappjava.ui.application;

import android.content.Intent;
import android.view.View.OnClickListener;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.informationappjava.R;

/**
 *
 */
public class ApplicationFragment extends Fragment {

  private ApplicationViewModel mViewModel;
  private CardView application;
  private CardView approve;
  private CardView enrol;
  private CardView start;
  private String applicationString = "application";
  private String enrolString = "enrol";
  private String approveString = "approve";
  private String startString = "start";

  /**
   * @return
   */
  public static ApplicationFragment newInstance() {
    return new ApplicationFragment();
  }

  /**
   * @param inflater
   * @param container
   * @param savedInstanceState
   * @return
   */
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_application, container, false);

    application = view.findViewById(R.id.application_appli);
    application.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent(getActivity(), ApplicationContextActivity.class);
        intent.putExtra("value", applicationString);
        startActivity(intent);
      }
    });

    approve = view.findViewById(R.id.application_approve);
    approve.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent(getActivity(), ApplicationContextActivity.class);
        intent.putExtra("value", approveString);
        startActivity(intent);
      }
    });

    enrol = view.findViewById(R.id.application_enrol);
    enrol.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent(getActivity(), ApplicationContextActivity.class);
        intent.putExtra("value", enrolString);
        startActivity(intent);
      }
    });

    start = view.findViewById(R.id.application_start);
    start.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent(getActivity(), ApplicationContextActivity.class);
        intent.putExtra("value", startString);
        startActivity(intent);
      }
    });

    return view;
  }

  /**
   * @param savedInstanceState
   */
  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    mViewModel = ViewModelProviders.of(this).get(ApplicationViewModel.class);
    // TODO: Use the ViewModel
  }

}