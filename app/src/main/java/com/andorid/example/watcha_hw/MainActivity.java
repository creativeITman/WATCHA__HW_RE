package com.andorid.example.watcha_hw;

import android.content.Intent;
import android.content.res.AssetManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final int fragment1 = 1;
    private final int fragment2 = 2;

    private Button tab1, tab2;

    private  String[][] movie_info = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        AssetManager assetManager = getResources().getAssets();

        try{

            AssetManager.AssetInputStream source = (AssetManager.AssetInputStream)assetManager.open("DB.json");

            BufferedReader br = new BufferedReader(new InputStreamReader(source));

            StringBuilder sb = new StringBuilder();

            int buffersize = 1024 * 1024;

            char readBuffer [] = new char[buffersize];

            int resultsize = 0;

            while((resultsize = br.read(readBuffer)) != -1){
                if(resultsize == buffersize)
                    sb.append(readBuffer);
                else{
                    for(int i = 0; i < resultsize; i++)
                        sb.append(readBuffer[i]);
                }
            }

            String jString = sb.toString();

            JSONObject jsonObject = new JSONObject(jString);

            JSONArray jsonArray = new JSONArray(jsonObject.getString("movie_lists"));

            movie_info = new String[jsonArray.length()][];

            for(int i = 0; i < jsonArray.length(); i++)
                movie_info[i] = new String[4];

            for(int i = 0; i < jsonArray.length(); i++){
                movie_info[i][0] = jsonArray.getJSONObject(i).getString("title").toString();
                movie_info[i][1] = jsonArray.getJSONObject(i).getString("image").toString();
                movie_info[i][2] = jsonArray.getJSONObject(i).getString("year").toString();
                movie_info[i][3] = "평점을 입력해주세요:D";
            }

        } catch (JSONException je){
            Log.e("jsonerror", "JSON ERROR OCCUR!", je);
        }catch (Exception e){
            Log.e("exception", "ERROR OCCUR!", e);
        }

        tab1 = (Button)findViewById(R.id.bt_tab1);
        tab2 = (Button)findViewById(R.id.bt_tab2);

        tab1.setOnClickListener(this);
        tab2.setOnClickListener(this);

        Fragmentcall(fragment1);
    }

    @Override
    public void onClick(View view) {

        switch(view.getId()){
            case R.id.bt_tab1:
                Fragmentcall(fragment1);
                break;
            case R.id.bt_tab2:
                Fragmentcall(fragment2);
                break;

        }
    }


    private void Fragmentcall(int frg){

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        switch(frg){
            case 1:
                Fragment1 fr1 = new Fragment1();
                transaction.replace(R.id.fragment_container, fr1);
                transaction.commit();
                break;

            case 2:
                Fragment2 fr2 = new Fragment2();
                transaction.replace(R.id.fragment_container, fr2);
                transaction.commit();
                break;

        }


    }

    public String[][] getData(){
        return movie_info;
    }
}
