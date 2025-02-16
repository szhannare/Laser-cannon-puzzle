package laserpuzzle.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PuzzleStateSmallerTest {
    PuzzleStateSmaller state1=new PuzzleStateSmaller(); // the starting state, right corner
    PuzzleStateSmaller state2 = new PuzzleStateSmaller(new Position(5,5), 20); //goal state, left corner
    PuzzleStateSmaller state3=new PuzzleStateSmaller(new Position(4, 1), 5); //in the column of inactive cannon
    PuzzleStateSmaller state4=new PuzzleStateSmaller(new Position(4, 1), 6); //in the column of active cannon
    PuzzleStateSmaller state5=new PuzzleStateSmaller(new Position(3, 4), 10); //on the (3, 4) no-go square
    PuzzleStateSmaller state6=new PuzzleStateSmaller(new Position(5, 0), 10); //on a non-cannon square
    PuzzleStateSmaller state7=new PuzzleStateSmaller(new Position(5, 0), 11); //on a non-cannon square
    PuzzleStateSmaller state8=new PuzzleStateSmaller(new Position(1, 3), 6); //in the row of inactive cannon
    PuzzleStateSmaller state9=new PuzzleStateSmaller(new Position(1, 3), 5); //in the row of active cannon

    @Test
    void constructor() {
        var position = new Position(0,0);

        PuzzleState state = new PuzzleState(position, 1);
        assertEquals(position, state.getPosition());
    }

    @Test
    void constructor_shouldThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new PuzzleStateSmaller(new Position(-1, -1), 1));
        assertThrows(IllegalArgumentException.class, () -> new PuzzleStateSmaller(new Position(6, 6), 1));
    }

    @Test
    void isSolved() {
        assertFalse(state1.isSolved());
        assertFalse(state3.isSolved());
        assertFalse(state4.isSolved());
        assertFalse(state5.isSolved());
        assertFalse(state6.isSolved());
        assertFalse(state8.isSolved());
        assertFalse(state9.isSolved());
        assertTrue(state2.isSolved());
    }

    @Test
    void gameEnd() {
        assertTrue(state2.gameEnd());
        assertTrue(state3.gameEnd());
        assertTrue(state5.gameEnd());
        assertTrue(state9.gameEnd());
        assertTrue(state4.gameEnd());
        assertFalse(state1.gameEnd());
        assertFalse(state6.gameEnd());
        assertFalse(state7.gameEnd());
        assertFalse(state8.gameEnd());
    }

    @Test
    void won() {
        assertFalse(state1.won());
        assertFalse(state3.won());
        assertFalse(state4.won());
        assertFalse(state5.won());
        assertFalse(state6.won());
        assertTrue(state2.won());
    }

    @Test
    void isLegalMove() {
        assertTrue(state1.isLegalMove(Direction.RIGHT));
        assertTrue(state1.isLegalMove(Direction.DOWN));
        assertFalse(state1.isLegalMove(Direction.UP));
        assertFalse(state1.isLegalMove(Direction.LEFT));

        assertFalse(state2.isLegalMove(Direction.RIGHT));
        assertFalse(state2.isLegalMove(Direction.DOWN));
        assertTrue(state2.isLegalMove(Direction.UP));
        assertTrue(state2.isLegalMove(Direction.LEFT));

        assertTrue(state3.isLegalMove(Direction.RIGHT));
        assertTrue(state3.isLegalMove(Direction.DOWN));
        assertTrue(state3.isLegalMove(Direction.UP));
        assertTrue(state3.isLegalMove(Direction.LEFT));

        assertTrue(state4.isLegalMove(Direction.RIGHT));
        assertTrue(state4.isLegalMove(Direction.DOWN));
        assertTrue(state4.isLegalMove(Direction.UP));
        assertTrue(state4.isLegalMove(Direction.LEFT));

        assertTrue(state5.isLegalMove(Direction.RIGHT));
        assertTrue(state5.isLegalMove(Direction.DOWN));
        assertTrue(state5.isLegalMove(Direction.UP));
        assertTrue(state5.isLegalMove(Direction.LEFT));

        assertTrue(state6.isLegalMove(Direction.RIGHT));
        assertFalse(state6.isLegalMove(Direction.DOWN));
        assertTrue(state6.isLegalMove(Direction.UP));
        assertFalse(state6.isLegalMove(Direction.LEFT));
    }

    @Test
    void makeMove_right(){
        state1.makeMove(Direction.RIGHT);
        assertEquals(new Position(0, 1), state1.getPosition());
        assertEquals(2, state1.getCurrentTurn());

        state2.makeMove(Direction.RIGHT);
        assertEquals(new Position(5, 5), state2.getPosition());
        assertEquals(20, state2.getCurrentTurn());

        state3.makeMove(Direction.RIGHT);
        assertEquals(new Position(4, 2), state3.getPosition());
        assertEquals(6, state3.getCurrentTurn());

        state4.makeMove(Direction.RIGHT);
        assertEquals(new Position(4, 2), state4.getPosition());
        assertEquals(7, state4.getCurrentTurn());

        state5.makeMove(Direction.RIGHT);
        assertEquals(new Position(3, 5), state5.getPosition());
        assertEquals(11, state5.getCurrentTurn());

        state6.makeMove(Direction.RIGHT);
        assertEquals(new Position(5, 1), state6.getPosition());
        assertEquals(11, state6.getCurrentTurn());
    }

    @Test
    void makeMove_down(){
        state1.makeMove(Direction.DOWN);
        assertEquals(new Position(1, 0), state1.getPosition());
        assertEquals(2, state1.getCurrentTurn());

        state2.makeMove(Direction.DOWN);
        assertEquals(new Position(5, 5), state2.getPosition());
        assertEquals(20, state2.getCurrentTurn());

        state3.makeMove(Direction.DOWN);
        assertEquals(new Position(5, 1), state3.getPosition());
        assertEquals(6, state3.getCurrentTurn());

        state4.makeMove(Direction.DOWN);
        assertEquals(new Position(5, 1), state4.getPosition());
        assertEquals(7, state4.getCurrentTurn());

        state5.makeMove(Direction.DOWN);
        assertEquals(new Position(4, 4), state5.getPosition());
        assertEquals(11, state5.getCurrentTurn());

        state6.makeMove(Direction.DOWN);
        assertEquals(new Position(5, 0), state6.getPosition());
        assertEquals(10, state6.getCurrentTurn());
    }

    @Test
    void makeMove_up(){
        state1.makeMove(Direction.UP);
        assertEquals(new Position(0, 0), state1.getPosition());
        assertEquals(1, state1.getCurrentTurn());

        state2.makeMove(Direction.UP);
        assertEquals(new Position(4, 5), state2.getPosition());
        assertEquals(21, state2.getCurrentTurn());

        state3.makeMove(Direction.UP);
        assertEquals(new Position(3, 1), state3.getPosition());
        assertEquals(6, state3.getCurrentTurn());

        state4.makeMove(Direction.UP);
        assertEquals(new Position(3, 1), state4.getPosition());
        assertEquals(7, state4.getCurrentTurn());

        state5.makeMove(Direction.UP);
        assertEquals(new Position(2, 4), state5.getPosition());
        assertEquals(11, state5.getCurrentTurn());

        state6.makeMove(Direction.UP);
        assertEquals(new Position(4, 0), state6.getPosition());
        assertEquals(11, state6.getCurrentTurn());
    }

    @Test
    void makeMove_left(){
        state1.makeMove(Direction.LEFT);
        assertEquals(new Position(0, 0), state1.getPosition());
        assertEquals(1, state1.getCurrentTurn());

        state2.makeMove(Direction.LEFT);
        assertEquals(new Position(5, 4), state2.getPosition());
        assertEquals(21, state2.getCurrentTurn());

        state3.makeMove(Direction.LEFT);
        assertEquals(new Position(4, 0), state3.getPosition());
        assertEquals(6, state3.getCurrentTurn());

        state4.makeMove(Direction.LEFT);
        assertEquals(new Position(4, 0), state4.getPosition());
        assertEquals(7, state4.getCurrentTurn());

        state5.makeMove(Direction.LEFT);
        assertEquals(new Position(3, 3), state5.getPosition());
        assertEquals(11, state5.getCurrentTurn());

        state6.makeMove(Direction.LEFT);
        assertEquals(new Position(5, 0), state6.getPosition());
        assertEquals(10, state6.getCurrentTurn());
    }

    @Test
    void getLegalMoves() {
        var state1LegalMoves = EnumSet.noneOf(Direction.class);
        state1LegalMoves.add(Direction.RIGHT);
        state1LegalMoves.add(Direction.DOWN);

        assertEquals(state1LegalMoves, state1.getLegalMoves());

        var state3LegalMoves = EnumSet.noneOf(Direction.class);
        state3LegalMoves.add(Direction.RIGHT);
        state3LegalMoves.add(Direction.DOWN);
        state3LegalMoves.add(Direction.UP);
        state3LegalMoves.add(Direction.LEFT);

        assertEquals(state3LegalMoves, state3.getLegalMoves());

        var state5LegalMoves = EnumSet.noneOf(Direction.class);
        state5LegalMoves.add(Direction.RIGHT);
        state5LegalMoves.add(Direction.LEFT);
        state5LegalMoves.add(Direction.UP);
        state5LegalMoves.add(Direction.DOWN);

        assertEquals(state5LegalMoves, state5.getLegalMoves());

    }

    @Test
    void testToString() {
        StringBuilder s=new StringBuilder();

        s.append("position: ");
        s.append(state1.getPosition().toString());
        s.append(" | current turn: ");
        s.append(String.valueOf(state1.getCurrentTurn()));

        assertEquals(s.toString(), state1.toString());
    }

    @Test
    void testClone() {
        var clone = state1.clone();

        assertEquals(clone, state1);
        assertNotSame(clone, state1);
    }


}