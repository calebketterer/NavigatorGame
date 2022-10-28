import java.util.Random;
import java.util.Scanner;

public class Navigator {
    public static int xbound, startingx, ybound, startingy, currentx, currenty, dir;
    public static int treasurex, treasurey, jumpx, jumpy, smx, smy;
    public static int score = 0, scoreInc = 1, Lifetime = 0, LifetimeInc = 5, LifetimeLimit = 5, ReincarnationCounter = 0;
    public static String in;

    //Collectables
    public static boolean JumpLoaded = false; //Collectable J: Jump 5 spaces or half the board.
    public static boolean ScoreMultiplier = true; //Collectable S: Score x2 (not including round finish.) Once per lifetime.
    public static boolean LifeSupport = false; // Collectable L: An additional life will appear on the map if the player hasn't received an extra life.
    public static boolean LocalTeleportLoaded = false; // Collectable 0: Teleport to the treasure if within [3] block radius
    public static boolean RandomTeleportLoaded = false; //Collectable R: Teleports to random place on the map
    public static boolean CarryOver = false; //Allows abilities to carry between lifetimes

    //Game Modifiers
    public static boolean RandomGame = false; // Generates randomized game if enabled
    public static boolean DiagonalMovement = true; // Collectable D: Enables diagonal movement
    public static boolean AbilityStacking = false; //Collecting two boosts jumps twice as far, collecting two teleports increases radius
    public static boolean AbilityStorage = false; //Storage allows multiple of the same ability to be kept. Doesn't work with AStacking.

    //New Locations
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

    //Generating Methods
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
                else if (i== jumpx &&j== jumpy){
                    System.out.print(" J");//◯●
                }
                else if (i== smx &&j== smy){
                    System.out.print(" S");//◯●
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
    static void genAllCollectables(){
        genTreasure();
        genJump();
        genScoreMultiplier();
    }
    public static void genTreasure(){
        Random rand = new Random();
        treasurex = rand.nextInt(1,xbound);
        treasurey = rand.nextInt(1,ybound);
    }
    public static void genJump(){
        Random rand = new Random();
        jumpx = rand.nextInt(1,xbound);
        jumpy = rand.nextInt(1,ybound);
    }
    public static void genScoreMultiplier(){
        Random rand = new Random();
        smx = rand.nextInt(1,xbound);
        smy = rand.nextInt(1,ybound);
    }

    //Check Methods
    public static void checkTreasure(){
        if (currentx == treasurex && currenty == treasurey){
            System.out.print("""
                    Congrats, you found the treasure!
                    Your score and lifetime limit have been increased.
                    """);
            score = score + scoreInc;
            LifetimeLimit=LifetimeLimit+LifetimeInc;
            genTreasure();
        }
    }
    public static void checkLifetime(){
        if (Lifetime>=25){
            score = score + 10;
            scoreInc++;
            Lifetime = 0;
            LifetimeLimit = 5;
            ReincarnationCounter++;
            ScoreMultiplier = false;
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
    public static void checkJump(){
        if (currentx == jumpx && currenty == jumpy){
            System.out.print("""
                    You collected a jump!
                    Type a capital letter to jump 5 blocks in that direction.
                    This ability only lasts through this lifetime.
                    
                    """);
            JumpLoaded = true;
            genJump();
        }
    }
    public static void checkScoreMultiplier(){
        if(currentx == smx && currenty == smy){
            System.out.print("""
                    You collected a score multiplier!
                    This passively increases the score of each
                    treasure you find for the rest of the game.
                    
                    """);
            scoreInc++;
            genScoreMultiplier();
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
        if (JumpLoaded){System.out.println("You have a jump ready!");}
        System.out.println("Your score is "+score+".");
        if (ReincarnationCounter > 0){System.out.println("You have lived "+ ReincarnationCounter +" lives. ");}
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
            case "W" -> {
                if (JumpLoaded){
                    currenty=currenty+5;
                    Lifetime++;
                    JumpLoaded =false;
                }
                else {System.out.println("You don't have a boost to use!");}
            }
            case "A" -> {
                if (JumpLoaded) {
                    currentx = currentx - 5;
                    Lifetime++;
                    JumpLoaded =false;
                }
                else {System.out.println("You don't have a boost to use!");}
            }
            case "S" -> {
                if (JumpLoaded) {
                    currenty = currenty - 5;
                    Lifetime++;
                    JumpLoaded =false;
                }
                else {System.out.println("You don't have a boost to use!");}
            }
            case "D" -> {
                if(JumpLoaded) {
                    currentx = currentx + 5;
                    Lifetime++;
                    JumpLoaded =false;
                }
                else {System.out.println("You don't have a boost to use!");}
            }
            case "aw","dw","ds","as","AW","DW","AS","DS" -> {
                if (DiagonalMovement) {
                    switch (in) {
                        case "aw" -> {
                            currenty++;
                            currentx--;
                            Lifetime++;
                        }
                        case "as" -> {
                            currentx--;
                            currenty--;
                            Lifetime++;
                        }
                        case "ds" -> {
                            currenty--;
                            currentx++;
                            Lifetime++;
                        }
                        case "dw" -> {
                            currentx++;
                            currenty++;
                            Lifetime++;
                        }
                        case "AW" -> {
                            if (JumpLoaded) {
                                currenty = currenty + 5;
                                currentx = currentx - 5;
                                Lifetime++;
                                JumpLoaded = false;
                            } else {
                                System.out.println("You don't have a boost to use!");
                            }
                        }
                        case "AS" -> {
                            if (JumpLoaded) {
                                currentx = currentx - 5;
                                currenty = currenty - 5;
                                Lifetime++;
                                JumpLoaded = false;
                            } else {
                                System.out.println("You don't have a boost to use!");
                            }
                        }
                        case "DS" -> {
                            if (JumpLoaded) {
                                currenty = currenty - 5;
                                currentx = currentx + 5;
                                Lifetime++;
                                JumpLoaded = false;
                            } else {
                                System.out.println("You don't have a boost to use!");
                            }
                        }
                        case "DW" -> {
                            if (JumpLoaded) {
                                currentx = currentx + 5;
                                currenty = currenty + 5;
                                Lifetime++;
                                JumpLoaded = false;
                            }
                        }
                    }
                } else {System.out.println("Diagonal movement is disabled!\n");}
            }
            case "stop" -> System.exit(0);
            default -> System.out.println("invalid input.\n");
        }
    }

    //Loops
    static void runLifetime(){
        do{
            getMovementInput();
            checkTreasure();
            checkLifetime();
            checkJump();
            checkScoreMultiplier();
            genMap();
            displayPlayerStats();
        }while(Lifetime<LifetimeLimit);
        if (score == 0)System.out.println("You didn't reach the first treasure.");
        else System.out.println("You did not reach the next treasure.\nFinal score: " + score);
    }

    public static void main(String[] args) {
        newConstantLocation();
        genAllCollectables();
        genMap();
        initialMapStats();
        runLifetime();
    }
}