package com.example.informationappjava.ui;

import android.view.animation.GridLayoutAnimationController;
import android.widget.GridLayout;
import androidx.appcompat.widget.ScrollingTabContainerView;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.ViewAssertion;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
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
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static java.util.EnumSet.allOf;

@RunWith(AndroidJUnit4.class)
public class TestInstitute {

    @Rule
    public ActivityScenarioRule<InstituteContextActivity> activityRule = new ActivityScenarioRule<>(
            InstituteContextActivity.class);

    @Test
    public void TestInstitute_masch() {
        onView(withId(R.id.ins_masch)).perform(scrollTo(), click());

    }

    @Test
    public void TestInstitute_bwl() {
        onView(withId(R.id.ins_bwl)).perform(scrollTo(), click());
    }

    @Test
    public void TestInstitute_infor() {
        onView(ViewMatchers.withId(R.id.ins_infor)).perform(scrollTo(), click());
    }

    @Test
    public void TestInstitute_ing() {
        onView(ViewMatchers.withId(R.id.ins_infor)).perform(scrollTo(), click());
    }

    @Test
    public void TestInstitute_mba() {
        onView(ViewMatchers.withId(R.id.ins_mba)).perform(scrollTo(), click());
    }

    @Test
    public void TestInstitute_energy() {
        onView(ViewMatchers.withId(R.id.ins_energy)).perform(scrollTo(), click());
    }

    @Test
    public void TestInstitute_mut() {
        onView(ViewMatchers.withId(R.id.ins_mut)).perform(scrollTo(), click());
    }
}