package com.example.informationappjava.ui.institute;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.loader.content.AsyncTaskLoader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.informationappjava.R;

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

  private String[] names = {
      "Frederike Holmer\nStudienassistenz",
      "Elke Schmidt\nStudierendensekretariat",
      "Daniela Timmer\nStudierendensekretariat",
      "Imke Garrelmann\nStudierendensekretariat"
  };

  private String[] descriptions = {
      "Raum: KF 0115\nTelefon: 0591 80098-208\nstudienassistenz-imt@hs-osnabrueck.de\nSprechzeiten: Nach Vereinbarung\nJabberID: f.holmer@hsoschat.de",
      "Raum: KC 0001\nTelefon: 0591 80098-636\nel.schmidt@hs-osnabrueck.de\nSprechzeiten:\nWerktags außer mittwochs\n9:30 - 12:00 Uhr\nJabberID: el.schmidt@hsoschat.de",
      "Raum: KC 0001\nTelefon: 0591 80098-631\nd.timmer@hs-osnabrueck.de\nSprechzeiten:\nWerktags außer mittwochs\n9:30 - 12:00 Uhr\nJabberID: d.timmer@hsoschat.de",
      "Raum: KC 0004\nTelefon: 0591 80098-637\ni.garrelmann@hs-osnabrueck.de\nSprechzeiten:\nWerktags außer mittwochs\n9:30 - 12:00 Uhr\nJabberID: i.garrelmann@hsoschat.de"
  };

  private int[] images = {
      R.drawable.holmer,
      R.drawable.ic_baseline_person_24,
      R.drawable.ic_baseline_person_24,
      R.drawable.imke
      };

  private List<Person> personList = new ArrayList<>();

  public View onCreateView(@NonNull LayoutInflater inflater,
      ViewGroup container, Bundle savedInstanceState) {
    instituteViewModel = ViewModelProviders.of(this).get(InstituteViewModel.class);
    View view = inflater.inflate(R.layout.fragment_institute, container, false);

    recyclerView = view.findViewById(R.id.personRecycler);
    layoutManager = new LinearLayoutManager(getActivity());
    recyclerView.setLayoutManager(layoutManager);

    prepareTheList();

    adapter = new PersonAdapter(personList);
    recyclerView.setAdapter(adapter);

    TextView ins_kurse = view.findViewById(R.id.ins_Kurse);
    ins_kurse.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        String url = "https://www.hs-osnabrueck.de/wir/fakultaeten/mkt/institute/institut-fuer-management-und-technik/studiengaenge/";

        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
      }
    });

    TextView ins_ersti = view.findViewById(R.id.ins_ersti);
    ins_ersti.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        String url = "https://www.hs-osnabrueck.de/wir/fakultaeten/mkt/institute/institut-fuer-management-und-technik/erstsemesterinformationen/";

        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
      }
    });

    TextView ins_personen = view.findViewById(R.id.ins_personen);
    ins_personen.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        String url = "https://www.hs-osnabrueck.de/wir/fakultaeten/mkt/organisation/personen-a-z/";

        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
      }
    });

    TextView ins_veranstaltungen = view.findViewById(R.id.ins_veranstaltungen);
    ins_veranstaltungen.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        String url = "https://www.hs-osnabrueck.de/wir/wir-stellen-uns-vor/veranstaltungen/";

        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
      }
    });

    return view;
  }

  public void prepareTheList() {

    int count = 0;
    for (String name : names) {

        Person person = new Person(name, descriptions[count], images[count]);
        personList.add(person);
        count++;
    }
  }


}