package com.example.informationappjava.ui;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import com.example.informationappjava.ui.campus.CampusMapActivity;
import org.junit.Rule;

public class CampusMapActivityTest {

  @Rule
  public ActivityScenarioRule<CampusMapActivity> activityScenarioRule = new ActivityScenarioRule<>(
      CampusMapActivity.class);
}