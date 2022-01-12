package com.example.leprojetdelesjeux;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class Colors extends AppCompatActivity {

    public Button button6;
    public Button button7;
    public Button button8;
    public Button button9;
    public Button button10;
    public Button button11;
    public Button buttonStartColor;
    public String instruction;
    public int value1;
    public String value2;
    public Boolean selected;
    public TextView textResult;
    public TextView timerColor;
    private boolean finished;
    private boolean started = false;
    private CountDownTimer countDownTimer;
    public int score;
    public ArrayList<String> names = new ArrayList<>();
    public ArrayList<Integer> colors = new ArrayList<>();
    public ArrayList<Button> buttons = new ArrayList<>();
    public ArrayList<String> listname = new ArrayList<>();
    public ArrayList<Integer> listcolors = new ArrayList<>();
    public ArrayList<String> types = new ArrayList<>();
    public TextView textInstruction;
    public TextView scorePrint;

    int indexAlea1;
    int indexAlea2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colors);

        button6 = (Button) findViewById(R.id.button6);
        button7 = (Button) findViewById(R.id.button7);
        button8 = (Button) findViewById(R.id.button8);
        button9 = (Button) findViewById(R.id.button9);
        button10 = (Button) findViewById(R.id.button10);
        button11 = (Button) findViewById(R.id.button11);
        buttonStartColor = (Button) findViewById(R.id.buttonStartColors);
        textResult = (TextView) findViewById(R.id.textResultColors);
        timerColor = (TextView) findViewById(R.id.timerColor);
        textInstruction = (TextView) findViewById(R.id.textIntruction);
        scorePrint = (TextView) findViewById(R.id.scorePrint);
        score = 0;

        names.add("ROUGE");
        names.add("BLEU");
        names.add("JAUNE");
        names.add("CYAN");
        names.add("MAGENTA");
        names.add("VERT");


        int red = Color.RED;
        int blue = Color.BLUE;
        int yellow = Color.YELLOW;
        int magenta = Color.MAGENTA;
        int cyan = Color.CYAN;
        int green = Color.GREEN;

        colors.add(red);
        colors.add(blue);
        colors.add(yellow);
        colors.add(magenta);
        colors.add(cyan);
        colors.add(green);

        buttons.add(button6);
        buttons.add(button7);
        buttons.add(button8);
        buttons.add(button9);
        buttons.add(button10);
        buttons.add(button11);

        System.out.println(names);
        System.out.println(colors);

        listname = names;
        listcolors = colors;

        types.add("le mot");
        //types.add("la couleur");

        Random random = new Random();
        int indexAlea11 = random.nextInt(types.size());
        int indexAlea22 = random.nextInt(names.size());

        for(int i =0;i<buttons.size();i++){
            buttons.get(i).setEnabled(false);
            buttons.get(i).setText("");
        }

        buttonStartColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selected = false;
                instruction = "Vous devez appuyer sur"+ types.get(indexAlea11) + names.get(indexAlea22);
                textInstruction.setText(instruction);
                buttonStartColor.setEnabled(false);
                score = 0;
                scorePrint.setText(""+score);
                for(int i =0;i<buttons.size();i++){
                    buttons.get(i).setEnabled(true);
                }
                countDownTimer.start();
                randomize();
            }
        });

        countDownTimer = new CountDownTimer(20 * 1000, 10) {
            public void onTick(long millisUntilFinished) {
                timerColor.setText("Temps restant : " + millisUntilFinished / 1000);
                started = true;
            }

            @Override
            public void onFinish() {
                finished = true;
                buttonStartColor.setEnabled(true);
                scorePrint.setText(""+score);
                for(int i =0;i<buttons.size();i++){
                    buttons.get(i).setEnabled(false);
                    buttons.get(i).setText("");
                }
            }
        };

    }

    public void randomize(){

        listname = (ArrayList<String>) names.clone();
        listcolors = (ArrayList<Integer>)colors.clone();

        Random random1 = new Random();
        int indexAlea11 = random1.nextInt(types.size());
        int indexAlea12 = random1.nextInt(names.size());
        instruction = "Vous devez appuyer sur "+ types.get(indexAlea11) +" "+ names.get(indexAlea12);
        textInstruction.setText(instruction);
        for(int i=0;i<buttons.size();i++){
            Random random = new Random();

            int sizeAlea = 8 +random.nextInt(30);
            buttons.get(i).setTextSize(sizeAlea);
            int indexAlea1 = random.nextInt(listname.size());
            buttons.get(i).setText(listname.get(indexAlea1));
            listname.remove(indexAlea1);
            int indexAlea2 = random.nextInt(listcolors.size());
            buttons.get(i).setTextColor(listcolors.get(indexAlea2));
            listcolors.remove(indexAlea2);
        }

    }

    public void onClick6(View view) {
        randomize();
        value2= (String) button6.getText();
        value1= button6.getCurrentTextColor();
        if(types.get(indexAlea1).equals("le mot")){
            if(value2 == names.get(indexAlea2)){
                textResult.setText("BRAVO");
                score++;
                scorePrint.setText(""+score);
            }
            else{
                textResult.setText("Raté");
                score--;
                scorePrint.setText(""+score);
            }
        }
        else{
            if(value1 == colors.get(indexAlea1)){
                textResult.setText("BRAVO");
                score++;
                scorePrint.setText(""+score);
            }
            else{
                textResult.setText("Raté");
                score--;
                scorePrint.setText(""+score);
            }
        }
    }



    public void onClick7(View view) {
        randomize();
        value2= (String) button7.getText();
        value1= button7.getCurrentTextColor();
        if(types.get(indexAlea1).equals("le mot")){
            if(value2 == names.get(indexAlea2)){
                textResult.setText("BRAVO");
                score++;
                scorePrint.setText(""+score);
            }
            else{
                textResult.setText("Raté");
                score--;
                scorePrint.setText(""+score);
            }
        }
        else{
            if(value1 == colors.get(indexAlea1)){
                textResult.setText("BRAVO");
                score++;
                scorePrint.setText(""+score);
            }
            else{
                textResult.setText("Raté");
                score--;
                scorePrint.setText(""+score);
            }

        }
    }


    public void onClick8(View view) {
        randomize();
        value2= (String) button8.getText();
        value1= button8.getCurrentTextColor();
        if(types.get(indexAlea1).equals("le mot")){
            if(value2 == names.get(indexAlea2)){
                textResult.setText("BRAVO");
                score++;
                scorePrint.setText(""+score);
            }
            else{
                textResult.setText("Raté");
                score--;
                scorePrint.setText(""+score);
            }
        }
        else{
            if(value1 == colors.get(indexAlea1)){
                textResult.setText("BRAVO");
                score++;
                scorePrint.setText(""+score);
            }
            else{
                textResult.setText("Raté");
                score--;
                scorePrint.setText(""+score);
            }

        }
    }

    public void onClick9(View view) {
        randomize();
        value2= (String) button9.getText();
        value1= button9.getCurrentTextColor();
        if(types.get(indexAlea1).equals("le mot")){
            if(value2 == names.get(indexAlea2)){
                textResult.setText("BRAVO");
                score++;
                scorePrint.setText(""+score);
            }
            else{
                textResult.setText("Raté");
                score--;
                scorePrint.setText(""+score);
            }
        }
        else{
            if(value1 == colors.get(indexAlea1)){
                textResult.setText("BRAVO");
                score++;
                scorePrint.setText(""+score);
            }
            else{
                textResult.setText("Raté");
                score--;
                scorePrint.setText(""+score);
            }

        }
    }

    public void onClick10(View view) {
        randomize();
        value2= (String) button10.getText();
        value1= button10.getCurrentTextColor();
        if(types.get(indexAlea1).equals("le mot")){
            if(value2 == names.get(indexAlea2)){
                textResult.setText("BRAVO");
                score++;
                scorePrint.setText(""+score);
            }
            else{
                textResult.setText("Raté");
                score--;
                scorePrint.setText(""+score);
            }
        }
        else{
            if(value1 == colors.get(indexAlea1)){
                textResult.setText("BRAVO");
                score++;
                scorePrint.setText(""+score);
            }
            else{
                textResult.setText("Raté");
                score--;
                scorePrint.setText(""+score);
            }

        }
    }

    public void onClick11(View view) {
        randomize();
        value2= (String) button11.getText();
        value1= button11.getCurrentTextColor();

        if(types.get(indexAlea1).equals("le mot")){
            if(value2 == names.get(indexAlea2)){
                textResult.setText("BRAVO");
                score++;
                scorePrint.setText(""+score);
            }
            else{
                textResult.setText("Raté");
                score--;
                scorePrint.setText(""+score);
            }
        }
        else{
            if(value1 == colors.get(indexAlea1)){
                textResult.setText("BRAVO");
                score++;
                scorePrint.setText(""+score);
            }
            else{
                textResult.setText("Raté");
                score--;
                scorePrint.setText(""+score);
            }

        }
    }

}