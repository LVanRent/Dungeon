package com.example.dungeon;

import android.util.Log;

import java.util.Random;


/*
opposing characters
 */
public class Mob extends Character {
    public Mob nextmob;





    public Mob(Map cMap, Mob cMob, Random mobGen){
        setHp(100);
        nextmob=cMob;
        setPositionX(mobGen.nextInt(cMap.getWidth()));
        setPositionY(mobGen.nextInt(cMap.getLength()));

        Log.i("try mob at","x="+getPositionX()+", y="+getPositionY()+" c="+(cMap.getValMap(getPositionX(),getPositionY())));
        setCurrentCell(cMap.getValMap(getPositionX(),getPositionY()));

        while (getCurrentCell()!=2) {
            setPositionX(mobGen.nextInt(cMap.getWidth()));
            setPositionY(mobGen.nextInt(cMap.getLength()));

            Log.i("try mob at","x="+getPositionX()+", y="+getPositionY()+" c="+(cMap.getValMap(getPositionX(),getPositionY())));
            setCurrentCell(cMap.getValMap(getPositionX(),getPositionY()));

        }
        cMap.setMap(getPositionX(),getPositionY(),6);
        Log.i("newmob at","x="+getPositionX()+", y="+getPositionY()+" c="+getCurrentCell());


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
        Log.d("Mobseeplayer","before if");
        if(getCurrentMap().isSeen(getPositionX(),getPositionY(),player.getPositionX(),player.getPositionY())) {
            Log.d("Mobseeplayer","player seen");
            int diffX = getPositionX() - player.getPositionX();
            int diffY = getPositionY() - player.getPositionY();
            if (diffX + diffY != 1 && diffX + diffY != -1 && (diffX != 0 && diffX != 1 && diffX != -1)) {
                if (diffX >= diffY && diffX + diffY >= 0) moveMob(0);
                if (diffX < diffY && diffX + diffY >= 0) moveMob(1);
                if (diffX >= diffY && diffX + diffY < 0) moveMob(2);
                if (diffX < diffY && diffX + diffY < 0) moveMob(3);
            } else {
                player.setHp(getHp() - 20);
            }
            Log.d("Mobposition","x="+getPositionX()+" y="+getPositionY());
        }

    }

    public int moveMob(int direction){
        Log.d("6 move","");
        if(direction==0)
        {
            int val = getCurrentMap().getValMap(getPositionX() +1, getPositionY());
            if(getCurrentMap().getValMap(getPositionX() +1, getPositionY())!=0) {
                getCurrentMap().setMap(getPositionX(), getPositionY(), getCurrentCell());
                setCurrentCell(val);
                setPositionX(getPositionX() + 1);
                getCurrentMap().setMap(getPositionX(), getPositionY(), 6);

            }
            else return 0;
        }
        if(direction==1)
        {
            int val = getCurrentMap().getValMap(getPositionX(), getPositionY() + 1);
            if (getCurrentMap().getValMap(getPositionX(), getPositionY() + 1) != 0) {
                getCurrentMap().setMap(getPositionX(), getPositionY(), getCurrentCell());
                setCurrentCell(val);
                setPositionY(getPositionY() + 1);
                getCurrentMap().setMap(getPositionX(), getPositionY(), 6);
            }
            else return 0;
        }
        if(direction==2)
        {
            int val = getCurrentMap().getValMap(getPositionX() -1, getPositionY());
            if(getCurrentMap().getValMap(getPositionX() -1, getPositionY())!=0) {
                getCurrentMap().setMap(getPositionX(), getPositionY(), getCurrentCell());
                setCurrentCell(val);
                setPositionX(getPositionX() - 1);
                getCurrentMap().setMap(getPositionX(), getPositionY(), 6);
            }
            else return 0;
        }
        if(direction==3)
        {
            int val = getCurrentMap().getValMap(getPositionX(), getPositionY() - 1);
            if (getCurrentMap().getValMap(getPositionX(), getPositionY() - 1) != 0) {
                getCurrentMap().setMap(getPositionX(), getPositionY(), getCurrentCell());
                setCurrentCell(val);
                setPositionY(getPositionY() - 1);
                getCurrentMap().setMap(getPositionX(), getPositionY(), 6);
            }
            else return 0;
        }

        if(direction==-1) return 0;
        return 1;


    }



}
