package Projekt1.database;

import java.util.ArrayList;
import java.util.List;

public class FakeDataCollection implements IDataService{
    private List<Data> steps;

    public FakeDataCollection(){
        steps = new ArrayList<>();
    }

    public Data findByRoundNo(int round){
        for(Data step : steps){
            if(step.getRound() == round)
                return step;
        }
        return null;
    }


    public void insert(Data step){
        steps.add(step);
    }

    public List<Data> getAllSteps(){
        return steps.size() != 0 ? steps : null;
    }

    public void eraseAll() {
        steps.clear();
    }

    public int getLastMoveNo(){
        if(steps.size() != 0)
            return steps.get(steps.size() - 1).getRound();
        return 0;

    }

    public boolean isDbEmpty(){
        return steps.size() == 0;
    }


    public void save(Data step){
        boolean saved = false;
        for(Data s : steps){
            if(s.getRound() == step.getRound()) {
                s = step;
                saved = true;
            }
        }
        if(!saved){
            throw new IllegalArgumentException("Nie mozna zapisac!");
        }
    }
}
