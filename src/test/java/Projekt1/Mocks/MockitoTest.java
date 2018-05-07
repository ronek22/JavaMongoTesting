package Projekt1.Mocks;

import Projekt1.Coord;
import Projekt1.Direction;
import Projekt1.Ship;
import Projekt1.database.Data;
import Projekt1.database.DataCollection;
import Projekt1.database.DataMongo;
import Projekt1.database.FakeDataCollection;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SuppressWarnings("deprecation")
@RunWith(MockitoJUnitRunner.class)
public class MockitoTest {

    @Mock
    DataCollection dataCollection;

    @InjectMocks
    private DataMongo dataMongo = new DataMongo();

    private Ship player;
    private List<Coord> islands;
    private int mapSize;
    private int stepNo;

    @SuppressWarnings("unchecked")
    @Test
    public void mocking_Work_As_Expected(){
        player = mock(Ship.class);
        islands = mock(List.class);
        mapSize = 10;
        stepNo = 1;
        Data step = new Data(stepNo, islands, player, mapSize, 0);
        doReturn(step).when(dataCollection).findByRoundNo(stepNo);
        assertThat(dataCollection.findByRoundNo(stepNo)).isEqualTo(step);
    }

    @Test
    public void count_Islands_Db_Test(){
        player = mock(Ship.class);
        islands = spy(new ArrayList<>());
        mapSize = 10;
        stepNo = 1;
        doReturn(5).when(islands).size();

        Data step = new Data(stepNo, islands, player, mapSize, 0);
        doReturn(step).when(dataCollection).findByRoundNo(stepNo);
        assertThat(step.getIslands().size()).isEqualTo(dataMongo.getIslands(stepNo).size());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void getCollision_Number_Test(){
        player = mock(Ship.class);
        islands = mock(List.class);
        mapSize = 10;
        stepNo = 1;
        int collisions = 5;
        Data step = new Data(stepNo, islands, player, mapSize, collisions);
        doReturn(step).when(dataCollection).findByRoundNo(stepNo);
        assertThat(dataMongo.getCollisionNo(stepNo)).isEqualTo(collisions);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void getPlayer_Test(){
        player = new Ship(2,5, Direction.E);
        islands = mock(List.class);
        mapSize = 10;
        stepNo = 1;
        Data step = new Data(stepNo, islands, player, mapSize, 0);
        doReturn(step).when(dataCollection).findByRoundNo(stepNo);
        assertThat(dataMongo.getPlayer(stepNo)).isEqualTo(player);

    }

    @SuppressWarnings("unchecked")
    @Test
    public void count_Steps_Db_Test(){

        List<Data> steps = new ArrayList<>();
        player = mock(Ship.class);
        islands = mock(List.class);
        mapSize = 10;
        doAnswer(invocation -> {
            steps.add(invocation.getArgument(0));
            return null;
        }).when(dataCollection).insert(isA(Data.class));

        dataMongo.insertStep(islands, player, mapSize, 0);
        dataMongo.insertStep(islands, player, mapSize, 0);
        dataMongo.insertStep(islands, player, mapSize, 0);
        assertEquals(3, steps.size());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void insert_First_Step_In_Db_Should_Be_Step_No_1(){

        List<Data> steps = new ArrayList<>();
        player = mock(Ship.class);
        islands = mock(List.class);
        mapSize = 10;
        doAnswer(invocation -> {
            steps.add(invocation.getArgument(0));
            return null;
        }).when(dataCollection).insert(isA(Data.class));

        dataMongo.insertStep(islands, player, mapSize, 0);
        assertEquals(1, steps.get(0).getRound());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void insert_Five_Steps_In_Db_ThirdHasRound_Equals_3(){

        List<Data> steps = new ArrayList<>();
        player = mock(Ship.class);
        islands = mock(List.class);
        mapSize = 10;
        stepNo = 0;
        doAnswer(invocation -> {
            steps.add(invocation.getArgument(0));
            return null;
        }).when(dataCollection).insert(isA(Data.class));

        for(int i = 0; i<5; i++){
            doReturn(stepNo++).when(dataCollection).getLastMoveNo();
            dataMongo.insertStep(islands,player,mapSize,0);
        }
        assertEquals(3, steps.get(2).getRound());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void Step_Not_In_DB_Should_Return_Empty_Islands_List(){

        List<Data> steps = new ArrayList<>();
        player = mock(Ship.class);
        islands = mock(List.class);
        mapSize = 10;
        stepNo = 0;
        doAnswer(invocation -> {
            steps.add(invocation.getArgument(0));
            return null;
        }).when(dataCollection).insert(isA(Data.class));

        for(int i = 0; i<5; i++){
            doReturn(stepNo++).when(dataCollection).getLastMoveNo();
            dataMongo.insertStep(islands,player,mapSize,0);
        }
        assertThat(dataMongo.getIslands(1000)).isEmpty();
    }


}
