package laserpuzzle.model;

import puzzle.State;

import java.util.EnumSet;
import java.util.Set;

/**
 * Represents the state of a smaller version of the puzzle to demonstrate the logic is functional.
 */
public class PuzzleStateSmaller implements State<Direction> {
    /**
     * The size of the board.
     */
    public static final int BOARD_SIZE=6;

    /**
     * Stores which columns have active cannons in the even numbered turns.
     */
    private static final int[] evenCannonCols=new int[]{1};
    /**
     * Stores which rows have active cannons in the even numbered turns.
     */
    private static final int[] evenCannonRows=new int[]{2};
    /**
     * Stores which columns have active cannons in the odd numbered turns.
     */
    private static final int[] oddCannonCols=new int[]{2};
    /**
     * Stores which rows have active cannons in the odd numbered turns.
     */
    private static final int[] oddCannonRows=new int[]{1, 4};

    /**
     * The position of the player.
     */
    private Position position;

    /**
     * Which turn the player is currently in.
     */
    private int currentTurn;

    /**
     * Creates a {@code PuzzleStateSmaller} object that corresponds to the original initial state of the puzzle.
     */
    public PuzzleStateSmaller() {
        this.position=new Position(0, 0);
        this.currentTurn=1;
    }
    /**
     * Creates a {@code PuzzleStateSmaller} object initializing the positions of the pieces with the positions specified. The constructor expects an array of four {@code Position} objects or four {@code Position} objects.
     *
     * @param position the initial position of the player
     * @param turn the turn which the player is currently in
     */
    public PuzzleStateSmaller(Position position, int turn) {
        checkPositions(position);
        this.position = position;
        this.currentTurn=turn;
    }


    /**
     * {@return whether the player's position is within the board}
     * @param position the position in question
     */
    private boolean isOnBoard(Position position){
        return position.row()>=0 && position.row() < BOARD_SIZE &&
                position.col()>=0 && position.col() < BOARD_SIZE;
    }

    /**
     * Checks if the player is given a viable position when a {@code PuzzleStateSmaller} is instantiated
     * @param position the position of the player given in the constructor
     */
    private void checkPositions(Position position){
        if (!isOnBoard(position)){
            throw new IllegalArgumentException();
        }
    }

    /**
     * {@return whether the puzzle is solved}
     */
    @Override
    public boolean isSolved() {
        return (this.position.col()==5 && this.position.row()==5);
    }

    /**
     * {@return whether during the current turn the player is standing in the row or column of an active cannon}
     */
    private boolean activeCannon(){
        if(currentTurn%2==0){
            return onTurn(evenCannonRows, evenCannonCols);
        }
        else{
            return onTurn(oddCannonRows, oddCannonCols);
        }
    }

    /**
     * {@return whether the player is standing in the row or column of an active cannon}
     * @param cannonRows which rows have active cannons
     * @param cannonCols which cols have active cannons
     */
    private boolean onTurn(int[] cannonRows, int[] cannonCols) {
        for(int i : cannonRows){
            if(this.position.row()==i){
                return true;
            }
        }
        for(int i: cannonCols){
            if(this.position.col()==i){
                return true;
            }
        }

        return false;
    }


    /**
     * {@return whether the player is standing on a blocked square}
     */
    private boolean blockedSquare(){
        return position.col() == 4 & position.row() == 3;
    }

    public boolean gameEnd(){
        if (isSolved()){
            return true;
        }
        if (activeCannon()){
            return true;
        }
        return blockedSquare();
    }

    public boolean won(){
        if(gameEnd()){
            return isSolved();
        }
        return false;
    }

    /**
     * {@return whether the player can move to the direction specified, it's within the board}
     *
     * @param direction a direction to which the player is intending to move
     */
    @Override
    public boolean isLegalMove(Direction direction) {
        return switch (direction) {
            case UP -> canMoveUp();
            case RIGHT -> canMoveRight();
            case DOWN -> canMoveDown();
            case LEFT -> canMoveLeft();
        };
    }

    private boolean canMoveUp(){
        return (isOnBoard(this.position.move(Direction.UP)));
    }
    private boolean canMoveRight(){
        return (isOnBoard(this.position.move(Direction.RIGHT)));
    }
    private boolean canMoveDown(){
        return (isOnBoard(this.position.move(Direction.DOWN)));
    }
    private boolean canMoveLeft(){
        return (isOnBoard(this.position.move(Direction.LEFT)));
    }

    /**
     * The player moves to the direction specified, taking a step left, right, down or up.
     *
     * @param direction the direction to which the player moves
     */
    @Override
    public void makeMove(Direction direction) {
        switch (direction) {
            case UP -> moveUp();
            case RIGHT -> moveRight();
            case DOWN -> moveDown();
            case LEFT -> moveLeft();
        }
    }

    private void moveUp(){
        if (isLegalMove(Direction.UP)){
            this.position = this.position.move(Direction.UP);
            increaseCurrentTurn();
        }
    }

    private void moveRight(){
        if (isLegalMove(Direction.RIGHT)){
            this.position = this.position.move(Direction.RIGHT);
            increaseCurrentTurn();
        }
    }

    private void moveDown(){
        if (isLegalMove(Direction.DOWN)){
            this.position = this.position.move(Direction.DOWN);
            increaseCurrentTurn();
        }
    }

    private void moveLeft(){
        if (isLegalMove(Direction.LEFT)){
            this.position = this.position.move(Direction.LEFT);
            increaseCurrentTurn();
        }
    }

    /**
     * Increases the number of the current turn
     */
    private void increaseCurrentTurn(){
        this.currentTurn=this.currentTurn+1;
    }

    /**
     * {@return the set of directions to which the player can be moved}
     */
    @Override
    public Set<Direction> getLegalMoves() {
        var legalMoves = EnumSet.noneOf(Direction.class);
        for (var direction : Direction.values()) {
            if (isLegalMove(direction)) {
                legalMoves.add(direction);
            }
        }
        return legalMoves;
    }

    @Override
    public String toString() {
        StringBuilder s=new StringBuilder();

        s.append("position: ");
        s.append(position.toString());
        s.append(" | current turn: ");
        s.append(String.valueOf(currentTurn));

        return s.toString();
    }

    /**
     * {@return the deepcloned {@code PuzzleStateSmaller} object /the object itself, not just the reference to it/}
     */
    @Override
    public PuzzleStateSmaller clone() {
        PuzzleStateSmaller clone = new PuzzleStateSmaller();
        clone.position = new Position(this.position.row(), this.position.col());
        return clone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PuzzleStateSmaller that = (PuzzleStateSmaller) o;
        return position.equals(that.position);
    }

    public Position getPosition() {
        return position;
    }

    public int getCurrentTurn() {
        return currentTurn;
    }
}
