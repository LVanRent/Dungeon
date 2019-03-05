package com.example.dungeon;
//import java.awt.image.BufferedImage;
//import java.io.File;
//import javax.imageio.ImageIO;
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

    public void setMap(int x, int y, int val){
        map[x][y]=val;
    }
    public int getValMap(int x, int y){
        return map[x][y];
    }

    public int[][] generateTunnels()
    {
        int tunnelNumber=9;
        int minDepth=15;
        int x=generator.nextInt(length-2)+1;
        int y=generator.nextInt(width-2)+1;
        int direction=generator.nextInt(4);
        int[] dir02=new int[] {1,3};
        int[] dir13=new int[] {0,2};
        for(int i=0; i<tunnelNumber; i++)
        {
            if(direction==0)
            {
                if(length-2-x<minDepth+1)
                {
                    addTunnel(x,y,direction,length-2-x);
                    x=length-2;
                }
                else
                {
                    int depth=generator.nextInt(length-x-1-minDepth)+minDepth;
                    addTunnel(x,y,direction,depth);
                    x=x+depth;
                }
                direction=dir02[generator.nextInt(2)];
            }
            else if(direction==1)
            {
                if(width-2-y<minDepth+1)
                {
                    addTunnel(x,y,direction,width-2-y);
                    y=width-2;
                }
                else
                {
                    int depth=generator.nextInt(width-y-1-minDepth)+minDepth;
                    addTunnel(x,y,direction,depth);
                    y=y+depth;
                }
                direction=dir13[generator.nextInt(2)];
            }
            else if(direction==2)
            {
                if(x<minDepth+2)
                {
                    addTunnel(x,y,direction,x-1);
                    x=1;
                }
                else
                {
                    int depth=generator.nextInt(x-minDepth)+minDepth;
                    addTunnel(x,y,direction,depth);
                    x=x-depth;
                }
                direction=dir02[generator.nextInt(2)];
            }
            else if(direction==3)
            {
                if(y<minDepth+2)
                {
                    addTunnel(x,y,direction,y-1);
                    y=1;
                }
                else
                {
                    int depth=generator.nextInt(y-minDepth)+minDepth;
                    addTunnel(x,y,direction,depth);
                    y=y-depth;
                }
                direction=dir13[generator.nextInt(2)];
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
            for(int i=0; i<depth+1;i++)
            {
                map[x+i][y]=1;
            }
        }
        if(direction==1)
        {
            for(int i=0; i<depth+1;i++)
            {
                map[x][y+i]=1;
            }
        }
        if(direction==2)
        {
            for(int i=0; i<depth+1;i++)
            {
                map[x-i][y]=1;
            }
        }
        if(direction==3)
        {
            for(int i=0; i<depth;i++)
            {
                map[x][y-i]=1;
            }
        }
        return map;
    }

    private int[][] generateRooms()
    {
        int x=14;
        int y=14;
        int[] size=sizes(5,9);
        int generated=0;
        while(generated<5)
        {
            int i=generator.nextInt(length-21)+10;
            int j=generator.nextInt(width-21)+10;
            int i2=i+size[generator.nextInt(size.length)];
            int j2=j+size[generator.nextInt(size.length)];
            if (map[i][j]==1 && notOccupied(i,j,i2,j2))
            {
                addRoom(i,j,i2,j2);
                x=i;
                y=j;
                generated++;
            }
        }
        return map;
    }

    private boolean notOccupied(int i,int j,int i2,int j2) {
        int x1 = Math.min(i, i2);
        int x2 = Math.max(i, i2);
        int y1 = Math.min(j, j2);
        int y2 = Math.max(j, j2);
        for (int m = x1; m <= x2 + 1; m++) {
            for (int n = y1; n <= y2 + 1; n++) {
                if (map[m][n] == 2) {
                    return false;
                }
            }
        }
        return true;
    }

    private int[][] addRoom(int x1,int y1,int x2,int y2)
    {
        int up=Math.max(x1,x2);
        int down=Math.min(x1,x2);
        int right=Math.max(y1,y2);
        int left=Math.min(y1,y2);
        for(int i=down;i<up+1;i++)
        {
            for(int j=left;j<right+1;j++)
            {
                map[i][j]=2;
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

    private void drawMap()
    {
  /*      try
        {
            int h=600;
            int w=600;
            BufferedImage bufferImage2=new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
            for(int i=0; i<w; i++)
            {
                for(int j=0; j<h; j++)
                {
                    if(map[i/10][j/10]==0)
                    {
                        bufferImage2.setRGB(i,j,1234);
                    }
                    else
                    {
                        bufferImage2.setRGB(i,j,0);
                    }
                }
            }
            File outputfile = new File("D:\\saved.jpg");
            ImageIO.write(bufferImage2, "jpg", outputfile);
        }
        catch(Exception ee)
        {
            ee.printStackTrace();
        }
  */  }
    public int[][] getMap(){
        return map;
    }

    public int getLength(){
        return length;
    }
    public int getWidth(){
        return width;
    }
    public void addStairs(){
        int x = 0;
        int y = 0;
        while(map[x][y] != 2) {
            x = generator.nextInt(length - 2) + 1;
            y = generator.nextInt(width - 2) + 1;
        }
        map[x][y] = 4;
    }

    public static void main(String[] args)
    {
        Map map1=new Map(60,60);
        map1.generateTunnels();
        map1.generateRooms();
        map1.addStairs();
        map1.drawMap();
    }
}
