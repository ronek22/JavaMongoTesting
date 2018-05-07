package Projekt1;

public class Coord {
    private int x,y;

    public Coord(){

    }

    public Coord(int x, int y){
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        Coord c = (Coord) o;
        return c.x == x && c.y == y;
    }


    public int getX(){ return x;}
    public int getY(){ return y;}

    public void setX(int x){ this.x = x;}
    public void setY(int y){ this.y = y;}
    public void setCoord(int x, int y){ this.x = x; this.y = y;}

    @Override
    public String toString(){
        return "( " + x + ", " + y + " )";
    }

}