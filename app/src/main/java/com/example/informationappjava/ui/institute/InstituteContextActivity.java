package com.example.informationappjava.ui.institute;

import android.content.Intent;
import android.widget.GridLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.informationappjava.R;
import java.util.ArrayList;
import java.util.List;

public class InstituteContextActivity extends AppCompatActivity {

  private String course = "course";
  private String freshmanHelp = "freshmanHelp";
  private String event = "event";
  private String person = "person";
  private GridLayout gridLayout;
  private RecyclerView recyclerView;
  private RecyclerView.Adapter adapter;
  private RecyclerView.LayoutManager layoutManager;

  private List<Person> personList = new ArrayList<>();

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

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_institute_context);

    Intent intent = getIntent();
    String s1 = intent.getStringExtra("value");

    recyclerView = findViewById(R.id.ins_recycler);

    layoutManager = new LinearLayoutManager(this);
    recyclerView.setLayoutManager(layoutManager);

    adapter = new PersonAdapter(personList);
    recyclerView.setAdapter(adapter);

    prepareTheList();


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