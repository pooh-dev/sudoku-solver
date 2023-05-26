namespace SudokuSolver.Domain;

public class Cell
{
    public int Value { get; set; }
    public HashSet<int> PossibleValues { get; set; }
    public int Row { get; set; }
    public int Column { get; set; }
    public int Block { get; set; }

    public Cell(int row, int col)
    {
        Value = 0;
        PossibleValues = new() { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
        Row = row;
        Column = col;
        // only the integer part of the number is needed
        Block = 3 * (row / 3) + (col / 3);
    }

    public void SetValue(int value)
    {
        Value = value;
        PossibleValues.Clear();
    }

    public void RemoveFromPossibleValues(int value)
    {
        PossibleValues.Remove(value);
    }
}