package com.example.leprojetdelesjeux;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class BlindTest extends AppCompatActivity {

    ArrayList<String> listSong = new ArrayList<String>();
    ArrayList<MediaPlayer> listPlayer = new ArrayList<MediaPlayer>();
    ArrayList<Extrait> listExtrait = new ArrayList<Extrait>();
    Button buttonStart;
    int score;
    String name;

    Button buttonSimp;
    Button buttonOuiOui;
    Button buttonOnTheFloor;
    Button buttonMarseillaise;
    Button buttonLDC;
    Button buttonGOT;
    Button buttonAuDD;
    Button buttonWati;

    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blind_test);

        score = 0;
        buttonStart = findViewById(R.id.buttonStartBlindTest);

        buttonSimp = findViewById(R.id.buttonSimpson);
        buttonOuiOui = findViewById(R.id.buttonOuiOui);
        buttonOnTheFloor = findViewById(R.id.buttonOnTheFloor);
        buttonMarseillaise = findViewById(R.id.buttonMarseillaise);
        buttonLDC = findViewById(R.id.buttonLDC);
        buttonGOT = findViewById(R.id.buttonGOT);
        buttonAuDD = findViewById(R.id.buttonAuDD);
        buttonWati = findViewById(R.id.buttonWati);

        listSong.add("generiquedessimpson2eme");
        listSong.add("generiqueouiouifrancais");
        listSong.add("icejjfishonthefloorofficialmusicvideothatrawcompresents");
        listSong.add("lamarseillaise");
        listSong.add("musiqueliguedeschampionstheme");
        listSong.add("officialopeningcreditsgameofthroneshbo");
        listSong.add("pnlauddclipofficiel");
        listSong.add("sexiondassautwatibynight");


        MediaPlayer mpSimpsons = MediaPlayer.create(this, R.raw.generiquedessimpson2eme);
        MediaPlayer mpOuiOui = MediaPlayer.create(this, R.raw.generiqueouiouifrancais);
        MediaPlayer mpOnTheFloor = MediaPlayer.create(this, R.raw.icejjfishonthefloorofficialmusicvideothatrawcompresents);
        MediaPlayer mpMarseillaise = MediaPlayer.create(this, R.raw.lamarseillaise);
        MediaPlayer mpLDC = MediaPlayer.create(this, R.raw.musiqueliguedeschampionstheme);
        MediaPlayer mpGOT = MediaPlayer.create(this, R.raw.officialopeningcreditsgameofthroneshbo);
        MediaPlayer mpAuDD = MediaPlayer.create(this, R.raw.pnlauddclipofficiel);
        MediaPlayer mpWati = MediaPlayer.create(this, R.raw.sexiondassautwatibynight);

        listPlayer.add(mpSimpsons);
        listPlayer.add(mpOuiOui);
        listPlayer.add(mpOnTheFloor);
        listPlayer.add(mpMarseillaise);
        listPlayer.add(mpLDC);
        listPlayer.add(mpGOT);
        listPlayer.add(mpAuDD);
        listPlayer.add(mpWati);

        Extrait simp = new Extrait(listSong.get(0), listPlayer.get(0));
        Extrait ouioui = new Extrait(listSong.get(1), listPlayer.get(1));
        Extrait onTheFloor = new Extrait(listSong.get(2), listPlayer.get(2));
        Extrait Marseillaise = new Extrait(listSong.get(3), listPlayer.get(3));
        Extrait LDC = new Extrait(listSong.get(4), listPlayer.get(4));
        Extrait GOT = new Extrait(listSong.get(5), listPlayer.get(5));
        Extrait auDD = new Extrait(listSong.get(6), listPlayer.get(6));
        Extrait Wati = new Extrait(listSong.get(7), listPlayer.get(7));

        listExtrait.add(simp);
        listExtrait.add(ouioui);
        listExtrait.add(onTheFloor);
        listExtrait.add(Marseillaise);
        listExtrait.add(LDC);
        listExtrait.add(GOT);
        listExtrait.add(auDD);
        listExtrait.add(Wati);


        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                score = 0;
                buttonStart.setEnabled(false);
                startBlindTest();
                listExtrait.get(listExtrait.size()-1).getMp().release();
            }
        });

        buttonSimp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (name == "generiquedessimpson2eme") {
                    score++;
                    buttonSimp.setEnabled(false);
                    buttonSimp.setBackgroundColor(Color.GREEN);
                } else {
                    buttonSimp.setEnabled(false);
                    buttonSimp.setBackgroundColor(Color.RED);
                }
            }
        });

        buttonOuiOui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (name == "generiqueouiouifrancais") {
                    score++;
                    buttonOuiOui.setEnabled(false);
                    buttonOuiOui.setBackgroundColor(Color.GREEN);
                } else {
                    buttonOuiOui.setEnabled(false);
                    buttonOuiOui.setBackgroundColor(Color.RED);
                }
            }
        });

        buttonLDC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (name == "musiqueliguedeschampionstheme") {
                    score++;
                    buttonLDC.setEnabled(false);
                    buttonLDC.setBackgroundColor(Color.GREEN);
                } else {
                    buttonLDC.setEnabled(false);
                    buttonLDC.setBackgroundColor(Color.RED);
                }
            }
        });

        buttonGOT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (name == "officialopeningcreditsgameofthroneshbo") {
                    score++;
                    buttonGOT.setEnabled(false);
                    buttonGOT.setBackgroundColor(Color.GREEN);
                } else {
                    buttonGOT.setEnabled(false);
                    buttonGOT.setBackgroundColor(Color.RED);
                }
            }
        });

        buttonAuDD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (name == "pnlauddclipofficiel") {
                    score++;
                    buttonAuDD.setEnabled(false);
                    buttonAuDD.setBackgroundColor(Color.GREEN);
                } else {
                    buttonAuDD.setEnabled(false);
                    buttonAuDD.setBackgroundColor(Color.RED);
                }
            }
        });

        buttonOnTheFloor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (name == "icejjfishonthefloorofficialmusicvideothatrawcompresents") {
                    score++;
                    buttonOnTheFloor.setEnabled(false);
                    buttonOnTheFloor.setBackgroundColor(Color.GREEN);
                } else {
                    buttonOnTheFloor.setEnabled(false);
                    buttonOnTheFloor.setBackgroundColor(Color.RED);
                }
            }
        });

        buttonWati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (name == "sexiondassautwatibynight") {
                    score++;
                    buttonWati.setEnabled(false);
                    buttonWati.setBackgroundColor(Color.GREEN);
                } else {
                    buttonWati.setEnabled(false);
                    buttonWati.setBackgroundColor(Color.RED);
                }
            }
        });

        buttonMarseillaise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (name == "lamarseillaise") {
                    score++;
                    buttonMarseillaise.setEnabled(false);
                    buttonMarseillaise.setBackgroundColor(Color.GREEN);
                } else {
                    buttonMarseillaise.setEnabled(false);
                    buttonMarseillaise.setBackgroundColor(Color.RED);
                }
            }
        });

        countDownTimer = new CountDownTimer(60 * 1000, 10) {
            public void onTick(long millisUntilFinished) {
                //timer.setText("Temps restant : " + millisUntilFinished / 1000);
                //started = true;
            }

            public void onFinish() {
                //timer.setText("Temps Ecoulé");
                //finished = true;
                buttonStart.setEnabled(true);
            }
        };
    }

    public Button FindRightButton(String name) {
        if (name == "generiquedessimpson2eme") {
            return buttonSimp;
        } else if (name == "generiqueouiouifrancais") {
            return buttonOuiOui;
        } else if (name == "icejjfishonthefloorofficialmusicvideothatrawcompresents") {
            return buttonOnTheFloor;
        } else if (name == "lamarseillaise") {
            return buttonMarseillaise;
        } else if (name == "musiqueliguedeschampionstheme") {
            return buttonLDC;
        } else if (name == "officialopeningcreditsgameofthroneshbo") {
            return buttonGOT;
        } else if (name == "pnlauddclipofficiel") {
            return buttonAuDD;
        } else {
            return buttonWati;
        }
    }


    public void startBlindTest() {
        countDownTimer.start();
        Collections.shuffle(listExtrait);
        int i;
        for (i = 0; i < listExtrait.size(); i++) {
            listExtrait.get(i).getMp().start();
            name = listExtrait.get(i).getName();
            System.out.println(name);
            while(listExtrait.get(i).getMp().isPlaying()){return;}
            listExtrait.get(i).getMp().stop();
            listExtrait.get(i).getMp().release();
        }

        //buttonStart.setEnabled(true);
        countDownTimer.cancel();
    }

    public void onPrepared(MediaPlayer player) {
        player.start();
    }
}

class Extrait{

    String name;
    MediaPlayer mp;

    public Extrait(String name, MediaPlayer mp){
        this.name = name;
        this.mp = mp;
    }

    public String getName(){
        return name;
    }

    public MediaPlayer getMp(){
        return mp;
    }
}



