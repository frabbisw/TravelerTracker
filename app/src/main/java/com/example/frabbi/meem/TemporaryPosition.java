package com.example.frabbi.meem;

/**
 * Created by root on 4/19/17.
 */

public class TemporaryPosition extends CheckedInPosition
{
    public TemporaryPosition(String id, String date, double latitude, double longitude)
    {
        super(id,date,latitude,longitude,"temp");

        this.latitude=Double.toString(latitude);
        this.longitude=Double.toString(longitude);
        this.date=date;
        this.id=id;
        this.type="temp";
    }
    public TemporaryPosition(String id, String date, String latitude, String longitude)
    {
        super(id,date,latitude,longitude, "temp");

        this.latitude=latitude;
        this.longitude=longitude;
        this.date=date;
        this.id=id;
        this.type="temp";
    }
}