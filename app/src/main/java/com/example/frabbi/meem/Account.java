package com.example.frabbi.meem;

import java.io.Serializable;

/**
 * Created by frabbi on 4/9/17.
 */

public class Account implements Serializable {
    String id;
    String name;
    String password;
    String latitude;
    String longitude;
    String imgPath;

    public Account(){}
    public Account(String id, String name, String password)
    {
        this.id=id;
        this.name=name;
        this.password=password;
        this.latitude="0";
        this.longitude="0";
    }
    public String getId()
    {
        return id;
    }
    public String getName()
    {
        return name;
    }
    public double getLatitude()
    {
        return Double.parseDouble(latitude);
    }
    public double getLongitude()
    {
        return Double.parseDouble(longitude);
    }
    public String getImagePath() { return this.imgPath; }

    public void setImgPath(String imgPath)
    {
        this.imgPath=imgPath;
    }
    public void setLatitude(double latitude)
    {
        this.latitude=Double.toString(latitude);
    }
    public void setLongitude(double longitude)
    {
        this.longitude=Double.toString(longitude);
    }
}