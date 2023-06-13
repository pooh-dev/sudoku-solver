using SudokuSolver.Algorithms;
using SudokuSolver.Domain;

namespace SudokuSolver;

public class Solver
{
    private readonly Field _field;
    private readonly List<ICellFinder> _cellFinderAlgorithms = new()
    {
        new CellFinderBySinglePossibleValue(),
        new CellFinderByUniquePossibleValue()
    };

    public Solver(Field field)
    {
        _field = field;
    }
}
