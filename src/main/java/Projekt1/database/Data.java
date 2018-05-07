package Projekt1.database;

import Projekt1.Coord;
import Projekt1.Direction;
import Projekt1.Ship;

import java.util.List;

public class Data {
    private int round;
    private List<Coord> islands;
    private Ship player;
    private int mapSize;
    private int numberOfCollisions;

    public Data(){}

    public Data(int round, List<Coord> islands, Ship player, int mapSize, int noOfCollisions){
        this.round = round;
        this.islands = islands;
        this.player = player;
        this.mapSize = mapSize;
        this.numberOfCollisions = noOfCollisions;
    }

    public int getRound() {
        return round;
    }

    public List<Coord> getIslands() {
        return islands;
    }

    public Ship getPlayer(){
        return player;
    }

    public int getNumberOfCollisions() {
        return numberOfCollisions;
    }
}
