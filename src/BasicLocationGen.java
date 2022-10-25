import java.util.Arrays;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

public class BasicLocationGen {
    public static int YPos = 0,XPos = 0;
    public static String s;

    static void checkInput(){
        switch (s) {
            case "w" -> YPos++;
            case "a" -> XPos--;
            case "s" -> YPos--;
            case "d" -> XPos++;
            case "stop" -> {}
            default -> System.out.println("invalid input.");
        }
    }
    static void myLocatorMethod(){
        Scanner sc = new Scanner(System.in);
        do {
            System.out.println("Your input: ");
            s = sc.nextLine();
            checkInput();
            int[] CurrentLocation = {XPos,YPos};
            System.out.println("You are located at "+ Arrays.toString(CurrentLocation));
        } while (!Objects.equals(s, "stop"));
    }

    public static void main(String[] args) {
        myLocatorMethod();}
}
