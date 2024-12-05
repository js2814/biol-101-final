package ptui;

import model.SimulationModel;
import model.Observer;
import java.util.Scanner;

/**
 * Plain text UI interface for the simulation - View and Controller
 *
 * @author Justin Shen - js2814@rit.edu
 */
public class SimulationPTUI implements Observer<SimulationModel> {
    private SimulationModel model;

    public SimulationPTUI(){
        this.model = new SimulationModel();
        this.model.addObserver(this);
    }

    public void update(SimulationModel model){

    }

    public void run(){
        Scanner in = new Scanner(System.in);
        int populationSize = 0;
        while (populationSize < 30){
            System.out.print("Enter population size: ");
            populationSize = in.nextInt();
            if (populationSize < 30){
                System.out.println("Population size must be 30 or greater");
            }
        }
        System.out.println("Total population size set to " + populationSize);
    }

    public static void main(String[] args){
        SimulationPTUI ptui = new SimulationPTUI();
        ptui.run();
    }
}
