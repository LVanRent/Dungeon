package com.example.dungeon;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.TextView;

public class GameOverActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        SharedPreferences test = PreferenceManager.getDefaultSharedPreferences(this);
        long seed = test.getLong("currentSeed",0);
        int level = test.getInt("level",-1);
        int highScore = test.getInt("highScore",-1);


        TextView score = findViewById(R.id.textView_score);
        score.setText(String.format(" Seed : " + String.valueOf(seed) + " | Level : " + String.valueOf(level)+" | High-Score : "+highScore));
    }

    public void retry(View v) {

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void quit(View v) {

        Intent intent = new Intent(this, AccueilActivity.class);
        startActivity(intent);
    }
}