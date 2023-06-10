using SudokuSolver.Domain;

namespace SudokuSolver.Algorithms;

public class CellFinderBySinglePossibleValue : ICellFinder
{
    public (Cell?, int) Find(HashSet<Cell> cells)
    {
        var cell = cells
            .Where(cell => !cell.IsOpened())
            .Where(cell => cell.PossibleValues.Count == 1)
            .FirstOrDefault();

        return cell is not null
            ? (cell, cell.PossibleValues.First())
            : (null, 0);
    }
}
