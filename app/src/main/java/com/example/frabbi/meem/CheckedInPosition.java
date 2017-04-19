package com.example.frabbi.meem;

/**
 * Created by root on 4/19/17.
 */

public class CheckedInPosition
{
    double latitude;
    double longitude;
    String dateTime;

    public CheckedInPosition(double latitude, double longitude, String dateTime)
    {
        this.latitude=latitude;
        this.longitude=longitude;
        this.dateTime=dateTime;
    }
}
