package com.example.dungeon;

import android.util.Log;

import java.util.Random;

public class Mob extends Character {
    public Mob nextmob;





    public Mob(Map cMap, Mob cMob, Random mobGen){
        setHp(100);
        nextmob=cMob;
        while (getCurrentCell()!=2) {
            setPositionX(mobGen.nextInt(cMap.getWidth()));
            setPositionY(mobGen.nextInt(cMap.getLength()));
            setCurrentCell(cMap.getValMap(getPositionX(),getPositionY()));

        }
        cMap.setMap(getPositionX(),getPositionY(),6);
        Log.i("newmob at","x="+getPositionX()+", y="+getPositionY());


    }

    public void mobGestion(Hero player,Mob prevMob){
        if(nextmob !=null){
            mobIsDead(prevMob,player);
            mobSeePlayer(player);
            nextmob.mobGestion(player,this);
        }



    }
    public void mobIsDead(Mob prevMob,Hero player){
        if(getHp()<=0){
            getCurrentMap().setMap(getPositionX(),getPositionY(),getCurrentCell());
            prevMob.nextmob=nextmob;
            player.mobAmount--;

        }

    }
    public void mobSeePlayer(Hero player){
        if(getCurrentMap().isSeen(getPositionX(),getPositionY(),player.getPositionX(),player.getPositionY())) {
            int diffX = getPositionX() - player.getPositionX();
            int diffY = getPositionY() - player.getPositionY();
            if (diffX + diffY != 1 && diffX + diffY != -1 && (diffX != 0 && diffX != 1 && diffX != -1)) {
                if (diffX >= diffY && diffX + diffY >= 0) moveChar(0, 6);
                if (diffX < diffY && diffX + diffY >= 0) moveChar(1, 6);
                if (diffX >= diffY && diffX + diffY < 0) moveChar(2, 6);
                if (diffX < diffY && diffX + diffY < 0) moveChar(3, 6);
            } else {
                player.setHp(getHp() - 20);
            }
        }

    }



}
