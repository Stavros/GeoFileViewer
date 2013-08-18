package sdy.itsystem.GeoFileViewer;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Fragment1 extends Fragment {
	
	private MapView mMapView;
    private GoogleMap mMap;
    private Bundle mBundle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflatedView = inflater.inflate(R.layout.geo, container, false);         

        try {
            MapsInitializer.initialize(getActivity());
        } catch (GooglePlayServicesNotAvailableException e) {
            // TODO handle this situation
        }

        mMapView = (MapView) inflatedView.findViewById(R.id.map);
        mMapView.onCreate(mBundle);      
        setUpMapIfNeeded(inflatedView);

        return inflatedView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBundle = savedInstanceState;
             
    }

    private void setUpMapIfNeeded(View inflatedView) {
        if (mMap == null) {
            mMap = ((MapView) inflatedView.findViewById(R.id.map)).getMap();
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    private void setUpMap() { 	
        
        // Zoom in the Google Map in desired LatLng
    	mMap.moveCamera( CameraUpdateFactory.newLatLngZoom(new LatLng(40.633948, 22.951126) , 12.0f) );    
      	 
        // add 4 markers w/ custom icons 
    	mMap.addMarker(new MarkerOptions().position(new LatLng(40.620563, 22.973270)).title("Triandria").icon(BitmapDescriptorFactory.fromResource(R.drawable.a)));
        mMap.addMarker(new MarkerOptions().position(new LatLng(40.613266, 22.971897)).title("Toumpa").icon(BitmapDescriptorFactory.fromResource(R.drawable.b)));
        mMap.addMarker(new MarkerOptions().position(new LatLng(40.599452, 22.967777)).title("Charilaou").icon(BitmapDescriptorFactory.fromResource(R.drawable.c)));
        mMap.addMarker(new MarkerOptions().position(new LatLng(40.580632, 22.945118)).title("Kalamaria").icon(BitmapDescriptorFactory.fromResource(R.drawable.d)));
        mMap.setMyLocationEnabled(true);
    }    

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        mMapView.onDestroy();
        super.onDestroy();
    }
}