package com.example.informationappjava.ui;

import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;
import com.example.informationappjava.R;
import com.example.informationappjava.ui.institute.InstituteContextActivity;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class TestInstitute {

    @Rule
    public ActivityScenarioRule<InstituteContextActivity> activityRule = new ActivityScenarioRule<>(
            InstituteContextActivity.class);

    @Test
    public void TestInstitute_masch() {
        onView(ViewMatchers.withId(R.id.ins_masch)).perform(click());
    }

    @Test
    public void TestInstitute_bwl() {
        onView(withId(R.id.ins_bwl)).perform(click());
    }

    @Test
    public void TestInstitute_infor() {
        onView(withId(R.id.ins_infor)).perform(click());
    }

    @Test
    public void TestInstitute_ing() {
        onView(withId(R.id.ins_ing)).perform(click());
    }

    @Test
    public void TestInstitute_mba() {
        onView(withId(R.id.ins_mba)).perform(click());
    }

    @Test
    public void TestInstitute_energy() {
        onView(withId(R.id.ins_energy)).perform(click());
    }

    @Test
    public void TestInstitute_mut() {
        onView(withId(R.id.ins_mut)).perform(click());
    }
}