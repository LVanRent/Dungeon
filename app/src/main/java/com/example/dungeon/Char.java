package com.example.dungeon;
import java.util.Random;

//
public class Char{
    private int positionX;
    private int positionY;
    private int currentCell;
    private Map currentMap;
    private int[][] explored;
    private int hp;
    private int hunger;

    public Char(Map cMap){
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

    public Char nextlevel(Char cChar){
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


    }

    public int getCurrentCell() {
        return currentCell;
    }

    public int getHp() {
        return hp;
    }

    public int getHunger() {
        return hunger;
    }

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
