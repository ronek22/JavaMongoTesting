package Projekt1;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class GameTest {
    private static Board board;

    @BeforeAll
    public static void setUp(){
        board = new Board(10,
                new ArrayList<>(Arrays.asList(
                        new Coord(2,4), new Coord(3,7), new Coord(4,4), new Coord(7,7),
                        new Coord(1,8), new Coord(9,9), new Coord(8,2), new Coord(4,2)
                )));

     /*
            0 1 2 3 4 5 6 7 8 9
           =====================
        0 | ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ |
        1 | ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ |
        2 | ~ ~ ~ ~ O ~ ~ ~ O ~ |
        3 | ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ |
        4 | ~ ~ O ~ O ~ ~ ~ ~ ~ |
        5 | ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ |
        6 | ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ |
        7 | ~ ~ ~ O ~ ~ ~ O ~ ~ |
        8 | ~ O ~ ~ ~ ~ ~ ~ ~ ~ |
        9 | ~ ~ ~ ~ ~ ~ ~ ~ ~ O |
           =====================
     */
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/CommandWithoutCollision.csv")
    void testCommand(int x, int y, String commands, int destX, int destY){
        Game game = new Game(board, x, y, Direction.E, true);
        game.command(commands);
        assertEquals(new Coord(destX, destY), game.ship.coord);
    }


    @ParameterizedTest
    @MethodSource("testCommandCollisionFixture")
    void testCommandCollision(int x, int y, Direction dir, String commands, int destX, int destY, int noOfCollision){
        Game game = new Game(board, x, y, dir, true);
        game.command(commands);
        assertAll(
                () -> assertEquals(new Coord(destX,destY), game.ship.coord),
                () -> assertEquals(noOfCollision, game.getNumberOfCollisions())
        );
    }

    private static Stream<Arguments> testCommandCollisionFixture(){
        return Stream.of(
                Arguments.of(0, 0, Direction.E, "npnnnnnnnn", 1, 7, 1),
                Arguments.of(4, 1, Direction.S, "nlnnnnlw", 8, 1, 2),
                Arguments.of(0, 8, Direction.E, "nlnpnnnlnnnpnnlnpnlnnnpwnnnpnnnnnlnnpnnn", 9, 8, 8)
        );
    }

    @Test
    public void testCommandThatDontExist(){
        Game game = new Game(board, 0, 0, Direction.E, true);
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            game.command("nnnnxn");
        });
        assertEquals("Nie ma takiego polecenia!", exception.getMessage());
    }

    @ParameterizedTest
    @MethodSource("testCommandOutOfBoundsFixture")
    void testCommandOutOfBounds(int boardSize, int x, int y, Direction dir, String commands){
        Game game = new Game(boardSize, new ArrayList<Coord>(), x, y, dir, true);
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            game.command(commands);
        });
    }

    private static Stream<Arguments> testCommandOutOfBoundsFixture(){
        return Stream.of(
                Arguments.of(3, 0, 0, Direction.E, "nnnn"),
                Arguments.of(5, 2, 3, Direction.S, "nn"),
                Arguments.of(7, 2, 2, Direction.E, "lnnnnnn"),
                Arguments.of(3, 0, 0, Direction.W, "n")
        );
    }

    @Test
    public void testCreateShipOnIsland1(){
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            Game game = new Game(board, 2, 4, Direction.E, true);
        });
        assertEquals("Statek nie może startować z wyspy.", exception.getMessage());
    }

    @Test
    public void testCreateShipOnIsland2(){
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            Game game = new Game(2, new ArrayList<>(Arrays.asList(new Coord(1,1))), 1, 1, Direction.E, true);
        });
        assertEquals("Statek nie może startować z wyspy.", exception.getMessage());
    }
}