package laserpuzzle.play;

import laserpuzzle.model.Direction;
import laserpuzzle.model.PuzzleState;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Prints the interactive game on the console.
 */
public class Console {
    /**
     * Stores which columns have cannons.
     */
    private static int[] cannonCols=new int[]{1, 2, 4, 7, 8, 10, 12};
    /**
     * Stores which rows have cannons.
     */
    private static int[] cannonRows=new int[]{1, 2, 4, 6, 7, 8, 10, 11, 12};
    /**
     * Stores which columns have active cannons in the even numbered turns.
     */
    private static int[] evenCannonCols=new int[]{1, 4, 8, 10, 12};
    /**
     * Stores which rows have active cannons in the even numbered turns.
     */
    private static int[] evenCannonRows=new int[]{2, 6, 8, 10, 12};
    /**
     * Stores which columns have active cannons in the odd numbered turns.
     */
    private static int[] oddCannonCols=new int[]{2, 7};
    /**
     * Stores which rows have active cannons in the odd numbered turns.
     */
    private static int[] oddCannonRows=new int[]{1, 4, 7, 11};


    /**
     * The 1. element of the list stores which columns have cannons, the 2. stores which rows have cannons (counts the rows and columns starting from 0)
     */
    private static List<int[]> cannons=new ArrayList<>();
    /**
     * The 1. element of the list stores which columns have active cannons in the current round, the 2. stores which rows have active cannons in the current round(counts the rows and columns starting from 0)
     */
    private static List<int[]> activeCannons=new ArrayList<>();

    /**
     * Adds the cannons that are active in the current turn
     */
    private static void addActiveCannons(){
        activeCannons=new ArrayList<>();

        if (game.getCurrentTurn()%2!=0){
            addOddCannons();
        }
        else{
            addEvenCannons();
        }
    }

    private static void addCannons(){
        cannons.add(cannonCols);
        cannons.add(cannonRows);
    }

    private static void addOddCannons(){
        activeCannons.add(oddCannonCols);
        activeCannons.add(oddCannonRows);
    }

    private static void addEvenCannons(){
        activeCannons.add(evenCannonCols);
        activeCannons.add(evenCannonRows);
    }

    static PuzzleState game=new PuzzleState();

    static StringBuilder solution=new StringBuilder();

    private static void printTitleAndRules(){
        System.out.println("LASER CANNON LABYRINTH");
        System.out.println();
        System.out.println("Your goal is to reach the other end of the board.");
        System.out.println("You can move left, right, up and down");
        System.out.println("The triangles on the edge of the board mark cannons.");
        System.out.println("The solid ones are inactive in the current round, the others will be inactive in the next.");
        System.out.println("You must not step on the solid squares, neither should you be standing in the row or column of an active cannon.");
        System.out.println("You are standing in the position marked by ✗");

        System.out.println();
    }
    private static void printBoard(int i){
        for(int j=0;j<15;j++){
            if(i==game.getPosition().row()){
                if(j==game.getPosition().col()){
                    System.out.print("✗ ");
                }
                else{
                    printBlackSquare(i, j);
                }
            } else printBlackSquare(i, j);
        }
    }

    private static void printBlackSquare(int i, int j) {
        if (i==14) {
            if(j==6){
                System.out.print("■ ");
            }
            else{
                System.out.print("□ ");
            }
        }
        else if (i==13) {
            if (j == 13) {
                System.out.print("■ ");
            } else {
                System.out.print("□ ");
            }
        }else{
            System.out.print("□ ");
        }
    }

    private static void printGame(){

        System.out.print("  ");

        printCannon(0, "▽ ", "▼ ");

        System.out.println();

        printCannon(1, "▷ ", "▶ ");

        System.out.println();
    }

    private static void printCannon(int columnOrRow, String activeCannon, String inactiveCannon){
        boolean cannon;
        boolean activecannon;

        for(int i=0;i<15;i++){
            cannon=false;
            for(int c: cannons.get(columnOrRow)){
                activecannon=false;
                if(c==i){
                    for(int ca: activeCannons.get(columnOrRow)){
                        if(ca==i){
                            System.out.print(activeCannon);
                            if(columnOrRow==1){
                                printBoard(i);
                            }
                            cannon=true;
                            activecannon=true;
                            break;
                        }
                    }
                    if(!activecannon){
                        System.out.print(inactiveCannon);
                        if(columnOrRow==1){
                            printBoard(i);
                        }
                        cannon=true;
                    }
                }
            }

            if (!cannon){
                if(columnOrRow==0)
                {
                    System.out.print("  ");
                }
                if(columnOrRow==1){
                    if (i==0){
                        System.out.print("S ");
                    }
                    else {
                        System.out.print("  ");
                    }
                    printBoard(i);
                }
            }

            if(columnOrRow==1){
                if (i==14){
                    System.out.println("C");
                }

                else {
                    System.out.println();
                }
            }
        }
    }

    /**
     * Controls the game mechanics of player movement.
     */
    public static void move(){
        Scanner in = new Scanner(System.in);

        System.out.print("What way would you like to move? (w for up, s for down, d for right and a for left) ");
        String direction=in.nextLine();
        solution.append(direction);
        solution.append(" ");

        if (direction.equals("w")){
            game.makeMove(Direction.UP);
        } else if (direction.equals("s")) {
            game.makeMove(Direction.DOWN);
        }else if (direction.equals("d")) {
            game.makeMove(Direction.RIGHT);
        }else if (direction.equals("a")) {
            game.makeMove(Direction.LEFT);
        }
        else {
            System.out.println("Invalid direction");
        }

        addActiveCannons();
    }

    /**
     * Controls and puts the game together.
     */
    public static void game(){
        printTitleAndRules();
        addCannons();
        addActiveCannons();

        play();

        gameEnd();
    }

    /**
     * Prompts the player to make the next move till the game ends.
     */
    private static void play(){
        while (true){
            printGame();
            if(game.gameEnd()){
                break;
            }
            move();
            System.out.println();
        }
    }

    /**
     * When the game ends it prints the right message.
     */
    private static void gameEnd(){
        if (game.won()){
            System.out.println("You won!");
            System.out.println("The buttons you pressed to get here: " + solution);
        }
        else {
            System.out.println("You lost...");
            System.out.println("The buttons you pressed to get here: " + solution);
        }
    }

    public static void main(String[] args) {
        System.out.println();
        System.out.println();
        game();
        System.out.println();
        System.out.println();
    }
}
