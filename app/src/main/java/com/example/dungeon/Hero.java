package com.example.dungeon;

import android.graphics.Bitmap;

class Hero extends Sprite {
    private String name;

    private int hunger;


    public Hero(Bitmap bmp) {
        super(bmp);
    }

    public Hero(Map cMap, Bitmap bmp) {
        super(cMap, bmp);
        super.setHp(100);
        hunger = 100;
        name = "hero";
    }

    public int getHunger() {
        return hunger;
    }


    public void Heroname(String HeroName) {
        name = HeroName;

    }

    public String getName() {
        return this.name;
    }

    @Override
    public int moveChar(int direction) {
        if (getHp() > 0) {
            int a = super.moveChar(direction);
            if (a == 1) {
                if (hunger > 0) {
                    hunger--;
                    if(getHp()<100) setHp(getHp()+1);

                } else {
                    setHp(getHp() - 1);
                }
            }
            if (getCurrentCell()==5){
                hunger = 100;

                setCurrentCell(1);
            }
            return a;
        }
    return -1;
    }
}
