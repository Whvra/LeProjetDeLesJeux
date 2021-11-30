package com.example.leprojetdelesjeux;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Timer;

public class ShakeIt extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor accelerometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shake_it);

        TextView chrono = findViewById(R.id.timerView);
        TextView score = findViewById(R.id.scoreView);
        Button startButton = findViewById(R.id.startButton);

        //initialisation du gestionnaire de capteurs
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        //récupération de l'accéléromètre
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        System.out.println(accelerometer.getName());

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int score = 0;
                new CountDownTimer(10000, 50) {
                    public void onTick(long millisUntilFinished) {
                        chrono.setText(""+millisUntilFinished / 1000.0);
                        //récupérer la valeur de l'accéléromètre et l'incrémenter
                        //display l'incrément
                    }

                    public void onFinish() {
                        chrono.setText("Terminé !");
                    }

                }.start();
            }
        });
    }

    @Override
    public final void onSensorChanged(SensorEvent event) {
        float acceleration = event.values[0];
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;

        if(acceleration > 10 || acceleration < -10) {
            CharSequence text = "OH OUI SECOUE MOI ENCORE";
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        System.out.println("Accuracy changed");
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }
}