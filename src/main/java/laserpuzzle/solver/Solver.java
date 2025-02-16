package laserpuzzle.solver;

import puzzle.solver.BreadthFirstSearch;
import laserpuzzle.model.Direction;
import laserpuzzle.model.PuzzleState;

/**
 * Gives a solution to the puzzle.
 */
public class Solver {
    public static void main(String[] args) {
        var bfs = new BreadthFirstSearch<Direction>();
        bfs.solveAndPrintSolution(new PuzzleState());
    }
}
