using System.ComponentModel.DataAnnotations;

namespace SudokuSolver.Domain;

public class Cell
{
    [Range(1, 9)]
    public int Value { get; set; }
    [Range(0, 8)]
    public int Row { get; }
    [Range(0, 8)]
    public int Column { get; }
    [Range(0, 8)]
    public int Block { get; }
    public HashSet<int> PossibleValues { get; }

    public Cell(int row, int col)
    {
        Value = 0;        
        Row = row;
        Column = col;
        // only the integer part of the number is needed
        Block = 3 * (row / 3) + (col / 3);
        PossibleValues = new() { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
    }

    public void SetValue(int value)
    {
        if (IsOpened())
        {
            throw new ArgumentException($"The Cell({Row},{Column}) has alredy opened and has a value: {Value}. " +
                $"Trying to set another value: {value}.");
        }

        Value = value;
        PossibleValues.Clear();
    }

    public void RemoveFromPossibleValues(int value) => PossibleValues.Remove(value);

    public bool IsOpened() => Value > 0;

    public override string? ToString()
    {
        return $"({Row},{Column},{Block})=>{Value} [{string.Join(',', PossibleValues)}] ";
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