package Projekt1;

import java.util.Arrays;
import java.util.List;

public class Board {
    public int size;
    private int[][] map;

    public Board(){}

    public Board(int boardSize){
        this.size = boardSize;
        this.map = new int[size][size];
        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                map[i][j] = 0;
            }
        }
    }

    public Board(int boardSize, List<Coord> islands){
        this(boardSize);
        setIslands(islands);
    }


    public void setIslands(List<Coord> islandsCoords){
        if(islandsCoords.size() > size*size) throw new IllegalArgumentException("Za dużo wysp! Nie zmieszczą się na mapie.");

        for(Coord coord : islandsCoords){
            if(coord.getX() < 0 || coord.getX() > size - 1 || 
            coord.getY() < 0 || coord.getY() > size - 1) throw new IllegalArgumentException("Wyspa poza obszarem gry.");
            map[coord.getX()][coord.getY()] = 1;
        }
    }

    public int countIslands() {
        int count = 0;
        for(int i = 0; i < size; i++)
            for(int j = 0; j < size; j++)
                if(map[i][j] == 1) count++;
        return count;
    }

    public boolean isIsland(int x, int y){
        return map[x][y] == 1 ? true : false;
    }

    public int getTile(int x, int y){
        return map[x][y];
    }

    public int[][] getMap(){return map;}

    @Override
    public boolean equals(Object o) {
        Board c = (Board) o;
        return c.size == this.size && Arrays.deepEquals(c.map, this.map);
    }
}