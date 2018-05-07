package Projekt1;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

public class BoardTest {

    private Board board;

    @BeforeEach
    public void setUp(){
        board = new Board(10);
    }


    @Test
    public void createBoardWithIslands(){
        board = new Board(2, new ArrayList<>(Arrays.asList(new Coord(0,1), new Coord(1,1))));
        assertArrayEquals(new int[][]{{0,1},{0,1}}, board.getMap());
    }

    @Test
    public void testSetIslandsTooMany(){
        board = new Board(2);
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            board.setIslands(new ArrayList<>(Arrays.asList(new Coord(0, 0), new Coord(0,1), new Coord(1,0), new Coord(1,1), new Coord(2,1))));
        });
        assertEquals("Za dużo wysp! Nie zmieszczą się na mapie.", exception.getMessage());
    }

    @Test
    public void testSetIslandsTooSmallX(){
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            board.setIslands(new ArrayList<>(Arrays.asList(new Coord(-1,1))));
        });
        assertEquals("Wyspa poza obszarem gry.", exception.getMessage());
    }

    @Test
    public void testSetIslandsTooBigX(){
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            board.setIslands(new ArrayList<>(Arrays.asList(new Coord(10,1))));
        });
        assertEquals("Wyspa poza obszarem gry.", exception.getMessage());
    }

    @Test
    public void testSetIslandsTooSmallY(){
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            board.setIslands(new ArrayList<>(Arrays.asList(new Coord(0,-2))));
        });
        assertEquals("Wyspa poza obszarem gry.", exception.getMessage());
    }

    @Test
    public void testSetIslandsTooBigY(){
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            board.setIslands(new ArrayList<>(Arrays.asList(new Coord(1,10))));
        });
        assertEquals("Wyspa poza obszarem gry.", exception.getMessage());
    }

    @Test
    public void testSetIslandsShouldWork(){
        board.setIslands(new ArrayList<>(Arrays.asList(new Coord(1,2), new Coord(3,1), new Coord(4,5),
                new Coord(5,7), new Coord(8,8))));
        Integer[] islandsFromBoard = new Integer[5];
        islandsFromBoard[0] = board.getTile(1, 2);
        islandsFromBoard[1] = board.getTile(3, 1);
        islandsFromBoard[2] = board.getTile(4, 5);
        islandsFromBoard[3] = board.getTile(5, 7);
        islandsFromBoard[4] = board.getTile(8, 8);

        assertThat(islandsFromBoard, arrayContaining(1,1,1,1,1));
    }

    @Test
    public void testCountIslands(){
        board.setIslands(new ArrayList<>(Arrays.asList(new Coord(2, 1), new Coord(4,2), new Coord(7,8))));
        assertThat(board.countIslands(), equalTo(3));
    }

    @Test
    public void testIsIslandReturnTrue(){
        board.setIslands(new ArrayList<>(Arrays.asList(new Coord(2,2), new Coord(4,1), new Coord(5,7))));
        assertTrue(board.isIsland(4, 1));
    }

    @Test
    public void testIsIslandReturnFalse(){
        board.setIslands(new ArrayList<>(Arrays.asList(new Coord(2,2), new Coord(4,1), new Coord(5,7))));
        assertFalse(board.isIsland(1, 1));
    }

    @AfterEach
    public void tearDown(){
        board = null;
    }
}
