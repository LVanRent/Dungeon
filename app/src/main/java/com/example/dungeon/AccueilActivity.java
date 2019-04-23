package com.example.dungeon;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class AccueilActivity extends Activity {
     Button mButton;
    EditText mEdit;


        /** Called when the activity is first created. */
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_accueil);

            mButton = (Button)findViewById(R.id.button_seed);
            mEdit   = (EditText)findViewById(R.id.editSeed);
            SharedPreferences test;
            final SharedPreferences.Editor editor;
            test = PreferenceManager.getDefaultSharedPreferences(this);
            editor = test.edit();


            mButton.setOnClickListener(
                    new View.OnClickListener()
                    {
                        public void onClick(View view)
                        {
                            Log.v("EditText", mEdit.getText().toString());
                            editor.putLong("newSeed",Long.valueOf(mEdit.getText().toString()));
                            editor.commit();
                        }
                    });

        }

        public void main(View v) {

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }

}
