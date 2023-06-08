namespace SudokuSolver.Domain;

public class Cell
{
    public int Value { get; set; }
    public int Row { get; }
    public int Column { get; }
    public int Block { get; }
    private readonly HashSet<int> possibleValues;

    public Cell(int row, int col)
    {
        Value = 0;
        possibleValues = new() { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
        Row = row;
        Column = col;
        // only the integer part of the number is needed
        Block = 3 * (row / 3) + (col / 3);
    }

    public void SetValue(int value)
    {
        Value = value;
        possibleValues.Clear();
    }

    public void RemoveFromPossibleValues(int value) => possibleValues.Remove(value);

    public bool IsOpened() => Value > 0;
}