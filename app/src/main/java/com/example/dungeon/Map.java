package com.example.dungeon;
import java.util.Random;
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
    public Random generator;
    private int length;
    private int width;

    public Map(int length,int width)
    {
        map=new int[length][width];
        generator=new Random();
        this.length=length;
        this.width=width;
    }

    public Map(int length,int width, int seed)
    {
        map=new int[length][width];
        generator=new Random(seed);
        this.length=length;
        this.width=width;
    }

    //directions: 0-positivelength 1-positivewidth
    //            2-negativelength 3-negativewidth
    //            x-length y-width

    public void setMap(int x, int y, int val)
    {
        map[x][y]=val;
    }
    public int getValMap(int x, int y)
    {
        return map[x][y];
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
                addTunnel(x2,y1,3,y2-y1);
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

    private int[][] generateStuff()
    {
        int i;
        int j;
        int i2;
        int j2;
        int[] size=sizes(5,9);
        int generated=0;
        int numberOfRooms=8;
        int[][] coords=new int [numberOfRooms][2];
        while(generated<numberOfRooms)
        {
            if(generated==0)
            {
                i=11;
                j=11;
                addRoom(i,j,
                        i+size[generator.nextInt(size.length)],
                        j+size[generator.nextInt(size.length)],
                        5);
                coords[0]=new int[]{10,10};
                generated++;
            }
            else if(generated==numberOfRooms-1)
            {
                i=49;
                j=49;
                addRoom(i,j,
                        i+size[generator.nextInt(size.length)],
                        j+size[generator.nextInt(size.length)],
                        6);
                coords[coords.length-1]=new int[]{50,50};
                generated++;
            }
            else
            {
                i=generator.nextInt(length-21)+10;
                j=generator.nextInt(width-21)+10;
                i2=i+size[generator.nextInt(size.length)];
                j2=j+size[generator.nextInt(size.length)];
                if (notOccupied(i,j,i2,j2))
                {
                    addRoom(i,j,i2,j2,2);
                    coords[generated]=new int[] {i,j};
                    generated++;
                }
            }
        }
        generateTunnels(coords);
        return map;
    }

    private boolean notOccupied(int i,int j,int i2,int j2) {
        int x1 = Math.min(i, i2);
        int x2 = Math.max(i, i2);
        int y1 = Math.min(j, j2);
        int y2 = Math.max(j, j2);
        for (int m = x1-1; m <= x2 + 1; m++) {
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
    public void addExit(){
        int x = 0;
        int y = 0;
        while(map[x][y] != 6) {
            x = generator.nextInt(length - 2) + 1;
            y = generator.nextInt(width - 2) + 1;
        }
        map[x][y] = 4;
    }

    public void addEntrance(){
        int x = 0;
        int y = 0;
        while(map[x][y] != 5) {
            x = generator.nextInt(length - 2) + 1;
            y = generator.nextInt(width - 2) + 1;
        }
        map[x][y] = 7;
    }

    public static Map createMap(){
        Map map1=new Map(60,60);
        map1.generateStuff();
        map1.addExit();
        map1.addEntrance();
        return map1;

    }

    public static void main(String[] args)
    {
        Map map1=new Map(60,60);
        map1.generateStuff();
        map1.addExit();
        map1.addEntrance();
    }
}
