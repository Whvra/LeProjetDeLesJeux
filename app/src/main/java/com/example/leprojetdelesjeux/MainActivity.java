package com.example.leprojetdelesjeux;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void OpenGames(View view) {
        Intent intent = new Intent(this, GamesInterface.class);
        startActivity(intent);
    }

    public void OpenSinglePlayer(View view) {
        Intent intent = new Intent(this, SinglePlayer.class);
        startActivity(intent);
    }

    public void OpenMultiplayer(View view) {
        Intent intent = new Intent(this, Multiplayer.class);
        startActivity(intent);
    }
}