package com.example.dungeon;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.view.SurfaceView;
import android.view.SurfaceHolder;
import android.view.MotionEvent;
import android.util.Log;

import static java.lang.Math.min;
import static java.lang.Thread.sleep;

/*
gestion of the screen while in game
 */
public class GameView  extends SurfaceView implements SurfaceHolder.Callback {
    private MainThread thread;
    public int pixelSize = 40;
    public MotionEvent mEvent;
    public int lastevent;
    public float lastTouchX;
    public float lastTouchY;
    public int screenWidht;
    public int screenHeight;
    public int flagEnd =1;
    public Context context;
    public Intent intent;
    public int didAttack;
    public boolean rotateFlag=false;
    public boolean map=false;

    public final static int wallOnMap = 0;
    public final static int pathOnMap = 1;
    public final static int roomOnMap = 2;
    public final static int playerOnMap = 3;
    public final static int stairOnMap = 4;
    public final static int foodOnMap = 5;
    public final static int mobOnMap = 6;
    public Bitmap zelda_menu;


    public GameView (Context context){
        super(context);
        getHolder().addCallback(this);


        thread = new MainThread(getHolder(), this);
        setFocusable(true);
    }
    public GameView (Context context,int widht,int height,Intent gameOver){
        super(context);
        getHolder().addCallback(this);
        screenHeight=height;
        screenWidht=widht;
        Log.i("height is", ""+height);
        Log.i("width is", ""+widht);
        pixelSize = min(height,widht)/20;
        this.context=context;
        intent = gameOver;

        thread = new MainThread(getHolder(), this);
        setFocusable(true);

        zelda_menu = BitmapFactory.decodeResource(getResources(), R.drawable.zelda_menu);
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
                (context).startActivity(intent);



            }
            catch (InterruptedException e){
                e.printStackTrace();
            }
            retry = false;

        }

    }

    public void update(){}


    public void update (Hero player, Canvas canvas){
        if ((lastevent==1||lastevent==2)&&lastTouchX>screenWidht-2*pixelSize &&lastTouchY<2*pixelSize){
            map=!map;
            Log.i("map",""+map);}
        if(!map){

            while(player.mobAmount*2 < player.getCurrentLevel()+1){
                player.firstMob = new Mob(player.getCurrentMap(),player.firstMob,player.mobGen);
                player.mobAmount++;
            }

            int direction = -1;
            if (this.lastevent ==2||lastevent==1) {
                if ((lastTouchY > screenWidht+(screenHeight-screenWidht)/3)
                        && (lastTouchY<(screenWidht+2*(screenHeight-screenWidht)/3))
                        && (lastTouchX>((screenHeight-screenWidht)/3))
                        && (lastTouchX<(2*(screenHeight-screenWidht)/3))) {
                    rotateFlag = !rotateFlag;
                }
                else {
                    if (lastTouchY > screenWidht && screenWidht + lastTouchX < screenHeight) {
                        if (lastTouchY - screenWidht < lastTouchX && lastTouchY + lastTouchX < screenHeight) {
                            direction = 3;
                        }
                        if (lastTouchY - screenWidht > lastTouchX && lastTouchY + lastTouchX > screenHeight) {
                            direction = 1;
                        }
                        if (lastTouchY - screenWidht > lastTouchX && lastTouchY + lastTouchX < screenHeight) {
                            direction = 2;
                        }
                        if (lastTouchY - screenWidht < lastTouchX && lastTouchY + lastTouchX > screenHeight) {
                            direction = 0;
                        }
                    }
                    if (lastTouchX > screenHeight - screenWidht && lastTouchY > screenWidht) {

                        Log.d("attack", "1");
                        player.attack(player.firstMob);
                        didAttack = 3;
                        Log.d("attack", "2");

                    }
                }
            }
            int a=0;

            //direction = player.moveCharWall(direction);
            if(rotateFlag){ player.orientChar(direction);}
            else a=player.moveChar(direction);
            if ((a>0)||didAttack==3){

                Log.d("direction",""+direction+" "+didAttack);

                player.firstMob.mobGestion(player,null);
            }


            player.getCurrentMap().updateVisible(player.getPositionX(),player.getPositionY());
            player.getCurrentMap().updateExplored(player.getPositionX(),player.getPositionY());
            this.draw(canvas,player);
            if (player.getHp()<=0){
                //running = false;
                (context).startActivity(intent);
            }
        }
        else{
            this.draw(canvas,player);


        }

        this.lastevent=0;

    }
    @Override
    public boolean onTouchEvent(MotionEvent me) {

        lastevent = me.getAction();
        lastTouchX = me.getX();
        lastTouchY = me.getY();
        Log.i("action x y",""+lastevent+" "+lastTouchX+" "+lastTouchY);
/*
        Log.v("MotionEvent", "Action = " + action);
        Log.v("MotionEvent", "X = " + currentXPosition + "Y = " + currentYPosition);
*/





        return true;
    }

    public void drawTriangle(Canvas canvas, Paint paint, float x, float y, int width,int direction) {
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
        canvas.drawColor(Color.WHITE);
        if (canvas != null) {
            Paint maper = new Paint();
            maper.setColor(Color.YELLOW);



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
            Paint food = new Paint();
            food.setColor(Color.rgb(200, 200, 70));
            Paint darkfood = new Paint();
            darkfood.setColor(Color.rgb(100, 100, 35));
            Paint fog = new Paint();
            fog.setColor(Color.rgb(0, 0, 0));
            Paint command = new Paint();
            command.setColor(Color.rgb(0,0,0));
            Paint attack = new Paint();
            attack.setColor(Color.rgb(180,180,250));
            Paint mob = new Paint();
            mob.setColor(Color.rgb(200,0,0));

            Paint hp = new Paint();
            hp.setColor(Color.rgb(200,0,0));
            Paint hunger = new Paint();
            hunger.setColor(Color.rgb(0,200,0));
            int x = current.getPositionX();
            int y = current.getPositionY();
            Map cMap = current.getCurrentMap();
            float a=0;
            float b=0;
            int pi=0;
            int pj=0;

            if(map){

                int pixelSize = (screenWidht/cMap.getWidth());
                Log.d("try map","start");
                Log.d("try map","sW= "+screenWidht+" mW= "+cMap.getWidth()+"pS= "+pixelSize);

//                canvas.drawRect(0,0,screenWidht,screenWidht,fog);


                for(int i=0;i<cMap.getLength();i++){
                    for (int j=0;j<cMap.getWidth();j++){
                       // Log.d("tryMap",""+cMap.getValExplored(i,j));
                        if(cMap.getValExplored(i,j)==1){
                            //Log.d("tryMap",""+cMap.getValExplored(i,j)+" "+i+" "+j+" "+cMap.getValMap(i,j));
                            if(cMap.getValMap(i,j)==wallOnMap)
                                canvas.drawRect(i*pixelSize,j*pixelSize,(i*pixelSize)+pixelSize,(j*pixelSize)+pixelSize,wall);
                            if(cMap.getValMap(i,j)==pathOnMap)
                                canvas.drawRect(i*pixelSize,j*pixelSize,i*pixelSize+pixelSize,j*pixelSize+pixelSize,ground);
                            if(cMap.getValMap(i,j)==roomOnMap) canvas.drawRect(i*pixelSize,j*pixelSize,i*pixelSize+pixelSize,j*pixelSize+pixelSize,ground);
                            if(cMap.getValMap(i,j)==playerOnMap) canvas.drawRect(i*pixelSize,j*pixelSize,i*pixelSize+pixelSize,j*pixelSize+pixelSize,player);
                            if(cMap.getValMap(i,j)==stairOnMap) canvas.drawRect(i*pixelSize,j*pixelSize,i*pixelSize+pixelSize,j*pixelSize+pixelSize,stairs);
                            if(cMap.getValMap(i,j)==foodOnMap) canvas.drawRect(i*pixelSize,j*pixelSize,i*pixelSize+pixelSize,j*pixelSize+pixelSize,food);
                            if(cMap.getValMap(i,j)==mobOnMap){
                                if(cMap.getValVisible(i,j)==1)canvas.drawRect(i*pixelSize,j*pixelSize,i*pixelSize+pixelSize,j*pixelSize+pixelSize,mob);
                                else {canvas.drawRect(i*pixelSize,j*pixelSize,i*pixelSize+pixelSize,j*pixelSize+pixelSize,ground);
                                }

                            }




                        }
                        else{
                            canvas.drawRect(i*pixelSize,j*pixelSize,(i*pixelSize)+pixelSize,(j*pixelSize)+pixelSize,fog);

                        }
                    }

                }
                Log.d("try map","map should be here"+pixelSize);


            }
            else{


                for(int i=-10;i<10;i++) {
                    for (int j = -10; j < 10; j++) {

                        if (x + i < 0 || x + i >= cMap.getLength() || y + j < 0 || y + j >= cMap.getWidth()) {
                            canvas.drawRect((i + 10) * pixelSize, (j + 10) * pixelSize, (i + 11) * pixelSize, (j + 11) * pixelSize, fog);
                        } else {
                            if (cMap.getValVisible((x + i), (y + j)) == 1) {
                                if (cMap.getValMap(x + i, y + j) == roomOnMap || cMap.getValMap(x + i, y + j) == pathOnMap) {
                                    canvas.drawRect((i + 10) * pixelSize, (j + 10) * pixelSize, (i + 11) * pixelSize, (j + 11) * pixelSize, ground);
                                } else if (cMap.getValMap(x + i, y + j) == stairOnMap) {
                                    canvas.drawRect((i + 10) * pixelSize, (j + 10) * pixelSize, (i + 11) * pixelSize, (j + 11) * pixelSize, stairs);
                                } else if (cMap.getValMap(x + i, y + j) == wallOnMap) {
                                    canvas.drawRect((i + 10) * pixelSize, (j + 10) * pixelSize, (i + 11) * pixelSize, (j + 11) * pixelSize, wall);
                                } else if (cMap.getValMap(x + i, y + j) == foodOnMap) {
                                    canvas.drawRect((i + 10) * pixelSize, (j + 10) * pixelSize, (i + 11) * pixelSize, (j + 11) * pixelSize, food);
                                } else if (cMap.getValMap(x + i, y + j) == mobOnMap) {
                                    canvas.drawRect((i + 10) * pixelSize, (j + 10) * pixelSize, (i + 11) * pixelSize, (j + 11) * pixelSize, mob);
                                } else if (cMap.getValMap(x + i, y + j) == playerOnMap && didAttack > 0) {
                                    a = (float) (i + 10.5) * pixelSize;
                                    b = (float) (j + 10.5) * pixelSize;

                                } else if (cMap.getValMap((x + i), (y + j)) == playerOnMap) {
                                    pi=i;
                                    pj=j;
                                }

                            }
                            if (cMap.getValVisible((x + i), (y + j)) == 0) {
                                canvas.drawRect((i + 10) * pixelSize, (j + 10) * pixelSize, (i + 11) * pixelSize, (j + 11) * pixelSize, fog);
                            }
                            if (cMap.getValVisible((x + i), (y + j)) == 0 && cMap.getValExplored((x + i), (y + j)) == 1) {
                                if (cMap.getValMap(x + i, y + j) == roomOnMap || cMap.getValMap(x + i, y + j) == pathOnMap|| cMap.getValMap(x + i, y + j) ==mobOnMap) {
                                    canvas.drawRect((i + 10) * pixelSize, (j + 10) * pixelSize, (i + 11) * pixelSize, (j + 11) * pixelSize, darkground);
                                } else if (cMap.getValMap(x + i, y + j) == stairOnMap) {
                                    canvas.drawRect((i + 10) * pixelSize, (j + 10) * pixelSize, (i + 11) * pixelSize, (j + 11) * pixelSize, darkstairs);
                                } else if (cMap.getValMap(x + i, y + j) == wallOnMap) {
                                    canvas.drawRect((i + 10) * pixelSize, (j + 10) * pixelSize, (i + 11) * pixelSize, (j + 11) * pixelSize, darkwall);
                                } else if (cMap.getValMap(x + i, y + j) == foodOnMap) {
                                    canvas.drawRect((i + 10) * pixelSize, (j + 10) * pixelSize, (i + 11) * pixelSize, (j + 11) * pixelSize, darkfood);
                                }
                            }
                        }
                    }
                }
                if (didAttack>0) {
                    if(current.dirChar==0){a=a+(float)(pixelSize/2);}
                    if(current.dirChar==1){b=b+(float)(pixelSize/2);}
                    if(current.dirChar==2){a=a-(float)(pixelSize/2);}
                    if(current.dirChar==3){b=b-(float)(pixelSize/2);}

                    canvas.drawCircle(a, b, (float)(pixelSize/2), attack);
                    didAttack--;
                }
                canvas.drawRect((pi + 10) * pixelSize, (pj + 10) * pixelSize, (pi + 11) * pixelSize, (pj + 11) * pixelSize, player);
                if (current.dirChar == 0)
                    canvas.drawRect((float) ((pi + 10.8) * pixelSize), (float) ((pj + 10.4) * pixelSize), (float) ((pi + 11) * pixelSize), (float) ((pj + 10.6) * pixelSize), ground);
                if (current.dirChar == 1)
                    canvas.drawRect((float) ((pi + 10.4) * pixelSize), (float) ((pj + 10.8) * pixelSize), (float) ((pi + 10.6) * pixelSize), (float) ((pj + 11) * pixelSize), ground);
                if (current.dirChar == 2)
                    canvas.drawRect((float) ((pi + 10) * pixelSize), (float) ((pj + 10.4) * pixelSize), (float) ((pi + 10.2) * pixelSize), (float) ((pj + 10.6) * pixelSize), ground);
                if (current.dirChar == 3)
                    canvas.drawRect((float) ((pi + 10.4) * pixelSize), (float) ((pj + 10) * pixelSize), (float) ((pi + 10.6) * pixelSize), (float) ((pj + 10.2) * pixelSize), ground);
                Paint arrow = new Paint();

                if(rotateFlag)arrow.setColor(Color.BLUE);
                else arrow.setColor(Color.BLACK);

                canvas.drawRect(screenHeight-screenWidht,screenWidht,screenHeight-screenWidht+pixelSize/(float)5,screenHeight,command);

                canvas.drawRect((screenHeight-screenWidht)/(float)2,screenWidht,(screenHeight-screenWidht)/(float)2+pixelSize/(float)5,screenHeight,command);

                canvas.drawRect(0,(screenHeight+screenWidht)/(float)2,screenHeight-screenWidht,(screenHeight+screenWidht+pixelSize/(float)5)/2,arrow);



                //up
                drawTriangle(canvas, arrow, (screenHeight - screenWidht)/(float)2, screenWidht+(screenHeight-screenWidht)/(float)8, (screenHeight-screenWidht)/4, 3);

                //down
                drawTriangle(canvas, arrow, (screenHeight - screenWidht)/(float)2, screenHeight-(screenHeight-screenWidht)/(float)8, (screenHeight-screenWidht)/4, 1);

                //left
                drawTriangle(canvas, arrow, (screenHeight-screenWidht)/(float)8, (screenHeight+screenWidht)/(float)2, (screenHeight-screenWidht)/4, 2);
                //right
                drawTriangle(canvas, arrow,  (screenHeight-screenWidht)-(screenHeight-screenWidht)/(float)8,(float)(screenHeight+screenWidht)/2, (screenHeight-screenWidht)/4, 0);

                //rotate
                canvas.drawCircle((float)((screenHeight-screenWidht)/2),(float)((screenHeight+screenWidht)/2),(float)((screenHeight-screenWidht)/6),arrow);

                // Caracteristique
                fog.setTextSize( 6*pixelSize/(float)10);
                fog.setColor(Color.BLACK);
                canvas.drawText("Hp : "+current.getHp() +"/100", screenHeight-screenWidht+pixelSize/(float)2,screenWidht+ (screenHeight-screenWidht)/(float)16 ,fog);
                canvas.drawRect(screenHeight-screenWidht+pixelSize/(float)2,screenWidht+(float)1.5*(screenHeight-screenWidht)/(float)16, screenHeight-screenWidht+(pixelSize/(float)2) + ((-screenHeight+2*screenWidht-pixelSize)*(current.getHp())/(float)100),screenWidht+(float)2.5*(screenHeight-screenWidht)/(float)16,hp);
                canvas.drawText("Hg : "+current.getHunger() +"/100", screenHeight-screenWidht+pixelSize/(float)2,screenWidht+(float) 3.5*(screenHeight-screenWidht)/(float)16  ,fog);
                canvas.drawRect(screenHeight-screenWidht+pixelSize/(float)2,screenWidht+(float)4*(screenHeight-screenWidht)/(float)16,screenHeight-screenWidht+(pixelSize/(float)2) + ((-screenHeight+2*screenWidht-pixelSize)*(current.getHunger())/(float)100),screenWidht+(float)5*(screenHeight-screenWidht)/(float)16,hunger);
                canvas.drawText("lvl : "+current.getCurrentLevel(), screenHeight-screenWidht+pixelSize/(float)2,screenWidht+(float)6*(screenHeight-screenWidht)/(float)16 ,fog);

                canvas.drawRect(screenHeight-screenWidht,screenWidht+(float)6.5*(screenHeight-screenWidht)/(float)16,screenWidht,screenWidht+(float)7*(screenHeight-screenWidht)/(float)16,command);

                Paint Sword = new Paint();
                Sword.setColor(Color.BLUE);
                drawTriangle(canvas, Sword, (screenHeight - screenWidht)+(float) 2*(2*screenWidht - screenHeight)/(float) 4, screenWidht+(float)9.5*(screenHeight-screenWidht)/(float)16, (screenHeight-screenWidht)/4, 3);
                drawTriangle(canvas, Sword, (screenHeight - screenWidht)+(float) 2*(2*screenWidht - screenHeight)/(float) 4, screenWidht+(float)13.5*(screenHeight-screenWidht)/(float)16, (screenHeight-screenWidht)/4, 1);



            }
            int itmSz=126;

            canvas.drawRect(screenWidht-2*pixelSize,0,screenWidht,2*pixelSize,maper);


             //       int posX = 168+itmSz;
             //       int posY = 4450+itmSz;
             //       Rect mapPlace = new Rect(screenWidht-2*pixelSize,0,screenWidht,2*pixelSize);



              //      Rect mapSRC = new Rect(posX, posY, posX + itmSz, posY + itmSz);
              //      canvas.drawBitmap(zelda_menu, mapSRC, mapPlace, null);


        }

    }


}
