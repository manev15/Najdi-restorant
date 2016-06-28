package info.androidhive.materialdesign.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import info.androidhive.materialdesign.R;

public class ZaMapActivity extends AppCompatActivity {

    private GoogleMap mMap;
    private LatLng lokacija1;
    private String mode = "";
    private String name = "";
    private String lati = "";
    private String longi = "";
    private double latitude = 0;
    private double longitude = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_za_map);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map3)).getMap();
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.clear();
        Intent i = getIntent();
        mode = i.getStringExtra("mode");


        if (mode.equals("single")) {
            name = i.getStringExtra("name");
            lati = i.getStringExtra("lat");
            longi = i.getStringExtra("lng");
            latitude = Double.parseDouble(lati);
            longitude = Double.parseDouble(longi);
        }



        LatLng sydney = new LatLng(Singleton.getInstance().lat, Singleton.getInstance().lng);
        mMap.addMarker(new MarkerOptions().position(sydney).title("I am here"));

        lokacija1 = new LatLng(Singleton.getInstance().lat, Singleton.getInstance().lng);


        if (mode.equals("all")) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lokacija1, 15));
            List<Bankomat> bankom = Singleton.getInstance().mGobal;
            for (int j = 0; j < bankom.size(); j++) {
                String ime = bankom.get(j).name;
                double lat = bankom.get(j).lat;
                double lng = bankom.get(j).lng;
                mMap.addMarker(new MarkerOptions().title(ime).position(new LatLng(lat, lng)).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
            }
        } else if (mode.equals("single")) {
            LatLng single = new LatLng(latitude, longitude);
            mMap.addMarker(new MarkerOptions().position(single).title(name).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(single, 15));
        }

    }

}
