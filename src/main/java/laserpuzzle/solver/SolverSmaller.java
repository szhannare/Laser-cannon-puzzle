package laserpuzzle.solver;

import laserpuzzle.model.Direction;
import laserpuzzle.model.PuzzleStateSmaller;
import puzzle.solver.BreadthFirstSearch;

/**
 * Gives a solution to the smaller version of the puzzle.
 */
public class SolverSmaller {
    public static void main(String[] args) {
        var bfs = new BreadthFirstSearch<Direction>();
        bfs.solveAndPrintSolution(new PuzzleStateSmaller());
    }
}
