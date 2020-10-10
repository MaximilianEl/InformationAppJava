package com.example.informationappjava;

import android.Manifest.permission;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import androidx.core.app.ActivityCompat;
import com.google.android.material.navigation.NavigationView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

/**
 * Navigation Drawer Activity
 */
public class NavDrawer extends AppCompatActivity {

  private com.getbase.floatingactionbutton.FloatingActionButton fabInsta;
  private com.getbase.floatingactionbutton.FloatingActionButton fabTwitter;
  private com.getbase.floatingactionbutton.FloatingActionButton fabFaceBook;
  private com.getbase.floatingactionbutton.FloatingActionButton fabYoutube;

  private AppBarConfiguration mAppBarConfiguration;

  /**
   * onCreate Function of NavDrawer includes Functions to navigate to different Fragments and
   * Activities from the App. Also the Functions for the FloatingActionButton are implemented here.
   *
   * @param savedInstanceState
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {

    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_nav_drawer);
    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    if (ActivityCompat.checkSelfPermission(this, permission.READ_EXTERNAL_STORAGE)
        != PackageManager.PERMISSION_GRANTED) {
      ActivityCompat.requestPermissions(this, new String[]{permission.READ_EXTERNAL_STORAGE}, 10);
    }

    DrawerLayout drawer = findViewById(R.id.drawer_layout);
    NavigationView navigationView = findViewById(R.id.nav_view);
    // Passing each menu ID as a set of Ids because each
    // menu should be considered as top level destinations.
    mAppBarConfiguration = new AppBarConfiguration.Builder(
        R.id.nav_chat, R.id.nav_home, R.id.nav_campus, R.id.nav_institute, R.id.nav_options,
        R.id.nav_application
    ).setDrawerLayout(drawer)
        .build();
    NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
    NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
    NavigationUI.setupWithNavController(navigationView, navController);

    fabInsta = findViewById(R.id.instagram);
    fabTwitter = findViewById(R.id.twitter);
    fabFaceBook = findViewById(R.id.facebook);
    fabYoutube = findViewById(R.id.youtube);

    fabInsta.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        String url = "https://www.instagram.com/hs_osnabrueck/?hl=de";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
      }
    });

    fabTwitter.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        String url = "https://twitter.com/HS_Osnabrueck?ref_src=twsrc%5Egoogle%7Ctwcamp%5Eserp%7Ctwgr%5Eauthor";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
      }
    });

    fabFaceBook.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        String url = "https://www.facebook.com/hs.osnabrueck";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
      }
    });

    fabYoutube.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        String url = "https://www.youtube.com/user/HochschuleOS";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
      }
    });
  }

  /**
   * @return NavigationUI.navigateUp(navController, mAppBarConfiguration || super.onSupportNavigateUp()
   */
  @Override
  public boolean onSupportNavigateUp() {
    NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
    return NavigationUI.navigateUp(navController, mAppBarConfiguration)
        || super.onSupportNavigateUp();
  }
}