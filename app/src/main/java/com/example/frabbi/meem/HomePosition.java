package com.example.frabbi.meem;

/**
 * Created by root on 4/19/17.
 */

public class HomePosition extends CheckedInPosition
{
    public HomePosition(String id, String dateTime, double latitude, double longitude)
    {
        super(id,dateTime,latitude,longitude,"home");

        this.latitude=Double.toString(latitude);
        this.longitude=Double.toString(longitude);
        this.dateTime=dateTime;
        this.id=id;
        this.type="home";
    }
    public HomePosition(String id, String dateTime, String latitude, String longitude)
    {
        super(id,dateTime,latitude,longitude, "home");

        this.latitude=latitude;
        this.longitude=longitude;
        this.dateTime=dateTime;
        this.id=id;
        this.type="home";
    }
}
