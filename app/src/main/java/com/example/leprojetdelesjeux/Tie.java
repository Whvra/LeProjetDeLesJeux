package com.example.leprojetdelesjeux;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;

public class Tie extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tie);

        MediaPlayer tieSong = MediaPlayer.create(this, R.raw.valdmameilleureamie);
        tieSong.start();
    }
}