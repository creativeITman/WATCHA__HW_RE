package com.andorid.example.watcha_hw;

import android.graphics.drawable.Drawable;

/**
 * Created by KangYunHo on 2019-01-08.
 */

public class MyItem {

    private Drawable image;
    private String movie_name;
    private String movie_year;
    private String movie_rate;

    public Drawable getImage(){
        return image;
    }

    public void setImage(Drawable image) {
        this.image = image;
    }

    public String getmovie_name(){ return movie_name; }

    public void setmovie_name(String name){
        this.movie_name = name;
    }

    public String getmovie_year(){
        return movie_year;
    }

    public void setmovie_year(String year){
        this.movie_year = year;
    }

    public String getmovie_rate(){
        return movie_rate;
    }

    public void setmovie_rate(String rate){
        this.movie_rate = rate;
    }
}
