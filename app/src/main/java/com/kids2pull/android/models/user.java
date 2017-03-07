package com.kids2pull.android.models;

import org.joda.time.DateTime;

/**
 * Created by livnatavikasis on 05/03/2017.
 */

public class user {
    private int user_id;
    private String first_name ;
    private String last_name ;
    private String email ;
    private DateTime created_at ;
    private DateTime last_updated ;
    private DateTime birthday ;
    private UserType userType_id ;

    public user(String first_name, String last_name, String email, DateTime created_at, DateTime birthday, UserType userType_id, int user_id) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.created_at = created_at;
        this.birthday = birthday;
        this.userType_id = userType_id;
        this.user_id = user_id;
    }
    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public DateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(DateTime created_at) {
        this.created_at = created_at;
    }

    public DateTime getLast_updated() {
        return last_updated;
    }

    public void setLast_updated(DateTime last_updated) {
        this.last_updated = last_updated;
    }

    public DateTime getBirthday() {
        return birthday;
    }

    public void setBirthday(DateTime birthday) {
        this.birthday = birthday;
    }

    public UserType getUserType_id() {
        return userType_id;
    }

    public void setUserType_id(UserType userType_id) {
        this.userType_id = userType_id;
    }
    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
