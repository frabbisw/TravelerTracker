package com.example.frabbi.meem;

/**
 * Created by root on 4/19/17.
 */

public class CheckedInPosition
{
    String latitude;
    String longitude;
    String dateTime;
    String id;
    String type;

    public CheckedInPosition(String id, String dateTime, double latitude, double longitude, String type)
    {
        this.latitude=Double.toString(latitude);
        this.longitude=Double.toString(longitude);
        this.dateTime=dateTime;
        this.id=id;
        this.type=type;
    }
    public CheckedInPosition(String id, String dateTime, String latitude, String longitude, String type)
    {
        this.latitude=latitude;
        this.longitude=longitude;
        this.dateTime=dateTime;
        this.id=id;
        this.type=type;
    }
}
