package com.example.dungeon;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

import java.util.Random;

import static com.example.dungeon.MainThread.canvas;

//
public class Character {
    private int positionX;
    private int positionY;
    private int currentCell;
    private Map currentMap;
    private int hp;
    private Bitmap image;
    private int currentLevel;

    public Random generator;


    public void draw(Canvas canvas){
        canvas.drawBitmap(image,100,100,null);
    }


    public Character(long seed){
        generator = new Random(seed);
        currentMap = Map.createMap(generator);
        positionX =currentMap.getEnter()[0];
        positionY =currentMap.getEnter()[1];
        currentCell = 2;
        currentMap.setMap(positionX, positionY,3);

    }
    public Character(){
        generator = new Random();
        currentMap = Map.createMap(generator);
        positionX =currentMap.getEnter()[0];
        positionY =currentMap.getEnter()[1];
        currentCell = 2;
        currentMap.setMap(positionX, positionY,3);

    }

    public Character(Map cMap){

        currentLevel=1;
        currentMap = cMap;
        positionX =cMap.getEnter()[0];
        positionY =cMap.getEnter()[1];
        currentCell = 2;
        currentMap.setMap(positionX, positionY,3);


    }

    public void nextlevel(){
        currentLevel++;
        currentMap = Map.createMap(generator);
        positionX = 0;
        positionY = 0;
        positionX =currentMap.getEnter()[0];
        positionY =currentMap.getEnter()[1];


        currentCell = 2;
        currentMap.setMap(positionX, positionY,3);
        Log.d("end nextlevel",""+currentLevel);
        //explored = new int[currentMap.getLength()][currentMap.getWidth()];
        return ;

    }

    public int getCurrentCell() {
        return currentCell;
    }

    public int getHp() {
        return hp;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }
    public void setPositionY(int positionY){
        this.positionY = positionY;
    }

    public void setCurrentCell(int currentCell)
    {
        this.currentCell = currentCell;
    }

    public void setCurrentMap(Map map)
    { this.currentMap = map;}

   // public void setExplored(int [][] explored){
     //   this.explored = explored;
    //}

    public void setHp (int hp)
    {this.hp = hp;}

    public void setImage (Bitmap image)
    {this.image = image;}

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public Map getCurrentMap() {
        return currentMap;
    }

    public int moveChar(int direction)
    {
        if(direction==0)
        {
            int val = currentMap.getValMap(positionX +1, positionY);
            if(currentMap.getValMap(positionX +1, positionY)!=0&&currentMap.getValMap(positionX +1, positionY)!=6) {
                currentMap.setMap(positionX, positionY, currentCell);
                currentCell = val;
                positionX = positionX + 1;
                currentMap.setMap(positionX, positionY, 3);

            }
            else return 0;
        }
        if(direction==1)
        {
            int val = currentMap.getValMap(positionX, positionY + 1);
            if (currentMap.getValMap(positionX, positionY + 1) != 0&&currentMap.getValMap(positionX +1, positionY)!=6) {
                currentMap.setMap(positionX, positionY, currentCell);
                currentCell = val;
                positionY = positionY + 1;
                currentMap.setMap(positionX, positionY, 3);
            }
            else return 0;
        }
        if(direction==2)
        {
            int val = currentMap.getValMap(positionX -1, positionY);
            if(currentMap.getValMap(positionX -1, positionY)!=0&&currentMap.getValMap(positionX +1, positionY)!=6) {
                currentMap.setMap(positionX, positionY, currentCell);
                currentCell = val;
                positionX = positionX - 1;
                currentMap.setMap(positionX, positionY, 3);
            }
            else return 0;
        }
        if(direction==3)
        {
            int val = currentMap.getValMap(positionX, positionY - 1);
            if (currentMap.getValMap(positionX, positionY - 1) != 0&&currentMap.getValMap(positionX +1, positionY)!=6) {
                currentMap.setMap(positionX, positionY, currentCell);
                currentCell = val;
                positionY = positionY - 1;
                currentMap.setMap(positionX, positionY, 3);
            }
            else return 0;
        }

        if(direction==-1) return 0;
        return 1;




    }public int moveChar(int direction,int evil)
    {
        if(direction==0)
        {
            int val = currentMap.getValMap(positionX +1, positionY);
            if(currentMap.getValMap(positionX +1, positionY)!=0) {
                currentMap.setMap(positionX, positionY, currentCell);
                currentCell = val;
                positionX = positionX + 1;
                currentMap.setMap(positionX, positionY, evil);

            }
            else return 0;
        }
        if(direction==1)
        {
            int val = currentMap.getValMap(positionX, positionY + 1);
            if (currentMap.getValMap(positionX, positionY + 1) != 0) {
                currentMap.setMap(positionX, positionY, currentCell);
                currentCell = val;
                positionY = positionY + 1;
                currentMap.setMap(positionX, positionY, evil);
            }
            else return 0;
        }
        if(direction==2)
        {
            int val = currentMap.getValMap(positionX -1, positionY);
            if(currentMap.getValMap(positionX -1, positionY)!=0) {
                currentMap.setMap(positionX, positionY, currentCell);
                currentCell = val;
                positionX = positionX - 1;
                currentMap.setMap(positionX, positionY, evil);
            }
            else return 0;
        }
        if(direction==3)
        {
            int val = currentMap.getValMap(positionX, positionY - 1);
            if (currentMap.getValMap(positionX, positionY - 1) != 0) {
                currentMap.setMap(positionX, positionY, currentCell);
                currentCell = val;
                positionY = positionY - 1;
                currentMap.setMap(positionX, positionY, evil);
            }
            else return 0;
        }

        if(direction==-1) return 0;
        return 1;




    }
    public int moveCharWall(int direction)
    {
        if(direction==0)
        {
            int val = currentMap.getValMap(positionX +1, positionY);
            if(currentMap.getValMap(positionX +1, positionY)!=0) {
                currentMap.setMap(positionX, positionY, currentCell);
                currentCell = val;
                positionX = positionX + 1;
                currentMap.setMap(positionX, positionY, 3);
            }
            else direction++;
        }
        else if(direction==1)
        {
            int val = currentMap.getValMap(positionX, positionY + 1);
            if (currentMap.getValMap(positionX, positionY + 1) != 0) {
                currentMap.setMap(positionX, positionY, currentCell);
                currentCell = val;
                positionY = positionY + 1;
                currentMap.setMap(positionX, positionY, 3);
            }
            else direction++;
        }
        else if(direction==2)
        {
            int val = currentMap.getValMap(positionX -1, positionY);
            if(currentMap.getValMap(positionX -1, positionY)!=0) {
                currentMap.setMap(positionX, positionY, currentCell);
                currentCell = val;
                positionX = positionX - 1;
                currentMap.setMap(positionX, positionY, 3);
            }
            else direction++;
        }
        else if(direction==3)
        {
            int val = currentMap.getValMap(positionX, positionY - 1);
            if (currentMap.getValMap(positionX, positionY - 1) != 0) {
                currentMap.setMap(positionX, positionY, currentCell);
                currentCell = val;
                positionY = positionY - 1;
                currentMap.setMap(positionX, positionY, 3);
            }
            else direction = 0;
        }
        return direction;



    }


}
