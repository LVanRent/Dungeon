package com.example.dungeon;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceView;
import android.view.SurfaceHolder;

import static com.example.dungeon.MainThread.canvas;


public class GameView  extends SurfaceView implements SurfaceHolder.Callback {
    private MainThread thread;
    final int pixelSize = 40;
    Sprite ash;

    public GameView (Context context){
        super(context);
        getHolder().addCallback(this);

        thread = new MainThread(getHolder(), this);
        setFocusable(true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){

    }

    @Override
    public void surfaceCreated (SurfaceHolder holder){
        thread.setRunning(true);
        thread.start();
        ash = new Sprite(BitmapFactory.decodeResource(getResources(),R.drawable.ash));
        ash.draw(canvas);


    }

    @Override
    public void surfaceDestroyed (SurfaceHolder holder){
        boolean retry = true;

        while (retry){
            try{
                thread.setRunning(false);
                thread.join();
            }
            catch (InterruptedException e){
                e.printStackTrace();
            }
            retry = false;

        }

    }

    public void update (){


    }

    public void draw (Canvas canvas, Sprite current){
        super.draw(canvas);
        if (canvas != null) {
            canvas.drawColor(Color.WHITE);
            Paint player = new Paint();
            player.setColor(Color.rgb(0, 250, 0));
            Paint ground = new Paint();
            ground.setColor(Color.rgb(0, 0, 250));
            Paint stairs = new Paint();
            stairs.setColor(Color.rgb(250, 0, 250));
            Paint wall = new Paint();
            wall.setColor(Color.rgb(0, 0, 0));
           int x = current.getPositionX();
           int y = current.getPositionY();
           int a=0;
           int b=0;
           Map cMap = current.getCurrentMap();


            for(int i=-10;i<10;i++){
                for(int j=-10;j<10;j++){
                    if(x<10){ a=10-x;}
                    if(y<10){b=10-y;}
                    if(x>cMap.getLength()-10){a=x-cMap.getLength();}
                    if(y>cMap.getWidth()-10){b = y-cMap.getWidth();}

                    if(cMap.getValMap((x+i+a),(y+j+b))==3) {
                        canvas.drawRect((i+10)*pixelSize, (j+10)*pixelSize, (i+11)*pixelSize, (j+11)*pixelSize, player);
                    }
                    else if(cMap.getValMap(x+i+a,y+j+b)==2||cMap.getValMap(x+i+a,y+j+b)==1){
                        canvas.drawRect((i+10)*pixelSize, (j+10)*pixelSize, (i+11)*pixelSize, (j+11)*pixelSize, ground);
                    }
                    else if(cMap.getValMap(x+i+a,y+j+b)==4){
                        canvas.drawRect((i+10)*pixelSize, (j+10)*pixelSize, (i+11)*pixelSize, (j+11)*pixelSize, stairs);
                    }
                    else if(cMap.getValMap(x+i+a,y+j+b)==0){
                        canvas.drawRect((i+10)*pixelSize, (j+10)*pixelSize, (i+11)*pixelSize, (j+11)*pixelSize, wall);
                    }

                }
            }

        }

    }


}
