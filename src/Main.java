import java.util.Scanner;

public class Main {

    private static boolean running = true;

    public static void main(String [] args){

        Model database = new Model();
        Scanner scn = new Scanner(System.in);
        Automat automat = new Automat(database);

        while(running){
            String input = scn.nextLine();
            automat.processInput(input);
        }
    }
}
