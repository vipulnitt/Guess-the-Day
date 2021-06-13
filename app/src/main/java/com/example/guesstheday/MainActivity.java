package com.example.guesstheday;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;



public class MainActivity extends AppCompatActivity {
    private TextView date;
    View view;
    private TextView d;
    TextView timer,HScore;
    RadioGroup radioGroup;
    Vibrator vibrator;
    String[] opt={"raw","raw","raw","raw"};
    int month,year,day;
    int xy=151,highscore,bgclr=0,chkr=0;
    CountDownTimer ct;
    RadioButton radioButton, radioButton1, radioButton2, radioButton3,radioButton4;
    public static final String SCORE ="com.example.guesstheday.extra.score";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        date = findViewById(R.id.textView2);
        d = findViewById(R.id.textView3);
        timer = findViewById(R.id.textView4);
        HScore =findViewById(R.id.textView6);
        SharedPreferences getscore = getSharedPreferences("hscore",MODE_PRIVATE);
        highscore = getscore.getInt("Highestscore",0);
        HScore.setText("Highest Score:"+highscore);
        vibrator = (Vibrator)getSystemService(VIBRATOR_SERVICE);
        radioGroup = findViewById(R.id.radioGroup2);
        radioButton = findViewById(R.id.radioButton);
        radioButton1 = findViewById(R.id.radioButton2);
        radioButton2 = findViewById(R.id.radioButton3);
        radioButton3 = findViewById(R.id.radioButton4);
        Question.a=0;
        setcolor();
        option();
        ct = new CountDownTimer(3000000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                xy--;
                if(xy<0&&chkr==0){
                    finishAffinity();
                    OpenActivity();
                    chkr=1;
                }
                timer.setText("Countdown:"+xy);
            }
            @Override
            public void onFinish()
            {
            }
        }.start();
        if(chkr==1)
        {
            ct.cancel();
        }
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
        month = ThreadLocalRandom.current().nextInt(1, 12);
        year = ThreadLocalRandom.current().nextInt(1800, 2021);
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
        for(int i=0;i<4;i++)
        {
            opt[i]=sday[i];
        }
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
            bgclr=1;
            setcolor();
            xy+=4;
            vibrator.vibrate(300);
            option();
        }
        else {
            bgclr=2;
            setcolor();
            vibrator.vibrate(700);
            d.setText("Wrong Answer!   \nCurrent Point:"+Question.a);
            xy-=3;
            option();
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("score_state",Question.a);
        outState.putInt("day_state",day);
        outState.putInt("year_state",year);
        outState.putInt("month_state",month);
        outState.putString("Ans_state",Question.ans);
        outState.putStringArray("Option_state",opt);
        outState.putInt("duration_state",xy);
        outState.putInt("color_id",bgclr);
        ct.cancel();
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Question.a= savedInstanceState.getInt("score_state",0);
        day = savedInstanceState.getInt("day_state",0);
        year=savedInstanceState.getInt("year_state",0);
        month =savedInstanceState.getInt("month_state",0);
        opt = savedInstanceState.getStringArray("Option_state");
        Question.ans = savedInstanceState.getString("Ans_state", String.valueOf(0));
        xy = savedInstanceState.getInt("duration_state",0);
        bgclr = savedInstanceState.getInt("color_id",0);
        setcolor();
        d.setText("Correct Answer!   \nCurrent Point:"+Question.a);
        radioButton.setText(opt[0]);
        radioButton1.setText(opt[1]);
        radioButton2.setText(opt[2]);
        radioButton3.setText(opt[3]);
        date.setText("Date: "+day + "/" + month + "/" + year + "\nSelect Correct Day! ");
    }
    public void setcolor()
    {
        if(bgclr==0)
        {
            view = this.getWindow().getDecorView();
            view.setBackgroundResource(R.color.white);
        }
        if(bgclr==1)
        {
            view = this.getWindow().getDecorView();
            view.setBackgroundResource(R.color.green);
        }
        if(bgclr==2){
            view = this.getWindow().getDecorView();
            view.setBackgroundResource(R.color.red);
        }
    }



}
