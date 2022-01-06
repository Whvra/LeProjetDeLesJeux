package com.example.leprojetdelesjeux;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] permissions = {"android.permission.RECORD_AUDIO", "android.permission.ACCESS_WIFI_STATE", "android.permission.CHANGE_WIFI_STATE", "android.permission.ACCESS_FINE_LOCATION", "android.permission.CHANGE_NETWORK_STATE", "android.permission.INTERNET", "android.permission.ACCESS_NETWORK_STATE"};
        this.requestAllPermissions(permissions);
    }

    //Demande des permissions pendant l'exécution
    public void requestAllPermissions(String[] permissions)
    {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, 80);
        }
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