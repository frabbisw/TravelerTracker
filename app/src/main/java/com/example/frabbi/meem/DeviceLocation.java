package com.example.frabbi.meem;

/**
 * Created by root on 5/2/17.
 */

public class DeviceLocation
{
    String latitude;
    String longitude;
    public DeviceLocation(double latitude, double longitude)
    {
        this.latitude=Double.toString(latitude);
        this.longitude=Double.toString(longitude);
    }
}
