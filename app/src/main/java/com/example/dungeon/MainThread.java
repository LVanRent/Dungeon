package com.example.dungeon;

import android.graphics.BitmapFactory;
import android.view.SurfaceHolder;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.view.*;
import android.view.SurfaceView;
import android.view.SurfaceHolder;
import android.view.MotionEvent;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import static java.lang.Math.min;
import static com.example.dungeon.MainThread.canvas;

import android.view.View;
import android.view.View.OnTouchListener;

import java.util.Random;

import static com.example.dungeon.Map.*;
import static java.lang.Math.min;


public class MainThread extends Thread {


    private SurfaceHolder surfaceHolder;
    private GameView gameView;
    private boolean running;
    public static Canvas canvas;
    public MotionEvent lastEvent;
    public int direction;
    private int targetFPS =30;
    private double averageFPS;

    public MainThread(SurfaceHolder surfaceHolder, GameView gameView){

        super();
        this.surfaceHolder = surfaceHolder;
        this.gameView = gameView;

    }
    @Override
    public void run() {
        long startTime;
        long timeMillis;
        long waitTime;
        long totalTime=0;
        int frameCount=0;
        long targetTime = 1000/targetFPS;
        lastEvent = null;
        direction = 0;


        Hero player = new Hero();
        while (running) {
            startTime = System.nanoTime();
            canvas = null;

            try {
                //this.processEvents();
                canvas = this.surfaceHolder.lockCanvas();
                synchronized(surfaceHolder) {

                    // sleep(1000);

                    this.gameView.update(player,canvas);


                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (canvas != null) {
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            timeMillis =(System.nanoTime()-startTime)/1000000;
            waitTime = targetTime - timeMillis;

            try {
                this.sleep(waitTime);
            } catch (Exception e) {}

            totalTime += System.nanoTime() - startTime;
            frameCount++;
            if (frameCount == targetFPS)        {
                averageFPS = 1000 / ((totalTime / frameCount) / 1000000);
                frameCount = 0;
                totalTime = 0;
                Log.i("averageFPS",""+averageFPS);

            }



        }
    }

    public void processEvents() {
        if (lastEvent != null||gameView.mEvent != null/*&&
                lastEvent.getAction() == MotionEvent.ACTION_DOWN*/) {
            this.direction = 2;
        }
        lastEvent = null;
        gameView.mEvent=null;
    }


    public void setRunning(boolean isRunning) {
        running = isRunning;
    }

}
