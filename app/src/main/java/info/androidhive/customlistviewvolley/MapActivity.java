package info.androidhive.customlistviewvolley;

import java.util.ArrayList;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.SupportMapFragment;
import com.android.volley.toolbox.NetworkImageView;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

@SuppressWarnings("unused")

public class MapActivity extends FragmentActivity  {
    
	private GoogleMap googleMap;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location);
        
        try {
			// Loading map
			initilizeMap();

			// Changing map type
			googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

			// Showing / hiding your current location
			googleMap.setMyLocationEnabled(true);

			// Enable / Disable zooming controls
			googleMap.getUiSettings().setZoomControlsEnabled(true);

			// Enable / Disable my location button
			googleMap.getUiSettings().setMyLocationButtonEnabled(true);

			// Enable / Disable Compass icon
			googleMap.getUiSettings().setCompassEnabled(true);

			// Enable / Disable Rotate gesture
			googleMap.getUiSettings().setRotateGesturesEnabled(true);

			// Enable / Disable zooming functionality
			googleMap.getUiSettings().setZoomGesturesEnabled(true);
			
			Bundle extras = getIntent().getExtras();
		    String lat = extras.getString("lat");
		    String lon = extras.getString("lon");
		    String name = extras.getString("name");
		    String address = extras.getString("address");
		    
			double latitude = Double.parseDouble(lat);
			double longitude = Double.parseDouble(lon);

			MarkerOptions marker = new MarkerOptions().position(new LatLng(latitude, longitude)).title(name);

			marker.snippet(address);
			
			//marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
			marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.hicon));
			Marker locationMarker = googleMap.addMarker(marker);
			locationMarker.setDraggable(true);
			locationMarker.showInfoWindow();
			
			CameraPosition cameraPosition = new CameraPosition.Builder()
			.target(new LatLng(latitude, longitude)).zoom(15).build();

			googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    @Override
	protected void onResume() {
		super.onResume();
		initilizeMap();
	}

	/**
	 * function to load map If map is not created it will create it for you
	 * */
	private void initilizeMap() 
	{
		if (googleMap == null) 
		{
			googleMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();

			// check if map is created successfully or not
			if (googleMap == null) {
				Toast.makeText(getApplicationContext(),
						"Sorry! unable to create maps", Toast.LENGTH_SHORT)
						.show();
			}
		}
	}
}