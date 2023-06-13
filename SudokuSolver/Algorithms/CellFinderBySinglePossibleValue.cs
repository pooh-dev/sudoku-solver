using SudokuSolver.Domain;

namespace SudokuSolver.Algorithms;

public class CellFinderBySinglePossibleValue : ICellFinder
{
    public CellFinderResult Find(HashSet<Cell> cells)
    {
        var cell = cells
            .Where(cell => !cell.IsOpened() && cell.PossibleValues.Count == 1)
            .FirstOrDefault();

        return cell is null
            ? CellFinderResult.CellNotFound()
            : CellFinderResult.CellFound(cell, cell.PossibleValues.First());
    }
}
