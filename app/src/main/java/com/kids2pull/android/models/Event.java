package com.kids2pull.android.models;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by livnatavikasis on 05/03/2017.
 */

public class Event {

    private String event_id;
    private String hobby_id;
    private DateTime event_date;
    private ArrayList<String> user_ids_picker;
    private ArrayList<String> user_ids_spreader;


    public Event(String hobby_id, DateTime event_date) {
        user_ids_picker = new ArrayList<>();
        user_ids_spreader = new ArrayList<>();

        this.event_id = UUID.randomUUID().toString();
        this.hobby_id = hobby_id;
        this.event_date = event_date;
    }

    public String getEvent_id() {
        return event_id;
    }

    public void setEvent_id(String event_id) {
        this.event_id = event_id;
    }

    public String getHobby_id() {
        return hobby_id;
    }

    public void setHobby_id(String hobby_id) {
        this.hobby_id = hobby_id;
    }

    public DateTime getEvent_date() {
        return event_date;
    }

    public void setEvent_date(DateTime event_date) {
        this.event_date = event_date;
    }

    public ArrayList<String> getUser_ids_picker() {
        return user_ids_picker;
    }

    public void setUser_ids_picker(ArrayList<String> user_ids_picker) {
        this.user_ids_picker = user_ids_picker;
    }

    public ArrayList<String> getUser_ids_spreader() {
        return user_ids_spreader;
    }

    public void setUser_ids_spreader(ArrayList<String> user_ids_spreader) {
        this.user_ids_spreader = user_ids_spreader;
    }


}
