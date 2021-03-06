package com.example.dungeon;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import java.util.Random;

class Hero extends Character {
    private String name;
    public Random mobGen;
    public Mob firstMob;
    public int mobAmount;
    public long currentSeed;




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
            return a;
           /* if(super.getCurrentLevel()<4) {
                Log.d("level", "" + super.getCurrentLevel());
                hunger = 550;
                //nextlevel();
                Log.d("level added", "" + super.getCurrentLevel());
                return -1;
            }*/

        }

        return -1;
    }


    public void attack(Mob cMob){

        if(((getPositionX()+1 == cMob.getPositionX() && getPositionY()==cMob.getPositionY())&&dirChar==0)||
                ((getPositionX()-1 == cMob.getPositionX() && getPositionY()==cMob.getPositionY())&&dirChar==2)||
                ((getPositionX() == cMob.getPositionX() && getPositionY()+1==cMob.getPositionY())&&dirChar==1)||
                ((getPositionX() == cMob.getPositionX() && getPositionY()-1==cMob.getPositionY())&&dirChar==3)){
            cMob.setHp(cMob.getHp()-100);
            Log.d("atk x y",""+cMob.getPositionX()+ " "+cMob.getPositionY());
        }
        Log.i("attack mob","");

       if(cMob.nextmob!=null) attack(cMob.nextmob);



    }
    public void spinAttack(){
        int dir = dirChar;
        dirChar = 0;
        attack(firstMob);
        dirChar=1;
        attack(firstMob);
        dirChar =2;
        attack(firstMob);
        dirChar =3;
        attack(firstMob);
        dirChar=dir;
    }
}
