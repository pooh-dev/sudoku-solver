using System.Text;

namespace SudokuSolver.Domain;

public class Field
{
    private readonly HashSet<Cell> _cells;

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
}
