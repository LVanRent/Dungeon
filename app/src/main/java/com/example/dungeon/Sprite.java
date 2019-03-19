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
    private int hp;
    private Bitmap image;


    public void draw(Canvas canvas){
        canvas.drawBitmap(image,100,100,null);
    }


    public Sprite(Bitmap bmp){
        image = bmp;

        currentMap = Map.createMap();
        positionX =currentMap.getEnter()[0];
        positionY =currentMap.getEnter()[1];
        currentCell = 2;
        currentMap.setMap(positionX, positionY,3);

    }

    public Sprite(Map cMap, Bitmap bmp){
        image = bmp;
        currentMap = cMap;
        positionX =cMap.getEnter()[0];
        positionY =cMap.getEnter()[1];
        currentCell = 2;
        currentMap.setMap(positionX, positionY,3);

    }

    public void nextlevel(Sprite cChar){
        currentMap = Map.createMap();
        positionX = 0;
        positionY = 0;
        positionX =currentMap.getEnter()[0];
        positionY =currentMap.getEnter()[1];


        currentCell = 2;
        currentMap.setMap(positionX, positionY,3);
        //explored = new int[currentMap.getLength()][currentMap.getWidth()];
        return ;

    }

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

    public void moveChar(int direction)
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
        }
        if(direction==1)
        {
            int val = currentMap.getValMap(positionX, positionY + 1);
            if (currentMap.getValMap(positionX, positionY + 1) != 0) {
                currentMap.setMap(positionX, positionY, currentCell);
                currentCell = val;
                positionY = positionY + 1;
                currentMap.setMap(positionX, positionY, 3);
            }
        }
        if(direction==2)
        {
            int val = currentMap.getValMap(positionX -1, positionY);
            if(currentMap.getValMap(positionX -1, positionY)!=0) {
                currentMap.setMap(positionX, positionY, currentCell);
                currentCell = val;
                positionX = positionX - 1;
                currentMap.setMap(positionX, positionY, 3);
            }
        }
        if(direction==3)
        {
            int val = currentMap.getValMap(positionX, positionY - 1);
            if (currentMap.getValMap(positionX, positionY - 1) != 0) {
                currentMap.setMap(positionX, positionY, currentCell);
                currentCell = val;
                positionY = positionY - 1;
                currentMap.setMap(positionX, positionY, 3);
            }
        }



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
