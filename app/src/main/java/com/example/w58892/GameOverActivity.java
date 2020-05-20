package com.example.w58892;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameOverActivity extends AppCompatActivity {

    private Button playAgain;
    private TextView DisplayScore;
    private TextView DisplayHighScore;
    private int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );

        score = getIntent().getIntExtra("score",0);

        playAgain = findViewById(R.id.playAgain);
        DisplayScore = findViewById(R.id.score);
        DisplayHighScore = findViewById(R.id.highScore);

        DisplayScore.setText("Zdobyte punkty: " + score);

        SharedPreferences prefs = getSharedPreferences("High_Score", Context.MODE_PRIVATE);
        int highScore = prefs.getInt("High_Score",0);

        if (score > highScore) {
            DisplayHighScore.setText("Rekord :" + score);

            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("High_Score",score);
            editor.apply();
        } else {
            DisplayHighScore.setText("Rekord : " + highScore);
        }

        playAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Intent = new Intent(GameOverActivity.this, MainActivity.class);
                startActivity(Intent);
            }
        });
    }
}
