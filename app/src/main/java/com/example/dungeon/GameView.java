package com.example.dungeon;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.view.Display;
import android.view.SurfaceView;
import android.view.SurfaceHolder;
import android.view.MotionEvent;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import static java.lang.Math.min;
import static com.example.dungeon.MainThread.canvas;
public class GameView  extends SurfaceView implements SurfaceHolder.Callback {
    private MainThread thread;
  //  final int pixelSize = 40;
    Sprite ash;
    public int pixelSize = 40;
    public MotionEvent mEvent;
    public int lastevent;
    public float lastTouchX;
    public float lastTouchY;
    public int screenWidht;
    public int screenHeight;

    public GameView (Context context){
        super(context);
        getHolder().addCallback(this);



        thread = new MainThread(getHolder(), this);
        setFocusable(true);
    }
    public GameView (Context context,int widht,int height){
        super(context);
        getHolder().addCallback(this);
        screenHeight=height;
        screenWidht=widht;
        Log.i("height is", ""+height);
        Log.i("width is", ""+widht);
        pixelSize = min(height,widht)/20;

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
        //ash = new Sprite(BitmapFactory.decodeResource(getResources(),R.drawable.ash));
        //ash.draw(canvas);


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
    @Override
    public boolean onTouchEvent(MotionEvent me) {

        int action = me.getAction();
        lastTouchX = me.getX();
        lastTouchY = me.getY();
        Log.i("action x y",""+action+" "+lastTouchX+" "+lastTouchY);
/*
        Log.v("MotionEvent", "Action = " + action);
        Log.v("MotionEvent", "X = " + currentXPosition + "Y = " + currentYPosition);
*/
        if (action == MotionEvent.ACTION_MOVE) {
            lastevent = 1;
        }
        if (action == MotionEvent.ACTION_UP) {
            lastevent =0;
        }
        if (action != 0) {
            lastevent =2;
        }




        return true;
    }

    public void drawTriangle(Canvas canvas, Paint paint, int x, int y, int width,int direction) {
        int halfWidth = width / 2;
        int rotate = 1 ;
        int beta = 1 ;
        int gamma = 0 ;
        int alpha = 1 ;
        if ( direction == 1  ) {
            rotate = -1 ;
        }

        if ( direction == 2 ) {
            beta = -1 ;
            gamma = 1 ;
        }

        if ( direction == 0) {
            beta = -1 ;
            gamma = 1 ;
            rotate  = -1 ;
            alpha = -1 ;
        }

        Path path = new Path();

        // anti horlogique
        path.moveTo(x - halfWidth * gamma * alpha , y - rotate *halfWidth * (1-gamma)); // Top ^_ or Left <|
        path.lineTo(x - rotate  * beta * halfWidth , y + rotate * halfWidth );
        path.lineTo(x + rotate  * halfWidth, y + rotate * beta * halfWidth ) ;
        path.lineTo(x - halfWidth * gamma * alpha , y - rotate  * halfWidth * (1-gamma) ); // Back to first point
        path.close();

        canvas.drawPath(path, paint);
    }

    public void draw (Canvas canvas, Hero current){
        super.draw(canvas);
        if (canvas != null) {
            canvas.drawColor(Color.WHITE);
            Paint player = new Paint();
            player.setColor(Color.rgb(0, 250, 0));
            Paint ground = new Paint();
            ground.setColor(Color.rgb(120, 68, 150));
            Paint darkground = new Paint();
            darkground.setColor(Color.rgb(60, 34, 75));
            Paint stairs = new Paint();
            stairs.setColor(Color.rgb(250, 0, 250));
            Paint darkstairs = new Paint();
            darkstairs.setColor(Color.rgb(125, 0, 125));
            Paint wall = new Paint();
            wall.setColor(Color.rgb(80, 37, 0));
            Paint darkwall = new Paint();
            darkwall.setColor(Color.rgb(40, 18, 0));
            Paint fog = new Paint();
            fog.setColor(Color.rgb(0, 0, 0));
            Paint command = new Paint();
            command.setColor(Color.rgb(0,0,0));
            Paint hp = new Paint();
            hp.setColor(Color.rgb(200,0,0));
            Paint hunger = new Paint();
            hunger.setColor(Color.rgb(0,200,0));
           int x = current.getPositionX();
           int y = current.getPositionY();
           int a=0;
           int b=0;
           Map cMap = current.getCurrentMap();


            for(int i=-10;i<10;i++) {
                for (int j = -10; j < 10; j++) {
                    if (x < 10) {
                        a = 10 - x;
                    }
                    if (y < 10) {
                        b = 10 - y;
                    }
                    if (x > cMap.getLength() - 10) {
                        a = x - cMap.getLength();
                    }
                    if (y > cMap.getWidth() - 10) {
                        b = y - cMap.getWidth();
                    }

                   if (cMap.getValVisible((x + i + a), (y + j + b)) == 1) {
                        if (cMap.getValMap((x + i + a), (y + j + b)) == 3) {
                            canvas.drawRect((i + 10) * pixelSize, (j + 10) * pixelSize, (i + 11) * pixelSize, (j + 11) * pixelSize, player);
                        } else if (cMap.getValMap(x + i + a, y + j + b) == 2 || cMap.getValMap(x + i + a, y + j + b) == 1) {
                            canvas.drawRect((i + 10) * pixelSize, (j + 10) * pixelSize, (i + 11) * pixelSize, (j + 11) * pixelSize, ground);
                        } else if (cMap.getValMap(x + i + a, y + j + b) == 4) {
                            canvas.drawRect((i + 10) * pixelSize, (j + 10) * pixelSize, (i + 11) * pixelSize, (j + 11) * pixelSize, stairs);
                        } else if (cMap.getValMap(x + i + a, y + j + b) == 0) {
                            canvas.drawRect((i + 10) * pixelSize, (j + 10) * pixelSize, (i + 11) * pixelSize, (j + 11) * pixelSize, wall);
                        }

                    }
                    if (cMap.getValVisible((x + i + a), (y + j + b)) == 0) {
                        canvas.drawRect((i + 10) * pixelSize, (j + 10) * pixelSize, (i + 11) * pixelSize, (j + 11) * pixelSize, fog);
                    }
                    if (cMap.getValVisible((x + i + a), (y + j + b)) == 0 && cMap.getValExplored((x + i + a), (y + j + b)) == 1) {
                        if (cMap.getValMap(x + i + a, y + j + b) == 2 || cMap.getValMap(x + i + a, y + j + b) == 1) {
                            canvas.drawRect((i + 10) * pixelSize, (j + 10) * pixelSize, (i + 11) * pixelSize, (j + 11) * pixelSize, darkground);
                        } else if (cMap.getValMap(x + i + a, y + j + b) == 4) {
                            canvas.drawRect((i + 10) * pixelSize, (j + 10) * pixelSize, (i + 11) * pixelSize, (j + 11) * pixelSize, darkstairs);
                        } else if (cMap.getValMap(x + i + a, y + j + b) == 0) {
                            canvas.drawRect((i + 10) * pixelSize, (j + 10) * pixelSize, (i + 11) * pixelSize, (j + 11) * pixelSize, darkwall);
                        }
                    }

                    canvas.drawRect(screenHeight-screenWidht,screenWidht,screenHeight-screenWidht+10,screenHeight,command);

                    canvas.drawRect((screenHeight-screenWidht)/2,screenWidht,(screenHeight-screenWidht)/2+5,screenHeight,command);

                    Paint UP = new Paint();
                    UP.setColor(Color.BLACK);
                    drawTriangle(canvas, UP, (screenHeight - screenWidht)/2, screenWidht+60, 120, 3);

                    Paint DOWN = new Paint();
                    DOWN.setColor(Color.BLACK);
                    drawTriangle(canvas, DOWN, (screenHeight - screenWidht)/2, screenHeight-60, 120, 1);

                    Paint LEFT = new Paint();
                    LEFT.setColor(Color.BLACK);
                    drawTriangle(canvas, LEFT, 60, (screenHeight+screenWidht)/2, 120, 2);

                    Paint RIGHT = new Paint();
                    RIGHT.setColor(Color.BLACK);
                    drawTriangle(canvas, RIGHT,  (screenHeight-screenWidht)-60,(screenHeight+screenWidht)/2, 120, 0);

                    // Caracteristique
                    canvas.drawRect(0,(float) (screenHeight+screenWidht)/2,screenHeight-screenWidht,(float ) (screenHeight+screenWidht+10)/2,command);
                    canvas.drawRect(screenHeight-screenWidht+20,screenWidht+20,screenHeight-screenWidht+20+current.getHp(),screenWidht+40,hp);
                    canvas.drawRect(screenHeight-screenWidht+20,screenWidht+60,screenHeight-screenWidht+20+current.getHunger(),screenWidht+80,hunger);
                    canvas.drawText("lvl"+current.getCurrentLevel(), screenHeight-screenWidht+20,screenWidht+140 ,fog);
                    fog.setColor(Color.BLACK);
                    fog.setTextSize(60);


                }
            }

        }

    }


}
