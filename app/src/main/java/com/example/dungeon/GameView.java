package com.example.dungeon;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceView;
import android.view.SurfaceHolder;

public class GameView  extends SurfaceView implements SurfaceHolder.Callback {

    private MainThread thread;

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
            canvas.drawColor(Color.BLACK);
            Paint player = new Paint();
            player.setColor(Color.rgb(0, 250, 0));
            Paint ground = new Paint();
            ground.setColor(Color.rgb(0, 0, 250));
            Paint stairs = new Paint();
            stairs.setColor(Color.rgb(0, 250, 250));
           int x = current.getPositionX();
           int y = current.getPositionY();
           Map cMap = current.getCurrentMap();


            for(int i=-10;i<10;i++){
                for(int j=-10;j<10;j++){
                    if(cMap.getValMap((x+i),(y+j))==3) {
                        canvas.drawRect((x+i)*100, (y+j)*100, (x+i+1)*100, (y+j+1)*100, player);
                    }
                    else if(cMap.getValMap(x+i,y+j)==2||cMap.getValMap(x+i,y+j)==1){
                        canvas.drawRect((x+i)*100, (y+j)*100, (x+i+1)*100, (y+j+1)*100, ground);
                    }
                    else if(cMap.getValMap(x+i,y+j)==4){
                        canvas.drawRect((x+i)*100, (y+j)*100, (x+i+1)*100, (y+j+1)*100, stairs);
                    }
                }
            }

        }

    }


}
