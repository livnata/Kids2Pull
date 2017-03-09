package com.kids2pull.android.models;

import android.location.Location;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by livnatavikasis on 05/03/2017.
 */

public class Hobby {

    private String hobby_id;
    private String hobby_name;
    private HobbyType hobby_type;
    private String address;
    private Location location;
    private ArrayList<String> usersIdsArray;
    private DateTime hobby_date ;

    public Hobby(String hobby_name, HobbyType hobby_type, String address, Location location, ArrayList<String> usersIdsArray,DateTime hobby_date) {
        this.hobby_id = UUID.randomUUID().toString();
        this.hobby_name = hobby_name;
        this.hobby_type = hobby_type;
        this.address = address;
        this.location = location;
        this.usersIdsArray = usersIdsArray;
        this.hobby_date = hobby_date;
    }

    public String getHobby_id() {
        return hobby_id;
    }

    public void setHobby_id(String hobby_id) {
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

    public ArrayList<String> getUsersIdsArray() {
        return usersIdsArray;
    }

    public void setUsersIdsArray(ArrayList<String> usersIdsArray) {
        this.usersIdsArray = usersIdsArray;
    }
    public DateTime getHobby_date() {
        return hobby_date;
    }

    public void setHobby_date(DateTime hobby_date) {
        this.hobby_date = hobby_date;
    }


}
