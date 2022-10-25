import java.util.Random;
import java.util.Scanner;

public class Main {
    public static int xbound, startingx, ybound, startingy, currentx, currenty, dir;
    public static int treasurex, treasurey;
    public static int score = 0, scoreInc = 1, Lifetime = 0, LifetimeInc = 5, LifetimeLimit = 5;
    public static String in;

    static void newConstantLocation(){
        xbound = 10;
        startingx = 5;
        ybound = 10;
        startingy = 5;
        currentx = startingx;
        currenty = startingy;
    } //Constant 20 by 20 start in the middle.
    static void newRandomLocation(){
        Random rand = new Random();
        xbound = rand.nextInt(10,20);
        startingx = rand.nextInt(3,xbound-4);
        ybound = rand.nextInt(10,20);
        startingy = rand.nextInt(3,ybound-4);
        currentx = startingx;
        currenty = startingy;

    } //For automatic, random inputs
    static void newCustomLocation(){
        Random rand = new Random();

        Scanner input = new Scanner(System.in);
        System.out.println("Please enter an x boundary: ");
        xbound = input.nextInt();

        System.out.println("Please enter a y boundary:  ");
        ybound = input.nextInt();

        System.out.println("Please enter an x location between 0 and " + xbound + ".");
        startingx = input.nextInt();
        if (startingx<=1||startingx>=xbound){
            startingx = rand.nextInt(1,xbound-1);
            System.out.println(startingx+ " has been chosen for you.");
        }

        System.out.println("Please enter a y location between 0 and " + ybound + ".");
        startingy = input.nextInt();
        if (startingy<=1||startingy>=ybound){
            startingy = rand.nextInt(1,ybound-1);
            System.out.println(startingy+ " has been chosen for you.");
        }

        currentx = startingx;
        currenty = startingy;

        System.out.print("\n");
    } //For manual inputs

    static void checkLifetime(){
        if (Lifetime>=25){
            score = score + 10;
            scoreInc++;
            Lifetime = 0;
            LifetimeLimit = 5;
            System.out.println("""
                    =====================================
                    You have been reincarnated!
                    Your lifetime has been reset to zero
                    You gained 10 points
                    The reward for finding treasure
                    is permanently increased by 1.
                    =====================================""");
        }
    }

    static void genMap(){
        Random rand = new Random();
        if (Lifetime==0){dir = rand.nextInt(1,5);}// Land is NWES

        for (int j = ybound; j >= 1; j--) {
            if(j<10) {System.out.print(" " + j);} else {System.out.print(j);} // Row Numbers

            for (int i = 1; i <= xbound; i++) {

                if (i==currentx&&j==currenty) {
                    System.out.print(" ●");//◯●
                }
                else if (i== treasurex &&j== treasurey){
                    System.out.print(" T");//◯●
                }
                else {System.out.print(" ■");
                    /*if (startingy<ybound/2){dir=1;}
                    if (startingx>xbound/2){dir=2;}
                    if (startingx<xbound/2){dir=3;}
                    if (startingy>ybound/2){dir=4;}
                    switch (dir) {
                        case 1 -> {
                            if (j < startingy) {System.out.print(" ~");}
                            else {System.out.print(" ■");}
                        } // Land is North
                        case 2 -> {
                            if (i > startingx) {System.out.print(" ~");}
                            else {System.out.print(" ■");}
                        } // Land is West
                        case 3 -> {
                            if (i < startingx) {System.out.print(" ~");}
                            else {System.out.print(" ■");}
                        } // Land is East
                        case 4 -> {
                            if (j > startingy) {System.out.print(" ~");}
                            else {System.out.print(" ■");}

                        } // Land is South
                        default -> System.out.print(" X");//⬜■
                    }*///Random wave/land direction

                }
            } // Acii Map Gen
            System.out.print("\n");
        } //Row Numbers, Ascii, and Current
        for (int k = 0;k<xbound+1;k++) {
            switch (k) {
                case 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 -> System.out.print(" " + k);
                default -> System.out.print(k);
            }
        }// Column Numbers
        System.out.println("\n");
    }
    public static void genTreasure(){
        Random rand = new Random();
        treasurex = rand.nextInt(1,xbound);
        treasurey = rand.nextInt(1,ybound);
    }
    public static void checkTreasure(){
        if (currentx== treasurex &&currenty== treasurey){
            System.out.print("""
                    Congrats, you found the treasure!
                    Your score and lifetime limit have been increased.
                    """);
            score=score+scoreInc;
            LifetimeLimit=LifetimeLimit+LifetimeInc;
            genTreasure();
        }
    }

    static void initialMapStats(){
        System.out.println("Grid size: "+xbound+" by "+ ybound+". ");
        System.out.println("Starting Location: (" + startingx+", "+startingy+").");
        if (treasurex == 0|| treasurey == 0){System.out.println("There is no treasure.");}
        else {System.out.println("Original Treasure Location: ("+ treasurex +", "+ treasurey +").");}
    }
    static void displayPlayerStats(){
        System.out.println("You are " + Lifetime + " turns old and are expected to live until turn " + LifetimeLimit + ".");
        System.out.println("Your score is "+score+".");
        System.out.println("You are located at ("+ currentx + ", " + currenty +").\n");
    }
    static void getMovementInput(){
        Scanner input = new Scanner(System.in);
        System.out.println("Your input: ");
        in = input.nextLine();
        switch (in) {
            case "w" -> {
                currenty++;
                Lifetime++;
            }
            case "a" -> {
                currentx--;
                Lifetime++;
            }
            case "s" -> {
                currenty--;
                Lifetime++;
            }
            case "d" -> {
                currentx++;
                Lifetime++;
            }
            case "stop" -> System.exit(0);
            default -> System.out.println("invalid input.\n");
        }
    }

    static void runLifetime(){
        do{
            getMovementInput();
            checkTreasure();
            checkLifetime();
            genMap();
            displayPlayerStats();
        }while(Lifetime<LifetimeLimit);
        if (score == 0)System.out.println("You didn't reach the first treasure.");
        else System.out.println("You did not reach the next treasure.\nTreasures found: "+score);
    }

    public static void main(String[] args) {
        newConstantLocation();
        genTreasure();
        initialMapStats();
        genMap();
        runLifetime();
    }
}