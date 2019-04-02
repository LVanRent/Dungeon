package com.example.dungeon;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class GameOverActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
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