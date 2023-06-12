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

    public Field()
    {
        Cells = new();
        foreach (var row in Range(0, 9))
        {
            foreach (var col in Range(0, 9))
            {
                Cells.Add(new(row, col));
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

    public void SetCellValue(Cell cell, int value)
    {
        cell.SetValue(value);

        // remove a possible value from the related cells
        _relatedCellsPredicates.ForEach(
            rcp => Cells.Where(rc => rcp(rc, cell))
                        .ToList()
                        .ForEach(cell => cell.RemoveFromPossibleValues(value)));
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
