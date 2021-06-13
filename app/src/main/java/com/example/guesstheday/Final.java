package com.example.guesstheday;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

public class Final extends AppCompatActivity {
    TextView score,Htextview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);
        score = findViewById(R.id.score);
        Htextview =findViewById(R.id.textView5);
        Intent intent = getIntent();
        String scr = intent.getStringExtra(MainActivity.SCORE);
        score.setText("Game Over! \nYour Score is "+scr);
        int cscore = Integer.parseInt(scr);
        int x;
        SharedPreferences getscore = getSharedPreferences("hscore",MODE_PRIVATE);
        x = getscore.getInt("Highestscore",0);
        if(cscore>x) {
            SharedPreferences Hscore = getSharedPreferences("hscore", MODE_PRIVATE);
            SharedPreferences.Editor editor = Hscore.edit();
            editor.putInt("Highestscore", cscore);
            Htextview.setText("Congratulations!\n You Crossed Highest Score");
            editor.apply();
        }


    }
}