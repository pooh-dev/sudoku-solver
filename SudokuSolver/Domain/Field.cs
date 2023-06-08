using System.Text;

namespace SudokuSolver.Domain;

public class Field
{
    private readonly HashSet<Cell> _cells;
    private readonly List<Func<Cell, Cell, bool>> _relatedCellsPredicates = new() 
    {
        (c1, c2) => c1.Row == c2.Row,
        (c1, c2) => c1.Column == c2.Column,
        (c1, c2) => c1.Block == c2.Block 
    };

    public Field()
    {
        _cells = new();
        for (int row = 0; row < 9; row++)
        {
            for (int col = 0; col < 9; col++)
            {
                _cells.Add(new(row, col));
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
                var cell = GetCell(row, col);
                cell.SetValue(rowValues[col]);
                RemovePossibleValuesFromRelatedCells(cell);
            }
        }
    }

    public Cell GetCell(int row, int col)
    {
        return _cells
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
                var cell = _cells
                    .Where(c => c.Row == row && c.Column == col)
                    .FirstOrDefault();
                sb.Append(cell);
            }
            sb.Append("\n\n");
        }
        return sb.ToString();
    }

    private void RemovePossibleValuesFromRelatedCells(Cell cell)
    {
        _relatedCellsPredicates.ForEach(rcp => 
            _cells.Where(rc => rcp(rc, cell))
                  .ToList()
                  .ForEach(c => c.RemoveFromPossibleValues(cell.Value)));
    }
}
