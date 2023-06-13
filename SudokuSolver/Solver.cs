using SudokuSolver.Algorithms;
using SudokuSolver.Domain;

namespace SudokuSolver;

public class Solver
{
    private readonly List<ICellFinder> _cellFinders = new()
    {
        new CellFinderBySinglePossibleValue(),
        new CellFinderByUniquePossibleValue()
    };

    public Field Solve(Field field)
    {
        bool isAnyCellOpen;
        do
        {
            isAnyCellOpen = false;
            foreach (var cellFinder in _cellFinders)
            {
                isAnyCellOpen |= OpenCellsUsing(cellFinder, field);
            }
        } while (isAnyCellOpen);

        if (field.AreAllCellsOpen())
        {
            return field;
        }

        // solution is not found
        // TODO: add logic to suggest a possible solution 
        throw new NotImplementedException();
    }

    private bool OpenCellsUsing(ICellFinder cellFinder, Field field)
    {
        var isAnyCellOpen = false;

        CellFinderResult cellFinderResult;
        do
        {
            cellFinderResult = cellFinder.Find(field.Cells);
            if (cellFinderResult.IsCellFound())
            {
                field.SetCellValue(cellFinderResult);
                isAnyCellOpen = true;
            }

        } while (cellFinderResult.IsCellFound());

        return isAnyCellOpen;
    }
}
