package com.example.frabbi.meem;

/**
 * Created by root on 4/19/17.
 */

public class HomePosition extends CheckedInPosition
{
    public HomePosition(String id, String date, double latitude, double longitude)
    {
        super(id,date,latitude,longitude,"home");

        this.latitude=Double.toString(latitude);
        this.longitude=Double.toString(longitude);
        this.date=date;
        this.id=id;
        this.type="home";
    }
    public HomePosition(String id, String date, String latitude, String longitude)
    {
        super(id,date,latitude,longitude, "home");

        this.latitude=latitude;
        this.longitude=longitude;
        this.date=date;
        this.id=id;
        this.type="home";
    }
}
