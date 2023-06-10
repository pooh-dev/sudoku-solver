using SudokuSolver.Domain;

namespace SudokuSolver.Algorithms;

public class CellFinderByUniquePossibleValue : ICellFinder
{
    public (Cell?, int) Find(HashSet<Cell> cells)
    {
        for (int i = 0; i < 9; i++)
        {
            FindInSetCells(cell => !cell.IsOpened() && cell.Row == i, cells);
            FindInSetCells(cell => !cell.IsOpened() && cell.Column == i, cells);
            FindInSetCells(cell => !cell.IsOpened() && cell.Block == i, cells);

            // TODO: Finish this algorithm

        }

        throw new NotImplementedException();
    }

    private (Cell?, int) FindInSetCells(Func<Cell, bool> selectedCells, HashSet<Cell> cells)
    {
        var possibleValuesDictionary = new Dictionary<int, HashSet<Cell>>();

        foreach (var cell in cells.Where(selectedCells))
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
            ? (null, 0)
            : (uniquePossibleValue.Value.First(), uniquePossibleValue.Key);
    }
}
