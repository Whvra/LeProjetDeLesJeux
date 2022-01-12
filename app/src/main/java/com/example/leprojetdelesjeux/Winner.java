package com.example.leprojetdelesjeux;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;

public class Winner extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winner);

        MediaPlayer winSong = MediaPlayer.create(this, R.raw.earthwindfireseptemberofficialhdvideo);
        winSong.start();
    }
}