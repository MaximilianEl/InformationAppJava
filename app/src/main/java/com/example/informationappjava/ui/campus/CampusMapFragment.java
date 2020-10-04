package com.example.informationappjava.ui.campus;

import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.informationappjava.R;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedOverlay;
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.util.ArrayList;

public class CampusMapFragment extends Fragment {

  public CampusMapFragment() {

  }

  private CampusMapViewModel mViewModel;
  private MyLocationNewOverlay mLocationoverlay;
  private ArrayList<OverlayItem> items;
  private MapView map = null;

  public static CampusMapFragment newInstance() {
    return new CampusMapFragment();
  }

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    Context ctx = container.getContext();
    Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));


    View view =  inflater.inflate(R.layout.fragment_campus_map, container, false);
    map = (MapView) view.findViewById(R.id.map);
    map.setTileSource(TileSourceFactory.MAPNIK);

    // add the position of the user
    this.mLocationoverlay = new MyLocationNewOverlay(new GpsMyLocationProvider(container.getContext()),map);
    this.mLocationoverlay.enableMyLocation();
    map.getOverlays().add(this.mLocationoverlay);

    // add some Building's Position in the map
    this.items = new ArrayList<OverlayItem>();

    //Mensa
    items.add(new OverlayItem("Mensa Lingen"," Description", new GeoPoint(52.519870,7.323969)));
    //KE-Seminarräume
    items.add(new OverlayItem("KE-Seminarräüme"," Description", new GeoPoint(52.518807,7.321633)));
    //KF-Informatik/Mathematik
    items.add(new OverlayItem("KF-Informatik / Mathematik"," Description", new GeoPoint(52.519433,7.322107)));
    //IT-Zentrum
    items.add(new OverlayItem("IT-Zentrum Lingen"," Description", new GeoPoint(52.519207,7.322921)));
    //KH-Studentisches Gebäude
    items.add(new OverlayItem("KH-Studentisches Gebäude"," Description", new GeoPoint(52.520177,7.322668)));
    //KG-Betriebswirtschaft
    items.add(new OverlayItem("KG-Betriebswirtschaft"," Description", new GeoPoint(52.519731,7.322306)));
    //KB-Kommunikationsmanagement
    items.add(new OverlayItem("KB-Kommunikationsmanagement"," Description", new GeoPoint(52.519400,7.322604)));
    //KD-Seminarraüme
    items.add(new OverlayItem("KD-Seminarraüme"," Description", new GeoPoint(52.518800,7.322070)));
    //LK-Institut für duale Studiengäne
    items.add(new OverlayItem("LK-Institut für duale Studiengänge"," Description", new GeoPoint(52.519341,7.322545)));
    //KC-Ingenieurwissenschaften
    items.add(new OverlayItem("KC-Ingenieurwissenschaften"," Description", new GeoPoint(52.519401,7.3226622)));




    //the Overlay
    ItemizedOverlayWithFocus<OverlayItem> mOverlay = new ItemizedOverlayWithFocus<>(items, new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
      @Override
      public boolean onItemSingleTapUp(int index, OverlayItem item) {
        // do something
        return false;
      }

      @Override
      public boolean onItemLongPress(int index, OverlayItem item) {
        return false;
      }
    },container.getContext());
    mOverlay.setFocusItemsOnTap(true);

    map.getOverlays().add(mOverlay);

    // Define the default Location and the Zoom
    IMapController mapController= map.getController();
    mapController.setZoom(19.);
    GeoPoint HS = new GeoPoint(52.519489,7.322869);
    mapController.setCenter(HS);
    return view;
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    mViewModel = ViewModelProviders.of(this).get(CampusMapViewModel.class);
    // TODO: Use the ViewModel
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