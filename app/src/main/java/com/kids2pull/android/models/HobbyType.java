package com.kids2pull.android.models;

import com.kids2pull.android.R;

/**
 * Created by livnatavikasis on 05/03/2017.
 */
public enum HobbyType {
    SPORT("Sports", R.drawable.fill_11), BALLET("Ballet", R.drawable.page_1), MUSIC("Music", R.drawable.fill_3), COOCKING("Coocking"), ARTS("Arts");
    String hobbyName;
    int drawableId ;
    HobbyType(String hobbyName) {
        this.hobbyName = hobbyName;
    }

    HobbyType(String hobbyName, int drawableId) {
        this.hobbyName = hobbyName;
        this.drawableId = drawableId;
    }

    public HobbyType StringToHobyType(String hobbyTypeName){

        if ( hobbyTypeName.toLowerCase().equals(SPORT.getHobbyName().toLowerCase())){
            return SPORT;
        }else if ( hobbyTypeName.toLowerCase().equals(BALLET.getHobbyName().toLowerCase())){
            return BALLET;
        }else if (hobbyTypeName.toLowerCase().equals(MUSIC.getHobbyName().toLowerCase())){
            return MUSIC;
        }else if (hobbyTypeName.toLowerCase().equals(COOCKING.getHobbyName().toLowerCase())) {
            return COOCKING;
        }else if (hobbyTypeName.toLowerCase().equals(ARTS.getHobbyName().toLowerCase())) {
            return ARTS;
        }
        else{
            return SPORT;
        }
    }

    public int getDrawableId() {
        return drawableId;
    }

    public void setDrawableId(int drawableId) {
        this.drawableId = drawableId;
    }

    public String getHobbyName() {
        return hobbyName;
    }

    public void setHobbyName(String hobbyName) {
        this.hobbyName = hobbyName;
    }
}
