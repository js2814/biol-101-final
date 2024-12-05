import java.util.Scanner;

public class Simulation {
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

    }
}
