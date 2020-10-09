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

/**
 *
 */
public class CampusMapActivity extends AppCompatActivity {

  private MyLocationNewOverlay mLocationoverlay;
  private ArrayList<OverlayItem> items;
  private MapView map = null;
  private String[] descriptions;

  /**
   * @param savedInstanceState
   */
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

    //KA-Bib
    items.add(new OverlayItem("Bibliothek", descriptions[0], new GeoPoint(52.520070, 7.323205)));

    //KB-Kommunikationsmanagement
    items.add(new OverlayItem("KB-Kommunikationsmanagement", descriptions[1],
        new GeoPoint(52.519625, 7.322832)));

    //KC-Ingenieurwissenschaften
    items.add(new OverlayItem("KC-Ingenieurwissenschaften", descriptions[2],
        new GeoPoint(52.519223, 7.322468)));

    //KD-Seminarraüme
    items
        .add(
            new OverlayItem("KD-Seminarraüme", descriptions[3], new GeoPoint(52.518730, 7.322007)));

    //KE-Seminarräume
    items
        .add(
            new OverlayItem("KE-Seminarräüme", descriptions[4], new GeoPoint(52.518807, 7.321633)));

    //KF-Informatik/Mathematik
    items.add(new OverlayItem("KF-Informatik / Mathematik", descriptions[5],
        new GeoPoint(52.519433, 7.322107)));

    //KG-Betriebswirtschaft
    items.add(new OverlayItem("KG-Betriebswirtschaft", descriptions[6],
        new GeoPoint(52.519731, 7.322306)));

    //KH-Studentisches Gebäude
    items.add(new OverlayItem("KH-Studentisches Gebäude", descriptions[7],
        new GeoPoint(52.520177, 7.322668)));

    //IT-Zentrum
    items.add(
        new OverlayItem("IT-Zentrum Lingen", descriptions[8], new GeoPoint(52.519040, 7.323023)));

    //LB-Theaterpädagogik
    items.add(
        new OverlayItem("LB-Theaterpädagogik", descriptions[9], new GeoPoint(52.523117, 7.318755)));

    //LC-Kokenmühle
    items.add(new OverlayItem("LC", descriptions[10], new GeoPoint(52.517403, 7.319054)));

    //LK-Institut für duale Studiengäne
    items.add(new OverlayItem("LK-Institut für duale Studiengänge", descriptions[11],
        new GeoPoint(52.519252, 7.322988)));

    //LL-Konrad-Adenauer-Ring
    items.add(
        new OverlayItem("Konrad-Adenauer-Ring", descriptions[12],
            new GeoPoint(52.519985, 7.316185)));

    //Mensa
    items.add(new OverlayItem("Mensa Lingen", descriptions[13], new GeoPoint(52.519870, 7.323969)));

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

  /**
   *
   */
  @Override
  public void onResume() {
    super.onResume();
    map.onResume();
  }

  /**
   *
   */
  @Override
  public void onPause() {
    super.onPause();
    map.onPause();
  }
}