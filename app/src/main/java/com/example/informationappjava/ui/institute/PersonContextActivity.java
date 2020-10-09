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
 *
 */
public class PersonContextActivity extends AppCompatActivity {

    private String course = "course";
    private String freshmanHelp = "freshmanHelp";
    private String event = "event";
    private RecyclerView recyclerView;
    private PersonAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private String[] personNames;
    private String[] personDescriptions;
    private String[] header;
    private TextView textViewHeader;

    private int[] personImages = {
            R.drawable.holmer,
            R.drawable.ic_baseline_person_24,
            R.drawable.ic_baseline_person_24,
            R.drawable.imke
    };

    private List<Person> personList = new ArrayList<>();

    /**
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
     *
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