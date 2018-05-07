package Projekt1.Mocks;

import Projekt1.Coord;
import Projekt1.Direction;
import Projekt1.Game;
import Projekt1.Ship;
import Projekt1.database.Data;
import Projekt1.database.DataMongo;
import Projekt1.database.FakeDataCollection;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class FakeDataTest {
    FakeDataCollection dataCollection;
    DataMongo dataMongo;

    Ship ship;
    List<Coord> islands;
    int mapSize;

    @BeforeEach
    public void setUp(){
        dataCollection = new FakeDataCollection();
        dataMongo = new DataMongo(dataCollection);
        ship = new Ship(0,0,Direction.E);
        mapSize = 10;
        List<Coord> islands = new ArrayList<>(Arrays.asList(
                new Coord(1,3),
                new Coord(4,4),
                new Coord(5,5),
                new Coord(7,3)
        ));
    }

    @Test
    public void insert_Data_Test(){
        dataMongo.insertStep(islands,ship,mapSize,0);
        assertThat(dataCollection.isDbEmpty()).isFalse();
    }

    @Test
    public void insert_Multiple_Data_Test(){
        dataMongo.insertStep(islands,ship,mapSize,0);
        ship.coord.setCoord(0,5);
        dataMongo.insertStep(islands,ship,mapSize,0);

        assertThat(dataCollection.getAllSteps().size()).isEqualTo(2);
    }

    @Test
    public void insert_Data_With_Wrong_Position_Should_Throw_Exception(){
        ship.setCoord(11,5);
        Throwable exception = assertThrows(IllegalArgumentException.class,
                () -> dataMongo.insertStep(islands, ship, mapSize, 0));
        assertEquals("Statek poza plansza!", exception.getMessage());
    }

    @Test
    public void db_Clear_Test(){
        dataMongo.insertStep(islands, ship, mapSize, 0);
        dataMongo.insertStep(islands,ship,mapSize,1);
        dataMongo.insertStep(islands,ship,mapSize,2);
        dataMongo.clearDb();
        assertTrue(dataMongo.isDbEmpty());
    }

    @Test
    public void isDbEmpty_With_Not_Empty_Db(){
        dataMongo.insertStep(islands, ship, mapSize, 0);
        assertThat(dataMongo.isDbEmpty()).isFalse();
    }

    @Test
    public void empty_Db_GetAllSteps_Return_Null(){
        assertThat(dataMongo.getAllSteps()).isNull();
    }

    @Test
    public void save_Inserted_Data(){
        Data step = new Data(1, islands, ship, mapSize, 0);
        dataCollection.insert(step);
        dataMongo.saveStep(step);
        assertEquals(step, dataCollection.findByRoundNo(1));
    }

    @Test
    public void save_Not_Inserted_Data_Should_Throw_Exception(){
        Throwable exception = assertThrows(IllegalArgumentException.class,
                () -> dataMongo.saveStep(new Data(1, islands, ship, mapSize, 0)));
        assertEquals("Nie mozna zapisac!", exception.getMessage());
    }

    @Test
    public void get_Last_Step_Number_Test(){
        dataMongo.insertStep(islands,ship,mapSize,0);
        dataMongo.insertStep(islands,ship,mapSize,0);
        dataMongo.insertStep(islands,ship,mapSize,0);
        dataMongo.insertStep(islands,ship,mapSize,0);
        assertEquals(4, dataCollection.getLastMoveNo());

    }

    @Test
    public void new_Session_Test(){
        Game game = new Game(true);
        game.command("nn");
        game.command("ww");
        int firstGameSteps = game.database.getAllSteps().size();
        Game game2 = new Game(true);
        int secondGameSteps = game2.database.getAllSteps().size();

        assertAll(
                () -> assertEquals(3, firstGameSteps),
                () -> assertEquals(1, secondGameSteps)
        );

    }
}
