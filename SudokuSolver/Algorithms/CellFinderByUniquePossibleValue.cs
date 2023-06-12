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
                if (cellFinderResult.IsSuccess())
                {
                    return cellFinderResult;
                }
            }
        }

        return CellFinderResult.NotFound();
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
            ? CellFinderResult.NotFound()
            : CellFinderResult.Success(uniquePossibleValue.Value.First(), uniquePossibleValue.Key);
    }
}
