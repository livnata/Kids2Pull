package com.kids2pull.android.models;

import android.location.Location;

/**
 * Created by livnatavikasis on 05/03/2017.
 */

public class hobby {

    private int hobby_id;
    private String hobby_name;
    private HobbyType hobby_type;
    private String address;
    private Location location;

    public int getHobby_id() {
        return hobby_id;
    }

    public void setHobby_id(int hobby_id) {
        this.hobby_id = hobby_id;
    }

    public String getHobby_name() {
        return hobby_name;
    }

    public void setHobby_name(String hobby_name) {
        this.hobby_name = hobby_name;
    }

    public HobbyType getHobby_type() {
        return hobby_type;
    }

    public void setHobby_type(HobbyType hobby_type) {
        this.hobby_type = hobby_type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }


}
