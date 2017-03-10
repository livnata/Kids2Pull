package com.kids2pull.android.models;

import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by livnatavikasis on 05/03/2017.
 */

public class User implements Serializable {
    private String phone_number;
    private String first_name;
    private String last_name;
    private String email;
    private DateTime created_at;
    private DateTime last_updated;
    private String birthday;
    private UserType userType_id;
    private String userId;
    private String url;


    public User(String first_name, String email, String phone_number,String url) {
        userId = UUID.randomUUID().toString();
        this.first_name = first_name;
        this.email = email;
        this.phone_number = phone_number;
        this.url=url;
        this.created_at = new DateTime();
        this.last_updated = new DateTime();
    }



    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public UserType getUserType_id() {
        return userType_id;
    }

    public void setUserType_id(UserType userType_id) {
        this.userType_id = userType_id;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
