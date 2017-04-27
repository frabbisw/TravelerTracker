package com.example.frabbi.meem;

/**
 * Created by root on 4/19/17.
 */

public class WorkPosition extends CheckedInPosition
{
    public WorkPosition(String id, String date, double latitude, double longitude)
    {
        super(id,date,latitude,longitude,"work");

        this.latitude=Double.toString(latitude);
        this.longitude=Double.toString(longitude);
        this.date=date;
        this.id=id;
        this.type="work";
    }
    public WorkPosition(String id, String date, String latitude, String longitude)
    {
        super(id,date,latitude,longitude, "work");

        this.latitude=latitude;
        this.longitude=longitude;
        this.date=date;
        this.id=id;
        this.type="work";
    }
}
