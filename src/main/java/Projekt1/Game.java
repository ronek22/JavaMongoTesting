package Projekt1;

import Projekt1.database.Data;
import Projekt1.database.DataCollection;
import Projekt1.database.DataMongo;
import Projekt1.database.FakeDataCollection;

import static Projekt1.Turn.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static Projekt1.Move.*;


public class Game {
    public Board board;
    public Ship ship;
    private int numberOfCollisions;
    List<Coord> islands;
    public DataMongo database;


    public Game(boolean fake){
        initializer(fake);
        islands = new ArrayList<>(Arrays.asList(
                new Coord(1,3),
                new Coord(4,4),
                new Coord(5,5),
                new Coord(7,3)
        ));
        board = new Board(10, islands);
        ship = new Ship(0, 0, Direction.E);
        saveStep();
    }

    public Game(int boardSize, List<Coord> islands, int shipX, int shipY, Direction shipDir, boolean fake) {
        initializer(fake);
        this.islands = islands;
        board = new Board(boardSize, islands);
        if(isCollision(shipX, shipY)) throw new IllegalArgumentException("Statek nie może startować z wyspy.");
        ship = new Ship(shipX, shipY, shipDir);
        saveStep();
    }

    public Game(Board board, int shipX, int shipY, Direction shipDir, boolean fake){
        initializer(fake);
        this.islands = null;
        this.board = board;
        if(isCollision(shipX, shipY)) throw new IllegalArgumentException("Statek nie może startować z wyspy.");
        ship = new Ship(shipX, shipY, shipDir);
        saveStep();
    }

//    private void initializer(){
//        this.numberOfCollisions = 0;
//        database = new DataMongo();
//        if(!database.isDbEmpty()) database.clearDb();
//    }

    private void initializer(boolean fake){
        this.numberOfCollisions = 0;
        if(fake)
            database = new DataMongo(new FakeDataCollection());
        else
            database = new DataMongo();
        if(!database.isDbEmpty()) database.clearDb();
    }

    public void moveShip(Move move){
        Coord newPos = ship.swim(move);

        if(!isCollision(newPos.getX(), newPos.getY())){
            ship.coord = newPos;
        }
    }

    public void command(String commandList){
        for(char comm : commandList.toCharArray()){
            switch(comm){
                case 'n':
                    moveShip(FORWARD);
                    break;
                case 'w':
                    moveShip(BACKWARD);
                    break;
                case 'l':
                    ship.turnShip(LEFT);
                    break;
                case 'p':
                    ship.turnShip(RIGHT);
                    break;
                default:
                    throw new IllegalArgumentException("Nie ma takiego polecenia!");                
            }
        }
        saveStep();
    }

    public boolean isCollision(int x, int y){
        if(x < 0 || x > board.size - 1 || y < 0 || y > board.size - 1) throw new IllegalArgumentException("Statek poza plansza!");
        if(board.isIsland(x, y)){
            raiseCollision();
            return true;
        } return false;
    }

    public void raiseCollision(){
        // System.out.println("Anulowanie. Statek nie moze wplynac na wyspe");
        numberOfCollisions++;
    }

    public int getNumberOfCollisions(){return numberOfCollisions;}

    private void saveStep() {
        database.insertStep(islands, ship, board.size, numberOfCollisions);
    }


    public void printGame(){
        for(int i = 0; i < board.size; i++){
            for(int j = 0; j < board.size; j++){
                if(ship.coord.getX() == j && ship.coord.getY() == i){
                    System.out.print(ship + "\t");
                } else if(board.isIsland(j, i)){
                    System.out.print("O\t");
                } else {
                    System.out.print("~\t");
                }
            }
            System.out.println();
        }
        System.out.println("SHIP POSITION: " + ship.coord);
    }

}