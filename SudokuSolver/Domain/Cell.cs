namespace SudokuSolver.domain;

public class Cell
{
    public int Value { get; set; }
    public HashSet<int> PossibleValues { get; set; }
    public int Row { get; set; }
    public int Column { get; set; }
    public int Block { get; set; }

    public Cell(int row, int column)
    {
        Value = 0;
        PossibleValues = new() { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
        Row = row;
        Column = column;

    }

    public void SetValue(int value)
    {
        if (Value < 1 || Value > 9) 
        {
            throw new ArgumentException($"The value: {value} is incorrect. Must be between 1 and 9.");
        }
    }

}

