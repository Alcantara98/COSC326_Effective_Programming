import java.util.*;
import java.lang.*;

public class Threes{
    public static void main(String[] args){
        int x = 2;
        int y = 3;
        int z = 1;

        for(int i = 0; i < 70;){
            System.out.println("X");
            while(Math.pow(y, 2) < (1 + Math.pow(x - 1, 3))){
                System.out.println("Y");
                while(z < x){
                    System.out.println("Z");
                    if(Math.pow(x, 2) + Math.pow(y, 2) == 1 + Math.pow(z, 3) /*&&
                       !((x%2 == 0 && (y%2 == 0 || z%2 == 0)) || (y%2 == 0 && (x%2 == 0 || z%2 ==0)))
                       && isCoPrime(y, x) && isCoPrime(y, z) && isCoPrime(x, z)*/){
                        System.out.printf("%d %d %d %d", i, x, y ,z);
                        System.out.println("");
                        i += 1;
                        break;
                    }
                    z += 1; 
                }
                z = 1;
                y += 1;
            }
            x += 1;
            y = x + 1;
        }
    }

    public static boolean isCoPrime(int candidateOne, int candidateTwo){
        int remainder = candidateOne % candidateTwo;
        while (remainder != 0){
            candidateOne = candidateTwo;
            candidateTwo = remainder;
            remainder = candidateOne % candidateTwo;
        }
        if(remainder == 0){
            if(candidateTwo == 1){
                return true;
            }else{
                return false;
            }
        }
        return false;
    }
}
