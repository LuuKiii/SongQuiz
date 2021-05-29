package com.example.musicquiz;


import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

public class QuizOver extends AppCompatActivity {

    TextView Wynik;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_over);
        Wynik = findViewById(R.id.Wynik);
        int points = getIntent().getExtras().getInt("points");
        Wynik.setText(points + "");

    }

    public void exit(View view) {
        finish();

    }

}
