package Projekt1;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ShipTest {

    private Ship ship;

    @BeforeEach
    public void setUp(){
        ship = new Ship(0, 0, Direction.E);
    }

    @Test
    public void testTurnRight(){
        ship.turnShip(Turn.RIGHT);
        assertEquals(Direction.S, ship.getDirection());
    }

    @Test
    public void testTurnLeft(){
        ship.turnShip(Turn.LEFT);
        assertEquals(Direction.N, ship.getDirection());
    }

    @Test
    public void testMoveForwardFromStartPosition(){
        assertEquals(new Coord(1, 0), ship.swim(Move.FORWARD));
    }

    @Test
    public void testMoveForwardFromDifferentPosition(){
        ship.setCoord(4, 5);
        assertEquals(new Coord(5, 5), ship.swim(Move.FORWARD));
    }


    @Test
    public void testMoveForwardAndBackwardFromStartPosition(){
        ship.setCoord(ship.swim(Move.FORWARD));
        ship.setCoord(ship.swim(Move.BACKWARD));
        assertEquals(new Coord(0, 0), ship.coord);
    }

    @Test
    public void testMoveAndTurns(){
        for(int i = 0; i < 3; i++)
            ship.setCoord(ship.swim(Move.FORWARD));
        ship.turnShip(Turn.RIGHT);
        for(int i = 0; i < 4; i++)
            ship.setCoord(ship.swim(Move.FORWARD));
        ship.turnShip(Turn.RIGHT);
        ship.setCoord(ship.swim(Move.FORWARD));
        ship.turnShip(Turn.RIGHT);
        ship.setCoord(ship.swim(Move.BACKWARD));
        assertEquals(new Coord(2,5), ship.coord);
    }

    @AfterEach
    public void tearDown(){
        ship = null;
    }
}