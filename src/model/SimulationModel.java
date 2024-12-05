package model;

import java.util.LinkedList;
import java.util.List;

/**
 * Model for the simulation
 *
 * @author Justin Shen - js2814@rit.edu
 */
public class SimulationModel {
    private List<Observer<SimulationModel>> observers; // observers of the model
    private int populationSize; // current size of the population
    private int generation; // current generation (initial = 0)
    private int males; // number of males in the population
    private int females; // number of females in the population
    private int colorblindMales;
    private int colorblindFemales;
    private int carrierFemales;

    /**
     * Constructor, initializes observers list and other necessary variables
     */
    public SimulationModel(){
        this.observers = new LinkedList<>();
        this.generation = 0;
    }

    /**
     * Add an observer to the model
     * @param observer The observer to add (PTUI/GUI)
     */
    public void addObserver(Observer<SimulationModel> observer){
        this.observers.add(observer);
    }

    /**
     * Notify the observers when the model changes
     */
    private void notifyObservers() {
        for (Observer<SimulationModel> observer: this.observers ) {
            observer.update(this);
        }
    }

    /**
     * Set population size
     * @param populationSize The starting population size (>= 30)
     */
    public void setPopulationSize(int populationSize){
        this.populationSize = populationSize;
    }

    /**
     * Set male/female populations based on percentages
     */
    public void setPercentages(double malePercentage){
        this.males = (int) (this.populationSize * (malePercentage / 100.0));
        this.females = this.populationSize - this.males;
    }

    /**
     * Set colorblindness/carrier status based on percentages
     * @param maleColorBlindPercentage percentage of males that are colorblind
     * @param femaleColorBlindPercentage percentage of females that are colorblind
     * @param femaleCarrierPercentage percentage of females that are carriers
     */
    public void setColorblind(double maleColorBlindPercentage, double femaleColorBlindPercentage,
                              double femaleCarrierPercentage){
        this.colorblindMales = (int) (this.males * (maleColorBlindPercentage / 100.0));
        this.colorblindFemales = (int) (this.females * (femaleColorBlindPercentage / 100.0));
        this.carrierFemales = (int) ( this.females * (femaleCarrierPercentage / 100.0));
    }

    /**
     * toString of the model for the PTUI, prints basic data
     */
    @Override
    public String toString(){
        String result = "";
        result += "Generation " + this.generation; // Generation number
        result += "\nPopulation Size: " + this.populationSize; // Population size
        result += "\nMales: " + this.males + " (" + String.format("%.2f", (double) this.males
                / this.populationSize * 100.0) + "% of total population)"; // Male %
        result += "\nFemales: " + this.females + " (" + String.format("%.2f", (double) this.females
                / this.populationSize * 100.0) + "% of total population)"; // Female %
        result += "\nColorblindness: " + (this.colorblindMales + this.colorblindFemales) + " individuals"
                + " are colorblind (" + String.format("%.2f", (double) (this.colorblindMales +
                this.colorblindFemales) / this.populationSize * 100) + "% of total population)"; // Colorblind %
        result += "\n" + this.colorblindMales + " males are colorblind (" + String.format("%.2f", (double)
                this.colorblindMales / this.males * 100) + "% of total males)"; // Colorblind Males
        result += "\n" + this.colorblindFemales + " females are colorblind (" + String.format("%.2f", (double)
                this.colorblindFemales / this.females * 100) + "% of total females)"; // Colorblind Females
        result += "\n" + this.carrierFemales + " females are carriers (" + String.format("%.2f", (double)
                this.carrierFemales / this.females * 100) + "% of total females)"; // Colorblind Females
        return result;
    }
}
