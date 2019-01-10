package com.andorid.example.watcha_hw;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

/**
 * Created by KangYunHo on 2019-01-07.
 */

public class Fragment2 extends Fragment {

    private String[][] movie_data;
    private ListView mListView;
    protected View mView;
    private final int CODE = 2;
    private int currentPosition;
    private MyAdapter mMyAdapter;
    private String rate;
    private int pos;

    public Fragment2(){

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if(getActivity()!=null && getActivity() instanceof MainActivity){
            movie_data = ((MainActivity)getActivity()).getData();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fr2, container, false);
        mView = view;

        mListView = (ListView)view.findViewById(R.id.listView2);


        datasetting();

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
                Intent mIntent = new Intent(getActivity(), MovieActivity.class);
                mIntent.putExtra("position", pos);
                mIntent.putExtra("movie_title", movie_data[pos + 20][0]);
                mIntent.putExtra("movie_image", movie_data[pos + 20][1]);
                mIntent.putExtra("movie_year", movie_data[pos + 20][2]);
                mIntent.putExtra("movie_rate", movie_data[pos + 20][3]);
                mIntent.putExtra("frag_num", 2);

                currentPosition = mListView.getFirstVisiblePosition();

                startActivityForResult(mIntent, CODE);
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == -1) {
            System.out.println("Result code: " + resultCode);

            if(data.getExtras() != null) {
                rate = data.getExtras().getString("rate");
                pos = data.getExtras().getInt("position");
                changerate(rate, pos);
            }
        }
        else
            System.out.println("Failed!");
    }

    public void datasetting(){

        mMyAdapter = new MyAdapter();

        for (int i = 20; i < 40; i++){
            mMyAdapter.addItem(ContextCompat.getDrawable(getActivity().getApplicationContext(), getContext().getResources().getIdentifier(movie_data[i][1], "drawable", getContext().getPackageName())), movie_data[i][0], movie_data[i][2], getPreferences(i));
        }

        mListView.setAdapter(mMyAdapter);
    }

    public void changerate(String rate, int pos){

        movie_data[pos + 20][3] = rate;
        savePreferences(pos + 20, rate);
        mMyAdapter.rate(rate, pos);
        mMyAdapter.notifyDataSetChanged();
        mListView.setAdapter(mMyAdapter);
        mListView.setSelection(currentPosition);
    }

    public String getPreferences(int i){
        SharedPreferences pref = getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);

        return pref.getString(String.valueOf(i), "");
    }

    public void savePreferences(int pos, String movie_rate){
        SharedPreferences pref = getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(String.valueOf(pos), movie_rate);
        editor.commit();
    }
}
