package com.reflexes.unixir.howgoodareyourreflexes;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
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

    TextView textViewColor,textViewScore;
    static String[] colors={"Red","Blue","Green","Yellow","Black","Gray"};
    static int[] colorID={Color.RED,Color.BLUE,Color.GREEN,Color.YELLOW,Color.BLACK,Color.GRAY};
    static int rInt=colorID.length+1, pColorID=rInt, score=0, p=0;;
    static String pColor="", TAG="TAG";
    Button[] buttons;
    Button buttonLeftUp, buttonRightUp, buttonLeftDown, buttonRightDown;
    Random random;
    ProgressBar progressBar;
    static boolean done=false;
    //CustomChronometer chronometer;
    Thread thread;;
    Context context;
    //final Intent intent=new Intent(this, GameOverActivity.class);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_game);
        Log.d(TAG,"on create");
        textViewColor =findViewById(R.id.textViewColor);
        textViewScore =findViewById(R.id.Score);
        buttonLeftUp=findViewById(R.id.buttonLeftUp);
        buttonRightUp=findViewById(R.id.buttonRightUp);
        buttonLeftDown=findViewById(R.id.buttonLeftDown);
        buttonRightDown=findViewById(R.id.buttonRightDown);
        progressBar=findViewById(R.id.progressBar);
        context=this;

        random=new Random();
        buttons=new Button[]{buttonLeftUp,buttonRightUp,buttonRightDown,buttonLeftDown};
        p=0;
        done=false;
        score=0;
        progressBar.setMax(2000);
        progressBar.setProgress(p);


        thread=new Thread(new Runnable() {

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void run()
            {
                try
                {
                    while(p <= 2000 && !done)
                    {
                        sleep(100);
                        p +=20;
                        progressBar.setProgress(p);
                        if(p==2000) done=true;
                    }
                    Log.d(TAG,"out of while loop");
                    //intent.putExtra("score",score);
                    //startActivity(intent);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        RefreshGame();
        thread.start();
    }

    public void RefreshGame()
    {
        if(done) {
            thread.interrupt();
        }
        else {
            textViewScore.setText(Integer.toString(score));
            buttons = ShuffleButtons(buttons);
            pColorID = RandomNumForColor();
            pColor = colors[pColorID];
            textViewColor.setText(pColor);
            for (int i = 0; i < buttons.length; i++) {
                if (i == 0)
                    buttons[i].setBackgroundColor(colorID[pColorID]);
                else
                    buttons[i].setBackgroundColor(colorID[RandomNumForColor()]);
            }
        }

    }
    /*public void updateProgressBar(final int milliseconds)
    {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setProgress(milliseconds);
            }
        });
    }*/
    public void onClick(View view)
    {
        Log.d(TAG,"onClick");
        Log.d(TAG, Integer.toString(buttons[0].getId()));
        boolean isCorrect=false;
        p=0;
        switch (view.getId())
        {
            case R.id.buttonLeftUp:
                if(buttons[0] == buttonLeftUp) isCorrect=true;
                Log.d(TAG, ((String) ("buttonLeftUp " + isCorrect)));
                break;

            case R.id.buttonRightUp:
                if(buttons[0] == buttonRightUp) isCorrect=true;
                Log.d(TAG,(String) ("buttonRightUp " +  isCorrect));
                break;

            case R.id.buttonLeftDown:
                if(buttons[0] == buttonLeftDown) isCorrect=true;
                Log.d(TAG,(String) ("buttonLeftDown ")+ isCorrect);
                break;

            case R.id.buttonRightDown:
                if(buttons[0] == buttonRightDown) isCorrect=true;
                Log.d(TAG,(String) ("buttonRightDown ") +isCorrect);
                break;

            default:
                break;
        }

        if(isCorrect) score+=3;
        else if(score >= 3) score-=3;

            /*else {Intent intent=new Intent(this,GameOverActivity.class);
            intent.putExtra("score",score);
            startActivity(intent);}*/

        RefreshGame();
    }

    public int RandomNumForColor()
    {
        int rNo=pColorID;

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

