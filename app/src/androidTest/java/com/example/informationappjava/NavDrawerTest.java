package com.example.informationappjava;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.core.AllOf.allOf;

import android.view.View;
import android.widget.TextView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.action.GeneralLocation;
import androidx.test.espresso.action.GeneralSwipeAction;
import androidx.test.espresso.action.Press;
import androidx.test.espresso.action.Swipe;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.*;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
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

  /**
   *
   */
  @Test
  public void open_Drawer_go_to_Campus() {
    openDrawerGoTo(R.id.nav_campus);
  }

  @Test
  public void open_Drawer_go_to_Campus_Swipe_Images_and_go_Campus_Map() {
    openDrawer();
    openPage(R.id.nav_campus);
    onView(withId(R.id.campus_fragment_slideshow)).perform(swipeLeft());
    onView(withId(R.id.campus_fragment_slideshow)).perform(swipeRight());
    onView(withId(R.id.campus_map_button)).perform(click());
  }

  @Test
  public void open_Drawer_go_to_Institute() {
    openDrawerGoTo(R.id.nav_institute);
  }

  @Test
  public void open_Drawer_go_to_Application() {
    openDrawerGoTo(R.id.nav_application);
  }


  /**
   *
   */
  @Test
  public void open_Drawer_go_to_Chat() {
    openDrawerGoTo(R.id.nav_chat);
  }

  @Test
  public void go_Chat_dont_login() {
    open_Drawer_go_to_Chat();
    pressBack();
  }

  @Test
  public void go_Chat_Login_False() {
    open_Drawer_go_to_Chat();
    onView(withId(R.id.password)).perform(clearText(), typeText("lalalal"));
    onView(withId(R.id.jid)).perform(setTextInTextView("Hihihi"));
    onView(withId(R.id.jid_sign_in)).perform(click());
    try {
      Thread.sleep(3000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void go_Chat_Login_True() {
    open_Drawer_go_to_Chat();
    onView(withId(R.id.jid)).perform(setTextInTextView("test@hsoschat.de"));
    onView(withId(R.id.password)).perform(clearText(), typeText("123456"));
    onView(withId(R.id.jid_sign_in)).perform(click());
    try {
      Thread.sleep(3000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }


  /**
   *
   */
  @Test
  public void open_Drawer_go_to_Settings() {
    openDrawerGoTo(R.id.nav_options);
  }

  /**
   *
   */
  @Test
  public void open_Drawer_go_to_Feedback() {
    openDrawerGoTo(R.id.nav_feedback);
  }

  @Test
  public void open_Drawer_go_to_Feedback_and_fill_EditText() {
    open_Drawer_go_to_Feedback();
    onView(withId(R.id.message)).perform(clearText(), typeText("Beste App ever!"));
    onView(withId(R.id.name)).perform(clearText(), typeText("Maximilianos Elberos"));
    onView(withId(R.id.subject)).perform(clearText(), typeText("Feedback"));
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

  @Test
  public void play_video() {
    onView(withId(R.id.videoView)).perform(click());
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

  private static ViewAction swipeRight() {
    return new GeneralSwipeAction(Swipe.FAST, GeneralLocation.CENTER_LEFT,
        GeneralLocation.CENTER_RIGHT, Press.FINGER);
  }

  private static ViewAction swipeLeft() {
    return new GeneralSwipeAction(Swipe.FAST, GeneralLocation.CENTER_RIGHT,
        GeneralLocation.CENTER_LEFT, Press.FINGER);
  }

  public static ViewAction setTextInTextView(final String value) {
    return new ViewAction() {
      @SuppressWarnings("unchecked")
      @Override
      public Matcher<View> getConstraints() {
        return allOf(isDisplayed(), isAssignableFrom(TextView.class));
      }

      @Override
      public void perform(UiController uiController, View view) {
        ((TextView) view).setText(value);
      }

      @Override
      public String getDescription() {
        return "replace text";
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

  public void pressBack() {
    onView(isRoot()).perform(ViewActions.pressBack());
  }

}

