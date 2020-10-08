package com.example.informationappjava.ui.institute;

import android.content.Intent;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.informationappjava.R;
import com.example.informationappjava.ui.institute.PersonAdapter.OnItemClickListener;
import java.util.ArrayList;
import java.util.List;

public class InstituteContextActivity extends AppCompatActivity {

  private String course = "course";
  private String freshmanHelp = "freshmanHelp";
  private String event = "event";
  private RecyclerView recyclerView;
  private PersonAdapter adapter;
  private RecyclerView.LayoutManager layoutManager;
  private String[] personNames;
  private String[] personDescriptions;
  private String[] header;
  private String[] courseNames;
  private String[] courseDescriptions;
  private TextView textViewHeader;

  private int[] personImages = {
      R.drawable.holmer,
      R.drawable.ic_baseline_person_24,
      R.drawable.ic_baseline_person_24,
      R.drawable.imke
  };

  private int[] courseImages = {
      R.drawable.ic_baseline_person_24,
      R.drawable.ic_baseline_person_24,
      R.drawable.ic_baseline_person_24,
      R.drawable.ic_baseline_person_24,
      R.drawable.ic_baseline_person_24,
      R.drawable.ic_baseline_person_24,
      R.drawable.ic_baseline_person_24,
  };

  private List<Person> personList = new ArrayList<>();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_institute_context);

    Intent intent = getIntent();
    String s1 = intent.getStringExtra("value");

    header = getResources().getStringArray(R.array.ins_header);
    personNames = getResources().getStringArray(R.array.person_header);
    personDescriptions = getResources().getStringArray(R.array.person_context);
    courseNames = getResources().getStringArray(R.array.course_names);
    courseDescriptions = getResources().getStringArray(R.array.course_context);

    recyclerView = findViewById(R.id.ins_recycler);
    textViewHeader = findViewById(R.id.institute_header);

    if (s1.equals(course)) {

      adapter = new PersonAdapter(personList);
      recyclerView.setAdapter(adapter);
      adapter.setOnItemClickListener(new OnItemClickListener() {
        @Override
        public void onItemClick(int position) {
        }
      });

      layoutManager = new GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false);
      recyclerView.setLayoutManager(layoutManager);

      textViewHeader.setText(header[0]);

      prepareCourseList();
    } else if (s1.equals(freshmanHelp)) {

      adapter = new PersonAdapter(personList);
      recyclerView.setAdapter(adapter);
      layoutManager = new GridLayoutManager(this, 4, LinearLayoutManager.VERTICAL, false);
      recyclerView.setLayoutManager(layoutManager);
      textViewHeader.setText(header[1]);
      preparePersonList();
    } else if (s1.equals(event)) {

      adapter = new PersonAdapter(personList);
      recyclerView.setAdapter(adapter);
      layoutManager = new GridLayoutManager(this, 3, LinearLayoutManager.VERTICAL, false);
      recyclerView.setLayoutManager(layoutManager);
      textViewHeader.setText(header[2]);
      preparePersonList();
    } else {

      adapter = new PersonAdapter(personList);
      recyclerView.setAdapter(adapter);
      layoutManager = new GridLayoutManager(this, 1, LinearLayoutManager.VERTICAL, false);
      recyclerView.setLayoutManager(layoutManager);
      textViewHeader.setText(header[3]);
      preparePersonList();
    }
  }

  public void preparePersonList() {

    int count = 0;
    for (String name : personNames) {

      Person person = new Person(name, personDescriptions[count], personImages[count]);
      personList.add(person);
      count++;
    }
  }

  public void prepareCourseList() {

    int count = 0;
    for (String name : courseNames) {

      Person person = new Person(name, courseDescriptions[count],courseImages[count]);
      personList.add(person);
      count++;
    }
  }
}