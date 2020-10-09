package com.example.informationappjava;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.espresso.ViewAssertion;
import android.view.View;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.contrib.*;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.rule.ActivityTestRule;
import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;

public class NavDrawerTest {

  @Rule
  public ActivityScenarioRule<NavDrawer> activityTestRule = new ActivityScenarioRule<>(
      NavDrawer.class);

  @Test
  public void open_Close_Drawer_Layout() {
    onView(withId(R.id.drawer_layout)).perform(actionOpenDrawer());
    try {
      Thread.sleep(5000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    onView(withId(R.id.drawer_layout)).perform(actionCloseDrawer());
  }

  private static ViewAction actionOpenDrawer() {
    return new ViewAction() {
      @Override
      public Matcher<View> getConstraints() {
        return isAssignableFrom(DrawerLayout.class);
      }

      @Override
      public String getDescription() {
        return "open drawer";
      }

      @Override
      public void perform(UiController uiController, View view) {
        ((DrawerLayout) view).openDrawer(GravityCompat.START);
      }
    };
  }

  private static ViewAction actionCloseDrawer() {
    return new ViewAction() {
      @Override
      public Matcher<View> getConstraints() {
        return isAssignableFrom(DrawerLayout.class);
      }

      @Override
      public String getDescription() {
        return "close drawer";
      }

      @Override
      public void perform(UiController uiController, View view) {
        ((DrawerLayout) view).closeDrawer(GravityCompat.START);
      }
    };
  }

}

