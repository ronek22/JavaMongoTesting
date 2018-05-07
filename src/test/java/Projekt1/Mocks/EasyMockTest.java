package Projekt1.Mocks;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.easymock.EasyMock.*;

import Projekt1.Coord;
import Projekt1.Direction;
import Projekt1.Ship;
import Projekt1.database.Data;
import Projekt1.database.DataCollection;
import Projekt1.database.DataMongo;
import org.easymock.EasyMockRunner;
import org.easymock.IAnswer;
import org.easymock.Mock;
import org.easymock.MockType;
import org.easymock.TestSubject;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(EasyMockRunner.class)
public class EasyMockTest {

    @TestSubject
    DataMongo dataMongo = new DataMongo();

    //A nice mock expects recorded calls in any order and returning null for other calls
    @Mock(type = MockType.NICE)
    DataCollection dataCollection;

    private Ship player;
    private List<Coord> islands;
    private int mapSize;
    private int stepNo;

    @SuppressWarnings("unchecked")
    @Test
    public void mockingWorksAsExpected(){
        stepNo = 1;
        mapSize = 10;
        islands = createMock(List.class);
        player = createMock(Ship.class);
        Data step = new Data(stepNo,islands,player,mapSize,0);
        expect(dataCollection.findByRoundNo(stepNo)).andReturn(step);
        replay(dataCollection);
        assertThat(dataCollection.findByRoundNo(stepNo)).isEqualTo(step);
    }


    @SuppressWarnings("unchecked")
    @Test
    public void check_Step_Content_Islands() {

        stepNo = 1;
        mapSize = 10;
        player = createMock(Ship.class);
        islands = new ArrayList<>(Arrays.asList(
                new Coord(2,2), new Coord(5,1), new Coord(0,5)
        ));
        Data step = createMock(Data.class);
        expect(dataCollection.findByRoundNo(stepNo)).andReturn(step);
        expect(step.getIslands()).andReturn(islands);
        replay(player);
        replay(step);
        replay(dataCollection);
        assertThat(dataMongo.getIslands(stepNo)).hasSize(3).containsOnly(new Coord(2,2), new Coord(5,1), new Coord(0,5));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void check_Step_Content_Player_Direction() {

        stepNo = 1;
        mapSize = 10;
        player = new Ship(0,0,Direction.E);
        islands = createMock(List.class);
        Data step = createMock(Data.class);
        expect(dataCollection.findByRoundNo(stepNo)).andReturn(step);
        expect(step.getPlayer()).andReturn(player);
        replay(islands);
        replay(step);
        replay(dataCollection);
        assertThat(dataMongo.getPlayer(stepNo).getDirection()).isEqualTo(Direction.E);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void Sum_Of_Collisions_Test() {

        mapSize = 10;
        player = createMock(Ship.class);
        islands = createMock(List.class);
        List<Data> steps = new ArrayList<>(Arrays.asList(
                new Data(stepNo++, islands, player, mapSize, 2),
                new Data(stepNo++, islands, player, mapSize, 3),
                new Data(stepNo++, islands, player, mapSize, 4)
        ));

        expect(dataCollection.getAllSteps()).andReturn(steps);
        replay(islands);
        replay(player);
        replay(dataCollection);

        int suma = 0;
        for(Data step : dataMongo.getAllSteps()){
            suma += step.getNumberOfCollisions();
        }

        assertEquals(9, suma);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void check_Step_Content_Player_Position() {

        stepNo = 1;
        mapSize = 10;
        player = new Ship(5,2,Direction.N);
        islands = createMock(List.class);
        Data step = createMock(Data.class);
        expect(dataCollection.findByRoundNo(stepNo)).andReturn(step);
        expect(step.getPlayer()).andReturn(player);
        replay(islands);
        replay(step);
        replay(dataCollection);
        assertEquals(new Coord(5,2), dataMongo.getPlayer(stepNo).coord);
    }

    @Test
    public void check_Step_Content_Islands_NotNull() {

        stepNo = 1;
        mapSize = 10;
        player = createMock(Ship.class);
        islands = new ArrayList<>(Arrays.asList(
                new Coord(0,5)
        ));
        Data step = createMock(Data.class);
        expect(dataCollection.findByRoundNo(stepNo)).andReturn(step);
        expect(step.getIslands()).andReturn(islands);
        replay(player);
        replay(step);
        replay(dataCollection);
        assertNotNull(dataMongo.getIslands(stepNo));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void check_Step_Content_Player_IsNotNull() {

        stepNo = 1;
        mapSize = 10;
        player = new Ship(5,2,Direction.N);
        islands = createMock(List.class);
        Data step = createMock(Data.class);
        expect(dataCollection.findByRoundNo(stepNo)).andReturn(step);
        expect(step.getPlayer()).andReturn(player);
        replay(islands);
        replay(step);
        replay(dataCollection);
        assertNotNull(dataMongo.getPlayer(stepNo));
    }

    @Test
    public void remove_Data_List_Should_Be_Empty() {
        List<Data> steps = new ArrayList<>();
        for(int i = 0; i<5; i++)
            steps.add(createMock(Data.class));

        // eraseAll z DataCollection zastepuje steps.clear(), ktory czysci tymczasowa liste
        dataCollection.eraseAll();
        expectLastCall().andAnswer((IAnswer) () -> {
            steps.clear();
            return null;
        });
        replay(dataCollection);

        // dataMongo clearDb wywoluje podmieniona metode z dataCollection
        dataMongo.clearDb();
        assertEquals(0, steps.size());
    }
}