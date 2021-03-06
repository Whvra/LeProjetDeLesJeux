package com.example.leprojetdelesjeux;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SinglePlayer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_player);
    }

    public void ShakeIt(View view) {
        Intent intent = new Intent(this, ShakeIt.class);
        startActivity(intent);
    }

    public void OpenJustePrix(View view) {
        Intent intent = new Intent(this, JustePrix.class);
        startActivity(intent);
    }

    public void OpenColors(View view) {
        Intent intent = new Intent(this, Colors.class);
        startActivity(intent);
    }

    public void OpenPerfectCircle(View view) {
        Intent intent = new Intent(this, PerfectCircle.class);
        startActivity(intent);
    }

    public void OpenBlindTest(View view) {
        Intent intent = new Intent(this, BlindTest.class);
        startActivity(intent);
    }

    public void OpenTimeTarget(View view) {
        Intent intent = new Intent(this, TimeTarget.class);
        startActivity(intent);
    }
}