package com.example.leprojetdelesjeux;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class TimeTarget extends AppCompatActivity {

    private CountDownTimer countDownTimer;
    Boolean started;
    Boolean finished;
    TextView timer;
    Button buttonStart;
    double rand;
    TextView timeGuess;
    TextView resultText;
    Button stop;
    double diff;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_target);

        buttonStart = findViewById(R.id.resultTime);
        timer = findViewById(R.id.timerTarget);
        timeGuess = findViewById(R.id.timeGuess);
        stop = findViewById(R.id.stop);
        stop.setEnabled(false);
        resultText = findViewById(R.id.result);
        diff= 0;

        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                countDownTimer.start();
                buttonStart.setEnabled(false);
                stop.setEnabled(true);
                Random random = new Random();
                rand = random.nextInt(1000)/100.00;
                timeGuess.setText(""+ rand);
                resultText.setText("");
            }
        });

        countDownTimer = new CountDownTimer(15 * 1000, 50) {
            public void onTick(long millisUntilFinished) {
                timer.setText((""+ millisUntilFinished / 1000.0));
                started = true;
            }

            public void onFinish() {
                timer.setText("Temps Ecoulé");
                finished = true;
                buttonStart.setEnabled(true);
            }
        };

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                countDownTimer.cancel();
                buttonStart.setEnabled(true);
                stop.setEnabled(false);
                double timerValue = Double.parseDouble((String)timer.getText());
                diff = Math.abs(rand - timerValue);
                resultText.setText("Différence de : "+diff);
            }
        });
    }




}