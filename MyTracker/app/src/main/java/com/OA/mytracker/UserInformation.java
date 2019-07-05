package com.OA.mytracker;

import java.io.Serializable;

public class UserInformation implements Serializable {
    public String lastonlinedate;
    public Long lat;
    public Long lon;


    public String getLastOnlineDate() {
        return lastonlinedate;
    }

    public void setLastOnlineDate(String lastOnlineDate) {
        lastonlinedate = lastOnlineDate;
    }

    public Long getLat() {
        return lat;
    }

    public void setLat(Long lat) {
        this.lat = lat;
    }

    public Long getLon() {
        return lon;
    }

    public void setLon(Long lon) {
        this.lon = lon;
    }

    public UserInformation() {

    }

    public UserInformation(String LastOnlineDate,Long lat,Long lon) {
        this.lastonlinedate=LastOnlineDate;
        this.lat=lat;
        this.lon=lon;
    }


}
