package com.example.leprojetdelesjeux;

import static java.lang.Math.abs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;

public class ShakeIt extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private Sensor lightSensor;
    private int score;
    private int gameToLaunch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shake_it);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, 10);
        }

        TextView chrono = findViewById(R.id.timerView);
        TextView titre = findViewById(R.id.titleView);
        TextView explanation = findViewById(R.id.explanationView);
        TextView scoreView = findViewById(R.id.scoreView);
        Button startButton = findViewById(R.id.startButton);

        explanation.setText("Bienvenue dans les Sensor Games. Ici, 3 jeux vous sont proposés, appuyez sur le bouton START et un jeu aléatoire se lancera");

        //initialisation du gestionnaire de capteurs
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        sensorManager.registerListener(this, accelerometer, 500000, sensorManager.SENSOR_DELAY_NORMAL);

        System.out.println("Infos accéléromètre : ");
        System.out.println("Min delay : "+accelerometer.getMinDelay());
        System.out.println("Max delay : "+accelerometer.getMaxDelay());
        System.out.println("Max range : "+accelerometer.getMaximumRange());

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameToLaunch = (int)(Math.random()*(2-0+2)+0); //jeu à lancer tiré au sort
                //gameToLaunch = 2;
                //System.out.println(gameToLaunch);
                score = 0;

                switch (gameToLaunch) {
                    case 0: //shake it
                        titre.setText("Shake It !");
                        explanation.setText("Vous avez 10s pour secouer votre téléphone le plus fort possible !");
                        startButton.setEnabled(false);
                        new CountDownTimer(5000, 50) {
                            public void onTick(long millisUntilFinished) {
                                chrono.setText(""+millisUntilFinished / 1000.0);
                                scoreView.setText(""+score);
                            }
                            public void onFinish() {
                                chrono.setText("Terminé !");
                                startButton.setEnabled(true);
                            }
                        }.start();
                    break;
                    case 1: //light game
                        titre.setText("Light Game !");
                        explanation.setText("Vous avez 5s pour trouver l'endroit le plus lumineux !");
                        startButton.setEnabled(false);
                        new CountDownTimer(5000, 50) {
                            public void onTick(long millisUntilFinished) {
                                chrono.setText(""+millisUntilFinished / 1000.0);
                                scoreView.setText(""+score);
                            }
                            public void onFinish() {
                                chrono.setText("Terminé !");
                                startButton.setEnabled(true);
                            }
                        }.start();
                    break;
                    case 2: //scream
                        titre.setText("Scream !");
                        explanation.setText("CRIEEEEEZ !");
                        startButton.setEnabled(false);
                        SoundMeter enregistrement = new SoundMeter();
                        try {
                            enregistrement.start();
                            System.out.println("Enregistrement commencé");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        final double[] score = {0};
                        new CountDownTimer(5000, 50) {
                            public void onTick(long millisUntilFinished) {
                                chrono.setText(""+millisUntilFinished / 1000.0);
                                double scoreToAdd = enregistrement.getAmplitude();
                                System.out.println("Score : "+score);
                                score[0] = score[0] + scoreToAdd;
                                scoreView.setText(""+ (int)score[0]);
                            }
                            public void onFinish() {
                                enregistrement.stop();
                                System.out.println("Enregistrement terminé");
                                chrono.setText("Terminé !");
                                startButton.setEnabled(true);
                            }
                        }.start();
                    break;
                }

            }
        });
    }

    @Override
    public final void onSensorChanged(SensorEvent event) {
        System.out.println("arrivée dans onSensorChange()");
        switch(gameToLaunch) {
            case 0: //capteur d'accélération
                float acceleration = event.values[0];
                acceleration = abs(acceleration);
                score += (int)acceleration;
            break;
            case 1: //capteur de luminosité
                float luminosity = event.values[0]*1000;
                System.out.println("Luminosité : "+luminosity);
                System.out.println("Score : "+score);
                if((int)luminosity > score) {
                    score = (int)luminosity;
                }
            break;
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

    public void start() throws IOException {
        if (mRecorder == null) {
            mRecorder = new MediaRecorder();
            //System.out.println(mRecorder.getActiveMicrophones());
            mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mRecorder.setOutputFile("/dev/null");
            mRecorder.prepare();
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