package com.example.informationappjava.ui.institute;

import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.informationappjava.R;
import java.util.ArrayList;
import java.util.List;

/**
 * PersonContextActivity
 */
public class PersonContextActivity extends AppCompatActivity {

  private final String course = "course";
  private final String freshmanHelp = "freshmanHelp";
  private final String event = "event";
  private RecyclerView recyclerView;
  private PersonAdapter adapter;
  private RecyclerView.LayoutManager layoutManager;
  private String[] personNames;
  private String[] personDescriptions;
  private String[] header;
  private TextView textViewHeader;

  private final int[] personImages = {
      R.drawable.holmer,
      R.drawable.ic_baseline_person_24,
      R.drawable.ic_baseline_person_24,
      R.drawable.imke
  };

  private final List<Person> personList = new ArrayList<>();

  /**
   * In this onCreate() function the Person Adapter is called and set to the recyclerView. The
   * Header is set from a String array and the preparePersonList() function is called.
   *
   * @param savedInstanceState
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_person_context);

    header = getResources().getStringArray(R.array.ins_header);
    personNames = getResources().getStringArray(R.array.person_header);
    personDescriptions = getResources().getStringArray(R.array.person_context);

    recyclerView = findViewById(R.id.ins_recycler);
    textViewHeader = findViewById(R.id.institute_header);

    adapter = new PersonAdapter(personList);
    recyclerView.setAdapter(adapter);
    layoutManager = new GridLayoutManager(this, 1, LinearLayoutManager.VERTICAL, false);
    recyclerView.setLayoutManager(layoutManager);
    textViewHeader.setText(header[3]);
    preparePersonList();

  }

  /**
   * Ther personList is prepared here for every name in the personNames String array the Person
   * Model is called and a new Person is created.
   */
  public void preparePersonList() {

    int count = 0;
    for (String name : personNames) {

      Person person = new Person(name, personDescriptions[count], personImages[count]);
      personList.add(person);
      count++;
    }
  }
}