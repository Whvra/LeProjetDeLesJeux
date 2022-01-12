package com.example.leprojetdelesjeux;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

    TextView textScore;
    TextView timerBT;

    MediaPlayer current;
    MediaPlayer next;

    private CountDownTimer countDownTimer;

    int i;
    int[] tabScores = new int[2];

    public ActivityResultLauncher<Intent> startActivityForResults;

    Boolean started;
    Boolean finished;

    public static final String RESULT = "RESULT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blind_test);

        startActivityForResults = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if(result != null && result.getResultCode() == RESULT_OK) {
                    if(result.getData() != null && result.getData().getIntArrayExtra(TimeTarget.RESULT) != null) {
                        int[] resultatsTimeTarget = result.getData().getIntArrayExtra(TimeTarget.RESULT);
                        tabScores[1] = resultatsTimeTarget[0];
                        Intent intentMulti = new Intent();
                        intentMulti.putExtra(RESULT, tabScores);
                        setResult(RESULT_OK, intentMulti);
                        finish();
                    }
                }
            }
        });

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

        textScore = findViewById(R.id.score);
        timerBT = findViewById(R.id.timerBT);
        buttonSimp.setEnabled(false);
        buttonOuiOui.setEnabled(false);
        buttonOnTheFloor.setEnabled(false);
        buttonMarseillaise.setEnabled(false);
        buttonLDC.setEnabled(false);
        buttonGOT.setEnabled(false);
        buttonAuDD.setEnabled(false);
        buttonWati.setEnabled(false);

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
                i=0;
                countDownTimer.start();
                Collections.shuffle(listExtrait);
                score = 0;
                buttonStart.setEnabled(false);
                //startBlindTest();
                buttonSimp.setEnabled(true);
                buttonOuiOui.setEnabled(true);
                buttonOnTheFloor.setEnabled(true);
                buttonMarseillaise.setEnabled(true);
                buttonLDC.setEnabled(true);
                buttonGOT.setEnabled(true);
                buttonAuDD.setEnabled(true);
                buttonWati.setEnabled(true);
                current = listExtrait.get(i).getMp();
                next = listExtrait.get(i+1).getMp();
                current.start();

            }
        });

       /** buttonSimp.setOnClickListener(new View.OnClickListener() {
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
                current.stop();
                next.start();
            }
        });

        buttonOuiOui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClickOuiOui(View view) {
                if (name == "generiqueouiouifrancais") {
                    score++;
                    buttonOuiOui.setEnabled(false);
                    buttonOuiOui.setBackgroundColor(Color.GREEN);
                } else {
                    buttonOuiOui.setEnabled(false);
                    buttonOuiOui.setBackgroundColor(Color.RED);
                }
                current.stop();
                next.start();
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
                current.stop();
                next.start();
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
                current.stop();
                next.start();
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
                current.stop();
                next.start();
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
                current.stop();
                next.start();
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
                current.stop();
                next.start();
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
                current.stop();
                next.start();
            }
        });**/

        countDownTimer = new CountDownTimer(30 * 1000, 1000) {
            public void onTick(long millisUntilFinished) {
                timerBT.setText("Temps restant : " + millisUntilFinished / 1000);
                started = true;
            }

            public void onFinish() {
                timerBT.setText("Terminé");
                finished = true;
                buttonStart.setEnabled(true);
                buttonSimp.setEnabled(false);
                buttonOuiOui.setEnabled(false);
                buttonOnTheFloor.setEnabled(false);
                buttonMarseillaise.setEnabled(false);
                buttonLDC.setEnabled(false);
                buttonGOT.setEnabled(false);
                buttonAuDD.setEnabled(false);
                buttonWati.setEnabled(false);
                countDownTimer.cancel();
                int scoreFinal = score;
                score =0;
                textScore.setText(""+score);
                lancerActiviteSuivante(scoreFinal);
            }
        };
    }

        public void onClickSimpson(View view) {
            name = listExtrait.get(i).getName();
            if (name == "generiquedessimpson2eme") {
                score++;
                buttonSimp.setEnabled(false);
                buttonSimp.setBackgroundColor(Color.GREEN);
            } else {
                buttonSimp.setEnabled(false);
                buttonSimp.setBackgroundColor(Color.RED);
            }
            current.stop();
            current.release();
            i++;
            current = listExtrait.get(i).getMp();
            if(i<listExtrait.size()-1){
                next.start();
                next = listExtrait.get(i+1).getMp();
            }
            if(i==7){
                buttonSimp.setEnabled(false);
                buttonOuiOui.setEnabled(false);
                buttonOnTheFloor.setEnabled(false);
                buttonMarseillaise.setEnabled(false);
                buttonLDC.setEnabled(false);
                buttonGOT.setEnabled(false);
                buttonAuDD.setEnabled(false);
                buttonWati.setEnabled(false);
                countDownTimer.cancel();
                textScore.setText(""+score);
                lancerActiviteSuivante(score);
            }
        }

        public void onClickOuiOui(View view) {
            name = listExtrait.get(i).getName();
            if (name == "generiqueouiouifrancais") {
                score++;
                buttonOuiOui.setEnabled(false);
                buttonOuiOui.setBackgroundColor(Color.GREEN);
            } else {
                buttonOuiOui.setEnabled(false);
                buttonOuiOui.setBackgroundColor(Color.RED);
            }
            current.stop();
            current.release();
            i++;
            current = listExtrait.get(i).getMp();
            if(i<listExtrait.size()-1){
                next.start();
                next = listExtrait.get(i+1).getMp();
            }
            if(i==7){
                buttonSimp.setEnabled(false);
                buttonOuiOui.setEnabled(false);
                buttonOnTheFloor.setEnabled(false);
                buttonMarseillaise.setEnabled(false);
                buttonLDC.setEnabled(false);
                buttonGOT.setEnabled(false);
                buttonAuDD.setEnabled(false);
                buttonWati.setEnabled(false);
                countDownTimer.cancel();
                textScore.setText(""+score);
                lancerActiviteSuivante(score);
            }
        }

        public void onClickLDC(View view) {
            name = listExtrait.get(i).getName();
            if (name == "musiqueliguedeschampionstheme") {
                score++;
                buttonLDC.setEnabled(false);
                buttonLDC.setBackgroundColor(Color.GREEN);
            } else {
                buttonLDC.setEnabled(false);
                buttonLDC.setBackgroundColor(Color.RED);
            }
            current.stop();
            current.release();
            i++;
            current = listExtrait.get(i).getMp();
            if(i<listExtrait.size()-1){
                next.start();
                next = listExtrait.get(i+1).getMp();
            }
            if(i==7){
                buttonSimp.setEnabled(false);
                buttonOuiOui.setEnabled(false);
                buttonOnTheFloor.setEnabled(false);
                buttonMarseillaise.setEnabled(false);
                buttonLDC.setEnabled(false);
                buttonGOT.setEnabled(false);
                buttonAuDD.setEnabled(false);
                buttonWati.setEnabled(false);
                countDownTimer.cancel();
                textScore.setText(""+score);
                lancerActiviteSuivante(score);
            }
        }

        public void onClickGOT(View view) {
            name = listExtrait.get(i).getName();
            if (name == "officialopeningcreditsgameofthroneshbo") {
                score++;
                buttonGOT.setEnabled(false);
                buttonGOT.setBackgroundColor(Color.GREEN);
            } else {
                buttonGOT.setEnabled(false);
                buttonGOT.setBackgroundColor(Color.RED);
            }
            current.stop();
            current.release();
            i++;
            current = listExtrait.get(i).getMp();
            if(i<listExtrait.size()-1){
                next.start();
                next = listExtrait.get(i+1).getMp();
            }
            if(i==7){
                buttonSimp.setEnabled(false);
                buttonOuiOui.setEnabled(false);
                buttonOnTheFloor.setEnabled(false);
                buttonMarseillaise.setEnabled(false);
                buttonLDC.setEnabled(false);
                buttonGOT.setEnabled(false);
                buttonAuDD.setEnabled(false);
                buttonWati.setEnabled(false);
                countDownTimer.cancel();
                textScore.setText(""+score);
                lancerActiviteSuivante(score);
            }
        }

        public void onClickAuDD(View view) {
            name = listExtrait.get(i).getName();
            if (name == "pnlauddclipofficiel") {
                score++;
                buttonAuDD.setEnabled(false);
                buttonAuDD.setBackgroundColor(Color.GREEN);
            } else {
                buttonAuDD.setEnabled(false);
                buttonAuDD.setBackgroundColor(Color.RED);
            }
            current.stop();
            current.release();
            i++;
            current = listExtrait.get(i).getMp();
            if(i<listExtrait.size()-1){
                next.start();
                next = listExtrait.get(i+1).getMp();
            }
            if(i==7){
                buttonSimp.setEnabled(false);
                buttonOuiOui.setEnabled(false);
                buttonOnTheFloor.setEnabled(false);
                buttonMarseillaise.setEnabled(false);
                buttonLDC.setEnabled(false);
                buttonGOT.setEnabled(false);
                buttonAuDD.setEnabled(false);
                buttonWati.setEnabled(false);
                countDownTimer.cancel();
                textScore.setText(""+score);
                lancerActiviteSuivante(score);
            }
        }

        public void onClickOnTheFloor(View view) {
            name = listExtrait.get(i).getName();
            if (name == "icejjfishonthefloorofficialmusicvideothatrawcompresents") {
                score++;
                buttonOnTheFloor.setEnabled(false);
                buttonOnTheFloor.setBackgroundColor(Color.GREEN);
            } else {
                buttonOnTheFloor.setEnabled(false);
                buttonOnTheFloor.setBackgroundColor(Color.RED);
            }
            current.stop();
            current.release();
            i++;
            current = listExtrait.get(i).getMp();
            if(i<listExtrait.size()-1){
                next.start();
                next = listExtrait.get(i+1).getMp();
            }
            if(i==7){
                buttonSimp.setEnabled(false);
                buttonOuiOui.setEnabled(false);
                buttonOnTheFloor.setEnabled(false);
                buttonMarseillaise.setEnabled(false);
                buttonLDC.setEnabled(false);
                buttonGOT.setEnabled(false);
                buttonAuDD.setEnabled(false);
                buttonWati.setEnabled(false);
                countDownTimer.cancel();
                textScore.setText(""+score);
                lancerActiviteSuivante(score);
            }
        }

        public void onClickWati(View view) {
            name = listExtrait.get(i).getName();
        name = listExtrait.get(i).getName();
            if (name == "sexiondassautwatibynight") {
                score++;
                buttonWati.setEnabled(false);
                buttonWati.setBackgroundColor(Color.GREEN);
            } else {
                buttonWati.setEnabled(false);
                buttonWati.setBackgroundColor(Color.RED);
            }
            current.stop();
            current.release();
            i++;
            current = listExtrait.get(i).getMp();
            if(i<listExtrait.size()-1){
                next.start();
                next = listExtrait.get(i+1).getMp();
            }
            if(i==7){
                buttonSimp.setEnabled(false);
                buttonOuiOui.setEnabled(false);
                buttonOnTheFloor.setEnabled(false);
                buttonMarseillaise.setEnabled(false);
                buttonLDC.setEnabled(false);
                buttonGOT.setEnabled(false);
                buttonAuDD.setEnabled(false);
                buttonWati.setEnabled(false);
                countDownTimer.cancel();
                textScore.setText(""+score);
                lancerActiviteSuivante(score);
            }
        }

        public void onClickMarseillaise(View view) {
            name = listExtrait.get(i).getName();
            if (name == "lamarseillaise") {
                score++;
                buttonMarseillaise.setEnabled(false);
                buttonMarseillaise.setBackgroundColor(Color.GREEN);
            } else {
                buttonMarseillaise.setEnabled(false);
                buttonMarseillaise.setBackgroundColor(Color.RED);
            }
            current.stop();
            current.release();
            i++;
            current = listExtrait.get(i).getMp();
            if(i<listExtrait.size()-1){
                next.start();
                next = listExtrait.get(i+1).getMp();
            }
            if(i==7){
                buttonSimp.setEnabled(false);
                buttonOuiOui.setEnabled(false);
                buttonOnTheFloor.setEnabled(false);
                buttonMarseillaise.setEnabled(false);
                buttonLDC.setEnabled(false);
                buttonGOT.setEnabled(false);
                buttonAuDD.setEnabled(false);
                buttonWati.setEnabled(false);
                countDownTimer.cancel();
                textScore.setText(""+score);
                lancerActiviteSuivante(score);
            }

        }

    private void lancerActiviteSuivante(int score) {
        new CountDownTimer(3 * 1000, 1000) {
            public void onTick(long millisUntilFinished) {
                System.out.println("tic tac");
            }

            public void onFinish() {
                tabScores[0] = score;
                //System.out.println("Temps restant : "+timerBT.getText().toString());
                //tabScores[1] = Integer.parseInt(timerBT.getText().toString());
                Intent intent = new Intent(getApplicationContext(), TimeTarget.class);
                startActivityForResults.launch(intent);
            }
        }.start();
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


    /**public void startBlindTest() {
        int i;
        for (i = 0; i < listExtrait.size()-1; i++) {
            current = listExtrait.get(i).getMp();
            if(i<listExtrait.size()-1){
                next = listExtrait.get(i+1).getMp();
            }
            if(i==0){
                current.start();
            }
            name = listExtrait.get(i).getName();
            System.out.println(name);
        }

        //buttonStart.setEnabled(true);
        countDownTimer.cancel();
    }**/

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



