package info.androidhive.materialdesign.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Locale;

import info.androidhive.materialdesign.R;

public class DodadiActivity extends AppCompatActivity {
    ConnectionClass connectionclass;
    String call, db, un, passwords;
    Connection connect;
    ResultSet rs, rs1, rs2, rs3;
    private Locale myLocale;

    private String a = "", aa = "", aaa = "";
    private Button najava;
    private EditText location;
    private EditText ime, adresa, lat, lng;
    private Button kopce;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dodadi);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        kopce = (Button) findViewById(R.id.dod);

        ime = (EditText) findViewById(R.id.name);

        adresa = (EditText) findViewById(R.id.address);

        lat = (EditText) findViewById(R.id.lat);
        lng = (EditText) findViewById(R.id.lng);

        lat.setText(Singleton.getInstance().lat + "");
        lng.setText(Singleton.getInstance().lng + "");


        Singleton.getInstance().activeUser = PreferenceManager.getDefaultSharedPreferences(DodadiActivity.this).getString("activeUser", Singleton.getInstance().activeUser);
        String active = Singleton.getInstance().activeUser;


        if (active.length() == 0) {
            Toast.makeText(getApplicationContext(),
                    "За да додадете ресторант мора да сте најавени ", Toast.LENGTH_LONG)
                    .show();
            Intent i = new Intent(DodadiActivity.this, LoginActivity.class);
            startActivity(i);
            finish();
        }
        kopce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                a = ime.getText().toString();
                aa = adresa.getText().toString();

                new LoginTask().execute();
            }
        });
    }

    class LoginTask extends AsyncTask<String, Void, String> {
        private ProgressDialog pDialog;
        private String rez;

        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(DodadiActivity.this);
            pDialog.setMessage("Ве молиме почекајте...");
            pDialog.setCancelable(false);
            pDialog.show();

        }


        protected void onProgressUpdate(Integer... a) {

        }

        protected void onPostExecute(String result) {

            if (result.equals("cool")) {
//                if(pDialog!=null){
//                    pDialog.cancel();
                //              }
                pDialog.dismiss();
            }


        }

        @Override
        protected String doInBackground(String... params) {


            connectionclass = new ConnectionClass();
            call = connectionclass.getip();
            un = connectionclass.getun();
            passwords = connectionclass.getpassword();
            db = connectionclass.getdb();
            connect = CONN(un, passwords, db, call);

            // TODO Auto-generated method stub


            String querycmd = "insert into restorant_lokacii (name,address,distance,lat,lng,chekins) values (N'" + a + "',N'" + aa + "',0,'" + Singleton.getInstance().lat + "','" + Singleton.getInstance().lng + "',0)";

            try {

                PreparedStatement prep;

                prep = connect.prepareStatement(querycmd, Statement.RETURN_GENERATED_KEYS);
                rs1 = prep.executeQuery();
                if (rs1.next() && rs1 != null) {

                    String ace = rs1.getInt(1) + "";

                } else {

                }
                rez = "cool";

            } catch (Exception e) {

                rez = "necool";

            }


            return rez;
        }
    }

}
