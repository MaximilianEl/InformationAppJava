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
    openDrawer();
    onView(withId(R.id.drawer_layout)).perform(actionCloseDrawer());
  }

  @Test
  public void open_Drawer_go_to_Campus() {
    openDrawerGoTo(R.id.nav_campus);
  }

  @Test
  public void open_Drawer_go_to_Institute() {
    openDrawerGoTo(R.id.nav_institute);
  }

  @Test
  public void open_Drawer_go_to_Application() {
    openDrawerGoTo(R.id.nav_application);
  }

  @Test
  public void open_Drawer_go_to_Chat() {
    openDrawerGoTo(R.id.nav_chat);
  }

  @Test
  public void open_Drawer_go_to_Settings() {
    openDrawerGoTo(R.id.nav_options);
  }

  @Test
  public void open_Drawer_go_to_Feedback() {
    openDrawerGoTo(R.id.nav_feedback);
  }

  @Test
  public void open_Drawer_go_to_Feedback_and_fill_EditText() {
    open_Drawer_go_to_Feedback();
    onView(withId(R.id.name)).perform();
  }

  @Test
  public void open_Drawer_go_to_Imprint() {
    openDrawerGoTo(R.id.nav_imprint);
  }

  @Test
  public void open_all_Fragments() {
    openDrawer();
    openPage(R.id.nav_campus);
    openDrawer();
    openPage(R.id.nav_institute);
    openDrawer();
    openPage(R.id.nav_application);
    openDrawer();
    openPage(R.id.nav_home);
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

  private void openDrawer() {
    onView(withId(R.id.drawer_layout)).perform(actionOpenDrawer());
    try {
      Thread.sleep(3000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  private void openPage(int viewAction) {
    onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(viewAction));
    try {
      Thread.sleep(3000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public void openDrawerGoTo(int i) {
    openDrawer();
    openPage(i);
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

