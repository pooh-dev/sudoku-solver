using SudokuSolver.Domain;
using static System.Linq.Enumerable;

namespace SudokuSolver.Algorithms;

public class CellFinderByUniquePossibleValue : ICellFinder
{
    private readonly List<Func<Cell, int, bool>> _selectedCellsPredicates = new()
    {
        (cell, rowIndex)    => !cell.IsOpened() && cell.Row == rowIndex,
        (cell, columnIndex) => !cell.IsOpened() && cell.Column == columnIndex,
        (cell, blockIndex)  => !cell.IsOpened() && cell.Block == blockIndex
    };

    public CellFinderResult Find(HashSet<Cell> cells)
    {
        foreach (var idx in Range(0, 9))
        {
            foreach (var scp in _selectedCellsPredicates)
            {
                var cellFinderResult = FindInSelectedCells(cells.Where(cell => scp(cell, idx)));
                if (cellFinderResult.IsCellFound())
                {
                    return cellFinderResult;
                }
            }
        }

        return CellFinderResult.CellNotFound();
    }

    private CellFinderResult FindInSelectedCells(IEnumerable<Cell> selectedCells)
    {
        var possibleValuesDictionary = new Dictionary<int, HashSet<Cell>>();

        foreach (var cell in selectedCells)
        {
            foreach (var possibleValue in cell.PossibleValues)
            {
                if (possibleValuesDictionary.TryGetValue(possibleValue, out var cellsWithPossibleValue))
                {
                    cellsWithPossibleValue.Add(cell);
                }
                else
                {
                    possibleValuesDictionary.Add(possibleValue, new() { cell });
                }
            }
        }

        var uniquePossibleValue = possibleValuesDictionary
            .Where(pair => pair.Value.Count == 1)
            .FirstOrDefault();

        return uniquePossibleValue.Equals(default(KeyValuePair<int, HashSet<Cell>>))
            ? CellFinderResult.CellNotFound()
            : CellFinderResult.CellFound(uniquePossibleValue.Value.First(), uniquePossibleValue.Key);
    }
}
