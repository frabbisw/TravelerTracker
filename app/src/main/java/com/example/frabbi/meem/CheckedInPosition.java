package com.example.frabbi.meem;

/**
 * Created by root on 4/19/17.
 */

public class CheckedInPosition
{
    String id;
    String date;
    String latitude;
    String longitude;
    String type;

    public CheckedInPosition(String id, String date, double latitude, double longitude, String type)
    {
        this.latitude=Double.toString(latitude);
        this.longitude=Double.toString(longitude);
        this.date=date;
        this.id=id;
        this.type=type;
    }
    public CheckedInPosition(String id, String date, String latitude, String longitude, String type)
    {
        this.latitude=latitude;
        this.longitude=longitude;
        this.date=date;
        this.id=id;
        this.type=type;
    }
    public double getLatitude()
    {
        return Double.parseDouble(latitude);
    }
    public double getLongitude()
    {
        return Double.parseDouble(longitude);
    }
}
