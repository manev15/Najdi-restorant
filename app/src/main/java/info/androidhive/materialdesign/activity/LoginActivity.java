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

public class LoginActivity extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        najava = (Button) findViewById(R.id.button);


        Singleton.getInstance().activeUser = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this).getString("activeUser", Singleton.getInstance().activeUser);
        String active = Singleton.getInstance().activeUser;


        if (active.length() != 0) {
            Toast.makeText(getApplicationContext(),
                    "Веќе сте најавени ", Toast.LENGTH_LONG)
                    .show();
            finish();
        }


        najava.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                usern = username.getText().toString();
                pass = password.getText().toString();
                new LoginTask().execute();
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(i);
                finish();
            }
        });
    }


    class LoginTask extends AsyncTask<String, Void, String> {
        private ProgressDialog pDialog;
        private String rez;

        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(LoginActivity.this);
            pDialog.setMessage("Ве молиме почекајте...");
            pDialog.setCancelable(false);
            pDialog.show();

        }


        protected void onProgressUpdate(Integer... a) {

        }

        protected void onPostExecute(String result) {

            if (result.equals("cool")) {


                Singleton.getInstance().activeUser = usern;
                PreferenceManager.getDefaultSharedPreferences(LoginActivity.this).edit().putString("activeUser", Singleton.getInstance().activeUser).commit();

                FragmentDrawer.aktivan.setText(username.getText().toString());
                finish();

            }

            if (result.equals("gonema")) {
                Toast.makeText(getApplicationContext(),
                        "Погрешно корисничко име или лозинка ", Toast.LENGTH_LONG)
                        .show();

            }

            pDialog.dismiss();
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


            String querycmd = "select * from restorant_users where username='" + usern + "' and password='" + pass + "'";

            try {

                PreparedStatement prep;

                prep = connect.prepareStatement(querycmd, Statement.RETURN_GENERATED_KEYS);
                rs1 = prep.executeQuery();
                if (rs1 != null) {

                    rez = "cool";
                } else {
                    rez = "gonema";
                }

            } catch (Exception e) {


            }


            return rez;
        }
    }

}
