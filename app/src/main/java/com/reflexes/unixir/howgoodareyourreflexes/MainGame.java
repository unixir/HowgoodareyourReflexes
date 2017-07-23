package com.reflexes.unixir.howgoodareyourreflexes;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Random;

import static java.lang.Thread.sleep;

public class MainGame extends AppCompatActivity {

    TextView textViewColor,textViewScore,textViewTime;
    static String[] colors={"Red","Blue","Green","Yellow","Black","Gray"};
    static int[] colorID={Color.RED,Color.BLUE,Color.GREEN,Color.YELLOW,Color.BLACK,Color.GRAY};
    static int rInt=colorID.length+1, pColorID=rInt, score=0, p=0,tScore;
    static String pColor="", TAG="TAG";
    Button[] buttons;
    Button buttonLeftUp, buttonRightUp, buttonLeftDown, buttonRightDown;
    Random random;
    ProgressBar progressBar;
    static boolean done=false, isCorrect;
    Handler myHandler;
    //CustomChronometer chronometer;
    Thread thread;
    Context context;
    static Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_game);
        Log.d(TAG,"on create");
        textViewColor =findViewById(R.id.textViewColor);
        textViewTime =findViewById(R.id.textViewTime);
        textViewScore =findViewById(R.id.Score);
        buttonLeftUp=findViewById(R.id.buttonLeftUp);
        buttonRightUp=findViewById(R.id.buttonRightUp);
        buttonLeftDown=findViewById(R.id.buttonLeftDown);
        buttonRightDown=findViewById(R.id.buttonRightDown);
        progressBar=findViewById(R.id.progressBar);
        context=this;
        myHandler=new Handler();
        random=new Random();
        buttons=new Button[]{buttonLeftUp,buttonRightUp,buttonRightDown,buttonLeftDown};
        p=0;
        done=false;
        score=0;
        progressBar.setMax(2000);
        progressBar.setProgressTintList(ColorStateList.valueOf(Color.BLUE));
        progressBar.setProgress(p);
        intent=new Intent(this, Game_over.class);


        thread=new Thread(new Runnable() {

            @Override
            public void run()
            {
                try
                {
                    while(p <= 2000 && !done)
                    {
                        p +=20;
                        myHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                if(p>0 && p<700) progressBar.setProgressTintList(ColorStateList.valueOf(Color.GREEN));
                                else if(p>700 && p<1500) progressBar.setProgressTintList(ColorStateList.valueOf(Color.BLUE));
                                else if(p>1500) progressBar.setProgressTintList(ColorStateList.valueOf(Color.RED));
                                progressBar.setProgress(p);
                            }
                        });
                        thread.sleep(25);
                        if(p==2000) done=true;
                    }
                    Log.d(TAG,"out of while loop");
                    intent.putExtra("score",score);
                    startActivity(intent);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        RefreshGame();
    }

    public void RefreshGame()
    {
        textViewScore.setText(Integer.toString(score));
            buttons = ShuffleButtons(buttons);
            pColorID = RandomNumForColor();
            pColor = colors[pColorID];
            textViewColor.setText(pColor);
            for (int i = 0; i < buttons.length; i++)
            {
                if (i == 0)
                    buttons[i].setBackgroundColor(colorID[pColorID]);
                else
                    buttons[i].setBackgroundColor(colorID[RandomNumForColor()]);
            }
        if(done) {
            thread.interrupt();
        }

    }

    public void onClick(View view)
    {
        //Log.d(TAG,"onClick");
        //Log.d(TAG, Integer.toString(buttons[0].getId()));
        tScore=p;
        p=0;
        isCorrect = false;

        switch (view.getId())
        {
            case R.id.buttonLeftUp:
                if(buttons[0] == buttonLeftUp) isCorrect=true;
                break;

            case R.id.buttonRightUp:
                if(buttons[0] == buttonRightUp) isCorrect=true;
                break;

            case R.id.buttonLeftDown:
                if(buttons[0] == buttonLeftDown) isCorrect=true;
                break;

            case R.id.buttonRightDown:
                if(buttons[0] == buttonRightDown) isCorrect=true;
                break;

            default:
                break;
        }
        if(isCorrect)
        {
            if(tScore>1500)
            {
                textViewScore.setTextColor(Color.GREEN);
                score += 3;
            }
            else if(tScore>500)
            {
                textViewScore.setTextColor(Color.YELLOW);
                score += 2;
            }
            else
            {
                textViewScore.setTextColor(Color.MAGENTA);
                score += 1;
            }
        }
        else if(score >= 3) score-=3;
        else {
            Intent intent=new Intent(this,Game_over.class);
            intent.putExtra("score",score);
            startActivity(intent);
        }

        RefreshGame();
    }

    public int RandomNumForColor()
    {
        int rNo;

        do {
            rNo=random.nextInt(colors.length);
        }
        while (rNo == pColorID);

        return rNo;
    }

    public Button[] ShuffleButtons(Button[] sButtons)
    {
        Button helper;
        for(int i=0;i<sButtons.length;i++)
        {
            int c=random.nextInt(sButtons.length);
            helper=sButtons[c];
            sButtons[c]=sButtons[i];
            sButtons[i]=helper;
        }
        return sButtons;
    }



}

