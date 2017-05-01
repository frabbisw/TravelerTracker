package com.example.frabbi.meem;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by imm on 4/24/2017.
 */

public class User implements Parcelable {

    public String ID;
    public String name;
    public String PhotoURL;
    String password;
    String latitude;
    String longitude;
    String type;
    public User() {}

    public User(String ID, String name, String URL){
        this.ID = ID;
        this.name= name;
        this.PhotoURL=URL;
    }

    public User(String ID, String name, String photoURL, String password, String latitude, String longitude, String type) {
        this.ID = ID;
        this.name = name;
        PhotoURL = photoURL;
        this.password = password;
        this.latitude = latitude;
        this.longitude = longitude;
        this.type = type;
    }

    public static User getUserTest(Account account, String type) {
        User user = new User();
        user.setID(account.getId());
        user.setName(account.getName());
        user.setLatitude(null);
        user.setLongitude(null);
        user.setPassword(null);
        user.setPhotoURL(account.getImagePath());
        user.setType(type);

        return user;
    }

    protected User(Parcel in) {
        ID = in.readString();
        name = in.readString();
        PhotoURL = in.readString();
        password = in.readString();
        latitude = in.readString();
        longitude = in.readString();
        type = in.readString();
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhotoURL() {
        return PhotoURL;
    }

    public void setPhotoURL(String photoURL) {
        PhotoURL = photoURL;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(ID);
        dest.writeString(name);
        dest.writeString(PhotoURL);
        dest.writeString(password);
        dest.writeString(latitude);
        dest.writeString(longitude);
        dest.writeString(type);
    }
}
