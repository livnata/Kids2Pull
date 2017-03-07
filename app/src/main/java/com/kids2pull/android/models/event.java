package com.kids2pull.android.models;

import org.joda.time.DateTime;

import java.util.List;

/**
 * Created by livnatavikasis on 05/03/2017.
 */

public class Event {

    private int event_id;
    private int hobby_id;
    private DateTime event_date;
    private List<Integer> user_ids_picker ;
    private List<Integer> user_ids_spreader ;



    public int getEvent_id() {
        return event_id;
    }

    public void setEvent_id(int event_id) {
        this.event_id = event_id;
    }

    public int getHobby_id() {
        return hobby_id;
    }

    public void setHobby_id(int hobby_id) {
        this.hobby_id = hobby_id;
    }

    public DateTime getEvent_date() {
        return event_date;
    }

    public void setEvent_date(DateTime event_date) {
        this.event_date = event_date;
    }

    public List<Integer> getUser_ids_picker() {
        return user_ids_picker;
    }

    public void setUser_ids_picker(List<Integer> user_ids_picker) {
        this.user_ids_picker = user_ids_picker;
    }

    public List<Integer> getUser_ids_spreader() {
        return user_ids_spreader;
    }

    public void setUser_ids_spreader(List<Integer> user_ids_spreader) {
        this.user_ids_spreader = user_ids_spreader;
    }



}
