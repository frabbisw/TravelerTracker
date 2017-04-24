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

    public Account(){}
    public Account(String id, String name, String password)
    {
        this.id=id;
        this.name=name;
        this.password=password;
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
}