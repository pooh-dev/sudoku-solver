using SudokuSolver.Algorithms;
using System.Text;
using static System.Linq.Enumerable;

namespace SudokuSolver.Domain;

public class Field
{
    public HashSet<Cell> Cells { get; }
    private readonly List<Func<Cell, Cell, bool>> _relatedCellsPredicates = new() 
    {
        (c1, c2) => c1.Row == c2.Row,
        (c1, c2) => c1.Column == c2.Column,
        (c1, c2) => c1.Block == c2.Block 
    };
    private readonly List<Func<Cell, int, bool>> _setOfOpenedCells = new()
    {
        (cell, rowIndex) => cell.IsOpened() && cell.Row == rowIndex,
        (cell, columnIndex) => cell.IsOpened() && cell.Row == columnIndex,
        (cell, blockIndex) => cell.IsOpened() && cell.Row == blockIndex
    };

    public Field()
    {
        Cells = new();
    }

    public void Init(List<string> rows)
    {
        foreach (var row in Range(0, 9))
        {
            foreach (var col in Range(0, 9))
            {
                Cells.Add(new(row, col));
            }
        }

        foreach (var row in Range(0, 9))
        {
            var rowValues = rows[row]
                .Split(',')
                .Select(int.Parse)
                .ToList();

            foreach (var col in Range(0, 9))
            {
                var initValue = rowValues[col];
                if (initValue < 0 || initValue > 9)
                {
                    throw new ArgumentOutOfRangeException($"The value must be between 0 and 9, but was {initValue}");
                }

                if (initValue > 0)
                {
                    SetCellValue(row, col, initValue);
                }
            }
        }
    }

    public Cell GetCell(int row, int col)
    {
        return Cells
            .Where(c => c.Row == row && c.Column == col)
            .FirstOrDefault()
            ?? throw new ArgumentNullException($"There is no Cell({row},{col})");
    }

    public void SetCellValue(int row, int column, int value)
    {
        SetCellValue(GetCell(row, column), value);
    }

    public void SetCellValue(CellFinderResult cellFinderResult)
    {
        SetCellValue(cellFinderResult.Cell, cellFinderResult.Value);
    }

    public void SetCellValue(Cell cell, int value)
    {
        cell.SetValue(value);

        // remove a possible value from the related cells
        _relatedCellsPredicates.ForEach(
            rcp => Cells.Where(rc => rcp(rc, cell))
                        .ToList()
                        .ForEach(cell => cell.RemoveFromPossibleValues(value)));
    }

    public bool AreAllCellsOpen()
    {
        foreach (var idx in Range(0, 9))
        {
            foreach (var soc in _setOfOpenedCells)
            {
                HashSet<int> requiredValues = new() { 1, 2, 3, 4, 5, 6, 7, 8, 9 };

                Cells.Where(cell => soc(cell, idx))
                     .ToList()
                     .ForEach(cell => requiredValues.Remove(cell.Value));

                if (requiredValues.Count != 0)
                {
                    return false;
                }
            }
        }
        return true;
    }

    public Field Copy()
    {
        Field newField = new();

        foreach (var cell in Cells)
        {
            newField.Cells.Add(cell.Copy());
        }

        return newField;
    }

    public override string? ToString()
    {
        StringBuilder sb = new();
        foreach (var row in Range(0, 9))
        {
            foreach (var col in Range(0, 9))
            {
                sb.Append(GetCell(row, col));
            }
            sb.Append("\n\n");
        }
        return sb.ToString();
    }
}
