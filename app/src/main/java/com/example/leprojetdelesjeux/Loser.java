package com.example.leprojetdelesjeux;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;

public class Loser extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loser);

        MediaPlayer loseSong = MediaPlayer.create(this, R.raw.corobizarpuelamerdenarutoparodie);
        loseSong.start();
    }
}