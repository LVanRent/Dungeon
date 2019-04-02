package com.example.dungeon;

public class Mob extends Character {
    public Mob nextmob;
    private int positionX;
    private int positionY;
    private int currentCell;
    private int hp;



    public Mob(Map cMap,Mob cMob){
        hp=100;
        nextmob=cMob;
        //positionX = ;
       // positionY = ;

    }

    public void mobGestion(Hero cHero){




    }



}
