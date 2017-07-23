package com.reflexes.unixir.howgoodareyourreflexes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Game_over extends AppCompatActivity {

    Button buttonTryAgain;
    TextView textViewScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        Intent intent=getIntent();
        int score=intent.getIntExtra("score",0);
        textViewScore=findViewById(R.id.ScoreDisplay);
        textViewScore.setText(Integer.toString(score));
        buttonTryAgain=findViewById(R.id.buttonTryAgain);
    }

    public void onClick(View view)
    {
        Intent intent=new Intent(this,MainGame.class);
        startActivity(intent);
    }
}
