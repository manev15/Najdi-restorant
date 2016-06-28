package info.androidhive.materialdesign.activity;

/**
 * Created by aleks on 4/15/2016.
 */
public class Bankomat {
    String name,address;
    int distance;
    double lat;
    double lng;
    int chekins;
    public Bankomat(String name, int distance, double lat, double lng,int chekins,String address) {
        this.name = name;
        this.distance = distance;
        this.lat = lat;
        this.lng = lng;
        this.chekins = chekins;
        this.address = address;
    }

    public int getChekins() {
        return chekins;
    }

    public void setChekins(int chekins) {
        this.chekins = chekins;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDistance() {
        return distance;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address){ this.address = address;   }



    public void setDistance(int distance) {
        this.distance = distance;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
}


