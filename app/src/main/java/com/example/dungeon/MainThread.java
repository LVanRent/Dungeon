package com.example.dungeon;
import android.view.SurfaceHolder;
import android.graphics.Canvas;
import android.view.MotionEvent;

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
        Map cMap = Map.createMap();
        Char player = new Char(cMap);
        while (running) {
            canvas = null;

            try {
                this.processEvents();
                canvas = this.surfaceHolder.lockCanvas();
                synchronized(surfaceHolder) {

                   // sleep(1000);
                    this.gameView.update();
                    if (this.gameView.lastevent ==2){
                        if(gameView.lastTouchX < gameView.lastTouchY && gameView.lastTouchX+gameView.lastTouchY<min(gameView.screenHeight,gameView.screenWidht)){
                        direction = 2;
                        }
                        if(gameView.lastTouchX>gameView.lastTouchY && gameView.lastTouchX+gameView.lastTouchY>min(gameView.screenHeight,gameView.screenWidht)){
                            direction=0;
                        }
                        if(gameView.lastTouchX>gameView.lastTouchY && gameView.lastTouchX+gameView.lastTouchY<min(gameView.screenHeight,gameView.screenWidht)){
                            direction=3;
                        }
                        if(gameView.lastTouchX<gameView.lastTouchY && gameView.lastTouchX+gameView.lastTouchY>min(gameView.screenHeight,gameView.screenWidht)){
                            direction=1;
                        }
                    }

                    this.gameView.lastevent=0;
                    //direction = player.moveCharWall(direction);
                    player.moveChar(direction);
                    direction=-1;
                    player.getCurrentMap().updateVisible(player.getPositionX(),player.getPositionY());
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
