package com.example.informationappjava.ui.campus;

import android.content.Context;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.preference.PreferenceManager;
import com.example.informationappjava.R;
import java.util.ArrayList;
import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

public class CampusMapActivity extends AppCompatActivity {

  private MyLocationNewOverlay mLocationoverlay;
  private ArrayList<OverlayItem> items;
  private MapView map = null;
  private String[] descriptions;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_campus_map);
    Context ctx = getApplicationContext();
    Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));

    descriptions = getResources().getStringArray(R.array.descriptions);

    map = findViewById(R.id.map);
    map.setTileSource(TileSourceFactory.MAPNIK);

    // add the position of the user
    this.mLocationoverlay = new MyLocationNewOverlay(new GpsMyLocationProvider(ctx), map);
    this.mLocationoverlay.enableMyLocation();
    map.getOverlays().add(this.mLocationoverlay);

    // add some Building's Position in the map
    this.items = new ArrayList<OverlayItem>();

    //Bib
    items.add(new OverlayItem("Bibliothek", descriptions[0], new GeoPoint(52.520070, 7.323205)));

    //Mensa
    items.add(new OverlayItem("Mensa Lingen", descriptions[0], new GeoPoint(52.519870, 7.323969)));

    //KE-Seminarräume
    items
        .add(new OverlayItem("KE-Seminarräüme", " Description", new GeoPoint(52.518807, 7.321633)));

    //KF-Informatik/Mathematik
    items.add(new OverlayItem("KF-Informatik / Mathematik", " Description",
        new GeoPoint(52.519433, 7.322107)));

    //IT-Zentrum
    items.add(
        new OverlayItem("IT-Zentrum Lingen", " Description", new GeoPoint(52.519040, 7.323023)));

    //KH-Studentisches Gebäude
    items.add(new OverlayItem("KH-Studentisches Gebäude", " Description",
        new GeoPoint(52.520177, 7.322668)));

    //KG-Betriebswirtschaft
    items.add(new OverlayItem("KG-Betriebswirtschaft", " Description",
        new GeoPoint(52.519731, 7.322306)));

    //KB-Kommunikationsmanagement
    items.add(new OverlayItem("KB-Kommunikationsmanagement", " Description",
        new GeoPoint(52.519625, 7.322832)));

    //KD-Seminarraüme
    items
        .add(new OverlayItem("KD-Seminarraüme", " Description", new GeoPoint(52.518730, 7.322007)));

    //LK-Institut für duale Studiengäne
    items.add(new OverlayItem("LK-Institut für duale Studiengänge", " Description",
        new GeoPoint(52.519252, 7.322988)));

    //KC-Ingenieurwissenschaften
    items.add(new OverlayItem("KC-Ingenieurwissenschaften", " Description",
        new GeoPoint(52.519223, 7.322468)));

    //LB-Theaterpädagogik
    items.add(
        new OverlayItem("LB-Theaterpädagogik", "Description", new GeoPoint(52.523117, 7.318755)));

    //LC-Kokenmühle
    items.add(new OverlayItem("LC", "Description", new GeoPoint(52.517403, 7.319054)));

    //Konrad-Adenauer-Ring
    items.add(
        new OverlayItem("Konrad-Adenauer-Ring", "Description", new GeoPoint(52.519985, 7.316185)));

    //the Overlay
    ItemizedOverlayWithFocus<OverlayItem> mOverlay = new ItemizedOverlayWithFocus<>(items,
        new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
          @Override
          public boolean onItemSingleTapUp(int index, OverlayItem item) {
            // do something
            return false;
          }

          @Override
          public boolean onItemLongPress(int index, OverlayItem item) {
            return false;
          }
        }, ctx);
    mOverlay.setFocusItemsOnTap(true);

    map.getOverlays().add(mOverlay);

    // Define the default Location and the Zoom
    IMapController mapController = map.getController();
    mapController.setZoom(17.3);
    GeoPoint HS = new GeoPoint(52.520146, 7.320442);
    mapController.setCenter(HS);
  }

  @Override
  public void onResume() {
    super.onResume();
    map.onResume();
  }

  @Override
  public void onPause() {
    super.onPause();
    map.onPause();
  }
}