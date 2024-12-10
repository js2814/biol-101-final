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
    private final SimulationModel model;
    /** Real-life statistics are used for the default values */
    private static final double DEFAULT_MALE_COLORBLIND = 8.0;
    private static final double DEFAULT_FEMALE_COLORBLIND = 0.5;
    private static final double DEFAULT_FEMALE_CARRIER = 15.0;

    /**
     * Constructor, initializes the model and adds itself as an observer
     */
    public SimulationPTUI(){
        this.model = new SimulationModel();
        this.model.addObserver(this);
    }

    public void update(SimulationModel model){
        System.out.println(this.model);
    }

    /**
     * Main loop for functionality, prompts user for input and runs the simulation based on it
     */
    public void run(){
        Scanner in = new Scanner(System.in);
        // Get population size from input then update model (must be >= 30)
        int populationSize = 0;
        while (populationSize < 30){
            System.out.print("Enter population size: ");
            populationSize = in.nextInt();
            if (populationSize < 30){
                System.out.println("Population size must be 30 or greater");
            }
        }
        System.out.println("Total population size set to " + populationSize);
        this.model.setPopulationSize(populationSize);
        // Get male percentage from input then update model (must be between 0 and 100)
        double malePercentage = 0.0;
        while (malePercentage <= 0.0 || malePercentage >= 100.0){
            System.out.print("Enter the percentage of males in the population: ");
            malePercentage = in.nextDouble();
            if (malePercentage <= 0.0 || malePercentage >= 100.0){
                System.out.println("Percentage must be between 0.0 and 100.0");
            }
        }
        System.out.println("Percentage of males set to: " + malePercentage + "%");
        System.out.println("Percentage of females set to: " + (100 - malePercentage) + "%");
        this.model.setPercentages(malePercentage);
        // Get colorblindness/carrier percentages from input then update model
        double maleColorBlindPercentage = 0.0;
        double femaleColorBlindPercentage = 0.0;
        double femaleCarrierPercentage = 0.0;
        while (maleColorBlindPercentage <= 0.0 || maleColorBlindPercentage >= 100.0){
            System.out.print("Enter the percentage of males that are colorblind (-1 to use defaults): ");
            maleColorBlindPercentage = in.nextDouble();
            if (maleColorBlindPercentage == -1){
                maleColorBlindPercentage = DEFAULT_MALE_COLORBLIND;
            } else if (maleColorBlindPercentage <= 0.0 || maleColorBlindPercentage >= 100.0){
                System.out.println("Percentage must be between 0.0 and 100.0");
            }
        }
        while (femaleColorBlindPercentage <= 0.0 || femaleColorBlindPercentage >= 100.0){
            System.out.print("Enter the percentage of females that are colorblind (-1 to use defaults): ");
            femaleColorBlindPercentage = in.nextDouble();
            if (femaleColorBlindPercentage == -1){
                femaleColorBlindPercentage = DEFAULT_FEMALE_COLORBLIND;
            } else if (femaleColorBlindPercentage <= 0.0 || femaleColorBlindPercentage >= 100.0){
                System.out.println("Percentage must be between 0.0 and 100.0");
            }
        }
        while (femaleCarrierPercentage <= 0.0 || femaleCarrierPercentage >= 100.0){
            System.out.print("Enter the percentage of females that are carriers (-1 to use defaults): ");
            femaleCarrierPercentage = in.nextDouble();
            if (femaleCarrierPercentage == -1){
                femaleCarrierPercentage = DEFAULT_FEMALE_CARRIER;
            } else if (femaleCarrierPercentage <= 0.0 || femaleCarrierPercentage >= 100.0){
                System.out.println("Percentage must be between 0.0 and 100.0");
            }
        }
        System.out.println("Percentage of males with color blindness set to: " + maleColorBlindPercentage + "%");
        System.out.println("Percentage of females with color blindness set to: " + femaleColorBlindPercentage + "%");
        System.out.println("Percentage of female carriers of color blindness set to: " + femaleCarrierPercentage + "%");
        model.setColorblind(maleColorBlindPercentage, femaleColorBlindPercentage, femaleCarrierPercentage);
        // Generation 0
        this.update(model);
        // Generation 1+ (main loop)
        int x = 1;
        while (x != -1){
            System.out.print("Type 0 to continue to next generation or -1 to exit: ");
            x = in.nextInt();
            if (x == 0) {
                System.out.println("Placeholder");
            }
        }
    }

    /**
     * Main method, creates the PTUI and runs it
     * @param args None
     */
    public static void main(String[] args){
        SimulationPTUI ptui = new SimulationPTUI();
        ptui.run();
    }
}
