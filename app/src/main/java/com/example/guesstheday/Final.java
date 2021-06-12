package com.example.guesstheday;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Final extends AppCompatActivity {
    TextView score;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);
        score = findViewById(R.id.score);
        Intent intent = getIntent();
        String scr = intent.getStringExtra(MainActivity.SCORE);
        score.setText("Game Over! \nYour Score is "+scr);


    }
}