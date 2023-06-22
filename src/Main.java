import java.util.Scanner;

/**
 * Trida pro spousteni celeho programu
 */
public class Main {

    private static boolean running = true;

    public static void main(String [] args){
        /**Komunikace s data*/
        Model database = new Model();
        Scanner scn = new Scanner(System.in);
        /**Hlavni ridici trida automatu*/
        Automat automat = new Automat(database);

        while(running){
            String input = scn.nextLine();
            automat.processInput(input);
        }
    }
}
