package com.example.informationappjava.ui;

import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;
import com.example.informationappjava.R;
import com.example.informationappjava.ui.settings.SettingsActivity;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class TestSettings {

    @Rule
    public ActivityTestRule<SettingsActivity> activityRule = new ActivityTestRule<>(
            SettingsActivity.class);

    @Test
    public void Testsettings_daynight() {
        onView(ViewMatchers.withId(R.id.dayNight)).perform(click());
    }

    @Test
    public void Testsettings_language(){
        onView(withId(R.id.language)).perform(click());
    }

    @Test
    public void Testsettings_myswitch(){
        onView(withId(R.id.myswitch)).perform(click());
    }
}
