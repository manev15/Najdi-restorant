package info.androidhive.materialdesign.activity;

/**
 * Created by Ravi on 29/07/15.
 */

import android.Manifest;
import android.annotation.SuppressLint;
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
import android.os.StrictMode;
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
import android.widget.Button;
import android.widget.EditText;
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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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


public class HomeFragment extends Fragment implements LocationListener {
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

    private Statement statement;

    ConnectionClass connectionclass;
    String call, db, un, passwords;
    Connection connect;
    ResultSet rs, rs1, rs2, rs3;
    private Locale myLocale;
    private EditText username, password;
    private String usern = "", pass = "";
    private Button najava;


    @SuppressLint("NewApi")
    private Connection CONN(String _user, String _pass, String _DB,
                            String _server) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection conn = null;
        String ConnURL = null;
        try {

            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            ConnURL = "jdbc:jtds:sqlserver://" + _server + ";"
                    + "databaseName=" + _DB + ";user=" + _user + ";password="
                    + _pass + ";";
            conn = DriverManager.getConnection(ConnURL);
        } catch (SQLException se) {
            Log.e("ERRO", se.getMessage());
        } catch (ClassNotFoundException e) {
            Log.e("ERRO", e.getMessage());
        } catch (Exception e) {
            Log.e("ERRO", e.getMessage());
        }
        return conn;
    }
    // final String latitude = "41.9973";
    // String longitude = "21.4280";

    //private String longtitude;
    String petelko;
    ArrayAdapter<String> myAdapter;

    //	private double latitute;
    String lone;


    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        //lone=longitude+","+latitude+"&query=coffe";
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
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        cont = getActivity();
        rv = (RecyclerView) rootView.findViewById(R.id.rv);
        FloatingActionButton kopce = (FloatingActionButton) rootView.findViewById(R.id.fab);

        LinearLayoutManager llm = new LinearLayoutManager(cont);
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);
        // lone=longitude+","+latitude+"&query=coffe";

        kopce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ZaMapActivity.class);
                intent.putExtra("mode", "all");
                startActivity(intent);

            }
        });


        // start the AsyncTask that makes the call for the venus search.


        //    initializeData();
        //    initializeAdapter();


        // Inflate the layout for this fragment
        return rootView;
    }

    static void metod(int a) {

        Intent i = new Intent(cont, SingleActivity.class);
        i.putExtra("pozicija", a);
        cont.startActivity(i);

    }

    private void initializeData() {


        String skopje = "Capitol Mall^41,986038 21,466573~City Mall Скопје^42.004405 21.392426~Ramstor Горно Лисиче ^41,984180 21,483557~АМСМ Дирекција ул.Теодосиј Гологанов бр.51^41,994746 21,421179~Аптека \"Марија\" ул.Водњанска бр.36^41,989650 21,419323~БЕ-КО^41,999418 21,412693~Бензинска пумпа Евроил^42,052370 21,450222~Бензинска пумпа Никол Комерц^42,053836 21,451402~БП Лукоил ^41,944016 21,497742~Бриколаж^41.996380 21.475617~Градежен Институт Македонија - КАТАСТАР ул.Дрезенска бр.52^41,999617 21,406759~ГТЦ Златара Рубин приземје (заден влез макпетрол ) ^41,994275 21,435942~Драмски театар^42,002910 21,408251~ДТЦ Палома Бјанка^41,994969 21,428185~Експозитура Автокоманда^42,001587 21,460264~Експозитура Аеродром^41,986788 21,463268~Експозитура Г.Т.Ц. 2^41,994881 21,434773~Експозитура Г.Т.Ц. кеј 13 Ноември бр.3^41,994850 21,435234~Експозитура Ѓорче Петров^42,006314 21,368307~Експозитура Драчево^41,938554 21,519487~Експозитура Илинден^41,994506 21,577484~Експозитура Капиштец^41,993622 21,411008~Експозитура Карпош 3- Т.Ц. Лептокарија^42,003602 21,397149~Експозитура Карпош 4^42,004999 21,384004~Експозитура Кисела Вода^41,984714 21,438850~Експозитура Клинички Центар^41,990217 21,420793~Експозитура Ново Лисиче^41,981336 21,475429~Експозитура Расадник^41,976098 21,443635~Експозитура Собрание^41,991748 21,431211~Експозитура стар Аеродром^41,986253 21,442894~Експозитура Стара Чаршија^41,999331 21,437557~Експозитура Три Бисери^41,989419 21,459942~Експозитура Улица Македонија^41,995009 21,430814~Експозитура Универзална^41,999131 21,418958~Експозитура Центар^41,996532 21,427606~Експозитура Чаир^42,020783 21,449299~ЕЛ-ДЕ Маркет^42,003150 21,465586~ЕлектроЕлемент^41,995982 21,486239~Зегин^41,998525 21,422477~Комуна^41,991971 21,494017~ЛаПетит Слаткарница^41,974489 21,472055~Лукоил Автокоманда^42,000758 21,461251~Лукоил Аеродром^41,987633 21,473032~Лукоил Камник^42,004353 21,483084~Макоил Зајчев рид^42,015458 21,404324~Макоил Лисиче^41,979658 21,465779~Макпетрол - Козле^41,994220 21,397758~Макпетрол Ѓорче Петров^42,007225 21,334087~Макси Д К.Вода^41,983182 21,439171~Менувачница Фаустина^42,005302 21,416600~МПМ ул Никола бавцаров б.б. ^41,995436 21,429923~Народна Банка^41,993709 21,442444~НЛБ ТБ Дирекција 2^41,992768 21,425310~НЛБ ТБ Дирекција^41,992768 21,425310~НЛБ ТБ Експозитура Сарај ул.\"Сарај\" бб. ^442,004196 21,332238~НЛБ ТБ Соборна^41,999175 21,425676~НЛБ ТБ Ченто ^42,014932 21,521247~Општина Илинден^41.994465 21.575625~Принц Маркет^42,017116 21,436597~Пуцко Петрол - Бензинска Пумпа^42,003247 21,396317~Рамстор^41,992210 21,426468~Рептил Маркет Црниче ^41,983752 21,428838~Салон за мебел СТОЛ^41,989435 21,436339~Салон за убавина Сплендид^42,000606 21,414372~СП Маркет Бутел^42,031048 21,444890~СП Маркет Кисела Вода^41,977249 21,443849~СП Маркет Тафталиџе^42,001015 21,390095~СРЦ Јане Сандански^41,992740 21,465035~Супер Тинекс - ул. Перо Наков бб^41,996197 21,477881~Супер Тинекс Зебра^41,992784 21,419827~Супер Тинекс Раде Кончар^41,981907 21,452819~Супер Тинекс ул.Првомајска бб^41,975143 21,452304~Т.Ц. Беверли Хилс^41,993996 21,416362~Т.Ц. Олимпико^41,998557 21,393595~Тинекс Ѓорче Петров^42,007104 21,361280~Тобако 1 ^41,984929 21,466643~Тобако 2 ^41,987768 21,453231~Тобако ПРЧ ^41,985171 21,467174~Тутунски Комбинат^41,987745 21,437691~ТЦ. Веро^41,99237 21,441575~ул.Орце Николов бр.188 - до МИ-ДА моторс^42,006466 21,407886";


        String niza[] = skopje.split("~");
        for (int i = 0; i < niza.length; i++) {
            String deleno[] = niza[i].split("\\^");
            String name = deleno[0];
            String latlng = deleno[1];

            String niza1[] = latlng.split(" ");
            niza1[0] = niza1[0].replace(",", ".");
            niza1[1] = niza1[1].replace(",", ".");
            double lat = Double.parseDouble(niza1[0]);
            double lng = Double.parseDouble(niza1[1]);

//            Location locationA = new Location("point A");
//            locationA.setLatitude(lat);
//            locationA.setLongitude(lng);
//
//            Location locationBb = new Location("point B");
//            locationBb.setLatitude(lat1);
//            locationBb.setLongitude(lng1);
//            double distance = locationA.distanceTo(locationBb);

            double distance = 0;
            //     bankomati.add(new Bankomat(name, (int) distance, lat, lng));
        }

        Bankomat temp;
        if (bankomati.size() > 1) // check if the number of orders is larger than 1
        {
            for (int x = 0; x < bankomati.size() - 1; x++) // bubble sort outer loop
            {
                for (int i = 1; i < bankomati.size() - x; i++) {
                    if (bankomati.get(i - 1).distance > bankomati.get(i).distance) {
                        temp = bankomati.get(i - 1);
                        bankomati.set(i - 1, bankomati.get(i));
                        bankomati.set(i, temp);
                    }
                }
            }


        }

    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    private static ArrayList<FoursquareVenue> parseFoursquare(final String response) {

        ArrayList<FoursquareVenue> temp = new ArrayList<FoursquareVenue>();
        try {

            // make an jsonObject in order to parse the response
            JSONObject jsonObject = new JSONObject(response);

            // make an jsonObject in order to parse the response
            if (jsonObject.has("response")) {
                if (jsonObject.getJSONObject("response").has("venues")) {
                    JSONArray jsonArray = jsonObject.getJSONObject("response").getJSONArray("venues");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        FoursquareVenue poi = new FoursquareVenue();
                        if (jsonArray.getJSONObject(i).has("name")) {
                            poi.setName(jsonArray.getJSONObject(i).getString("name"));

                            if (jsonArray.getJSONObject(i).has("location")) {
                                if (jsonArray.getJSONObject(i).getJSONObject("location").has("address")) {
                                    if (jsonArray.getJSONObject(i).getJSONObject("location").has("city")) {
                                        poi.setCity(jsonArray.getJSONObject(i).getJSONObject("location").getString("city"));
                                    }
                                    if (jsonArray.getJSONObject(i).has("categories")) {
                                        if (jsonArray.getJSONObject(i).getJSONArray("categories").length() > 0) {
                                            if (jsonArray.getJSONObject(i).getJSONArray("categories").getJSONObject(0).has("icon")) {
                                                poi.setCategory(jsonArray.getJSONObject(i).getJSONArray("categories").getJSONObject(0).getString("name"));
                                            }
                                        }
                                    }
                                    temp.add(poi);
                                }
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<FoursquareVenue>();
        }
        return temp;

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

                    connectionclass = new ConnectionClass();
                    call = connectionclass.getip();
                    un = connectionclass.getun();
                    passwords = connectionclass.getpassword();
                    db = connectionclass.getdb();
                    connect = CONN(un, passwords, db, call);

                    // TODO Auto-generated method stub


                    String querycmd = "select * from restorant_lokacii";

                    try {

                        PreparedStatement prep;

                        prep = connect.prepareStatement(querycmd, Statement.RETURN_GENERATED_KEYS);
                        rs1 = prep.executeQuery();

                        while (rs1.next()) {

                            String name = rs1.getString("name");
                            String adresa = rs1.getString("address");
                            int dis = rs1.getInt("distance");
                            String lat = rs1.getString("lat");
                            String lng = rs1.getString("lng");
                            int chek = rs1.getInt("chekins");

                            double aa1 = Double.parseDouble(lat);
                            double aa2 = Double.parseDouble(lng);
                            bankomati.add(new Bankomat(name, dis, aa1, aa2, chek, adresa));
                        }


                    } catch (Exception e) {


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
                        if (bankomati.get(i - 1).distance > bankomati.get(i).distance) {
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
