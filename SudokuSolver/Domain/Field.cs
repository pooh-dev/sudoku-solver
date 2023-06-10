using System.Text;

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
        for (int row = 0; row < 9; row++)
        {
            for (int col = 0; col < 9; col++)
            {
                Cells.Add(new(row, col));
            }
        }
    }

    public void Init(List<string> rows)
    {
        for (int row = 0; row < 9; row++) 
        {
            var rowValues = rows[row]
                .Split(',')
                .Select(int.Parse)
                .ToList();
            
            for (int col = 0; col < 9; col++)
            {
                var initValue = rowValues[col];
                if (initValue < 0 || initValue > 9)
                {
                    throw new ArgumentOutOfRangeException($"The value must be between 0 and 9, but was {initValue}");
                }

                if (initValue > 0)
                {
                    var cell = GetCell(row, col);
                    cell.SetValue(initValue);
                    RemovePossibleValuesFromRelatedCells(cell);
                }
            }
        }
    }

    public Cell GetCell(int row, int col)
    {
        return Cells
            .Where(c => c.Row == row && c.Column == col)
            .FirstOrDefault()
            ?? throw new ArgumentNullException($"There is no Cell({row},{col}");
    }

    public override string? ToString()
    {
        StringBuilder sb = new();
        for (int row = 0; row < 9; row++)
        {
            for (int col = 0; col < 9; col++)
            {
                sb.Append(GetCell(row, col));
            }
            sb.Append("\n\n");
        }
        return sb.ToString();
    }

    private void RemovePossibleValuesFromRelatedCells(Cell cell)
    {
        _relatedCellsPredicates.ForEach(rcp => 
            Cells.Where(rc => rcp(rc, cell))
                  .ToList()
                  .ForEach(c => c.RemoveFromPossibleValues(cell.Value)));
    }
}
