package info.androidhive.materialdesign.activity;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionClass {
	 
    String ip;
    String classs;
    String db;
    String un;
    String password;
 
    public ConnectionClass() {
        classs = "net.sourceforge.jtds.jdbc.Driver";
        db = "proba";
        un = "manev15";
        password = "CRNORIZEChrabar463008";
        ip = "62.210.109.201";


    }
 
    public ConnectionClass(String Ip, String Classs, String Db, String Un,
                           String Password) {
        ip = Ip;
        classs = Classs;
        db = Db;
        un = Un;
        password = Password;
    }
    @SuppressLint("NewApi")
    public Connection CONN() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection conn = null;
        String ConnURL = null;
        try {

            Class.forName(classs);
            ConnURL = "jdbc:jtds:sqlserver://" + ip + ";"
                    + "databaseName=" + db + ";user=" + un + ";password="
                    + password + ";";
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

 
    public String getip() {
        return ip;
 
    }
 
    public String getclasss() {
        return classs;
 
    }
 
    public String getdb() {
        return db;
    }
 
    public String getun() {
        return un;
    }
 
    public String getpassword() {
        return password;
    }
 
    public void setip(String Ip) {
        ip = Ip;
    }
 
    public void setdb(String Db) {
        db = Db;
    }
 
    public void setclasss(String Classs) {
        classs = Classs;
    }
 
    public void setun(String Un) {
        un = Un;
    }
 
    public void setpassword(String Password) {
        password = Password;
    }
 
}