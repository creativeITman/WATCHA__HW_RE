package com.andorid.example.watcha_hw;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

/**
 * Created by KangYunHo on 2019-01-09.
 */

public class MovieActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView movie_poster;
    private TextView movie_name;
    private TextView movie_date;
    private TextView movie_rating;
    private Button back;
    private String movie_title;
    private String movie_image;
    private String movie_year;
    private String movie_rate;
    private int pos;
    private int frag_num;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_movie);

        Intent mIntent = getIntent();

        if(mIntent.getExtras() != null) {
            pos = mIntent.getExtras().getInt("position");
            movie_title = mIntent.getExtras().getString("movie_title");
            movie_image = mIntent.getExtras().getString("movie_image");
            movie_year = mIntent.getExtras().getString("movie_year");
            movie_rate = mIntent.getExtras().getString("movie_rate");
            frag_num = mIntent.getExtras().getInt("frag_num");
        }

        movie_poster = (ImageView)findViewById(R.id.movie_poster);
        movie_name = (TextView)findViewById(R.id.movie_n);
        movie_date = (TextView)findViewById(R.id.movie_y);
        movie_rating = (TextView)findViewById(R.id.movie_rate);
        back = (Button)findViewById(R.id.back);

        int id = getResources().getIdentifier(movie_image,"drawable", getPackageName());
        Drawable drawable = getResources().getDrawable(id);
        movie_poster.setImageDrawable(drawable);

        movie_name.setText(movie_title);
        movie_date.setText(movie_year);

        RatingBar rating = (RatingBar)findViewById(R.id.rating_bar);

        movie_rating.setText(getPreferences());

        if(!getPreferences().equals("평점을 입력해주세요:D") && !getPreferences().equals(""))
            rating.setRating(Float.parseFloat(getPreferences()));

        back.setOnClickListener(this);

        rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                movie_rating.setText(String.valueOf(v));
                movie_rate = movie_rating.getText().toString();
            }

        });
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.back:
                Intent mIntent = new Intent();
                mIntent.putExtra("rate", movie_rate);
                mIntent.putExtra("position", pos);
                setResult(RESULT_OK, mIntent);
                finish();
                break;

        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        savePreferences(pos, frag_num, movie_rate);
    }

    public String getPreferences(){
        SharedPreferences pref = getSharedPreferences("pref", Context.MODE_PRIVATE);

        if(frag_num == 1)
            return pref.getString(String.valueOf(pos), "");
        else
            return pref.getString(String.valueOf(pos + 20), "");
    }

    public void savePreferences(int pos, int fr_num, String movie_rate){
        SharedPreferences pref = getSharedPreferences("pref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        if(fr_num == 1)
            editor.putString(String.valueOf(pos), movie_rate);
        else
            editor.putString(String.valueOf(pos + 20), movie_rate);

        editor.commit();
    }
}
