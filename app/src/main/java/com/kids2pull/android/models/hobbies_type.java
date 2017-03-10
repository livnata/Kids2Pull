package com.kids2pull.android.models;

import java.util.UUID;

/**
 * Created by livnatavikasis on 10/03/2017.
 */

public class Hobbies_type {

    private String hobby_type_id;
    private String hobby_type_name;

    public Hobbies_type(String hobby_type_name) {
        this.hobby_type_id = UUID.randomUUID().toString();
        this.hobby_type_name = hobby_type_name;
    }

    public String getHobby_type_id() {
        return hobby_type_id;
    }

    public void setHobby_type_id(String hobby_type_id) {
        this.hobby_type_id = hobby_type_id;
    }

    public String getHobby_type_name() {
        return hobby_type_name;
    }

    public void setHobby_type_name(String hobby_type_name) {
        this.hobby_type_name = hobby_type_name;
    }
}
