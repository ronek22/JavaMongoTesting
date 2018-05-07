package Projekt1.database;

import Projekt1.Coord;
import Projekt1.Ship;

import java.net.UnknownHostException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class DataMongo {
    private IDataService service;

    public DataMongo(){
        connect();
    }

    public DataMongo(FakeDataCollection fake){
        service = fake;
    }

    public void connect(){
        try{
            service = new DataCollection();
        } catch(UnknownHostException e){
            e.printStackTrace();
        }
    }

    public List<Data> getAllSteps(){
        return service.getAllSteps();
    }

    public List<Coord> getIslands(int round){
        Data step = service.findByRoundNo(round);
        if(step == null) return Collections.emptyList();
        return step.getIslands();
    }

    public Ship getPlayer(int round){
        Data step = service.findByRoundNo(round);
        if(step == null) return null;
        return step.getPlayer();
    }

    public int getCollisionNo(int round){
        Data step = service.findByRoundNo(round);
        if(step == null) return 0;
        return step.getNumberOfCollisions();
    }

    public void insertStep(List<Coord> islands, Ship player, int mapSize, int noOfCollisions){
        int round = service.getLastMoveNo()+1;
        int posX = player.getX(), posY = player.getY();
        if(posX >= mapSize || posX < 0 || posY >= mapSize || posY < 0)
            throw new IllegalArgumentException("Statek poza plansza!");
        service.insert(new Data(round, islands, player, mapSize, noOfCollisions));
    }

    public boolean isDbEmpty(){
        return service.isDbEmpty();
    }

    public void clearDb(){
        service.eraseAll();
    }

    public void saveStep(Data step){
        service.save(step);
    }









}
