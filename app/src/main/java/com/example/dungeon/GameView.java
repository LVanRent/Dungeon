package com.example.dungeon;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceView;
import android.view.SurfaceHolder;

public class GameView  extends SurfaceView implements SurfaceHolder.Callback {

    private MainThread thread;
    final int pixelSize = 15;

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

    public void draw (Canvas canvas, Char current){
        super.draw(canvas);
        if (canvas != null) {
            canvas.drawColor(Color.DKGRAY);
            Paint player = new Paint();
            player.setColor(Color.rgb(0, 250, 0));
            Paint ground = new Paint();
            ground.setColor(Color.rgb(0, 0, 250));
            Paint stairs = new Paint();
            stairs.setColor(Color.rgb(250, 0, 250));
           int x = current.getPositionX();
           int y = current.getPositionY();
           int a=0;
           int b=0;
           Map cMap = current.getCurrentMap();


            for(int i=-10;i<10;i++){
                for(int j=-10;j<10;j++){
                    if(x<10){}
                    if(y<10){}
                    if(x>cMap.getLength()-10){}
                    if(y>cMap.getWidth()-10){}

                    if(cMap.getValMap((x+i+a),(y+j+b))==3) {
                        canvas.drawRect((x+i+a)*pixelSize, (y+j+b)*pixelSize, (x+i+a+1)*pixelSize, (y+j+b+1)*pixelSize, player);
                    }
                    else if(cMap.getValMap(x+i+a,y+j+b)==2||cMap.getValMap(x+i+a,y+j+b)==1){
                        canvas.drawRect((x+i+a)*pixelSize, (y+j+b)*pixelSize, (x+i+1+a)*pixelSize, (y+j+1+b)*pixelSize, ground);
                    }
                    else if(cMap.getValMap(x+i+a,y+j+b)==4){
                        canvas.drawRect((x+i+a)*pixelSize, (y+j+b)*pixelSize, (x+i+1+a)*pixelSize, (y+j+1+b)*pixelSize, stairs);
                    }
                }
            }

        }

    }


}
