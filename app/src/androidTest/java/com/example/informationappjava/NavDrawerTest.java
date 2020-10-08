package com.example.informationappjava;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.espresso.ViewAssertion;
import android.view.View;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.contrib.NavigationViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.rule.ActivityTestRule;
import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;

public class NavDrawerTest {

  @Rule
  public ActivityScenarioRule<NavDrawer> activityTestRule = new ActivityScenarioRule<>(
      NavDrawer.class);

//  @Test
//  public void open_Close_Drawer_Layout() {
//    onView(withId(R.id.nav_view)).perform(act);
//    try {
//      Thread.sleep(5000);
//    } catch (InterruptedException e) {
//      e.printStackTrace();
//    }
//    onView(withId(R.id.drawer_layout)).perform(actionCloseDrawer());
//    try {
//      Thread.sleep(5000);
//    } catch (InterruptedException e) {
//      e.printStackTrace();
//    }
//  }
}

