package info.androidhive.materialdesign.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.w3c.dom.Text;

import info.androidhive.materialdesign.R;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;




public class SingleActivity extends AppCompatActivity implements OnMapReadyCallback {

    ConnectionClass connectionClass;
    private ImageButton mAddButton;
    private GoogleMap mMap1;
    private int poz = 0;
    private Bankomat item;
    private TextView mTitle,mAddress,mCheckins,mDistance,mRating;
    private ListView lista;
    public String name;
    public String comment;
    public RatingBar ratingBar;
    public int goleminaNiza;
    public double p=0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map1);
        mapFragment.getMapAsync(this);

        lista = (ListView) findViewById(R.id.listView1);
        FloatingActionButton kopce = (FloatingActionButton) findViewById(R.id.fab1);

     //   mAddButton = (ImageButton) findViewById(R.id.btn_add);
      //  mAddButton.setImageResource(R.drawable.icn_morph_reverse);
       // mAddButton.setVisibility(View.VISIBLE);
        mTitle = (TextView) findViewById(R.id.textView);
        mAddress = (TextView)findViewById(R.id.txtAdress);
        mCheckins = (TextView)findViewById(R.id.txtCheckins);
        mDistance = (TextView)findViewById(R.id.txtDistance);
        ratingBar = (RatingBar)findViewById(R.id.ratingBar);
        mRating = (TextView)findViewById(R.id.txtRating);


           Intent i = getIntent();
            String aa = i.getStringExtra("pozicija");
            poz = Integer.parseInt(aa);
            item = Singleton.getInstance().mGobal.get(poz);



        name = item.getName();
        String address = item.getAddress();
        int checkins = item.getChekins();
        int distance = item.getDistance();

        String niza = address.substring(1,address.length()-1);
        connectionClass = new ConnectionClass();

         p =  initializeRating();


        mTitle.setText(name);
        mAddress.setText("Адреса: "+niza);
        mDistance.setText("Одалеченост: "+Integer.toString(distance) + " m");
        mCheckins.setText("Чекирања: "+Integer.toString(checkins));
        if(Double.isNaN(p)) {

            mRating.setText("Рејтинг: 0.0" );
       }
        else
        {

            mRating.setText("Рејтинг: " + Double.toString(p));


        }


        goleminaNiza = broiKomentari();
        initialize();

        //voa ti e delo deka so ke gi plnis komentarto

        assert kopce != null;
        kopce.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              final AlertDialog.Builder alertDialog = new AlertDialog.Builder(SingleActivity.this);
              //AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();

              // Setting Dialog Title
              alertDialog.setTitle("КОМЕНТАР");
              alertDialog.setIcon(R.drawable.comments);

              // Setting Dialog Message
              alertDialog.setMessage("Напишете коментар");
              final EditText input = new EditText(SingleActivity.this);
              LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                      LinearLayout.LayoutParams.MATCH_PARENT,
                      LinearLayout.LayoutParams.MATCH_PARENT);
              input.setLayoutParams(lp);
              alertDialog.setView(input);

              alertDialog.setPositiveButton("Додади",
                      new DialogInterface.OnClickListener() {
                          public void onClick(DialogInterface dialog,int which) {
                              // Write your code here to execute after dialog
            comment = input.getText().toString();
                              try {
                                  Connection con = connectionClass.CONN();
                                  if (con == null) {

                                  } else {
                                      String query = "insert into restoranti_komentari(content,name) values ('"+comment+"',N'"+name+"')";
                                      Statement statement = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                                              ResultSet.CONCUR_READ_ONLY);

                                      ResultSet rs = statement.executeQuery(query);


                                  }
                              }
                              catch (Exception ex)
                              {


                              }finally{

                            goleminaNiza = broiKomentari();
                                  initialize();
                              }

                              Toast.makeText(getApplicationContext(),"Коментарот е додаден", Toast.LENGTH_SHORT).show();
                          //    Intent myIntent1 = new Intent(view.getContext(), Show.class);
                           //   startActivityForResult(myIntent1, 0);
                              dialog.cancel();
                          }
                      });
              // Setting Negative "NO" Button
              alertDialog.setNegativeButton("Откажи",
                      new DialogInterface.OnClickListener() {
                          public void onClick(DialogInterface dialog, int which) {
                              // Write your code here to execute after dialog
                              dialog.cancel();
                          }
                      });

              // closed

              // Showing Alert Message
              alertDialog.show();

          }
      });

      ratingBar.setOnTouchListener(new View.OnTouchListener() {
          @Override
          public boolean onTouch(View v, MotionEvent event) {
              if (event.getAction() == MotionEvent.ACTION_UP) {
                  float touchPositionX = event.getX();
                  float width = ratingBar.getWidth();
                  float starsf = (touchPositionX / width) * 5.0f;
                  int stars = (int)starsf + 1;
                  ratingBar.setRating(stars);

                  try {
                      Connection con = connectionClass.CONN();
                      if (con == null) {

                      } else {
                          String query = "insert into restoranti_rating(rating,name) values ('"+stars+"',N'"+name+"')";
                          Statement statement = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                                  ResultSet.CONCUR_READ_ONLY);

                          ResultSet rs = statement.executeQuery(query);


                      }
                  }
                  catch (Exception ex)
                  {


                  }finally{


                  p =   initializeRating();
                      mRating.setText("Рејтинг: "+Double.toString(p));

                      //initialize();
                      //metod za zimanje na rejtinzi
                  }


             //     Toast.makeText(SingleActivity.this, String.valueOf("Додадовте рејтинг:"+stars), Toast.LENGTH_SHORT).show();
                  v.setPressed(false);
              }
              if (event.getAction() == MotionEvent.ACTION_DOWN) {
                  v.setPressed(true);
              }

              if (event.getAction() == MotionEvent.ACTION_CANCEL) {
                  v.setPressed(false);
              }
              return false;
          }
      });
    }
    private int broiKomentari() {
        int brojce=0;

        try {
            Connection con = connectionClass.CONN();
            if (con == null) {

            } else {
                String query = "select * from restoranti_komentari where name=N'" + name + "'";

                Statement statement = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY);

                ResultSet rs = statement.executeQuery(query);


                while(rs.next())
                {
                    brojce++;


                }
            }
        }
        catch (Exception ex)
        {


        }

        return brojce;
    }



    private double initializeRating() {

        String z = "";
        Boolean isSuccess = false;
        int zbir=0;
        int br=0;
        double rejting=0;



            try {
                Connection con = connectionClass.CONN();
                if (con == null) {
                    z = "Error in connection with SQL server";
                } else {
                    String query = "select * from restoranti_rating where name=N'" + name + "'";

                    Statement statement = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                            ResultSet.CONCUR_READ_ONLY);

                    ResultSet rs = statement.executeQuery(query);


                    while(rs.next())
                    {
                        String petko = rs.getString("rating");
                        br++;
                        zbir+=Integer.parseInt(petko);
                        isSuccess = true;


                    }


        rejting = (double) zbir/br;


                }
            }
            catch (Exception ex)
            {
                isSuccess = false;
                z = "Exceptions";
            }
return rejting;
    }

    public void initialize()
{
    DoLogin doLogin = new DoLogin();
    doLogin.execute("");

}
    public void onMapReady(GoogleMap googleMap1) {
        mMap1 = googleMap1;

        // Add a marker in Sydney and move the camera


        double lat = item.getLat();
        double lng = item.getLng();
//        double lat = 42.22;
//        double lng = 22.21;

        mMap1.addMarker(new MarkerOptions().title("aa").position(new LatLng(lat, lng)).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        LatLng sydney = new LatLng(lat, lng);

       // mMap1.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap1.moveCamera(CameraUpdateFactory.newLatLngZoom((new LatLng(lat, lng)), 25));
    }

    public class DoLogin extends AsyncTask<String,String,String>
    {
        String z = "";
        Boolean isSuccess = false;
        String niza[] = new String[goleminaNiza];
        int br=0;


        @Override
        protected void onPreExecute() {
    }

        @Override
        protected void onPostExecute(String r) {
     //   Toast.makeText(SingleActivity.this,r,Toast.LENGTH_SHORT).show();

        if(isSuccess) {
            lista.setAdapter(new CustomAdapter(SingleActivity.this, niza));


        }

    }

        @Override
        protected String doInBackground(String... params) {

            try {
                Connection con = connectionClass.CONN();
                if (con == null) {
                    z = "Error in connection with SQL server";
                } else {
                    String query = "select * from restoranti_komentari where name=N'" + name + "'";

                                     Statement statement = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                            ResultSet.CONCUR_READ_ONLY);

                   ResultSet rs = statement.executeQuery(query);


                        while(rs.next())
                        {
                            String petko = rs.getString("content");

                            niza[br]=petko;
                            br++;
                            isSuccess = true;


                        }



                }
            }
            catch (Exception ex)
            {
                isSuccess = false;
                z = "Exceptions";
            }

        return z;
    }
    }



}
