package com.example.musicquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class activity_quiz extends AppCompatActivity {
    private Button button10;

    private Button button;
    private Button button7;
    private Button button8;
    private Button button9;

    private TextView countDownText;
    private CountDownTimer countDownTimer;
    private long timeLeft = 15999;


    MediaPlayer player;


    HashMap<String, Integer> map = new HashMap<>();
    ArrayList<String> songName = new  ArrayList<>();
    int index;
    TextView Points;
    int points;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        button10 = (Button)this.findViewById(R.id.button10);
        countDownText = findViewById(R.id.Counter);
        Points = findViewById(R.id.Pointer);

        button = findViewById(R.id.button);
        button7 = findViewById(R.id.button7);
        button8 = findViewById(R.id.button8);
        button9 = findViewById(R.id.button9);

        index = 0;

        songinit();
        Collections.shuffle(songName);

        points = 0;


        disabler();
        startGame();

    }

    private void songinit() {
        songName.add("Elektryczne Gitary - Dzieci Wybiegły");
        songName.add("Myslovitz - Dlugość dźwięku Samotności");
        songName.add("Strachy na lachy - Piła");
        songName.add("Wilki - Bohema");

        songName.add("Akurat - Lubię mówić z Tobą");
        songName.add("Czesław Niemen - Dziwny jest ten świat");
        songName.add("HAPPYSAD - Zanim pójdę");
        songName.add("Marek Grechuta - Dni których nie znamy");

        songName.add("Myslovitz - Z twarzą Marilyn Monroe");
        songName.add("T.Love - Nie nie nie");
        songName.add("Yugopolis & Maciej Maleńczuk - Ostatnia nocka");
        songName.add("Lady Pank - Stacja Warszawa");

        map.put(songName.get(0),R.raw.elektrycznegitarydzieciwybiegy);
        map.put(songName.get(1),R.raw.myslovitzdlugoscdzwiekusamotnosci);
        map.put(songName.get(2),R.raw.strachynalachypilatango);
        map.put(songName.get(3),R.raw.wilkibohema);

        map.put(songName.get(4),R.raw.akuratlubiemowicztoba);
        map.put(songName.get(5),R.raw.czeslawniemendziwnyjesttenswiat);
        map.put(songName.get(6),R.raw.happysadzanimpojde);
        map.put(songName.get(7),R.raw.marekgrechutadniktorychnieznamy);

        map.put(songName.get(8),R.raw.myslovitzztwarzamarilynmonroe);
        map.put(songName.get(9),R.raw.tlovenienienie);
        map.put(songName.get(10),R.raw.yugopolismaciejmalenczukostatnianocka);
        map.put(songName.get(11),R.raw.ladypankstacjawarszawa);
    }


    private void startGame() {
            timeLeft = 15999;
            Points.setText(points + "/" + index);
            generatequest(index);
            button10.setClickable(true);
            button10.setEnabled(true);



        if (player ==null) {
            player = MediaPlayer.create(this, map.get(songName.get(index)));


        }

        button10.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                button10.setClickable(false);
                button10.setEnabled(false);
                player.start();
                startTimer();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        player.stop();
                        player.release();
                        player = null;
                        Toast.makeText(activity_quiz.this, "Zakończono Odtwarzanie", Toast.LENGTH_SHORT).show();
                        enabler();

                    }
                }, 5000);


            }
        });



    }

    private void generatequest(int index) {
        ArrayList<String> songNameTemp = (ArrayList<String>) songName.clone();
        String Answer = songName.get(index);
        songNameTemp.remove(Answer);
        Collections.shuffle(songNameTemp);
        ArrayList<String> newList = new ArrayList<>();
        newList.add(songNameTemp.get(0));
        newList.add(songNameTemp.get(1));
        newList.add(songNameTemp.get(2));
        newList.add(Answer);
        Collections.shuffle(newList);

        button.setText(newList.get(0));
        button7.setText(newList.get(1));
        button8.setText(newList.get(2));
        button9.setText(newList.get(3));


    }

    public void answersed(View view){
        countDownTimer.cancel();
        String answer = ((Button)view).getText().toString().trim();
        String correctanswer = songName.get(index);
        if(answer.equals(correctanswer)){
            points++;
            Points.setText((points+""));
        }

        nextQuest();
    }

    public void nextQuest (){
        countDownTimer.cancel();
        index++;

        if (index > songName.size() - 1) {
            Intent intent = new Intent(activity_quiz.this, QuizOver.class);
            intent.putExtra("points",points);
            startActivity(intent);
            finish();
        }else {
            disabler();
            startGame();
        }
    }


    public void startTimer(){
        countDownTimer = new CountDownTimer(timeLeft, 100) {
            @Override
            public void onTick(long l) {
                timeLeft = l;
                updateTimer();
            }

            @Override
            public void onFinish() {
                index++;
                if (index > songName.size() - 1) {
                    Intent intent = new Intent(activity_quiz.this, QuizOver.class);
                    intent.putExtra("points",points);
                    startActivity(intent);
                    finish();
                }else {
                    disabler();
                    startGame();
                }
            }
        }.start();


    }

    public void disabler(){
            button.setEnabled(false);
            button7.setEnabled(false);
            button8.setEnabled(false);
            button9.setEnabled(false);

    }

    public void enabler(){
        button.setEnabled(true);
        button7.setEnabled(true);
        button8.setEnabled(true);
        button9.setEnabled(true);
    }

    public void updateTimer(){

        int seconds = (int) timeLeft/1000;
        int mseconds = (int) timeLeft%1000;

        String timeLeftText = "" + seconds;
        if(seconds < 10) timeLeftText+="."+mseconds;
        if(timeLeft<100) timeLeftText = "0.0";
        countDownText.setText(timeLeftText);
    }
}