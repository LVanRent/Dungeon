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

    public MainThread(SurfaceHolder surfaceHolder, GameView gameView){

        super();
        this.surfaceHolder = surfaceHolder;
        this.gameView = gameView;

    }
    @Override
    public void run() {
        lastEvent = null;
        direction = 0;
        Hero player = new Hero();
        while (running) {
            canvas = null;

            try {
                this.processEvents();
                canvas = this.surfaceHolder.lockCanvas();
                synchronized(surfaceHolder) {

                   // sleep(1000);
                    this.gameView.update();
                    if (this.gameView.lastevent ==2){
                        if(gameView.lastTouchY>gameView.screenWidht && gameView.screenWidht+gameView.lastTouchX<gameView.screenHeight){
                        if(gameView.lastTouchY-gameView.screenWidht < gameView.lastTouchX && gameView.lastTouchY+gameView.lastTouchX<gameView.screenHeight){
                        direction = 3;
                        }
                        if(gameView.lastTouchY-gameView.screenWidht>gameView.lastTouchX && gameView.lastTouchY+gameView.lastTouchX>gameView.screenHeight){
                            direction=1;
                        }
                        if(gameView.lastTouchY-gameView.screenWidht>gameView.lastTouchX && gameView.lastTouchY+gameView.lastTouchX<gameView.screenHeight){
                            direction=2;
                        }
                        if(gameView.lastTouchY-gameView.screenWidht<gameView.lastTouchX && gameView.lastTouchY+gameView.lastTouchX>gameView.screenHeight){
                            direction=0;
                        }
                    }
                    }

                    this.gameView.lastevent=0;
                    //direction = player.moveCharWall(direction);
                    player.moveChar(direction);
                    direction=-1;
                    player.getCurrentMap().updateVisible(player.getPositionX(),player.getPositionY());
                    player.getCurrentMap().updateExplored(player.getPositionX(),player.getPositionY());
                    this.gameView.draw(canvas,player);

                }
            } catch (Exception e) {} finally {
                if (canvas != null) {
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
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
