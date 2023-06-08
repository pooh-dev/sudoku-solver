namespace SudokuSolver.Domain;

public class Cell
{
    public int Value { get; set; }
    public int Row { get; }
    public int Column { get; }
    public int Block { get; }
    private readonly HashSet<int> _possibleValues;

    public Cell(int row, int col)
    {
        Value = 0;
        _possibleValues = new() { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
        Row = row;
        Column = col;
        // only the integer part of the number is needed
        Block = 3 * (row / 3) + (col / 3);
    }

    public void SetValue(int value)
    {
        Value = value;
        _possibleValues.Clear();
    }

    public void RemoveFromPossibleValues(int value) => _possibleValues.Remove(value);

    public bool IsOpened() => Value > 0;

    public override string? ToString()
    {
        return $"({Row},{Column},{Block})=>{Value} ";
    }

    public override bool Equals(object? obj)
    {
        return obj is Cell other &&
               Value == other.Value &&
               Row == other.Row &&
               Column == other.Column &&
               Block == other.Block;
    }

    public override int GetHashCode()
    {
        return HashCode.Combine(Value, Row, Column, Block);
    }
}