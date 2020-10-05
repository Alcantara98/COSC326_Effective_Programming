import java.util.*;

/**
 * Main class to take input and run Epidemic Application.
 * 
 * @author Elbert Alcantara
 */
public class Main {

    /**
     * Main function for application.
     * 
     * @param args "A" for running task A, "B" for running task B
     */
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        ArrayList<String> universe = new ArrayList<String>();

        while (scan.hasNextLine()) {
            String inputLine = scan.nextLine();

            if (inputLine.length() != 0) {
                universe.add(inputLine);
            }
            if (inputLine == "" || !scan.hasNextLine()) {
                Epidemic epic = new Epidemic(universe);
                if (args[0].toString().equals("A")) {
                    epic.runA();
                } else if (args[0].toString().equals("B")) {
                    epic.runB();
                }
                universe.clear();
            }
        }

        scan.close();
    }
}
