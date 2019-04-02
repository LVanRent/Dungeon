package com.example.dungeon;

import android.util.Log;

import java.util.Random;

class Hero extends Character {
    private String name;
    public Random mobGen;
    public Mob firstMob;
    public int mobAmount;




    private int hunger;


    public Hero() {
        super();
        super.setHp(100);
        hunger = 100;
        name = "hero";
        mobGen = new Random(generator.nextLong());

    }
    public Hero(long seed) {
        super(seed);
        super.setHp(100);
        hunger = 100;
        name = "hero";
        mobGen=new Random(generator.nextLong());


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
                    if (getHp() < 100) setHp(getHp() + 1);

                } else {
                    setHp(getHp() - 1);
                }
            }
            if (getCurrentCell() == 5) {
                hunger = 100;

                setCurrentCell(1);
            }
            if (getCurrentCell() == 4) {

                mobAmount=0;
                firstMob=null;
                nextlevel();
                hunger = hunger + 30;
                return 2;
            }
           /* if(super.getCurrentLevel()<4) {
                Log.d("level", "" + super.getCurrentLevel());
                hunger = 550;
                //nextlevel();
                Log.d("level added", "" + super.getCurrentLevel());
                return -1;
            }*/

        }
        else{


        }
        return -1;
    }
    public void attack(Mob cMob){
        Mob lMob = firstMob;
        while(lMob.nextmob!=null){
            if((getPositionX()+1 == cMob.getPositionX() && getPositionY()==cMob.getPositionY())||(getPositionX()-1 == cMob.getPositionX() && getPositionY()==cMob.getPositionY())||(getPositionX() == cMob.getPositionX() && getPositionY()+1==cMob.getPositionY())||(getPositionX()+1 == cMob.getPositionX() && getPositionY()-1==cMob.getPositionY())){
                lMob.setHp(getHp()-100);
            }
        }



    }
}
