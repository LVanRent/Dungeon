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
        Log.d("mobgestion","test"+(nextmob !=null));

        int a=mobIsDead(prevMob,player);
        if(a==1) mobSeePlayer(player);
        if(nextmob !=null){
            nextmob.mobGestion(player,this);
        }



    }
    public int mobIsDead(Mob prevMob,Hero player){
        if(getHp()<=0){
            int a = getCurrentCell();
            if ((a== 1||a==2) && player.mobGen.nextInt(3)==2) a=5;
            player.getCurrentMap().setMap(getPositionX(),getPositionY(),a);
            if(prevMob==null)player.firstMob = nextmob;
            else prevMob.nextmob=nextmob;
            player.mobAmount--;
            return 0;
        }
        return 1;

    }
    public void mobSeePlayer(Hero player){
        Log.d("Mobseeplayer","before if" + player.getCurrentMap().getValVisible(getPositionX(), getPositionY()));
        if(player.getCurrentMap().getValVisible(getPositionX(), getPositionY())==1) {
            Log.d("Mobseeplayer","player seen");
            int diffX = getPositionX() - player.getPositionX();
            int diffY = getPositionY() - player.getPositionY();
            Log.i("diff pm"," x "+diffX+" y "+diffY);
            if ((diffX == 0 && (diffY== 1 || diffY == -1))||(diffY == 0 && (diffX== 1 || diffX == -1))) {

                player.setHp(player.getHp() - 20);
            }else{
                if (diffX >= diffY && diffX + diffY >= 0) moveMob(2,player); //←
                if (diffX < diffY && diffX + diffY >= 0) moveMob(3,player); //↑
                if (diffX >= diffY && diffX + diffY < 0) moveMob(1,player); //↓
                if (diffX < diffY && diffX + diffY < 0) moveMob(0,player); //→

            }
            Log.d("Mobposition","x="+getPositionX()+" y="+getPositionY());
        }

    }

    public int moveMob(int direction,Hero player){
        Log.d("6 move","");
        if(direction==0)
        {
            int val = player.getCurrentMap().getValMap(getPositionX() +1, getPositionY());
            if(player.getCurrentMap().getValMap(getPositionX() +1, getPositionY())!=0&&player.getCurrentMap().getValMap(getPositionX() +1, getPositionY())!=6) {
                player.getCurrentMap().setMap(getPositionX(), getPositionY(), getCurrentCell());
                setCurrentCell(val);
                setPositionX(getPositionX() + 1);
                player.getCurrentMap().setMap(getPositionX(), getPositionY(), 6);

            }
            else return 0;
        }
        if(direction==1)
        {
            int val = player.getCurrentMap().getValMap(getPositionX(), getPositionY() + 1);
            if (player.getCurrentMap().getValMap(getPositionX(), getPositionY() + 1) != 0&&player.getCurrentMap().getValMap(getPositionX(), getPositionY() + 1) != 6) {
                player.getCurrentMap().setMap(getPositionX(), getPositionY(), getCurrentCell());
                setCurrentCell(val);
                setPositionY(getPositionY() + 1);
                player.getCurrentMap().setMap(getPositionX(), getPositionY(), 6);
            }
            else return 0;
        }
        if(direction==2)
        {
            int val = player.getCurrentMap().getValMap(getPositionX() -1, getPositionY());
            if(player.getCurrentMap().getValMap(getPositionX() -1, getPositionY())!=0&&player.getCurrentMap().getValMap(getPositionX() -1, getPositionY())!=6) {
                player.getCurrentMap().setMap(getPositionX(), getPositionY(), getCurrentCell());
                setCurrentCell(val);
                setPositionX(getPositionX() - 1);
                player.getCurrentMap().setMap(getPositionX(), getPositionY(), 6);
            }
            else return 0;
        }
        if(direction==3)
        {
            int val = player.getCurrentMap().getValMap(getPositionX(), getPositionY() - 1);
            if (player.getCurrentMap().getValMap(getPositionX(), getPositionY() - 1) != 0&&player.getCurrentMap().getValMap(getPositionX(), getPositionY() - 1) != 6) {
                player.getCurrentMap().setMap(getPositionX(), getPositionY(), getCurrentCell());
                setCurrentCell(val);
                setPositionY(getPositionY() - 1);
                player.getCurrentMap().setMap(getPositionX(), getPositionY(), 6);
            }
            else return 0;
        }

        if(direction==-1) return 0;
        return 1;


    }



}
