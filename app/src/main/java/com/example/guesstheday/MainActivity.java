package com.example.guesstheday;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    private TextView date;
    private TextView d;
    RadioGroup radioGroup;
    RadioButton radioButton, radioButton1, radioButton2, radioButton3,radioButton4;
    public static final String SCORE ="com.example.guesstheday.extra.score";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        date = findViewById(R.id.textView2);
        d = findViewById(R.id.textView3);
        radioGroup = findViewById(R.id.radioGroup2);
        radioButton = findViewById(R.id.radioButton);
        radioButton1 = findViewById(R.id.radioButton2);
        radioButton2 = findViewById(R.id.radioButton3);
        radioButton3 = findViewById(R.id.radioButton4);
        Question.a=0;
        option();
        radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onclick();
            }
        });
        radioButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onclick();
            }
        });
        radioButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onclick();
            }
        });
        radioButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onclick();
            }
        });




    }

    public int leapyr(int x) {
        if (x % 400 == 0) {
            return 1;
        }
        if (x % 100 == 0)
            return 0;
        if (x % 4 == 0)
            return 1;
        else
            return 0;
    }
    static void shuffleArray(String[] ar)
    {
        // If running on Java 6 or older, use `new Random()` on RHS here
        Random rnd = ThreadLocalRandom.current();
        for (int i = ar.length - 1; i > 0; i--)
        {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            String a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
    }
    static void check(String[] opt,String ans)
    {
        int y=0;
        for(int i=0;i<4;i++)
        {
            if(ans==opt[i])
            {
                y++;
            }
        }
        if(y==0)
            opt[0]=ans;
        shuffleArray(opt);
    }
    public void OpenActivity()
    {
        Toast.makeText(this, "Wrong Answer!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this,Final.class);
        String s= String.valueOf(Question.a);
        intent.putExtra(SCORE,s);
        startActivity(intent);
    }
    void option()
    {
        int month = ThreadLocalRandom.current().nextInt(1, 12);
        int year = ThreadLocalRandom.current().nextInt(1800, 2021);
        int day;
        if (month == 2) {
            if (leapyr(year) == 1) {
                day = ThreadLocalRandom.current().nextInt(1, 29);
            } else {
                day = ThreadLocalRandom.current().nextInt(1, 28);
            }
        } else {
            if ((month % 2 == 0 && month < 8) || (month % 2 != 0 && month > 8))
                day = ThreadLocalRandom.current().nextInt(1, 30);
            else
                day = ThreadLocalRandom.current().nextInt(1, 31);
        }
        date.setText("Date: "+day + "/" + month + "/" + year + "\nSelect Correct Day! ");
        Calendar c = Calendar.getInstance();
        c.set(year, month - 1, day);
        int x = c.get(Calendar.DAY_OF_WEEK);
        String[] sday = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thrusday", "Friday", "Saturday"};
        String ans = sday[x - 1];
        Question.ans=ans;
        shuffleArray(sday);
        String[] opt = {sday[0],sday[1],sday[2],sday[3]};
        check(opt,ans);
        radioButton.setText(opt[0]);
        radioButton1.setText(opt[1]);
        radioButton2.setText(opt[2]);
        radioButton3.setText(opt[3]);
        radioGroup.clearCheck();
    }
    public void onclick()
    {
        int RadioId = radioGroup.getCheckedRadioButtonId();
        radioButton4 = findViewById(RadioId);
        if(radioButton4.getText()==Question.ans)
        {
            Question.a++;
            d.setText("Correct Answer!   \nCurrent Point:"+Question.a);
            option();
        }
        else {
            finishAffinity();
            OpenActivity();
        }
    }





}
