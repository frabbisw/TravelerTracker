package com.example.frabbi.meem;

/**
 * Created by root on 4/19/17.
 */

public class TemporaryPosition extends CheckedInPosition
{
    public TemporaryPosition(String id, String dateTime, double latitude, double longitude)
    {
        super(id,dateTime,latitude,longitude,"temp");

        this.latitude=Double.toString(latitude);
        this.longitude=Double.toString(longitude);
        this.dateTime=dateTime;
        this.id=id;
        this.type="temp";
    }
    public TemporaryPosition(String id, String dateTime, String latitude, String longitude)
    {
        super(id,dateTime,latitude,longitude, "temp");

        this.latitude=latitude;
        this.longitude=longitude;
        this.dateTime=dateTime;
        this.id=id;
        this.type="temp";
    }
}