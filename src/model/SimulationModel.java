package model;

import java.util.List;

/**
 * Model for the simulation
 *
 * @author Justin Shen - js2814@rit.edu
 */
public class SimulationModel {
    private List<Observer<SimulationModel>> observers;

    public SimulationModel(){

    }

    public void addObserver(Observer<SimulationModel> observer){
        this.observers.add(observer);
    }
}
