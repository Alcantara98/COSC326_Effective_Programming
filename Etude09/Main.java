import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        ArrayList<String> universe = new ArrayList<String>();

        while(scan.hasNextLine()){
            String inputLine =  scan.nextLine();

            if(inputLine.length() != 0){
                universe.add(inputLine);
            }
            if(inputLine == "" || !scan.hasNextLine()){
                System.out.println("Hello");
                Epedemic epic = new Epedemic(universe);
                epic.runA();
            }
        }
    }
}
