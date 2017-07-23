package com.reflexes.unixir.howgoodareyourreflexes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class Intro extends AppCompatActivity {

    static String TAG="TAG";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
    }


    public void StartGame(View v)
    {
        Intent intent=new Intent(this,MainGame.class);
        Log.d(TAG,"on Startgame");
        startActivity(intent);
    }
}
