import java.util.Arrays;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

public class LocationGen {
    static void myLocatorMethod(){
        Scanner sc = new Scanner(System.in);

        String s;
        int XPos = 0;
        int YPos = 0;

        do {
            System.out.println("Your input: ");
            s = sc.nextLine();

            switch (s) {
                case "w" -> YPos++;
                case "a" -> XPos--;
                case "s" -> YPos--;
                case "d" -> XPos++;
                case "stop" -> {return;}
                default -> System.out.println("invalid input.");
            }
            int[] CurrentLocation = {XPos,YPos};
            System.out.println("You are located at "+ Arrays.toString(CurrentLocation));
        } while (!Objects.equals(s, "stop"));
    }

    public static void main(String[] args) {
        myLocatorMethod();
    }
}
