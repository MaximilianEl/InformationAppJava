package com.example.informationappjava.ui.institute;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.loader.content.AsyncTaskLoader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.informationappjava.R;

import com.example.informationappjava.ui.application.ApplicationContextActivity;
import de.measite.minidns.record.A;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class InstituteFragment extends Fragment {

  private InstituteViewModel instituteViewModel;
  private RecyclerView recyclerView;
  private RecyclerView.Adapter adapter;
  private RecyclerView.LayoutManager layoutManager;
  private CardView cardViewCourse;
  private CardView cardViewFreshmanHelp;
  private CardView cardViewEvent;
  private CardView cardViewPerson;
  private String course = "course";
  private String freshmanHelp = "freshmanHelp";
  private String event = "event";
  private String person = "person";

  public View onCreateView(@NonNull LayoutInflater inflater,
      ViewGroup container, Bundle savedInstanceState) {
    instituteViewModel = ViewModelProviders.of(this).get(InstituteViewModel.class);
    View view = inflater.inflate(R.layout.fragment_institute, container, false);

    cardViewCourse = view.findViewById(R.id.institute_courses);
    cardViewCourse.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent(getActivity(), InstituteContextActivity.class);
        intent.putExtra("value", course);
        startActivity(intent);
      }
    });

    cardViewFreshmanHelp = view.findViewById(R.id.institute_freshman_help);
    cardViewFreshmanHelp.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent(getActivity(), InstituteContextActivity.class);
        intent.putExtra("value", freshmanHelp);
        startActivity(intent);
      }
    });

    cardViewEvent = view.findViewById(R.id.institute_events);
    cardViewEvent.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent(getActivity(), InstituteContextActivity.class);
        intent.putExtra("value", event);
        startActivity(intent);
      }
    });

    cardViewPerson = view.findViewById(R.id.institute_person);
    cardViewPerson.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent(getActivity(), InstituteContextActivity.class);
        intent.putExtra("value", person);
        startActivity(intent);
      }
    });

//    ins_kurse.setOnClickListener(new View.OnClickListener() {
//      @Override
//      public void onClick(View view) {
//        String url = "https://www.hs-osnabrueck.de/wir/fakultaeten/mkt/institute/institut-fuer-management-und-technik/studiengaenge/";
//
//        Intent i = new Intent(Intent.ACTION_VIEW);
//        i.setData(Uri.parse(url));
//        startActivity(i);
//      }
//    });
//
//    TextView ins_ersti = view.findViewById(R.id.ins_ersti);
//    ins_ersti.setOnClickListener(new View.OnClickListener() {
//      @Override
//      public void onClick(View view) {
//        String url = "https://www.hs-osnabrueck.de/wir/fakultaeten/mkt/institute/institut-fuer-management-und-technik/erstsemesterinformationen/";
//
//        Intent i = new Intent(Intent.ACTION_VIEW);
//        i.setData(Uri.parse(url));
//        startActivity(i);
//      }
//    });
//
//    TextView ins_personen = view.findViewById(R.id.ins_personen);
//    ins_personen.setOnClickListener(new View.OnClickListener() {
//      @Override
//      public void onClick(View view) {
//        String url = "https://www.hs-osnabrueck.de/wir/fakultaeten/mkt/organisation/personen-a-z/";
//
//        Intent i = new Intent(Intent.ACTION_VIEW);
//        i.setData(Uri.parse(url));
//        startActivity(i);
//      }
//    });
//
//    TextView ins_veranstaltungen = view.findViewById(R.id.ins_veranstaltungen);
//    ins_veranstaltungen.setOnClickListener(new View.OnClickListener() {
//      @Override
//      public void onClick(View view) {
//        String url = "https://www.hs-osnabrueck.de/wir/wir-stellen-uns-vor/veranstaltungen/";
//
//        Intent i = new Intent(Intent.ACTION_VIEW);
//        i.setData(Uri.parse(url));
//        startActivity(i);
//      }
//    });

    return view;
  }




}