package info.androidhive.materialdesign.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import info.androidhive.materialdesign.R;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationListener;
import android.location.LocationProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.google.android.gms.location.LocationResult;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.ByteArrayBuffer;
import org.json.JSONArray;
import org.json.JSONObject;


import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import info.androidhive.materialdesign.R;

public class FriendsFragment extends Fragment implements LocationListener {

    private RecyclerView rv;
    public List<Bankomat> bankomati;
    private List<Bankomat> sort;
    static Context cont = null;
    public double gpslat, gpslng;
    LocationManager locationManager;
    String mprovider;
    AppLocationService appLocationService;
    public Geocoder geocoder;


    ArrayList<FoursquareVenue> venuesList;
    HashMap<String, String> lokacii;
    GPSTracker gps;

    private String kat = "", povik = "";

    public String longtitude;
    public String a1;
    public String a2;
    JSONArray forsquereApiResults = null, venusTraka = null, lokacija = null;
    private static String urlDel1 = "https://api.foursquare.com/v2/venues/search?ll=";
    private static String url2 = "";
    private static String urlDel2 = "&categoryId=4bf58dd8d48988d16d941735&oauth_token=0WJ1LKKR4NXVAJRT3IXGQCCPMTBF5LHCIE4LADGPINZZ4QCF&v=20150608";
    private static String langutude = "", venues, response, location, name, distance, address, city, state;
    private static String latitude = "";
    public static Location loc;
    // the foursquare client_id and the client_secret

    // ============== YOU SHOULD MAKE NEW KEYS ====================//
    final String CLIENT_ID = "0VB4DORMHVFA4OA4BBERPBUJ2ESPY0VU2SIJMIIE1WGOV1FW";
    final String CLIENT_SECRET = "O1CRADUDIXXZCLNO4WS4DNYPG4CCKIUNDQLPRREVWKH5W1K1";

    // we will need to take the latitude and the logntitude from a certain point
    // this is the center of New York
    String url = "https://api.foursquare.com/v2/venues/search?client_id=K441GTKBFP2Y0D2VPJQ3I1EJA0SGBKWJ4DRQ1OXZJKPQAY3P&client_secret=W1IU32VNHTZJBN4H5DJG33MOKRQPZHVWBVHXMZXKW20IBPUS&ll=41.9973,21.4280&v=20140806&m=foursquare&categoryId=4d4b7105d754a06374d81259";


    // final String latitude = "41.9973";
    // String longitude = "21.4280";

    //private String longtitude;
    String petelko;
    ArrayAdapter<String> myAdapter;

    //	private double latitute;
    String lone;


    public FriendsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        appLocationService = new AppLocationService(
                getContext());

        lokacii = new HashMap<String, String>();

        geocoder = new Geocoder(getContext(), Locale.getDefault());
        tryNetwork();


        new GetContacts().execute();

    }

    private void tryNetwork() {

        Location nwLocation = appLocationService.getLocation(LocationManager.NETWORK_PROVIDER);

        if (nwLocation != null) {
            double latitude = nwLocation.getLatitude();
            double longitude = nwLocation.getLongitude();


            gpslat = latitude;
            gpslng = longitude;

            petelko = Double.toString(gpslat) + "," + Double.toString(gpslng);
            Singleton.getInstance().lat = gpslat;
            Singleton.getInstance().lng = gpslng;


            try {
                List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
                String ace = addresses.get(0).getAddressLine(0);
                String ace1 = addresses.get(0).getLocality();
                Toast.makeText(
                        getContext(),
                        "Your location: " + ace1 + ", " + ace,
                        Toast.LENGTH_SHORT).show();
            } catch (IOException ioException) {
                // Catch network or other I/O problems.
                Toast.makeText(
                        getContext(),
                        ioException.toString(),
                        Toast.LENGTH_SHORT).show();
            }


        } else {
            gps.showSettingsAlert();
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_friends, container, false);

        cont = getActivity();
        rv = (RecyclerView) rootView.findViewById(R.id.rv);
        FloatingActionButton kopce = (FloatingActionButton) rootView.findViewById(R.id.fab1);

        LinearLayoutManager llm = new LinearLayoutManager(cont);
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);
        // lone=longitude+","+latitude+"&query=coffe";

        kopce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MapsActivity.class);
                intent.putExtra("mode", "all");
                startActivity(intent);

            }
        });


        // start the AsyncTask that makes the call for the venus search.


        //    initializeData();
        //    initializeAdapter();


        // Inflate the layout for this fragment


        // Inflate the layout for this fragment
        return rootView;
    }

    static void metod(int a) {

        Intent i = new Intent(cont, SingleActivity.class);
        i.putExtra("pozicija", a);
        cont.startActivity(i);

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    private class GetContacts extends AsyncTask<Void, Void, Void> {
        private List<Bankomat> bankomati = new ArrayList<>();
        private String stats;
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Ве молиме почекајте...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();
            url = "https://api.foursquare.com/v2/venues/search?client_id=K441GTKBFP2Y0D2VPJQ3I1EJA0SGBKWJ4DRQ1OXZJKPQAY3P&client_secret=W1IU32VNHTZJBN4H5DJG33MOKRQPZHVWBVHXMZXKW20IBPUS&ll=" + petelko + "&radius=2000&v=20140806&m=foursquare&categoryId=4d4b7105d754a06374d81259";
            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);

            Log.d("Response: ", "> " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    forsquereApiResults = JSonParser.parseStringToJsonArray(jsonStr);

                    // null

                    Log.d("Json", "-" + jsonObj);
                    response = jsonObj.getString("response");
                    Log.d("Response Json", "-" + response);


                    venusTraka = JSonParser.parseStringToJsonArray(response);
                    JSONObject jsonObjVenus = new JSONObject(response);
                    venues = jsonObjVenus.getString("venues");

                    Log.d("manol i fico mangi", venues);
                    JSONArray result = JSonParser.parseStringToJsonArray(venues);
                    for (int i = 0; i < result.length(); i++) {


                        JSONObject tempRow = null;

                        tempRow = result.getJSONObject(i);
                        name = tempRow.getString("name");
                        Log.d("NAME", name);
                        location = tempRow.getString("location");
                        Log.d("Location", location);


                        stats = tempRow.getString("stats");
                        JSONObject statis = new JSONObject(stats);


                        String chekins = statis.getString("checkinsCount");
                        int ch = Integer.parseInt(chekins);


                        //  String adrress = statis.getString("address");

                        Log.d("ААААААААААААААААAsaa", chekins);
                        JSONObject jsonLocation = new JSONObject(location);

                        langutude = jsonLocation.getString("lat");
                        Log.d("manol i fico mangi", langutude);
                        distance = jsonLocation.getString("distance");
                        address = jsonLocation.getString("formattedAddress");
                        //      city = jsonLocation.getString("city");
                        //    state = jsonLocation.getString("country");

                        latitude = jsonLocation.getString("lng");
                        Log.d("manol i fico mangi1", latitude);
                        double aa1 = Double.parseDouble(langutude);
                        double aa2 = Double.parseDouble(latitude);
                        double aa3 = Double.parseDouble(distance);

                        String ace = name + ";" + " " + langutude + " " + latitude;
                        lokacii.put(name, ace);
                        //        item.setName(name);
                        //       item.setDistance((int)aa3);
                        //       item.setLat(aa2);
                        //       item.setLng(aa1);


                        //       bankomati.add(item);
                        //   String konecna=address+","+city+","+state;
                        bankomati.add(new Bankomat(name, (int) aa3, aa1, aa2, ch, address));

                        // Getting JSON Array node

                        // looping through All Contacts
                        //        for (int i = 0; i < contacts.length(); i++) {
                        //          JSONObject c = contacts.getJSONObject(i);


                        // tmp hashmap for single contact
//                        HashMap<String, String> contact = new HashMap<String, String>();


                        // adding contact to contact list

//                    }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog


            Bankomat temp;
            if (bankomati.size() > 1) // check if the number of orders is larger than 1
            {
                for (int x = 0; x < bankomati.size() - 1; x++) // bubble sort outer loop
                {
                    for (int i = 1; i < bankomati.size() - x; i++) {
                        if (bankomati.get(i - 1).getChekins() < bankomati.get(i).getChekins()) {
                            temp = bankomati.get(i - 1);
                            bankomati.set(i - 1, bankomati.get(i));
                            bankomati.set(i, temp);
                        }
                    }
                }


            }


            String petko = lokacii.get(name);
            String kurle = "";

            /**
             * Updating parsed JSON data into ListView
             * */
            Singleton.getInstance().mGobal = bankomati;
            RVAdapter adapter = new RVAdapter(bankomati);
            rv.setAdapter(adapter);
            pDialog.dismiss();
        }

    }


}
