package com.example.informationappjava.ui.institute;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import com.example.informationappjava.R;

/**
 *
 */
public class InstituteFragment extends Fragment {

  private InstituteViewModel instituteViewModel;
  private RecyclerView recyclerView;
  private RecyclerView.Adapter adapter;
  private RecyclerView.LayoutManager layoutManager;
  private CardView cardViewCourse;
  private CardView cardViewFreshmanHelp;
  private CardView cardViewEvent;
  private CardView cardViewPerson;
  private final String course = "course";
  private final String freshmanHelp = "freshmanHelp";
  private final String event = "event";
  private final String person = "person";

  /**
   *
   * @param inflater
   * @param container
   * @param savedInstanceState
   * @return
   */
  public View onCreateView(@NonNull LayoutInflater inflater,
      ViewGroup container, Bundle savedInstanceState) {
    instituteViewModel = ViewModelProviders.of(this).get(InstituteViewModel.class);
    View view = inflater.inflate(R.layout.fragment_institute, container, false);

    cardViewCourse = view.findViewById(R.id.institute_courses);
    cardViewCourse.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent(getActivity(), InstituteCourseActivity.class);
        startActivity(intent);
      }
    });

    cardViewFreshmanHelp = view.findViewById(R.id.institute_freshman_help);
    cardViewFreshmanHelp.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        String url = "https://www.hs-osnabrueck.de/wir/fakultaeten/mkt/institute/institut-fuer-management-und-technik/erstsemesterinformationen/";
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
      }
    });

    cardViewEvent = view.findViewById(R.id.institute_events);
    cardViewEvent.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        String url = "https://www.hs-osnabrueck.de/wir/wir-stellen-uns-vor/veranstaltungen/";
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
      }
    });

    cardViewPerson = view.findViewById(R.id.institute_person);
    cardViewPerson.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent(getActivity(), PersonContextActivity.class);
        intent.putExtra("value", person);
        startActivity(intent);
      }
    });

    return view;
  }


}