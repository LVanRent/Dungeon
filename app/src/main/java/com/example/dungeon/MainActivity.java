package com.example.dungeon;

import android.app.Activity;
import android.graphics.Point;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.Window;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.view.MotionEvent;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Display display = this.getWindow().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(new GameView(this,width,height));

       /* setContentView(R.layout.activity_main);

       View GV = (View) findViewById(R.id.view3);
        setContentView(new GameView(GV.getContext()));

        Button Up = (Button) findViewById(R.id.button);
        Button Down = (Button) findViewById(R.id.button2);
        Button LEFT = (Button) findViewById(R.id.button3);
        Button RIGHT = (Button) findViewById(R.id.button4);
        */
    }

}
