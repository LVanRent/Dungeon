package com.example.dungeon;
import java.util.Random;
import android.util.Log;

/*
0=mur
1=couloir
2=salle
3=perso
4=sortie
*/
public class Map
{
    private int[][] map;
    private int[][] visible;
    public Random generator;
    private int length;
    private int width;
    private int[] enter;
    private int[] exit;
    private int[][] explored;

    public Map(int length,int width, Random generator)
    {
        this.generator=generator;
        map=new int[length][width];
        visible=new int[length][width];
        explored=new int[length][width];
        this.length=length;
        this.width=width;


        int i;
        int j;
        int i2;
        int j2;
        int minsize=5;
        int maxsize=15;
        int[] size=sizes(minsize,maxsize);
        int generated=0;
        int numberOfRooms=5;
        int[][] coords=new int [numberOfRooms][2];
        while(generated<numberOfRooms)
        {
            i=generator.nextInt(length-3-2*maxsize)+maxsize+1;
            j=generator.nextInt(width-3-2*maxsize)+maxsize;
            i2=i+size[generator.nextInt(size.length)];
           /* if (i2 > this.length || i2 <= 0) {
                continue ;
            }
            */

            j2=j+size[generator.nextInt(size.length)];
/*
            if (j2 > this.length || j2 <= 0) {
                continue ;
            }
  */          if (notOccupied(i,j,i2,j2))
            {
                Log.d("addroom generated:",""+generated +" i "+ i +" j "+ j +" i2 "+ i2 +" j2 "+ j2 );
                addRoom(i,j,i2,j2,2);
                coords[generated]=new int[] {i,j};
                generated++;
            }
        }
        generateTunnels(coords);

        addStairs(coords);

        addFood();

        for(i=0;i<length;i++)
        {
            for(j=0;j<width;j++)
            {
                if(isSeen(getEnter()[0],getEnter()[1],i,j))
                {
                    visible[i][j]=1;
                    explored[i][j]=1;

                }

            }
        }
    }



    //directions: 0-positivelength 1-positivewidth
    //            2-negativelength 3-negativewidth
    //            x-length y-width

    public void addFood()
    {

        int generated=3;
        for(int i=0; i<generated;i++)
        {
            int n=generator.nextInt(length);
            int m=generator.nextInt(length);
            if(map[m][n]==2)
            {
                map[m][n]=5;
            }
        }
    }

    public void setMap(int x, int y, int val)
    {
        map[x][y]=val;
    }
    public int getValMap(int x, int y)
    {
        return map[x][y];
    }

    public int getValVisible(int x, int y)
    {
        return visible[x][y];
    }

    public int getValExplored(int x, int y)
    {
        return explored[x][y];
    }

    public int[] getEnter()
    {
        return enter;
    }

    public int[] getExit()
    {
        return exit;
    }



    public int[][] generateTunnels(int[][] coords)
    {
        for(int i=0;i<coords.length-1;i++)
        {
            connectTunnel(coords[i][0],coords[i][1],coords[i+1][0],coords[i+1][1]);
        }
        return map;
    }

    public int[][] connectTunnel(int i1,int j1,int i2,int j2)
    {
        if((i2-i1)*(j2-j1)>=0)
        {
            int x1=Math.min(i1,i2);
            int x2=Math.max(i1,i2);
            int y1=Math.min(j1,j2);
            int y2=Math.max(j1,j2);
            if(generator.nextInt(2)==1)
            {
                addTunnel(x1,y1,0,x2-x1);
                addTunnel(x2,y1,1,y2-y1);
            }
            else
            {
                addTunnel(x2,y1,1,y2-y1);
                addTunnel(x1,y1,0,x2-x1);
            }
        }

        else
        {
            int x1=Math.min(i1,i2);
            int x2=Math.max(i1,i2);
            int y1=Math.max(j1,j2);
            int y2=Math.min(j1,j2);
            if(generator.nextInt(2)==1)
            {
                addTunnel(x1,y1,0,x2-x1);
                addTunnel(x2,y1,3,y1-y2);
            }
            else
            {
                addTunnel(x2,y1,3,y1-y2);
                addTunnel(x1,y1,0,x2-x1);
            }
        }
        return map;
    }
    //directions: 0-positivelength 1-positivewidth
    //            2-negativelength 3-negativewidth
    //            x-length y-width
    //returns the final tunnel point
    private int[][] addTunnel(int x,int y, int direction, int depth)
    {
        if(direction==0)
        {
            for(int i=0; i<=depth;i++)
            {
                map[x+i][y]=1;
            }
        }
        if(direction==1)
        {
            for(int i=0; i<=depth;i++)
            {
                map[x][y+i]=1;
            }
        }
        if(direction==2)
        {
            for(int i=0; i<=depth;i++)
            {
                map[x-i][y]=1;
            }
        }
        if(direction==3)
        {
            for(int i=0; i<=depth;i++)
            {
                map[x][y-i]=1;
            }
        }
        return map;
    }

    public int[][] addStairs(int[][] coords)
    {
        int i;
        int j;
        double dist=0;
        this.enter=new int[2];
        this.exit=new int[2];
        while(dist<1)
        {
            i=generator.nextInt(coords.length);
            j=generator.nextInt(coords.length);
            dist=distance(coords[i][0],coords[i][1],coords[j][0],coords[j][1]);
            this.enter=new int[] {coords[i][0],coords[i][1]};
            this.exit=new int[] {coords[j][0],coords[j][1]};
        }
        map[enter[0]][enter[1]]=7;
        map[exit[0]][exit[1]]=4;
        return map;
    }

    public double distance(int x1, int y1, int x2, int y2)
    {
        return Math.sqrt((x2-x1)*(x2-x1)+(y2-y1)*(y2-y1));
    }


    private boolean notOccupied(int i,int j,int i2,int j2) {
        int x1 = Math.min(i, i2);
        int x2 = Math.max(i, i2);
        int y1 = Math.min(j, j2);
        int y2 = Math.max(j, j2);
        for (int m = x1-1 ; m <= x2 + 1 ; m++) {
            for (int n = y1-1; n <= y2 + 1; n++) {
                if (map[m][n] == 2 || map[m][n] == 5 || map[m][n] == 6) {
                    return false;



                }
            }
        }
        return true;
    }

    //room types:
    //2-normal 5-entrance 6-exit
    private int[][] addRoom(int x1,int y1,int x2,int y2,int type)
    {
        int up=Math.max(x1,x2);
        int down=Math.min(x1,x2);
        int right=Math.max(y1,y2);
        int left=Math.min(y1,y2);
        for(int i=down;i<up+1;i++)
        {
            for(int j=left;j<right+1;j++)
            {
                map[i][j]=type;
            }
        }
        return map;
    }

    private static int[] sizes(int min,int max)
    {
        int[] sizes=new int[(2*max)-(2*min)+2];
        int st=max-min+1;
        int nd=2*max-2*min+2;
        max=-max;
        for(int i=0;i<st;i++)
        {
            sizes[i]=max;
            max++;
        }
        for(int i=st;i<nd;i++)
        {
            sizes[i]=min;
            min++;
        }
        return sizes;
    }

    public int[][] getMap(){
        return map;
    }

    public int getLength(){
        return length;
    }
    public int getWidth(){
        return width;
    }





    public boolean isSeen(int x1, int y1, int x2, int y2)
    {
        if(x1==x2)
        {
            for(int i=Math.min(y1,y2)+1;i<=Math.max(y1,y2)-1;i++)
            {
                if(map[x1][i]==0)
                {
                    return false;
                }
            }
        }
        else if(y1==y2)
        {
            for(int i=Math.min(x1,x2)+1;i<=Math.max(x1,x2)-1;i++)
            {
                if(map[i][y1]==0)
                {
                    return false;
                }
            }
        }
        else if(Math.abs((y2-y1)/(x2-x1))<1)
        {
            for(int i=Math.min(x1,x2)+1;i<=Math.max(x1,x2)-1;i++)
            {
                if(map[i][(int)Math.round(linePointValueY(x1,y1,x2,y2,i))]==0)
                {
                    return false;
                }
            }
        }
        else
        {
            for(int i=Math.min(y1,y2)+1;i<=Math.max(y1,y2)-1;i++)
            {
                if(map[(int)Math.round(linePointValueX(x1,y1,x2,y2,i))][i]==0)
                {
                    return false;
                }
            }
        }
        return true;
    }
    public double linePointValueY(double x1, double y1,double x2,double y2,double x)
    {
        return (x*(y2-y1)+y1*x2-x1*y2)/(x2-x1);
    }

    public double linePointValueX(double x1,double y1,double x2,double y2,double y)
    {
        return (y*(x2-x1)+x1*y2-y1*x2)/(y2-y1);
    }

    public int[][] updateVisible(int x, int y)
    {
        for(int i=0;i<length;i++)
        {
            for(int j=0;j<width;j++)
            {
                if(isSeen(x,y,i,j))
                {
                    visible[i][j]=1;
                }
                else{visible[i][j]=0;}
            }
        }
        return visible;
    }

    public int[][] updateExplored(int x, int y)
    {
        for(int i=0;i<length;i++)
        {
            for(int j=0;j<width;j++)
            {
                if(isSeen(x,y,i,j))
                {
                    explored[i][j]=1;
                }
            }
        }
        return explored;
    }

    public static Map createMap(Random generator){
        Map map1=new Map(60,60,generator);
        return map1;
    }

    public static void main(String[] args)
    {
        Map map1=new Map(100,100,new Random());
    }
}
