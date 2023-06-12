using SudokuSolver.Domain;

namespace SudokuSolver.Algorithms;

public interface ICellFinder
{
    CellFinderResult Find(HashSet<Cell> cells);
}
