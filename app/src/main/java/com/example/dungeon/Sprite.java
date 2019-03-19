package com.example.dungeon;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import static com.example.dungeon.MainThread.canvas;

//
public class Sprite {
    private int positionX;
    private int positionY;
    private int currentCell;
    private Map currentMap;
    private int[][] explored;
    private int hp;
    private Bitmap image;


    public void draw(Canvas canvas){
        canvas.drawBitmap(image,100,100,null);
    }

    public Sprite(Map cMap, Bitmap bmp){
        image = bmp;
        currentMap = cMap;
        positionX = 0;
        positionY = 0;
        while(currentMap.getValMap(positionX,positionY)!=7){
            positionX =currentMap.generator.nextInt(currentMap.getLength()-2)+1;
            positionY =currentMap.generator.nextInt(currentMap.getWidth()-2)+1;

        }
        currentCell = 2;
        currentMap.setMap(positionX, positionY,3);
        explored = new int[cMap.getLength()][cMap.getWidth()];

    }


    //modified to debug, change String into Sprite
    /**
    public Hero nextlevel(Hero cChar){
        currentMap = Map.createMap();
        positionX = 0;
        positionY = 0;
        while(currentMap.getValMap(positionX,positionY)!=7){
            positionX =currentMap.generator.nextInt(currentMap.getLength()-2)+1;
            positionY =currentMap.generator.nextInt(currentMap.getWidth()-2)+1;

        }
        currentCell = 2;
        currentMap.setMap(positionX, positionY,3);
        explored = new int[currentMap.getLength()][currentMap.getWidth()];
        return ;

    }
    */
    public int getCurrentCell() {
        return currentCell;
    }

    public int getHp() {
        return hp;
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

    public void setExplored(int [][] explored){
        this.explored = explored;
    }

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

    public int[][] getExplored() {
        return explored;
    }

    public Map getCurrentMap() {
        return currentMap;
    }

    public void moveChar(int direction)
    {
        if(direction==0)
        {
            int val = currentMap.getValMap(positionX +1, positionY);
            if(currentMap.getValMap(positionX +1, positionY)!=0)

                explored[positionX +1][positionY] = val;
            currentMap.setMap(positionX, positionY, currentCell);
            currentCell = val;
            positionX = positionX +1;
            currentMap.setMap(positionX, positionY,3);
        }
        if(direction==1)
        {
            int val = currentMap.getValMap(positionX, positionY + 1);
            if (currentMap.getValMap(positionX, positionY + 1) != 0)
                explored[positionX][positionY + 1] =val;
            currentMap.setMap(positionX, positionY, currentCell);
            currentCell = val;
            positionY = positionY + 1;
            currentMap.setMap(positionX, positionY, 3);
        }
        if(direction==2)
        {
            int val = currentMap.getValMap(positionX -1, positionY);
            if(currentMap.getValMap(positionX -1, positionY)!=0)
                explored[positionX -1][positionY] = val;
            currentMap.setMap(positionX, positionY, currentCell);
            currentCell = val;
            positionX = positionX -1;
            currentMap.setMap(positionX, positionY,3);
        }
        if(direction==3)
        {
            int val = currentMap.getValMap(positionX, positionY - 1);
            if (currentMap.getValMap(positionX, positionY - 1) != 0)
                explored[positionX][positionY - 1] =val;
            currentMap.setMap(positionX, positionY, currentCell);
            currentCell = val;
            positionY = positionY - 1;
            currentMap.setMap(positionX, positionY, 3);
        }


    }


}
