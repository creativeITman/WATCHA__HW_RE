package com.andorid.example.watcha_hw;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by KangYunHo on 2019-01-08.
 */

public class MyAdapter extends BaseAdapter {

    private ArrayList<MyItem> mitems = new ArrayList<>();
    private ImageView movie_image;
    private TextView movie_name;
    private TextView movie_year;
    private TextView movie_rate;
    private Button movie_detail;
    int position;

    @Override
    public int getCount() {
        return mitems.size();
    }

    @Override
    public MyItem getItem(int pos) {
        return mitems.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return 0;
    }

    @Override
    public View getView(int pos, View view, ViewGroup viewGroup) {

        Context context = viewGroup.getContext();

        if(view == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.listview_custom, viewGroup, false);
        }

        movie_image = (ImageView)view.findViewById(R.id.movie_image);
        movie_name = (TextView)view.findViewById(R.id.movie_name);
        movie_year = (TextView)view.findViewById(R.id.movie_year);
        movie_rate = (TextView)view.findViewById(R.id.movie_rate);
        movie_detail = (Button)view.findViewById(R.id.movie_detail);

        MyItem myItem = getItem(pos);

        movie_image.setImageDrawable(myItem.getImage());
        movie_name.setText(myItem.getmovie_name());
        movie_year.setText(myItem.getmovie_year());
        movie_rate.setText(myItem.getmovie_rate());
        movie_detail.setText("자세히 보기");

        position = pos;

        return view;
    }

    public void addItem(Drawable img, String name, String year, String rate){
        MyItem mItem = new MyItem();

        mItem.setImage(img);
        mItem.setmovie_name(name);
        mItem.setmovie_year(year);
        mItem.setmovie_rate(rate);

        mitems.add(mItem);
    }

    public void rate(String rate, int pos){
        MyItem myItem = getItem(pos);
        myItem.setmovie_rate(rate);

        movie_detail.setText(myItem.getmovie_rate());
    }
}
