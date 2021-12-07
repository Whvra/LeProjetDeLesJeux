package com.example.leprojetdelesjeux;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class JustePrix extends AppCompatActivity {

    private Button buttonTry;
    private EditText textTry;
    private TextView textResult;
    private TextView timer;
    private Button buttonStart;
    private CountDownTimer countDownTimer;
    private boolean finished;
    private boolean started = false;
    private int prix;
    private int rand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juste_prix);

        //System.out.println(prix);

        //buttonTry = (Button) findViewById(R.id.buttonTry);
        buttonStart = (Button) findViewById(R.id.buttonStart);
        textTry = (EditText) findViewById(R.id.textTry);
        textResult = (TextView) findViewById(R.id.textResult);
        timer = (TextView) findViewById(R.id.timer);
        finished = false;

        textTry.setText("");

        textTry.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    try {
                        if (started==false){
                            textResult.setText("Cliquez sur commencer");
                        }
                        else if (textTry.getText().toString().equals("")) {
                            textResult.setText("Rentrez un nombre");
                        }
                        else {
                            int guess = (int) Integer.parseInt(textTry.getText().toString());
                            textTry.setText((""));
                            if (finished) {
                                textResult.setText("Trop tard !");
                            } else if (guess < prix) {
                                textResult.setText("C'est plus !");
                            } else if (guess == prix) {
                                textResult.setText("Bravo c'est gagné, le prix était bien : " + prix);
                                countDownTimer.cancel();
                            } else {
                                textResult.setText("C'est moins!");
                            }
                        }
                    }
                    catch(Exception e){
                        System.out.println("coucou");
                        textResult.setText("Rentrez un entier!");
                    }
                }
                return false;
            }
        });
        /**
        buttonTry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (started==false){
                        textResult.setText("Cliquez sur commencer");
                    }
                    else if (textTry.getText().toString().equals("")) {
                        textResult.setText("Rentrez un nombre");
                    }
                    else {
                        int guess = (int) Integer.parseInt(textTry.getText().toString());
                        textTry.setText((""));
                        if (finished) {
                            textResult.setText("Trop tard !");
                        } else if (guess < prix) {
                            textResult.setText("C'est plus !");
                        } else if (guess == prix) {
                            textResult.setText("Bravo c'est gagné, le prix était bien : " + prix);
                            countDownTimer.cancel();
                        } else {
                            textResult.setText("C'est moins!");
                        }
                    }
                }
                catch(Exception e){
                    System.out.println("coucou");
                    textResult.setText("Rentrez un entier!");
                }
            }
        });**/

        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                countDownTimer.start();
                rand = (int) (Math.random()*100000);
                prix = (int) Math.round(rand * 100) / 100;
            }
        });

        countDownTimer = new CountDownTimer(60 * 1000, 10) {
            public void onTick(long millisUntilFinished) {
                timer.setText("Temps restant : " + millisUntilFinished / 1000);
                started = true;
            }

            public void onFinish() {
                timer.setText("Temps Ecoulé");
                finished = true;
            }
        };
    }

}