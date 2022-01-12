package com.example.leprojetdelesjeux;

import static java.lang.Math.abs;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Timer;

public class ShakeIt extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private Sensor lightSensor;
    private int scoreShake, scoreLight, scoreScream;
    private int[] tabScores = new int[6];
    private int gameToLaunch;
    private boolean sensorOn;
    public static final String RESULT = "RESULT";
    public ActivityResultLauncher<Intent> startActivityForResults;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shake_it);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, 10);
        }

        startActivityForResults = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if(result != null && result.getResultCode() == RESULT_OK) {
                    if(result.getData() != null && result.getData().getIntArrayExtra(JustePrix.RESULT) != null) {
                        int[] resultatsJustePrix = result.getData().getIntArrayExtra(JustePrix.RESULT);
                        tabScores[3] = resultatsJustePrix[0];
                        tabScores[4] = resultatsJustePrix[1];
                        tabScores[5] = resultatsJustePrix[2];
                        Intent intentMulti = new Intent();
                        intentMulti.putExtra(RESULT, tabScores);
                        setResult(RESULT_OK, intentMulti);
                        finish();
                    }
                }
            }
        });

        TextView chrono = findViewById(R.id.timerView);
        TextView titre = findViewById(R.id.titleView);
        TextView explanation = findViewById(R.id.explanationView);
        TextView scoreView = findViewById(R.id.scoreView);
        Button startButton = findViewById(R.id.startButton);

        scoreShake = 0;
        scoreLight = 0;
        scoreScream = 0;
        gameToLaunch = 0;
        sensorOn = false;

        explanation.setText("Bienvenue dans les Sensor Games. Ici, 3 jeux vous sont proposés, appuyez sur le bouton START et un jeu aléatoire se lancera");

        //initialisation du gestionnaire de capteurs
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        //sensorManager.registerListener(this, accelerometer, 500000, sensorManager.SENSOR_DELAY_NORMAL);

        startButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if (gameToLaunch == 0) {
                   sensorOn = true;
                   titre.setText("Shake It !");
                   explanation.setText("Vous avez 5s pour secouer votre téléphone le plus fort possible !");
                   startButton.setEnabled(false);
                   new CountDownTimer(5000, 50) {
                       public void onTick(long millisUntilFinished) {
                           chrono.setText("" + millisUntilFinished / 1000.0);
                           scoreView.setText("" + scoreShake);
                       }

                       public void onFinish() {
                           chrono.setText("Terminé !");
                           tabScores[0] = scoreShake;
                           startButton.setEnabled(true);
                           gameToLaunch++;
                           sensorOn = false;
                       }
                   }.start();
               } else if (gameToLaunch == 1) {
                   sensorOn = true;
                   titre.setText("Light Game !");
                   explanation.setText("Vous avez 5s pour trouver l'endroit le plus lumineux !");
                   startButton.setEnabled(false);
                   new CountDownTimer(5000, 50) {
                       public void onTick(long millisUntilFinished) {
                           chrono.setText("" + millisUntilFinished / 1000.0);
                           scoreView.setText("" + scoreLight);
                       }

                       public void onFinish() {
                           chrono.setText("Terminé !");
                           tabScores[1] = scoreLight;
                           startButton.setEnabled(true);
                           gameToLaunch++;
                           sensorOn = false;
                       }
                   }.start();
               } else if (gameToLaunch == 2) {
                   titre.setText("Scream !");
                   explanation.setText("CRIEEEEEZ !");
                   startButton.setEnabled(false);
                   SoundMeter enregistrement = new SoundMeter();
                   try {
                       enregistrement.start();
                       System.out.println("Enregistrement commencé");
                   } catch (IOException | InterruptedException e) {
                       e.printStackTrace();
                   }
                   //final double[] score = {0};
                   new CountDownTimer(5000, 50) {
                       public void onTick(long millisUntilFinished) {
                           chrono.setText("" + millisUntilFinished / 1000.0);
                           double scoreToAdd = enregistrement.getAmplitude() / 1000;
                           scoreScream = scoreScream + (int) scoreToAdd;
                           scoreView.setText("" + scoreScream);
                       }

                       public void onFinish() {
                           enregistrement.stop();
                           System.out.println("Enregistrement terminé");
                           chrono.setText("Terminé !");
                           //startButton.setEnabled(true);
                           startButton.setText("Terminé");
                           tabScores[2] = scoreScream;
                           startButton.setBackgroundColor(Color.GREEN);
                           lancerActiviteSuivante();
                       }
                   }.start();
               }
           }
        });
    }

    public void lancerActiviteSuivante(){
        new CountDownTimer(3 * 1000, 1000) {
            public void onTick(long millisUntilFinished) {
                System.out.println("tic tac");
            }

            public void onFinish() {
                Intent intent = new Intent(getApplicationContext(), JustePrix.class);
                startActivityForResults.launch(intent);
            }
        }.start();
    }

    @Override
    public final void onSensorChanged(SensorEvent event) {
        //System.out.println("arrivée dans onSensorChange()");
        if(gameToLaunch == 0 && sensorOn == true) {
            float acceleration = event.values[0];
            acceleration = abs(acceleration);
            scoreShake += (int) acceleration;
        }
        else if (gameToLaunch == 1 && sensorOn == true) {
            float luminosity = event.values[0] * 100;
            System.out.println("Luminosité : " + luminosity);
            System.out.println("Score : " + scoreLight);
            if ((int) luminosity > scoreLight) {
                scoreLight = (int) luminosity;
            }
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        System.out.println("Accuracy changed");
    }

    @Override
    protected void onResume() {
        super.onResume();
        switch(gameToLaunch) {
            case 0:
                sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
                break;
            case 1:
                sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
            break;
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }
}

class SoundMeter {

    private MediaRecorder mRecorder = null;

    public void start() throws IOException, InterruptedException {
        if (mRecorder == null) {
            mRecorder = new MediaRecorder();
            //System.out.println(mRecorder.getActiveMicrophones());
            mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
            mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
            mRecorder.setOutputFile("/dev/null");
            mRecorder.prepare();
            Thread.sleep(1000);
            mRecorder.start();
        }
    }

    public void stop() {
        if (mRecorder != null) {
            mRecorder.stop();
            mRecorder.release();
            mRecorder = null;
        }
    }

    public double getAmplitude() {
        if (mRecorder != null)
            return  mRecorder.getMaxAmplitude();
        else
            return 0;

    }
}