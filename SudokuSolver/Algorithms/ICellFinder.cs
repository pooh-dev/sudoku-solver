using SudokuSolver.Domain;

namespace SudokuSolver.Algorithms;

public interface ICellFinder
{
    (Cell?, int) Find(HashSet<Cell> cells);
}
