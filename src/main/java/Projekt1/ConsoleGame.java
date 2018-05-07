package Projekt1;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.jongo.MongoCursor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ConsoleGame {

    public static void main(String[] args){
        List<Coord> islands = new ArrayList<>(Arrays.asList(
                new Coord(1,3),
                new Coord(4,4),
                new Coord(5,5),
                new Coord(7,3)
        ));


        Game game = new Game(10, islands, 0,0,Direction.E, false);
        game.printGame();
        while(true){
            game.command(handleInput());
            game.printGame();
        }
    }


    public static String handleInput(){
        Scanner input = new Scanner(System.in);
        System.out.print("Podaj polecenie[nwlp]: ");
        return input.nextLine();
    }
}
