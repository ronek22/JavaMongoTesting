package Projekt1.database;

import org.jongo.MongoCollection;

import java.util.List;

public interface IDataService {
    Data findByRoundNo(int round);
    void insert(Data step);
    List<Data> getAllSteps();
    void eraseAll();
    int getLastMoveNo();
    boolean isDbEmpty();
    void save(Data step);
}
