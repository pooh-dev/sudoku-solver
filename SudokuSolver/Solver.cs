using SudokuSolver.Domain;
using static System.Linq.Enumerable;

namespace SudokuSolver;

public class Solver
{
    public Field Field { get; }

    public Solver()
    {
        Field = new Field();
    }

    public void InitField(List<string> rows)
    {
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
                    Field.SetCellValue(row, col, initValue);
                }
            }
        }
    }
}
