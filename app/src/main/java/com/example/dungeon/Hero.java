package com.example.dungeon;

import android.graphics.Bitmap;

class Hero extends Sprite {
    private String name;

    private int hunger;


    public Hero( Bitmap bmp) {
        super(bmp);
    }
    public Hero(Map cMap, Bitmap bmp) {
        super(cMap, bmp);
    }

    public int getHunger() {
        return hunger;
    }


    public void Hero (String HeroName){
        name = HeroName;

    }
    public String getName(){
        return this.name;
    }
 }
