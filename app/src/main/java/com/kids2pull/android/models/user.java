package com.kids2pull.android.models;

import org.joda.time.DateTime;

import java.util.UUID;

/**
 * Created by livnatavikasis on 05/03/2017.
 */

public class user {
    private String phone_number1;
    private String first_name ;
    private String last_name ;
    private String email ;
    private DateTime created_at ;
    private DateTime last_updated ;
    private String birthday ;
    private UserType userType_id ;
    private String userId;

    public user( String first_name, String email,String phone_number1) {
        userId = UUID.randomUUID().toString();
        this.first_name = first_name;
        this.email = email;
        this.phone_number1 = phone_number1;
    }

    public user(String first_name, String last_name, String email, DateTime created_at, UserType userType_id, String phone_number1) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.created_at = created_at;
        this.userType_id = userType_id;
        this.phone_number1 = phone_number1;
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
    public String getPhone_number1() {
        return phone_number1;
    }

    public void setPhone_number1(String phone_number1) {
        this.phone_number1 = phone_number1;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
