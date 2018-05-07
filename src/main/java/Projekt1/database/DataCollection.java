package Projekt1.database;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.jongo.MongoCursor;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DataCollection implements IDataService {

    private MongoCollection steps;

    public DataCollection() throws UnknownHostException{
        @SuppressWarnings({ "deprecation", "resource" })
        DB db = new MongoClient().getDB("GameData");
        Jongo jongo = new Jongo(db);
        steps = jongo.getCollection("steps");
    }

    public Data findByRoundNo(int round){
        return steps.findOne("{round: #", round).as(Data.class);
    }


    public void insert(Data step){
        steps.insert(step);
    }

    public List<Data> getAllSteps(){
        List<Data> all = new ArrayList<Data>();
        MongoCursor<Data> cursor = steps.find().as(Data.class);
        if(cursor == null)
            return Collections.emptyList();
        for(Data t : cursor) {
            all.add(t);
        }
        return all;
    }

    public void eraseAll() {
        steps.drop();
    }

    public int getLastMoveNo(){
        Data last = steps.findOne().orderBy("{round: -1}").as(Data.class);
        if(last == null) return 0;
        return last.getRound();
    }

    public boolean isDbEmpty(){
        return steps.count() == 0;
    }


    public void save(Data step){steps.save(step);}
}
