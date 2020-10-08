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

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_institute_context);

  }
}