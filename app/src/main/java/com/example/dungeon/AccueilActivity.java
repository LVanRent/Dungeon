package com.example.dungeon;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AccueilActivity extends Activity {
        /** Called when the activity is first created. */
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_accueil);
        }

        public void Main(View v) {

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
}
