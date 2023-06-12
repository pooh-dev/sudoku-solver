using SudokuSolver.Domain;

namespace SudokuSolver.Algorithms;

public class CellFinderResult
{
    public Cell? Cell { get; set; }
    public int Value { get; set; }

    public bool IsSuccess()
    {
        return Cell != null && Value != 0;
    }

    public static CellFinderResult Success(Cell cell, int value)
    {
        return new() { Cell = cell, Value = value };
    }

    public static CellFinderResult NotFound()
    {
        return new() { Cell = null, Value = 0 };
    }
}
