package model;

import java.util.LinkedList;
import java.util.List;

/**
 * Model for the simulation
 *
 * @author Justin Shen - js2814@rit.edu
 */
public class SimulationModel {
    private final List<Observer<SimulationModel>> observers; // observers of the model
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
     * Simulates the next generation of the current population by simulating reproduction for each
     * couple and the inheritance of the colorblindness trait, then updating the statistics
     */
    public void nextGeneration(){
        this.generation++;
        int newMales = 0, newFemales = 0, newColorblindMales = 0, newColorblindFemales = 0, newCarrierFemales = 0;

        // Continue until no pairs left
        while (this.males > 0 && this.females > 0){
            boolean maleColorblind = false, femaleColorblind = false, femaleCarrier = false;
            // Determine whether male parent is colorblind
            if (Math.random() < (double) this.colorblindMales / this.males){
                maleColorblind = true;
                this.colorblindMales--;
            }
            this.males--;
            // Determine whether female parent is colorblind and/or carrier
            if (Math.random() < (double) this.colorblindFemales / this.females){
                femaleColorblind = true;
                this.colorblindFemales--;
            } else if (Math.random() < (double) this.carrierFemales / (this.females - this.colorblindFemales)){
                femaleCarrier = true;
                this.carrierFemales--;
            }
            this.females--;
            int[] stats = reproduce(maleColorblind, femaleColorblind, femaleCarrier);
            newMales += stats[0];
            newFemales += stats[1];
            newColorblindMales += stats[2];
            newColorblindFemales += stats[3];
            newCarrierFemales += stats[4];
        }

        // Update simulation stats
        this.males = newMales;
        this.females = newFemales;
        this.colorblindMales = newColorblindMales;
        this.colorblindFemales = newColorblindFemales;
        this.carrierFemales = newCarrierFemales;
        this.populationSize = this.males + this.females;
        notifyObservers();
    }

    /**
     * Helper function for nextGeneration, simulates reproduction for a single couple
     * @param maleColorblind Whether the male parent is colorblind
     * @param femaleColorblind Whether the female parent is colorblind
     * @param femaleCarrier Whether the female parent is a carrier
     * @return Int array containing the amount of male children, amount of female children, amount of colorblind
     *         males, amount of colorblind females, and amount of carrier females
     */
    public int[] reproduce(boolean maleColorblind, boolean femaleColorblind, boolean femaleCarrier){
        int[] stats = new int[5];
        int newMales = 0, newFemales = 0, newColorblindMales = 0, newColorblindFemales = 0, newCarrierFemales = 0;
        int numChildren;
        // Random seed to determine number of children, loosely based on real statistics
        double randomSeed = Math.random();
        if (randomSeed < 0.10) {
            numChildren = 0; // 10% chance of no children
        } else if (randomSeed < 0.30) {
            numChildren = 1; // 20% chance of 1 child
        } else if (randomSeed < 0.65) {
            numChildren = 2; // 35% chance of 2 children
        } else if (randomSeed < 0.9) {
            numChildren = 3; // 25% chance of 3 children
        } else if (randomSeed < 0.95){
            numChildren = 4; // 5% chance of 4 children
        } else {
            numChildren = 5; // 5% chance of 5 children (5+ not represented for simplicity)
        }
        for (int i = 0; i < numChildren; i++){
            // Random seed to determine gender (50/50)
            boolean male = Math.random() < 0.5;
            boolean colorblind = false, carrier = false;
            if (male) { // child is male
                if (maleColorblind && femaleColorblind) {
                    colorblind = true; // Both parents are colorblind (100% colorblind)
                } else if (maleColorblind && femaleCarrier){
                    colorblind = Math.random() < 0.5; // Male is colorblind, female is carrier (50% colorblind)
                } else if (femaleColorblind){
                    colorblind = true; // Female is colorblind, male is not (100% colorblind)
                } else if (femaleCarrier){
                    colorblind = Math.random() < 0.5; // Female is carrier, male is not (50% colorblind)
                }
            } else { // child is female
                if (maleColorblind && femaleColorblind) {
                    colorblind = true; // Both parents are colorblind (100% colorblind)
                } else if (maleColorblind && femaleCarrier){
                    colorblind = Math.random() < 0.5;
                    carrier = !colorblind; // Male is colorblind, female is carrier (50% colorblind, 50% carrier)
                } else if (maleColorblind){
                    carrier = true; // Male is colorblind, female is not (100% carrier)
                } else if (femaleColorblind){
                    carrier = true; // Female is colorblind, male is not (100% carrier)
                } else if (femaleCarrier){
                    carrier = Math.random() < 0.5; // Female is colorblind, male is not (50% carrier)
                }
            }
            // Increment stats by the result
            if (male) {
                if (colorblind) {
                    newColorblindMales++;
                }
                newMales++;
            } else {
                if (colorblind) {
                    newColorblindFemales++;
                } else if (carrier) {
                    newCarrierFemales++;
                }
                newFemales++;
            }
        }
        stats[0] = newMales;
        stats[1] = newFemales;
        stats[2] = newColorblindMales;
        stats[3] = newColorblindFemales;
        stats[4] = newCarrierFemales;
        return stats;
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
                this.carrierFemales / this.females * 100) + "% of total females)"; // Carrier Females
        return result;
    }
}
